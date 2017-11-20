package com.t1t.t1c;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.mock.*;
import com.t1t.t1c.rest.*;
import com.t1t.t1c.services.FactoryService;
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

import java.util.concurrent.TimeUnit;

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
    @Mock
    private OcvRestClient ocvRestClient;

    @Before
    public void init() {

        LibConfig conf = new LibConfig();
        conf.setEnvironment(Environment.DEV);
        conf.setGclClientUri("https://localhost:10443/v1/");
        conf.setDsContextPath("/trust1team/gclds/v1/");
        conf.setOcvContextPath("/trust1team/ocv-api/v1");
        conf.setApiKey("7de3b216-ade2-4391-b2e2-86b80bac4d7d");
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);
        this.config = conf;

        mockContainerRestClient();
        mockDsRestClient();
        mockGclRestClient();
        mockGclAdminRestClient();
        mockOcvRestClient();

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
        expect(RestServiceBuilder.getOcvRestClient(conf)).andReturn(ocvRestClient);
        expect(RestServiceBuilder.getOcvRestClient(conf)).andReturn(ocvRestClient);
        expect(RestServiceBuilder.getOcvRestClient(conf)).andReturn(ocvRestClient);
        expect(RestServiceBuilder.getOcvRestClient(conf)).andReturn(ocvRestClient);

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
        NetworkBehavior behavior = getNetworkBehavior();
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getGclClientUri() + "plugins/", config.getApiKey(), config.getJwt(), true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        containerRestClient = new MockContainerRestClient(mockRetrofit.create(ContainerRestClient.class));
    }

    private void mockDsRestClient() {
        NetworkBehavior behavior = getNetworkBehavior();
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getDsUri(), config.getApiKey(), null, true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        dsRestClient = new MockDsRestClient(mockRetrofit.create(DsRestClient.class));
    }

    private void mockGclAdminRestClient() {
        NetworkBehavior behavior = getNetworkBehavior();
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getGclClientUri(), null, config.getJwt(), true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        gclAdminRestClient = new MockGclRestAdminClient(mockRetrofit.create(GclAdminRestClient.class));
    }

    private void mockGclRestClient() {
        NetworkBehavior behavior = getNetworkBehavior();
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getGclClientUri(), null, null, true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        gclRestClient = new MockGclRestClient(mockRetrofit.create(GclRestClient.class));
    }

    private void mockOcvRestClient() {
        NetworkBehavior behavior = getNetworkBehavior();
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(config.getOcvUri(), config.getApiKey(), null, true);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        ocvRestClient = new MockOcvRestClient(mockRetrofit.create(OcvRestClient.class));
    }

    private NetworkBehavior getNetworkBehavior() {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        behavior.setDelay(0, TimeUnit.MILLISECONDS);
        behavior.setVariancePercent(0);
        return behavior;
    }

}