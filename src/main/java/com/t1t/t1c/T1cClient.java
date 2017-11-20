package com.t1t.t1c;

import com.t1t.t1c.agent.IAgent;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.readerapi.IReaderApiContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeidClient;
import com.t1t.t1c.containers.smartcards.eid.dni.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdClient;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.ISafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustClient;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.DsClientException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.ocv.IOcvClient;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.services.GenericService;
import com.t1t.t1c.services.IGenericService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1cClient implements IT1cClient {
    private static final Logger log = LoggerFactory.getLogger(T1cClient.class);
    private Core core;
    private IGenericService genericService;
    private ConnectionFactory connFactory;


    public T1cClient(LibConfig config) {
        init(config, null);
    }

    public T1cClient(Path toConfigurationFile) {
        init(null, toConfigurationFile);
    }

    @Override
    public Core getCore() {
        return this.core;
    }

    @Override
    public LibConfig getConfig() {
        return connFactory.getConfig();
    }

    @Override
    public IDsClient getDsClient() {
        return connFactory.getDsClient();
    }

    @Override
    public IOcvClient getOcvClient() {
        return connFactory.getOcvClient();
    }

    @Override
    public List<GenericContainer> getGenericContainer(String readerId) {
        //TODO
        return null;
    }

    @Override
    public BeIdContainer getBeIdContainer(String readerId) {
        return connFactory.getContainer(readerId, BeIdContainer.class, GclBeidClient.class);
    }

    @Override
    public LuxIdContainer getLuxIdContainer(String readerId, String pin) {
        return connFactory.getContainer(readerId, LuxIdContainer.class, GclLuxIdClient.class);
    }

    @Override
    public LuxTrustContainer getLuxTrustContainer(String readerId, String pin) {
        return connFactory.getContainer(readerId, LuxTrustContainer.class, GclLuxTrustClient.class);
    }

    @Override
    public String getDownloadLink() {
        return genericService.getDownloadLink();
    }

/*    @Override
    public AllData dumpData(String readerId, List<String> filterParameters) {
        return dumpData(readerId, null, filterParameters);
    }

    @Override
    public AllData dumpData(String readerId, String pin) {
        return genericService.dumpData(readerId, pin, null);
    }

    @Override
    public AllData dumpData(String readerId, String pin, List<String> filterParameters) {
        return genericService.dumpData(readerId, pin, filterParameters);
    }*/

    @Override
    public List<GclReader> getAuthenticateCapableReaders() {
        return genericService.getAuthenticationCapableReaders();
    }

    @Override
    public List<GclReader> getSignCapableReaders() {
        return genericService.getSignCapableReaders();
    }

    @Override
    public List<GclReader> getPinVerificationCapableReaders() {
        return genericService.getPinVerificationCapableReaders();
    }

    @Override
    public String exchangeApiKeyForToken() {
        String jwt = null;
        try {
            jwt = getDsClient().getJWT();
        } catch (DsClientException ex) {
            log.error("Exception happened during JWT exchange: ", ex);
        }
        if (StringUtils.isNotEmpty(jwt)) {
            LibConfig conf = getConfig();
            conf.setJwt(jwt);
            connFactory.setConfig(conf);
        }
        return jwt;
    }

    @Override
    public String refreshJwt() {
        LibConfig conf = getConfig();
        String jwt = null;
        try {
            if (StringUtils.isNotEmpty(conf.getJwt())) {
                jwt = getDsClient().refreshJWT(conf.getJwt());
            } else {
                jwt = getDsClient().getJWT();
            }
        } catch (DsClientException ex) {
            log.error("Error happened during JWT refresh: ", ex);
        }
        if (StringUtils.isNotEmpty(jwt)) {
            conf.setJwt(jwt);
            connFactory.setConfig(conf);
        }
        return jwt;
    }

    //
    // Initialization methods
    //

    private void init(LibConfig config, Path toConfigurationFile) {
        T1cConfigParser clientConfig = null;
        if (config != null) {
            clientConfig = new T1cConfigParser(config);
        } else if (toConfigurationFile != null) {
            clientConfig = new T1cConfigParser(toConfigurationFile);
        }
        if (clientConfig == null || clientConfig.getAppConfig() == null) {
            if (config == null) throw ExceptionFactory.initializationException("Could not initialize config");
        }

        connFactory = new ConnectionFactory(config);
        this.core = new Core(config);
        this.genericService = new GenericService();

        //initialize public key for instance and register towards DS
        if (StringUtils.isNotEmpty(config.getApiKey())) {
            initSecurityContext();
            registerAndActivate();
        }
    }

    /**
     * Initialize the security context by setting the GCL's public key if necessary
     */
    private void initSecurityContext() {
        if (StringUtils.isBlank(core.getPubKey())) {
            String publicKey = connFactory.getDsClient().getPublicKey();
            if (!connFactory.getGclAdminClient().setPublicKey(publicKey)) {
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
            String token = connFactory.getDsClient().register(gclInfo.getUid(), registration);
            if (StringUtils.isNotBlank(token)) {
                config.setJwt(token);
                connFactory.setConfig(config);
            }
            boolean activated = core.activate();
            gclInfo.setActivated(activated);
            registration.setActivated(activated);
            if (activated) {
                connFactory.getDsClient().sync(gclInfo.getUid(), registration);
            }

        } else if (!gclInfo.getManaged()) {
            connFactory.getDsClient().sync(gclInfo.getUid(), registration);
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

