package com.t1t.t1c;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.remoteloading.RemoteLoadingContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.ModuleConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.*;
import com.t1t.t1c.ds.*;
import com.t1t.t1c.exceptions.DsClientException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.ocv.OcvClient;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * <p>
 * The T1C client is responsible to provide the client interfaces for:
 * <ul>
 * <li>T1C-DS: Distribution server</li>
 * <li>OCV-API</li>
 * <li>T1C-GCL-Core Interface</li>
 * <li>T1C-GCL-Admin Interface</li>
 * <li>T1C-Generic Interface</li>
 * <li>T1C-Container interfaces</li>
 * </ul>
 * <p>
 * The T1C client provides a connection factory object instance to be shared by all underlying containers.
 */
public class T1cClient implements IT1cClient {
    private static final Logger log = LoggerFactory.getLogger(T1cClient.class);
    private ConnectionFactory connFactory;

    /*Constructors*/
    public T1cClient(LibConfig config) {
        init(config, null);
    }

    public T1cClient(Path toConfigurationFile) {
        init(null, toConfigurationFile);
    }

    //TODO implement pin constraint in safe way
    private static String getPin(String... pin) {
        return (pin == null || pin.length == 0) ? "" : pin[0];
    }

    /**
     * Initialisation of the T1C Java client
     *
     * @param config
     * @param toConfigurationFile
     */
    private void init(LibConfig config, Path toConfigurationFile) {
        // Configure
        T1cConfigParser clientConfig = null;
        if (config != null) {
            clientConfig = new T1cConfigParser(config);
        } else if (toConfigurationFile != null) {
            clientConfig = new T1cConfigParser(toConfigurationFile);
        }
        if ((clientConfig == null || clientConfig.getAppConfig() == null) && config == null) {
            throw ExceptionFactory.initializationException("Could not initialize config");
        }
        LibConfig validatedConfig = clientConfig.getAppConfig();
        // Instantiate connections
        connFactory = new ConnectionFactory(validatedConfig);
        // Set citrix from core info
        GclInfo gclInfo = getCore().getInfo();
        validatedConfig.setCitrix(gclInfo.getCitrix());
        validatedConfig.setConsentRequired(gclInfo.getConsent());

        connFactory.setConfig(new T1cConfigParser(validatedConfig).getAppConfig());

        // Set core, ds and ocv client

        // Set generic service
        //this.genericService = new GenericService(); //TODO implement generic service

        // Initialize public key for instance and register towards DS
        if (StringUtils.isEmpty(config.getApiKey())) return;
        try {
            initSecurityContext();
            registerAndActivate(gclInfo);
        } catch (GclCoreException ex) {
            throw ExceptionFactory.initializationException(ex.getMessage());
        }
    }

    /**
     * Reset containers
     *
     * @return
     */


    public String exchangeApiKeyForToken() {
        String jwt = null;
        try {
            jwt = getDsClient().getJWT();
        } catch (DsClientException ex) {
            log.error("Exception happened during JWT exchange: ", ex);
        }
        if (StringUtils.isNotEmpty(jwt)) {
            connFactory.getConfig().setGclJwt(jwt);
        }
        return jwt;
    }

    /**
     * Initialize the security context by setting the GCL's public key if necessary
     */
    private void initSecurityContext() {
        try {
            try {
                getCore().getPubKey();
            } catch (GclCoreException ex) {
                if (ex.getCause() instanceof RestException) {
                    GclError error = ((RestException) ex.getCause()).getGclError();
                    if (error != null && error.getCode() == 201) {
                        String publicKey = getDsClient().getPublicKey();
                        if (!getCore().setPubKey(publicKey)) {
                            throw ExceptionFactory.initializationException("Could not set GCL public key");
                        }
                    }
                }
            }
        } catch (GclCoreException ex) {
            log.error("Error initiallizing security context: ", ex);
            throw ExceptionFactory.initializationException(ex.getMessage());
        }
    }

    /**
     * Register and activate the GCL with the distribution server if necessary
     */
    private void registerAndActivate(GclInfo gclInfo) {
        PlatformInfo platformInfo = getCore().getPlatformInfo();
        LibConfig config = connFactory.getConfig();
        config.setTokenCompatible(isTokenCompatible(gclInfo.getVersion()));
        connFactory.setConfig(config);

        DsDeviceRegistrationRequest registration = new DsDeviceRegistrationRequest()
                .withActivated(gclInfo.getActivated())
                .withManaged(gclInfo.getManaged())
                .withDesktopApplication(new DsDesktopApplication()
                        .withVersion(platformInfo.getJava().getVersion())
                        .withName("Java"))
                .withOs(new DsOs()
                        .withName(platformInfo.getOs().getName())
                        .withVersion(platformInfo.getOs().getVersion())
                        .withArchitecture(platformInfo.getOs().getArchitecture()))
                .withUuid(gclInfo.getUid())
                .withVersion(gclInfo.getVersion());

        if (!gclInfo.getActivated()) {
            String token = getDsClient().register(gclInfo.getUid(), registration);
            if (StringUtils.isNotBlank(token)) {
                config.setGclJwt(token);
                connFactory.setConfig(config);
            }
            boolean activated = getCore().activate();
            gclInfo.setActivated(activated);
            registration.setActivated(activated);
            if (activated) {
                getDsClient().register(gclInfo.getUid(), registration);
            }

        } else if (!gclInfo.getManaged()) {
            getDsClient().register(gclInfo.getUid(), registration);
        }
    }

    /**
     * Determines if the GCL version is token compatible
     *
     * @return
     */
    private boolean isTokenCompatible(String gclVersion) {
        if (StringUtils.isNotBlank(gclVersion)) {
            String version;
            if (gclVersion.contains("-")) {
                version = gclVersion.substring(gclVersion.indexOf("-") + 1);
            } else version = gclVersion;
            return version.compareToIgnoreCase("1.4.0") > 0;
        }
        return false;
    }

    public String refreshJwt() {
        String jwt = null;
        try {
            jwt = (StringUtils.isNotEmpty(connFactory.getConfig().getGclJwt())) ? getDsClient().refreshJWT(connFactory.getConfig().getGclJwt()) : getDsClient().getJWT();
        } catch (DsClientException ex) {
            log.error("Error happened during JWT refresh: ", ex);
        }
        if (StringUtils.isNotEmpty(jwt)) {
            connFactory.getConfig().setGclJwt(jwt);
        }
        return jwt;
    }

    @Override
    public ICore getCore() {
        return new Core(connFactory.getGclRestClient(), connFactory.getGclAdminRestClient(), connFactory.getGclCitrixRestClient(), connFactory.getConfig());
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return connFactory;
    }

    @Override
    public IDsClient getDsClient() {
        return new DsClient(connFactory.getDsRestClient(), connFactory.getConfig());
    }

    @Override
    public IOcvClient getOcvClient() {
        return new OcvClient(connFactory.getOcvRestClient());
    }

    @Override
    public BeIdContainer getBeIdContainer(GclReader reader) {
        return new BeIdContainer(connFactory.getConfig(), reader, connFactory.getGclBeIdRestClient());
    }

    @Override
    public LuxIdContainer getLuxIdContainer(GclReader reader, String pin) {
        return new LuxIdContainer(connFactory.getConfig(), reader, connFactory.getGclLuxIdRestClient(), getPin(pin));
    }

    @Override
    public LuxTrustContainer getLuxTrustContainer(GclReader reader) {
        return new LuxTrustContainer(connFactory.getConfig(), reader, connFactory.getGclLuxTrustRestClient());
    }

    @Override
    public DnieContainer getDnieContainer(GclReader reader) {
        return new DnieContainer(connFactory.getConfig(), reader, connFactory.getGclDniRestClient());
    }

    @Override
    public EmvContainer getEmvContainer(GclReader reader) {
        return new EmvContainer(connFactory.getConfig(), reader, connFactory.getGclEmvRestClient());
    }

    @Override
    public MobibContainer getMobibContainer(GclReader reader) {
        return new MobibContainer(connFactory.getConfig(), reader, connFactory.getGclMobibRestClient());
    }

    @Override
    public OcraContainer getOcraContainer(GclReader reader) {
        return new OcraContainer(connFactory.getConfig(), reader, connFactory.getGclOcraRestClient());
    }

    @Override
    public AventraContainer getAventraContainer(GclReader reader) {
        return new AventraContainer(connFactory.getConfig(), reader, connFactory.getGclAventraRestClient());
    }

    @Override
    public OberthurContainer getOberthurContainer(GclReader reader) {
        return new OberthurContainer(connFactory.getConfig(), reader, connFactory.getGclOberthurRestClient());
    }

    @Override
    public PivContainer getPivContainer(GclReader reader, String pin) {
        return new PivContainer(connFactory.getConfig(), reader, connFactory.getGclPivRestClient(), pin);
    }

    @Override
    public PtEIdContainer getPtIdContainer(GclReader reader) {
        return new PtEIdContainer(connFactory.getConfig(), reader, connFactory.getGclPtRestClient());
    }

    @Override
    public SafeNetContainer getSafeNetContainer(GclReader reader) {
        return getSafeNetContainer(reader, new ModuleConfiguration());
    }

    @Override
    public SafeNetContainer getSafeNetContainer(GclReader reader, ModuleConfiguration configuration) {
        return new SafeNetContainer(connFactory.getConfig(), reader, connFactory.getGclSafenetRestClient(), configuration);
    }

    @Override
    public RemoteLoadingContainer getRemoteLoadingContainer(GclReader reader) {
        return new RemoteLoadingContainer(connFactory.getConfig(), reader, connFactory.getGclRemoteLoadingRestClient());
    }

    @Override
    public ReaderApiContainer getReaderApiContainer() {
        return new ReaderApiContainer();
    }

    @Override
    public IGenericContainer getGenericContainer(GclReader reader, String... pin) {
        ContainerType type = ContainerUtil.determineContainer(reader.getCard());
        IGenericContainer container;
        switch (type) {
            case AVENTRA:
                container = getAventraContainer(reader);
                break;
            case BEID:
                container = getBeIdContainer(reader);
                break;
            case DNIE:
                container = getDnieContainer(reader);
                break;
            case EMV:
                container = getEmvContainer(reader);
                break;
            case LUXID:
                String luxPinToUse = getPin(pin);
                Preconditions.checkArgument(StringUtils.isNotEmpty(luxPinToUse), "Cannot instantiate generic container for this reader without PIN");
                container = getLuxIdContainer(reader, luxPinToUse);
                break;
            case LUXTRUST:
                container = getLuxTrustContainer(reader);
                break;
            case MOBIB:
                container = getMobibContainer(reader);
                break;
            case OBERTHUR:
                container = getOberthurContainer(reader);
                break;
            case OCRA:
                container = getOcraContainer(reader);
                break;
            case PIV:
                String pivPinToUse = getPin(pin);
                Preconditions.checkArgument(StringUtils.isNotEmpty(pivPinToUse), "Cannot instantiate generic container for this reader without PIN");
                container = getPivContainer(reader, pivPinToUse);
                break;
            case PT:
                container = getPtIdContainer(reader);
                break;
            case SAFENET:
                container = getSafeNetContainer(reader, new ModuleConfiguration());
                break;
            default:
                throw ExceptionFactory.genericContainerException("No generic container available for this reader");
        }
        return container;
    }

    @Override
    public String getDownloadLink() {
        return getDsClient().getDownloadLink();
    }

    @Override
    public List<GclReader> getAuthenticateCapableReaders() {
        return getCore().getAuthenticationCapableReaders();
    }

    @Override
    public List<GclReader> getSignCapableReaders() {
        return getCore().getSignCapableReaders();
    }

    @Override
    public List<GclReader> getPinVerificationCapableReaders() {
        return getCore().getPinVerificationCapableReaders();
    }

}

