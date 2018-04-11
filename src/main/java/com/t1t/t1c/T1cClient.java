package com.t1t.t1c;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.ModuleConfiguration;
import com.t1t.t1c.containers.smartcards.pkcs11.Pkcs11Container;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.*;
import com.t1t.t1c.ds.*;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.ocv.OcvClient;
import com.t1t.t1c.utils.ContainerUtil;
import com.t1t.t1c.utils.PinUtil;
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
    private T1cConfigParser configParser;
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
        this.configParser = null;
        if (config != null) {
            configParser = new T1cConfigParser(config);
        } else if (toConfigurationFile != null) {
            configParser = new T1cConfigParser(toConfigurationFile);
        }
        if ((configParser == null || configParser.getAppConfig() == null) && config == null) {
            throw ExceptionFactory.initializationException("Could not initialize config");
        }
        LibConfig validatedConfig = configParser.getAppConfig();
        // Instantiate connections
        connFactory = new ConnectionFactory(validatedConfig);
        // Get Core info, enrich config with core info and reset connection
        GclInfo gclInfo = getCore().getInfo();
        // Check that the core version is compatible with the Java library
        if (gclInfo.getVersion().compareToIgnoreCase("2.0.0") < 0) {
            throw ExceptionFactory.initializationException("GCL version not compatible with library. Upgrade the GCL to newer version");
        }
        connFactory.setConfig(configParser.parseConfig(validatedConfig, gclInfo));

        // Attempt to register or sync device
        initializeDevice(gclInfo);
    }

    /**
     * Register or sync the device with the Distribution Service
     *
     * @param currentInfo
     */
    private void initializeDevice(final GclInfo currentInfo) {
        GclInfo info = currentInfo;
        // Set the device's public key in the PIN util for encryption
        PinUtil.setDevicePubKey(getCore().getDevicePubKey());
        if (canRegisterOrSync()) {
            // Get the device's public key
            String devicePublicKey = getCore().getDevicePubKey();

            // If the device isn't managed and isn't activated, register it first and activate
            if (!info.getManaged() && !info.getActivated()) {
                DsSyncResponseDto registrationResponse = getDsClient().register(info.getUid(), createRegistrationOrSyncRequest(info, devicePublicKey));
                connFactory.setConfig(this.configParser.parseConfig(connFactory.getConfig(), registrationResponse));
                verifyDsPublicKey(info.getUid());
                log.info("Activated Core: {}", getCore().activate());
                info = getCore().getInfo();
            }
            DsSyncResponseDto syncResponse = null;
            // If the GCL is managed package or the managed sync is set to true, sync the device
            if (!info.getManaged() || connFactory.getConfig().isSyncManaged()) {
                try {
                    syncResponse = getDsClient().sync(info.getUid(), createRegistrationOrSyncRequest(info, devicePublicKey));
                    // Reset the connections with the newly obtained DS token
                    connFactory.setConfig(this.configParser.parseConfig(connFactory.getConfig(), syncResponse));
                } catch (Exception ex) {
                    // If the package is managed, ignore the error: we made an attempt to sync, that is enough.
                    if (!info.getManaged()) {
                        throw ExceptionFactory.initializationException("Could not initialize the GCL: ", ex);
                    }
                }
            }

            // If the GCL isn't managed, verify the DS public key and sync container/CORS whitelist
            if (!info.getManaged()) {
                verifyDsPublicKey(info.getUid());
                // Load the ATR list
                log.info("Loaded ATR list: {}", getCore().loadAtrList(syncResponse.getAtrList()));
                log.info("Loaded Container info: {}", getCore().loadContainers(syncResponse.getContainerResponses()));
                // Poll the GCL to check container download status
                info = getCore().pollContainerDownloadStatus(syncResponse.getContainerResponses());
                // Sync the new container states with the DS
                syncResponse = getDsClient().sync(info.getUid(), createRegistrationOrSyncRequest(info, devicePublicKey));
                connFactory.setConfig(this.configParser.parseConfig(connFactory.getConfig(), syncResponse));
            }
        }
    }

    private void verifyDsPublicKey(String deviceId) {
        // Check if the DS public key is set
        if (getCore().getDsPubKey() == null) {
            // Get the encrypted DS key for the device
            DsPublicKey dsPublicKey = getDsClient().getPublicKey(deviceId);
            log.info("Loaded DS Public key: {}", getCore().setDsPubKey(dsPublicKey.getEncryptedPublicKey(), dsPublicKey.getEncryptedAesKey()));
        }
    }

    /**
     * Determine if the client can sync or register. The client can attempt to register and/or sync if the configuration
     * has an URI for the Distribution Service and an API key
     *
     * @return boolean value
     */
    private boolean canRegisterOrSync() {
        return StringUtils.isNoneEmpty(connFactory.getConfig().getApiKey(), connFactory.getConfig().getDsUri());
    }

    private DsDeviceRegistrationRequest createRegistrationOrSyncRequest(GclInfo info, String devicePublicKey) {
        PlatformInfo platformInfo = getCore().getPlatformInfo();
        DsDeviceRegistrationRequest request = new DsDeviceRegistrationRequest()
                .withManaged(info.getManaged())
                .withActivated(info.getActivated())
                .withUuid(info.getUid())
                .withVersion(info.getVersion())
                .withDerEncodedPublicKey(devicePublicKey)
                .withOs(new DsOs()
                        .withName(platformInfo.getOs().getName())
                        .withArchitecture(platformInfo.getOs().getArchitecture())
                        .withVersion(platformInfo.getOs().getVersion()))
                .withDesktopApplication(new DsDesktopApplication()
                        .withVersion(platformInfo.getJava().getVersion())
                        .withName(DsDesktopApplication.Name.JAVA))
                .withClientInfo(new DsClientInfo()
                        .withType(DsClientInfo.Type.JAVA)
                        .withVersion(platformInfo.getJava().getSpecificationVersion()))
                .withContainerStates(info.getContainers())
                .withProxyDomain(connFactory.getConfig().getProxyDomain());
        return request;
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
        return new DsClient(connFactory.getDsRestClient());
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
    public Pkcs11Container getPkcs11Container(GclReader reader) {
        return getPkcs11Container(reader, new ModuleConfiguration());
    }

    @Override
    public Pkcs11Container getPkcs11Container(GclReader reader, ModuleConfiguration configuration) {
        return new Pkcs11Container(connFactory.getConfig(), reader, connFactory.getGclSafenetRestClient(), configuration);
    }

    @Override
    public ReaderApiContainer getReaderApiContainer(GclReader reader) {
        return new ReaderApiContainer(connFactory.getConfig(), reader, connFactory.getGclReaderApiRestClient());
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
            case PKCS11:
                container = getPkcs11Container(reader, new ModuleConfiguration());
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

