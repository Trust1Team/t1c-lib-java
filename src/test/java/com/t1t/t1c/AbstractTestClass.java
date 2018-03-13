package com.t1t.t1c;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.remoteloading.GclRemoteLoadingRestClient;
import com.t1t.t1c.containers.remoteloading.MockGclRemoteLoadingRestClient;
import com.t1t.t1c.containers.smartcards.eid.be.GclBeIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.be.MockGclBeIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDniRestClient;
import com.t1t.t1c.containers.smartcards.eid.dni.MockGclDnieRestClient;
import com.t1t.t1c.containers.smartcards.eid.lux.GclLuxIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.lux.MockGclLuxIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.pt.GclPtIdRestClient;
import com.t1t.t1c.containers.smartcards.eid.pt.MockGclPtIdRestClient;
import com.t1t.t1c.containers.smartcards.emv.GclEmvRestClient;
import com.t1t.t1c.containers.smartcards.emv.MockGclEmvRestClient;
import com.t1t.t1c.containers.smartcards.mobib.GclMobibRestClient;
import com.t1t.t1c.containers.smartcards.mobib.MockGclMobibRestClient;
import com.t1t.t1c.containers.smartcards.ocra.GclOcraRestClient;
import com.t1t.t1c.containers.smartcards.ocra.MockGclOcraRestClient;
import com.t1t.t1c.containers.smartcards.piv.GclPivRestClient;
import com.t1t.t1c.containers.smartcards.piv.MockGclPivRestClient;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.GclSafeNetRestClient;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.MockGclSafeNetRestClient;
import com.t1t.t1c.containers.smartcards.pki.aventra.GclAventraRestClient;
import com.t1t.t1c.containers.smartcards.pki.aventra.MockGclAventraRestClient;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.GclLuxTrustRestClient;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.MockGclLuxTrustRestClient;
import com.t1t.t1c.containers.smartcards.pki.oberthur.GclOberthurRestClient;
import com.t1t.t1c.containers.smartcards.pki.oberthur.MockGclOberthurRestClient;
import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclCitrixRestClient;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.ds.DsRestClient;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.mock.*;
import com.t1t.t1c.ocv.OcvRestClient;
import com.t1t.t1c.rest.MockRestServiceBuilder;
import com.t1t.t1c.rest.RestServiceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
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
@PrepareForTest({RestServiceBuilder.class, ConnectionFactory.class})
@PowerMockIgnore("javax.net.ssl.*")
public abstract class AbstractTestClass {

    private LibConfig config;

    private T1cClient client;

    @Mock
    private DsRestClient dsRestClient;
    @Mock
    private GclAdminRestClient gclAdminRestClient;
    @Mock
    private GclRestClient gclRestClient;
    @Mock
    private OcvRestClient ocvRestClient;
    @Mock
    private GclBeIdRestClient gclBeIdRestClient;
    @Mock
    private GclDniRestClient gclDniRestClient;
    @Mock
    private GclLuxIdRestClient gclLuxIdRestClient;
    @Mock
    private GclPtIdRestClient gclPtIdRestClient;
    @Mock
    private GclEmvRestClient gclEmvRestClient;
    @Mock
    private GclMobibRestClient gclMobibRestClient;
    @Mock
    private GclOcraRestClient gclOcraRestClient;
    @Mock
    private GclPivRestClient gclPivRestClient;
    @Mock
    private GclSafeNetRestClient gclSafeNetRestClient;
    @Mock
    private GclAventraRestClient gclAventraRestClient;
    @Mock
    private GclLuxTrustRestClient gclLuxTrustRestClient;
    @Mock
    private GclOberthurRestClient gclOberthurRestClient;
    @Mock
    private GclRemoteLoadingRestClient gclRemoteLoadingRestClient;
    @Mock
    private GclCitrixRestClient gclCitrixRestClient;

    @Before
    public void init() {
        setMockConfig();
        mockRestClients();

        PowerMock.mockStatic(RestServiceBuilder.class);

        setMocksBeforeConfigReset();
        setMocksBeforeConfigReset();
        setMocksBeforeConfigReset();

        replayAll();

        client = new T1cClient(config);
    }

    protected LibConfig getConfig() {
        return config;
    }

    protected T1cClient getClient() {
        return client;
    }

    protected GclSafeNetRestClient getSafeNetRestClient() {
        return gclSafeNetRestClient;
    }

    @After
    public void cleanUp() {
        client = null;
        config = null;
    }

    private void setMockConfig() {
        LibConfig conf = new LibConfig();
        conf.setEnvironment(Environment.DEV);

        conf.setGclClientUri("https://localhost:10443/v1/");
        conf.setDsUri("https://accapim.t1t.be/trust1team/gclds/v1/");
        conf.setOcvUri("https://accapim.t1t.be/trust1team/ocv-api/v1/");
        conf.setApiKey("7de3b216-ade2-4391-b2e2-86b80bac4d7d");
        conf.setSessionTimeout(40);
        conf.setDefaultPollingIntervalInSeconds(5);
        conf.setDefaultPollingTimeoutInSeconds(10);
        conf.setDefaultConsentDuration(1);
        conf.setDefaultConsentTimeout(30);
        conf.setHardwarePinPadForced(false);
        this.config = conf;
    }

    private <T> BehaviorDelegate<T> mockRestClient(Class<T> clazz, String baseUrl, String apikey, String jwt) {
        NetworkBehavior behavior = getNetworkBehavior();
        Retrofit retrofit = MockRestServiceBuilder.getRetrofit(baseUrl, apikey, jwt);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        return mockRetrofit.create(clazz);
    }

    private NetworkBehavior getNetworkBehavior() {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        behavior.setDelay(0, TimeUnit.MILLISECONDS);
        behavior.setVariancePercent(0);
        return behavior;
    }

    private void setMocksBeforeConfigReset() {
        expect(RestServiceBuilder.getDsRestClient(config)).andReturn(dsRestClient);
        expect(RestServiceBuilder.getGclAdminRestClient(config)).andReturn(gclAdminRestClient);
        expect(RestServiceBuilder.getGclRestClient(config)).andReturn(gclRestClient);
        expect(RestServiceBuilder.getGclCitrixRestClient(config)).andReturn(gclCitrixRestClient);
        expect(RestServiceBuilder.getOcvRestClient(config)).andReturn(ocvRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclBeIdRestClient.class)).andReturn(gclBeIdRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclDniRestClient.class)).andReturn(gclDniRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclLuxIdRestClient.class)).andReturn(gclLuxIdRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclPtIdRestClient.class)).andReturn(gclPtIdRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclEmvRestClient.class)).andReturn(gclEmvRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclMobibRestClient.class)).andReturn(gclMobibRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclOcraRestClient.class)).andReturn(gclOcraRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclPivRestClient.class)).andReturn(gclPivRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclSafeNetRestClient.class)).andReturn(gclSafeNetRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclAventraRestClient.class)).andReturn(gclAventraRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclLuxTrustRestClient.class)).andReturn(gclLuxTrustRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclOberthurRestClient.class)).andReturn(gclOberthurRestClient);
        expect(RestServiceBuilder.getContainerRestClient(config, GclRemoteLoadingRestClient.class)).andReturn(gclRemoteLoadingRestClient);
    }

    private void mockRestClients() {
        dsRestClient = new MockDsRestClient(mockRestClient(DsRestClient.class, config.getDsUri(), config.getApiKey(), null));
        gclRestClient = new MockGclRestClient(mockRestClient(GclRestClient.class, config.getGclClientUri(), null, config.getJwt()));
        gclCitrixRestClient = new MockGclCitrixRestClient(mockRestClient(GclCitrixRestClient.class, config.getGclClientUri(), null, config.getJwt()));
        gclAdminRestClient = new MockGclRestAdminClient(mockRestClient(GclAdminRestClient.class, config.getGclClientUri(), null, config.getJwt()));
        ocvRestClient = new MockOcvRestClient(mockRestClient(OcvRestClient.class, config.getOcvUri(), config.getApiKey(), null));
        gclBeIdRestClient = new MockGclBeIdRestClient(mockRestClient(GclBeIdRestClient.class, config.getGclClientUri(), null, null));
        gclDniRestClient = new MockGclDnieRestClient(mockRestClient(GclDniRestClient.class, config.getGclClientUri(), null, null));
        gclLuxIdRestClient = new MockGclLuxIdRestClient(mockRestClient(GclLuxIdRestClient.class, config.getGclClientUri(), null, null));
        gclPtIdRestClient = new MockGclPtIdRestClient(mockRestClient(GclPtIdRestClient.class, config.getGclClientUri(), null, null));
        gclEmvRestClient = new MockGclEmvRestClient(mockRestClient(GclEmvRestClient.class, config.getGclClientUri(), null, null));
        gclMobibRestClient = new MockGclMobibRestClient(mockRestClient(GclMobibRestClient.class, config.getGclClientUri(), null, null));
        gclOcraRestClient = new MockGclOcraRestClient(mockRestClient(GclOcraRestClient.class, config.getGclClientUri(), null, null));
        gclPivRestClient = new MockGclPivRestClient(mockRestClient(GclPivRestClient.class, config.getGclClientUri(), null, null));
        gclSafeNetRestClient = new MockGclSafeNetRestClient(mockRestClient(GclSafeNetRestClient.class, config.getGclClientUri(), null, null));
        gclAventraRestClient = new MockGclAventraRestClient(mockRestClient(GclAventraRestClient.class, config.getGclClientUri(), null, null));
        gclLuxTrustRestClient = new MockGclLuxTrustRestClient(mockRestClient(GclLuxTrustRestClient.class, config.getGclClientUri(), null, null));
        gclOberthurRestClient = new MockGclOberthurRestClient(mockRestClient(GclOberthurRestClient.class, config.getGclClientUri(), null, null));
        gclRemoteLoadingRestClient = new MockGclRemoteLoadingRestClient(mockRestClient(GclRemoteLoadingRestClient.class, config.getGclClientUri(), null, null));
    }
}