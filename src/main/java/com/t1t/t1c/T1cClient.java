package com.t1t.t1c;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;
import com.t1t.t1c.containers.GenericContainer;
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
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclStatus;
import com.t1t.t1c.core.ICore;
import com.t1t.t1c.ds.*;
import com.t1t.t1c.exceptions.DsClientException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.ocv.OcvClient;
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
    /*General clients*/
    private IDsClient dsClient;
    private IOcvClient ocvClient;
    /*Core and container clients*/
    private ICore core;

    /*Constructors*/
    public T1cClient(LibConfig config) {
        init(config, null);
    }
    public T1cClient(Path toConfigurationFile) {
        init(null, toConfigurationFile);
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
        if (clientConfig == null || clientConfig.getAppConfig() == null) {
            if (config == null) throw ExceptionFactory.initializationException("Could not initialize config");
        }

        // Instantiate connections
        connFactory = new ConnectionFactory(config);

        // Set core, ds and ocv client
        resetCore();
        resetDs();
        resetOcv();

        // Set generic service
        //this.genericService = new GenericService(); //TODO implement generic service

        // Initialize public key for instance and register towards DS
        if (StringUtils.isEmpty(config.getApiKey())) return;
        try {
            initSecurityContext();
            registerAndActivate();
        } catch (RestException re) {
            throw ExceptionFactory.initializationException(re.getMessage());
        }
    }

    /**
     * The core uses the gclRestClient and gclAdminRestClient.
     */
    private void resetCore() { this.core = new Core(connFactory.getGclRestClient(), connFactory.getGclAdminRestClient(), connFactory.getConfig()); }
    private void resetDs() { this.dsClient = new DsClient(connFactory.getDsRestClient(), connFactory.getConfig()); }
    private void resetOcv() { this.ocvClient = new OcvClient(connFactory.getOcvRestClient(), connFactory.getConfig()); }

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
            connFactory.getConfig().setJwt(jwt);
        }
        return jwt;
    }

    /**
     * Initialize the security context by setting the GCL's public key if necessary
     */
    private void initSecurityContext() {
        try {
            if (StringUtils.isBlank(core.getPubKey())) {
                String publicKey = dsClient.getPublicKey();
                if (!core.setPubKey(publicKey)) {
                    throw ExceptionFactory.initializationException("Could not set GCL public key");
                }
            }
        } catch (RestException re) {
            throw ExceptionFactory.initializationException(re.getMessage());
        }
    }

    /**
     * Register and activate the GCL with the distribution server if necessary
     */
    private void registerAndActivate() throws RestException {
        GclStatus gclInfo = core.getInfo();
        PlatformInfo platformInfo = core.getPlatformInfo();
        LibConfig config = connFactory.getConfig();
        config.setTokenCompatible(isTokenCompatible(gclInfo.getVersion()));
        connFactory.setConfig(config);

        DsDeviceRegistrationRequest registration = new DsDeviceRegistrationRequest()
                .withActivated(gclInfo.getActivated())
                .withManaged(gclInfo.getManaged())
                //TODO - Re-enable Java info once DS supports property
                //.withJava(new Java()
                //        .withVersion(platformInfo.getJava().getVersion())
                //        .withSpecificationVersion(platformInfo.getJava().getSpecificationVersion()))
                .withOs(new DsOs()
                        .withName(platformInfo.getOs().getName())
                        .withVersion(platformInfo.getOs().getVersion())
                        .withArchitecture(platformInfo.getOs().getArchitecture()))
                //TODO - Remove once DS no longer requires browser info
                .withBrowser(new DsBrowser().withName("NA").withVersion("NA"))
                .withUuid(gclInfo.getUid())
                .withVersion(gclInfo.getVersion());

        if (!gclInfo.getActivated()) {
            String token = dsClient.register(gclInfo.getUid(), registration);
            if (StringUtils.isNotBlank(token)) {
                config.setJwt(token);
                connFactory.setConfig(config);
            }
            boolean activated = core.activate();
            gclInfo.setActivated(activated);
            registration.setActivated(activated);
            if (activated) {
                dsClient.register(gclInfo.getUid(), registration);
            }

        } else if (!gclInfo.getManaged()) {
            dsClient.register(gclInfo.getUid(), registration);
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

/*    public GenericContainer getGenericContainer(String reader, String... pin) {
        return getGenericContainer(reader, ContainerUtil.determineContainer(gclClient.getReader(reader).getCard()), pin);
    }*/


/*    private GenericContainer getGenericContainer(String reader, ContainerType type, String... pin) {
        switch (type) {
            case BEID:
                return new BeIdContainer(reader, RestServiceBuilder.getContainerRestClient(config, GclBeidRestClient.class));
            case LUXID:
                return new LuxIdContainer(reader, RestServiceBuilder.getContainerRestClient(config, GclLuxIdRestClient.class), getPin(pin));
            case LUXTRUST:
                return new LuxTrustContainer(reader, RestServiceBuilder.getContainerRestClient(config, GclLuxTrustRestClient.class), getPin(pin));
            case DNIE:
                return new DnieContainer(reader, RestServiceBuilder.getContainerRestClient(config, GclDniRestClient.class));
            //TODO
*//*            case EMV:
                return getEmvContainer(reader);
            case PT:
                return getPtIdContainer(reader);
            case MOBIB:
                return getMobibContainer(reader);
            case OCRA:
                return getOcraContainer(reader);
            case AVENTRA:
                return getAventraContainer(reader);
            case OBERTHUR:
                return getOberthurContainer(reader);
            case PIV:
                return getPivContainer(reader);
            case READER_API:
                return getReaderContainer(reader);
            case SAFENET:
                return getSafeNetContainer(reader);*//*
            default:
                throw ExceptionFactory.unsupportedOperationException("No container for type found");
        }
    }*/

    //TODO implement pin constraint in safe way
    private static String getPin(String... pin) {
        return (pin.length == 0) ? "" : pin[0];
    }

    public String refreshJwt() {
        String jwt = null;
        try {
            jwt = (StringUtils.isNotEmpty(connFactory.getConfig().getJwt())) ? getDsClient().refreshJWT(connFactory.getConfig().getJwt()) : getDsClient().getJWT();
        } catch (DsClientException ex) {
            log.error("Error happened during JWT refresh: ", ex);
        }
        if (StringUtils.isNotEmpty(jwt)) {
            connFactory.getConfig().setJwt(jwt);
        }
        return jwt;
    }

    @Override
    public ICore getCore() { return core; }
    @Override
    public ConnectionFactory getConnectionFactory() { return connFactory; }
    @Override
    public IDsClient getDsClient() { return dsClient; }
    @Override
    public IOcvClient getOcvClient() { return ocvClient; }
    @Override
    public BeIdContainer getBeIdContainer(GclReader reader) { return new BeIdContainer(connFactory.getConfig(), reader,connFactory.getGclBeidRestClient()); }
    @Override
    public LuxIdContainer getLuxIdContainer(GclReader reader, String pin) { return new LuxIdContainer(connFactory.getConfig(), reader, connFactory.getGclLuxIdRestClient(), getPin(pin)); }
    @Override
    public LuxTrustContainer getLuxTrustContainer(GclReader reader, String pin) { return new LuxTrustContainer(connFactory.getConfig(), reader,connFactory.getGclLuxTrustRestClient(), getPin(pin)); }
    @Override
    public DnieContainer getDnieContainer(GclReader reader) { return new DnieContainer(connFactory.getConfig(), reader, connFactory.getGclDniRestClient()); }
    @Override
    public EmvContainer getEmvContainer(GclReader reader) { return new EmvContainer(connFactory.getConfig(), reader, connFactory.getGclEmvRestClient()); }

    @Override
    public MobibContainer getMobibContainer(GclReader reader) {
        return null;
    }

    @Override
    public OcraContainer getOcraContainer(GclReader reader) {
        return null;
    }

    @Override
    public AventraContainer getAventraContainer(GclReader reader) {
        return null;
    }

    @Override
    public OberthurContainer getOberthurContainer(GclReader reader) {
        return null;
    }

    @Override
    public PivContainer getPivContainer(GclReader reader) {
        return null;
    }

    @Override
    public PtEIdContainer getPtIdContainer(GclReader reader) {
        return null;
    }

    @Override
    public SafeNetContainer getSafeNetContainer(GclReader reader) {
        return null;
    }

    @Override
    public SafeNetContainer getSafeNetContainer(GclReader reader, SafeNetContainerConfiguration configuration) {
        return null;
    }

    @Override
    public RemoteLoadingContainer getRemoteLoadingContainer(GclReader reader) {
        return null;
    }

    @Override
    public ReaderApiContainer getReaderApiContainer() {
        return null;
    }

    @Override
    public GenericContainer getGenericContainer(GclReader reader) {
        return null; //TODO
    }

    @Override
    public String getDownloadLink() {
        return null;
    }

    @Override
    public List<GclReader> getAuthenticateCapableReaders() {
        return null;
    }

    @Override
    public List<GclReader> getSignCapableReaders() {
        return null;
    }

    @Override
    public List<GclReader> getPinVerificationCapableReaders() {
        return null;
    }

}

