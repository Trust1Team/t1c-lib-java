package com.t1t.t1c;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.mock.MockContainerRestClient;
import com.t1t.t1c.mock.MockDsRestClient;
import com.t1t.t1c.mock.MockGclRestAdminClient;
import com.t1t.t1c.mock.MockGclRestClient;
import com.t1t.t1c.rest.*;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.replayAll;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestServiceBuilder.class, FactoryService.class})
@PowerMockIgnore("javax.net.ssl.*")
public abstract class AbstractTestClass {

    private LibConfig config;

    private T1cClient client;

    @Mock
    private ContainerRestClient containerRestClient;
    @Mock
    private DsRestClient dsRestClient;
    @Mock
    private GclAdminRestClient gclAdminRestClient;
    @Mock
    private GclRestClient gclRestClient;

    @Before
    public void init() {

        LibConfig conf = new LibConfig();
        conf.setVersion("0.0.1-SNAPSHOT");
        conf.setEnvironment(Environment.DEV);
        conf.setDsDownloadContextPath("/trust1team/gclds-file/v1");
        conf.setGatewayUri("https://accapim.t1t.be");
        conf.setGclClientUri("https://localhost:10443/v1/");
        conf.setDsContextPath("/trust1team/gclds/v1/");
        conf.setApiKey("7de3b216-ade2-4391-b2e2-86b80bac4d7d");
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);
        this.config = conf;

        mockContainerRestClient();
        mockDsRestClient();
        mockGclRestClient();
        mockGclAdminRestClient();

        PowerMock.mockStatic(RestServiceBuilder.class);

        expect(RestServiceBuilder.getContainerRestClient(conf)).andReturn(containerRestClient);
        expect(RestServiceBuilder.getContainerRestClient(conf)).andReturn(containerRestClient);
        expect(RestServiceBuilder.getContainerRestClient(conf)).andReturn(containerRestClient);
        expect(RestServiceBuilder.getContainerRestClient(conf)).andReturn(containerRestClient);
        expect(RestServiceBuilder.getDsRestClient(conf)).andReturn(dsRestClient);
        expect(RestServiceBuilder.getDsRestClient(conf)).andReturn(dsRestClient);
        expect(RestServiceBuilder.getDsRestClient(conf)).andReturn(dsRestClient);
        expect(RestServiceBuilder.getDsRestClient(conf)).andReturn(dsRestClient);
        expect(RestServiceBuilder.getGclAdminRestClient(conf)).andReturn(gclAdminRestClient);
        expect(RestServiceBuilder.getGclAdminRestClient(conf)).andReturn(gclAdminRestClient);
        expect(RestServiceBuilder.getGclAdminRestClient(conf)).andReturn(gclAdminRestClient);
        expect(RestServiceBuilder.getGclAdminRestClient(conf)).andReturn(gclAdminRestClient);
        expect(RestServiceBuilder.getGclRestClient(conf)).andReturn(gclRestClient);
        expect(RestServiceBuilder.getGclRestClient(conf)).andReturn(gclRestClient);
        expect(RestServiceBuilder.getGclRestClient(conf)).andReturn(gclRestClient);
        expect(RestServiceBuilder.getGclRestClient(conf)).andReturn(gclRestClient);

        replayAll();

        client = new T1cClient(conf);
    }

    protected LibConfig getConfig() {
        return config;
    }

    protected T1cClient getClient() {
        return client;
    }

    @After
    public void cleanUp() {
        client = null;
        config = null;
    }

    private void mockContainerRestClient() {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getGclClientUri() + "plugins/", config.getApiKey(), config.getJwt(), true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        containerRestClient = new MockContainerRestClient(mockRetrofit.create(ContainerRestClient.class));
    }

    private void mockDsRestClient() {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getDsUri(), config.getApiKey(), config.getJwt(), true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        dsRestClient = new MockDsRestClient(mockRetrofit.create(DsRestClient.class));
    }

    private void mockGclAdminRestClient() {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getGclClientUri(), config.getApiKey(), config.getJwt(), true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        gclAdminRestClient = new MockGclRestAdminClient(mockRetrofit.create(GclAdminRestClient.class));
    }

    private void mockGclRestClient() {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getGclClientUri(), config.getApiKey(), config.getJwt(), true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        gclRestClient = new MockGclRestClient(mockRetrofit.create(GclRestClient.class));
    }

}