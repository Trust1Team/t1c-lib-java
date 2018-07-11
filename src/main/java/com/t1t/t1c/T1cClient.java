package com.t1t.t1c;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.ContainerVersion;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.functional.readerapi.ReaderApiContainer;
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
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.T1cPublicKey;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.ocv.OcvClient;
import com.t1t.t1c.utils.ContainerUtil;
import com.t1t.t1c.utils.CryptUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
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

    private static final String CONTAINER_VERSION_FORMAT = "%s-%s";

    private T1cConfigParser configParser;
    private ConnectionFactory connFactory;
    private List<ContainerVersion> installedContainerVersions;
    private List<ContainerVersion> availableApplicationContainerVersions;

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

        // Initialize library
        initializeLibrary(validatedConfig);
    }

    /**
     * Initialize the library & device
     */
    private void initializeLibrary(LibConfig validatedConfig) {
        // Get Core info, enrich config with core info and reset connection
        GclInfo info = getCore().getInfo();
        connFactory.setConfig(configParser.parseConfig(validatedConfig, info));

        // Check that the core version is compatible with the Java library
        if (isV2Compatible(info)) {

            // Get the device's public key
            T1cPublicKey devicePublicKey = getCore().getDevicePubKey(true);
            // Set the device's public key in the PIN util for encryption
            CryptUtil.setDevicePublicKey(devicePublicKey);


            // Only attempt to sync if API key & DS URL are provided
            if (canRegisterOrSync()) {
                if (!info.getActivated()) {
                    info = doActivation(info, devicePublicKey.getDerEncoded());
                }
                info = doSync(info, devicePublicKey.getDerEncoded(), false);
            } else {
                log.info("Distribution service URI, API key or JWT not configured, registration/synchronisation/activation disabled");
            }
            setInstalledContainerVersions(info);
        } else {
            throw ExceptionFactory.incompatibleCoreVersionException(info.getVersion(), getDsClient().getDownloadLink());
        }
    }

    private void setInstalledContainerVersions(GclInfo info) {
        List<ContainerVersion> installedContainers = new ArrayList<>();
        for (GclContainerInfo containerInfo : info.getContainers()) {
            if (containerInfo.getStatus().equals(GclContainerStatus.INSTALLED)) {
                installedContainers.add(new ContainerVersion(ContainerType.valueOfId(containerInfo.getName()), containerInfo.getVersion()));
            }
        }
        this.installedContainerVersions = installedContainers;
    }

    private void setAvailableApplicationContainerVersions(List<DsContainerResponse> appContainers) {
        List<ContainerVersion> appContainerVersions = new ArrayList<>();
        for (DsContainerResponse appContainer : appContainers) {
            appContainerVersions.add(new ContainerVersion(appContainer.getId()));
        }
        this.availableApplicationContainerVersions = new ArrayList<>(CollectionUtils.intersection(installedContainerVersions, appContainerVersions));
    }

    private GclInfo doSync(final GclInfo currentInfo, final String devicePublicKey, final boolean isRetry) {
        GclInfo info = currentInfo;
        try {
            // Check if the DS public key is loaded into the Core
            setDsPublicKeyIfAbsent(info.getUid());
            // Sync the device with the DS
            DsSyncResponseDto syncResponse = getDsClient().sync(info.getUid(), createRegistrationOrSyncRequest(info, devicePublicKey));
            // Reset the connections with the newly obtained DS JWT and context token
            connFactory.setConfig(this.configParser.parseConfig(connFactory.getConfig(), syncResponse));
            // Load the ATR list
            log.info("Loaded ATR list: {}", getCore().loadAtrList(syncResponse.getAtrList()));
            log.info("Loaded Container info: {}", getCore().loadContainers(syncResponse.getContainerResponses()));
            // Poll the GCL to check container download status
            info = getCore().pollContainerDownloadStatus(syncResponse.getContainerResponses());
            // Sync the new container states with the DS
            syncResponse = getDsClient().sync(info.getUid(), createRegistrationOrSyncRequest(info, devicePublicKey));
            setAvailableApplicationContainerVersions(syncResponse.getContainerResponses());
            connFactory.setConfig(this.configParser.parseConfig(connFactory.getConfig(), syncResponse));
        } catch (Exception ex) {
            if (!isRetry) {
                doSync(currentInfo, devicePublicKey, true);
            }
        }
        return info;
    }

    private GclInfo doActivation(final GclInfo info, final String devicePubKey) {
        // Register the device and its public key with the Distribution service
        DsSyncResponseDto registrationResponse = getDsClient().register(info.getUid(), createRegistrationOrSyncRequest(info, devicePubKey));
        // Reset the connection to make use of the newly obtained JWT from the DS
        connFactory.setConfig(this.configParser.parseConfig(connFactory.getConfig(), registrationResponse));
        // Set the pub key
        setDsPublicKeyIfAbsent(info.getUid());
        // Activate the device
        log.info("Core activated: {}", getCore().activate());
        return getCore().getInfo();
    }

    private boolean isV2Compatible(GclInfo info) {
        String sanitized = info.getVersion().split("-")[0];
        return sanitized.compareToIgnoreCase("2.0.0") >= 0;
    }

    private void setDsPublicKeyIfAbsent(String deviceId) {
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
        return (StringUtils.isNotEmpty((connFactory.getConfig().getApiKey())) || StringUtils.isNotEmpty(connFactory.getConfig().getGwJwt())) && StringUtils.isNotEmpty(connFactory.getConfig().getDsUri());
    }

    private DsDeviceRegistrationRequest createRegistrationOrSyncRequest(GclInfo info, String devicePublicKey) {
        PlatformInfo platformInfo = getCore().getPlatformInfo();
        return new DsDeviceRegistrationRequest()
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
    public BeIdContainer getBeIdContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.BEID, version);
        return new BeIdContainer(connFactory.getConfig(), reader, version, connFactory.getGclBeIdRestClient());
    }

    @Override
    public LuxIdContainer getLuxIdContainer(GclReader reader, String version, GclPace pace) {
        containerAvailabilityCheck(ContainerType.LUXID, version);
        return new LuxIdContainer(connFactory.getConfig(), reader, version, connFactory.getGclLuxIdRestClient(), pace);
    }

    @Override
    public LuxTrustContainer getLuxTrustContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.LUXTRUST, version);
        return new LuxTrustContainer(connFactory.getConfig(), reader, version, connFactory.getGclLuxTrustRestClient());
    }

    @Override
    public DnieContainer getDnieContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.DNIE, version);
        return new DnieContainer(connFactory.getConfig(), reader, version, connFactory.getGclDniRestClient());
    }

    @Override
    public EmvContainer getEmvContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.EMV, version);
        return new EmvContainer(connFactory.getConfig(), reader, version, connFactory.getGclEmvRestClient());
    }

    @Override
    public MobibContainer getMobibContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.MOBIB, version);
        return new MobibContainer(connFactory.getConfig(), reader, version, connFactory.getGclMobibRestClient());
    }

    @Override
    public OcraContainer getOcraContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.OCRA, version);
        return new OcraContainer(connFactory.getConfig(), reader, version, connFactory.getGclOcraRestClient());
    }

    @Override
    public AventraContainer getAventraContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.AVENTRA, version);
        return new AventraContainer(connFactory.getConfig(), reader, version, connFactory.getGclAventraRestClient());
    }

    @Override
    public OberthurContainer getOberthurContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.OBERTHUR, version);
        return new OberthurContainer(connFactory.getConfig(), reader, version, connFactory.getGclOberthurRestClient());
    }

    @Override
    public PivContainer getPivContainer(GclReader reader, String version, String pin) {
        containerAvailabilityCheck(ContainerType.PIV, version);
        return new PivContainer(connFactory.getConfig(), reader, version, connFactory.getGclPivRestClient(), pin);
    }

    @Override
    public PtEIdContainer getPtIdContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.PT, version);
        return new PtEIdContainer(connFactory.getConfig(), reader, version, connFactory.getGclPtRestClient());
    }

    @Override
    public Pkcs11Container getPkcs11Container(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.PIV, version);
        return getPkcs11Container(reader, version, new ModuleConfiguration());
    }

    @Override
    public Pkcs11Container getPkcs11Container(GclReader reader, String version, ModuleConfiguration configuration) {
        containerAvailabilityCheck(ContainerType.PKCS11, version);
        return new Pkcs11Container(connFactory.getConfig(), reader, version, connFactory.getGclSafenetRestClient(), configuration);
    }

    @Override
    public ReaderApiContainer getReaderApiContainer(GclReader reader, String version) {
        containerAvailabilityCheck(ContainerType.READER_API, version);
        return new ReaderApiContainer(connFactory.getConfig(), reader, version, connFactory.getGclReaderApiRestClient());
    }

    @Override
    public IGenericContainer getGenericContainer(GclReader reader) {
        return getGenericContainer(reader, null);
    }

    @Override
    public IGenericContainer getGenericContainer(GclReader reader, GclPace pace) {
        ContainerType type = ContainerUtil.determineContainer(reader.getCard());
        IGenericContainer container;
        switch (type) {
            case AVENTRA:
                container = getAventraContainer(reader, null);
                break;
            case BEID:
                container = getBeIdContainer(reader, null);
                break;
            case DNIE:
                container = getDnieContainer(reader, null);
                break;
            case EMV:
                container = getEmvContainer(reader, null);
                break;
            case LUXID:
                container = getLuxIdContainer(reader, null, pace);
                break;
            case LUXTRUST:
                container = getLuxTrustContainer(reader, null);
                break;
            case MOBIB:
                container = getMobibContainer(reader, null);
                break;
            case OBERTHUR:
                container = getOberthurContainer(reader, null);
                break;
            case OCRA:
                container = getOcraContainer(reader, null);
                break;
            case PIV:
                container = getPivContainer(reader, null, pace.getPin());
                break;
            case PT:
                container = getPtIdContainer(reader, null);
                break;
            case PKCS11:
                container = getPkcs11Container(reader, null, new ModuleConfiguration());
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

    private void containerAvailabilityCheck(ContainerType type, String version) {
        if (!this.installedContainerVersions.contains(new ContainerVersion(type, version))) {
            throw ExceptionFactory.containerNotAvailableException(type);
        }
    }
}

