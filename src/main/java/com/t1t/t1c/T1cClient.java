package com.t1t.t1c;

import com.t1t.t1c.agent.IAgent;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.readerapi.IReaderApiContainer;
import com.t1t.t1c.containers.remoteloading.belfius.IBelfiusContainer;
import com.t1t.t1c.containers.smartcards.eid.be.IBeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.esp.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.ISafenetContainer;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.services.GenericService;
import com.t1t.t1c.services.IGenericService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1cClient {

    private LibConfig config;
    private Core core;
    private IGenericService genericService;


    public T1cClient(LibConfig config) {
        init(config, false);
    }

    public Core getCore() {
        return this.core;
    }

    public LibConfig getConfig() {
        return config;
    }

    public IAgent getAgent() {
        return FactoryService.getAgent();
    }

    public IDsClient getDsClient() {
        return FactoryService.getDsClient();
    }

    public IOcvClient getOcvClient() {
        return FactoryService.getOcvClient();
    }

    public GenericContainer getGenericContainerFor(String readerId) {
        return FactoryService.getGenericContainer(readerId);
    }

    public IBeIdContainer getBeIdContainer(String readerId) {
        return FactoryService.getBeIdContainer(readerId);
    }

    public ILuxIdContainer getLuxIdContainer(String readerId, String pin) {
        return FactoryService.getLuxIdContainer(readerId, pin);
    }

    public ILuxTrustContainer getLuxTrustContainer(String readerId, String pin) {
        return FactoryService.getLuxTrustContainer(readerId, pin);
    }

    public IDnieContainer getDnieContainer(String readerId) {
        return FactoryService.getDnieContainer(readerId);
    }

    public IPtEIdContainer getPtIdContainer(String readerId) {
        return FactoryService.getPtIdContainer(readerId);
    }

    public IEmvContainer getEmvContainer(String readerId) {
        return FactoryService.getEmvContainer(readerId);
    }

    public IMobibContainer getMobibContainer(String readerId) {
        return FactoryService.getMobibContainer(readerId);
    }

    public IOcraContainer getOcraContainer(String readerId) {
        return FactoryService.getOcraContainer(readerId);
    }

    public IAventraContainer getAventraContainer(String readerId) {
        return FactoryService.getAventraContainer(readerId);
    }

    public IOberthurContainer getOberthurContainer(String readerId) {
        return FactoryService.getOberthurContainer(readerId);
    }

    public IPivContainer getPivContainer(String readerId) {
        return FactoryService.getPivContainer(readerId);
    }

    public ISafenetContainer getSafenetContainer(String readerId) {
        return FactoryService.getSafenetContainer(readerId);
    }

    public IReaderApiContainer getReaderContainer(String readerId) {
        return FactoryService.getReaderContainer(readerId);
    }

    public IBelfiusContainer getBelfiusContainer(String readerId) {
        return FactoryService.getBelfiusContainer(readerId);
    }

    public ContainerType getContainerFor(String readerId) {
        return genericService.getContainerTypeFor(readerId);
    }

    public String getDownloadLink() {
        return genericService.getDownloadLink();
    }

    public AllData dumpData(String readerId) {
        return genericService.dumpData(readerId, null, null);
    }

    public AllData dumpData(String readerId, List<String> filterParameters) {
        return dumpData(readerId, null, filterParameters);
    }

    public AllData dumpData(String readerId, String pin) {
        return genericService.dumpData(readerId, pin, null);
    }

    public AllData dumpData(String readerId, String pin, List<String> filterParameters) {
        return genericService.dumpData(readerId, pin, filterParameters);
    }

    public List<GclReader> readersCanAuthenticate() {
        return genericService.getAuthenticationCapableReaders();
    }

    public List<GclReader> readersCanSign() {
        return genericService.getSignCapableReaders();
    }

    public List<GclReader> readersCanVerifyPin() {
        return genericService.getPinVerificationCapableReaders();
    }

    public String authenticate(String readerId, GclAuthenticateOrSignData data, String... pin) {
        return genericService.authenticate(readerId, data, pin);
    }

    public String sign(String readerId, GclAuthenticateOrSignData data, String... pin) {
        return genericService.sign(readerId, data, pin);
    }

    public boolean verifyPin(String readerId, String... pin) {
        return genericService.verifyPin(readerId, pin);
    }

    //
    // Initialization methods
    //

    private void init(LibConfig config, boolean automatic) {
        T1cConfigParser clientConfig = new T1cConfigParser(config);
        if (clientConfig.getAppConfig() == null) {
            if (config == null) throw ExceptionFactory.initializationException("Could not initialize config");
        }
        this.config = config;

        FactoryService.setConfig(config);

        this.core = new Core(config);
        this.genericService = new GenericService();

        if (!automatic) {
            initSecurityContext();
            registerAndActivate();
        }

    }

    /**
     * Initialize the security context by setting the GCL's public key if necessary
     */
    private void initSecurityContext() {
        if (StringUtils.isBlank(core.getPubKey())) {
            String publicKey = FactoryService.getDsClient().getPubKey();
            if (!FactoryService.getGclAdminClient().setPublicKey(publicKey)) {
                throw ExceptionFactory.initializationException("Could not set GCL public key");
            }
        }
    }

    /**
     * Register and activate the GCL with the distribution server if necessary
     */
    private void registerAndActivate() {
        GclStatus gclInfo = core.getInfo();
        PlatformInfo platformInfo = core.getPlatformInfo();
        config.setTokenCompatible(isTokenCompatible(gclInfo.getVersion()));
        FactoryService.setConfig(config);

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
            String token = FactoryService.getDsClient().register(gclInfo.getUid(), registration);
            if (StringUtils.isNotBlank(token)) {
                config = config.withJwt(token);
                FactoryService.setConfig(config);
            }
            boolean activated = core.activate();
            gclInfo.setActivated(activated);
            registration.setActivated(activated);
            if (activated) {
                FactoryService.getDsClient().sync(gclInfo.getUid(), registration);
            }

        } else if (!gclInfo.getManaged()) {
            FactoryService.getDsClient().sync(gclInfo.getUid(), registration);
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
}

