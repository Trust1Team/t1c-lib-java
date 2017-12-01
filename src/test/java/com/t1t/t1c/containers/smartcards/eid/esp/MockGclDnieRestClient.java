package com.t1t.t1c.containers.smartcards.eid.esp;

import com.t1t.t1c.containers.smartcards.eid.dni.GclDniRestClient;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieAllData;
import com.t1t.t1c.containers.smartcards.eid.dni.GclDnieInfo;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclDnieRestClient extends AbstractMockRestClient<GclDniRestClient> implements GclDniRestClient {

    public MockGclDnieRestClient(BehaviorDelegate<GclDniRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclDnieInfo>> getDnieInfo(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclDnieAllData>> getDnieAllData(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclDnieAllData>> getDnieAllData(String containerId, String readerId, String filter) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(String containerId, String readerId, String filter) {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getIntermediateCertificate(String containerId, String readerId) {
        return null;
    }
}