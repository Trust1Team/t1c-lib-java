package com.t1t.t1c;

import com.t1t.t1c.agent.Agent;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.gcl.GclService;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.DsDeviceRegistrationRequest;
import com.t1t.t1c.model.rest.GclStatus;
import com.t1t.t1c.model.rest.Java;
import com.t1t.t1c.model.rest.Os;
import com.t1t.t1c.ocv.IOcvClient;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1cClient {

    private LibConfig config;
    private Core core;

    public T1cClient(LibConfig config) {
        init(config, false);
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

        GclService.setConfig(config);

        this.core = new Core(config);

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
            String publicKey = GclService.getDsClient().getPubKey();
            if (!GclService.getGclAdminClient().setPublicKey(publicKey)) {
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
        GclService.setConfig(config);

        DsDeviceRegistrationRequest registration = new DsDeviceRegistrationRequest()
                .withActivated(gclInfo.getActivated())
                .withManaged(gclInfo.getManaged())
                .withJava(new Java()
                        .withVersion(platformInfo.getJava().getVersion())
                        .withSpecificationVersion(platformInfo.getJava().getSpecificationVersion()))
                .withOs(new Os()
                        .withName(platformInfo.getOs().getName())
                        .withVersion(platformInfo.getOs().getVersion())
                        .withArchitecture(platformInfo.getOs().getArchitecture()))
                .withUuid(gclInfo.getUid())
                .withVersion(gclInfo.getVersion());

        if (!gclInfo.getActivated()) {
            String token = GclService.getDsClient().register(gclInfo.getUid(), registration);
            if (StringUtils.isNotBlank(token)) {
                config = config.withJwt(token);
                GclService.setConfig(config);
            }
            boolean activated = core.activate();
            gclInfo.setActivated(activated);
            registration.setActivated(activated);
            if (activated) {
                GclService.getDsClient().sync(gclInfo.getUid(), registration);
            }

        } else if (!gclInfo.getManaged()) {
            GclService.getDsClient().sync(gclInfo.getUid(), registration);
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

    public Core getCore() {
        return this.core;
    }

    public LibConfig getConfig() {
        return config;
    }

    //TODO - implement citrix
    public Agent getAgent() {
        throw new UnsupportedOperationException();
    }

    public IDsClient getDsClient() {
        return GclService.getDsClient();
    }

    //TODO - implement OCV
    public IOcvClient getOcvClient() {
        throw new UnsupportedOperationException();
    }
}

