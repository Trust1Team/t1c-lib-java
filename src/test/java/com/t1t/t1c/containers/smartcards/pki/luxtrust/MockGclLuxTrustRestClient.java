package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.utils.PinUtil;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclLuxTrustRestClient extends AbstractMockRestClient<GclLuxTrustRestClient> implements GclLuxTrustRestClient {

    public MockGclLuxTrustRestClient(BehaviorDelegate<GclLuxTrustRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAllDataResponse(filter)).getLuxTrustAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAllCertificatesResponse(filter)).getLuxTrustAllCertificates(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<Object>> isLuxTrustActivated(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSuccessResponseWithoutData()).isLuxTrustActivated(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getPin())).verifyPin(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(PinUtil.encryptPin("1111"))).verifyPin(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).authenticate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).authenticate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAuthenticationCertificate()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustSigningCertificate()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustRootCertificates()).getRootCertificates(containerId, readerId);
    }
}