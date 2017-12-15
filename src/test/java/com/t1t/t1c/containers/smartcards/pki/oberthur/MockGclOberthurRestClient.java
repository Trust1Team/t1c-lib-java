package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
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
public class MockGclOberthurRestClient extends AbstractMockRestClient<GclOberthurRestClient> implements GclOberthurRestClient {

    public MockGclOberthurRestClient(BehaviorDelegate<GclOberthurRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclOberthurAllData>> getAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurAllDataResponse(filter)).getAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclOberthurAllData>> getAllCertificates(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurAllDataResponse(filter)).getAllCertificates(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getPin())).verifyPin(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin("1111")).verifyPin(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).authenticate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).sign(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<List<String>>> getAuthenticationAlgoRefs(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurAuthenticationAlgoRefsResponse()).getAuthenticationAlgoRefs(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<String>>> getSignAlgoRefs(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurSignAlgoRefsResponse()).getSignAlgoRefs(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurRootCertificateResponse()).getRootCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurSigningCertificateResponse()).getSigningCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getIssuerCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurIssuerCertificateResponse()).getIssuerCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getEncryptionCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOberthurEncryptionCertificateResponse()).getEncryptionCertificate(containerId, readerId);
    }
}