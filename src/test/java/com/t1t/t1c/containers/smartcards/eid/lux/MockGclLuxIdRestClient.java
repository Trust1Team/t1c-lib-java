package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclLuxIdRestClient extends AbstractMockRestClient<GclLuxIdRestClient> implements GclLuxIdRestClient {

    public MockGclLuxIdRestClient(BehaviorDelegate<GclLuxIdRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, String pin) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, String pin, String filter) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, String pin) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, String pin, String filter) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(String containerId, String readerId, String pin) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(String containerId, String readerId, String pin) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(String containerId, String readerId, String pin) {
        return null;
    }
}