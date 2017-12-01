package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclPtIdRestClient extends AbstractMockRestClient<GclPtIdRestClient> implements GclPtIdRestClient {

    public MockGclPtIdRestClient(BehaviorDelegate<GclPtIdRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclPtIdData>> getPtIdData(String containerId, String readerId, boolean includePhoto) {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getPtIdPhoto(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getRootAuthenticationCertificate(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getRootNonRepudiationCertificate(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(String containerId, String readerId, String filter) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(String containerId, String readerId, String filter) {
        return null;
    }
}