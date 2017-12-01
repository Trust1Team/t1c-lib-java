package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclLuxTrustRestClient extends AbstractMockRestClient<GclLuxTrustRestClient> implements GclLuxTrustRestClient {

    public MockGclLuxTrustRestClient(BehaviorDelegate<GclLuxTrustRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(String containerId, String readerId, String pin) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(String containerId, String readerId, String pin, String filter) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(String containerId, String readerId, String pin) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(String containerId, String readerId, String pin, String filter) {
        return null;
    }

    @Override
    public Call<T1cResponse<Object>> isLuxTrustActivated(String containerId, String readerId, String pin) {
        return null;
    }
}