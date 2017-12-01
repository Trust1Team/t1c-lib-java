package com.t1t.t1c;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.rest.RestServiceBuilder;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
@PowerMockIgnore("javax.net.ssl.*")
public abstract class AbstractTestClass {

    private static final Logger log = LoggerFactory.getLogger(AbstractTestClass.class);

    private LibConfig config;

    private T1cClient client;

    private MockWebServer server;

    @Before
    public void init() {
        server = new MockWebServer();
        LibConfig conf = new LibConfig();
        conf.setEnvironment(Environment.DEV);
        conf.setGclClientUri(server.url("/").toString());
        conf.setDsContextPath("/trust1team/gclds/v1/");
        conf.setOcvContextPath("/trust1team/ocv-api/v1");
        conf.setApiKey("7de3b216-ade2-4391-b2e2-86b80bac4d7d");
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);
        this.config = conf;
        server.enqueue(MockResponseFactory.getMockResponseForResource(200, "GclAdminCertificate"));
        server.enqueue(MockResponseFactory.getMockResponseForResource(200, "GclInfo"));
        server.enqueue(MockResponseFactory.getMockResponseForResource(200, "DsToken"));
        client = new T1cClient(conf);
    }

    protected LibConfig getConfig() {
        return config;
    }

    protected T1cClient getClient() {
        return client;
    }

    protected MockWebServer getServer() {
        return server;
    }

    protected Logger getLogger() {
        return log;
    }

    @After
    public void cleanUp() {
        client = null;
        config = null;
    }
}