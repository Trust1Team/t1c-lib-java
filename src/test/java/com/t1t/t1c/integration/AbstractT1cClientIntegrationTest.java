package com.t1t.t1c.integration;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public abstract class AbstractT1cClientIntegrationTest {

    /*private T1cClient client;

    protected T1cClient getClient() {
        return client;
    }

    @Before
    public void init() {
        LibConfig conf = new LibConfig();
        conf.setVersion("0.0.1-SNAPSHOT");
        conf.setEnvironment(Environment.DEV);
        conf.setDsDownloadContextPath("/trust1team/gclds-file/v1");
        conf.setGatewayUri("https://accapim.t1t.be");
        conf.setGclClientUri("https://localhost:10443/v1/");
        conf.setDsContextPath("/trust1team/gclds/v1");
        conf.setApiKey("7de3b216-ade2-4391-b2e2-86b80bac4d7d");
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);
        this.client = new T1cClient(conf);
    }

    @After
    public void cleanUp() {
        this.client = null;
    }*/
}