package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclEmvRestClient extends AbstractMockRestClient<GclEmvRestClient> implements GclEmvRestClient {

    public MockGclEmvRestClient(BehaviorDelegate<GclEmvRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<List<GclEmvApplication>>> getEmvApplications(String containerId, String readerId) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclEmvApplicationData>> getEmvApplicationData(String containerId, String readerId) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclEmvPublicKeyCertificate>> getEmvIssuerPublicKeyCertificate(String containerId, String readerId, GclEmvAidRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclEmvPublicKeyCertificate>> getEmvIccPublicKeyCertificate(String containerId, String readerId, GclEmvAidRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclEmvAllData>> getEmvAllData(String containerId, String readerId) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclEmvAllData>> getEmvAllData(String containerId, String readerId, String filter) throws RestException {
        return null;
    }
}