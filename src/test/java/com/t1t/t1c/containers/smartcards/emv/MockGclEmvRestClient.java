package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclVerifyPinRequest;
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
        return delegate.returningResponse(MockResponseFactory.getGclEmvApplicationsResponse()).getEmvApplications(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclEmvApplicationData>> getEmvApplicationData(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclEmvApplicationDataResponse()).getEmvApplicationData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclEmvPublicKeyCertificate>> getEmvIssuerPublicKeyCertificate(String containerId, String readerId, GclEmvAidRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclEmvIssuerPublicKeyCertificateResponse(request.getAid())).getEmvIssuerPublicKeyCertificate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclEmvPublicKeyCertificate>> getEmvIccPublicKeyCertificate(String containerId, String readerId, GclEmvAidRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclEmvIccPublicKeyCertificateResponse(request.getAid())).getEmvIccPublicKeyCertificate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclEmvAllData>> getEmvAllData(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclEmvAllDataResponse(null)).getEmvAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclEmvAllData>> getEmvAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclEmvAllDataResponse(filter)).getEmvAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getPin())).verifyPin(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin("1111")).verifyPin(containerId, readerId);
    }
}