package com.t1t.t1c.containers.smartcards.pki.aventra;

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
public class MockGclAventraRestClient extends AbstractMockRestClient<GclAventraRestClient> implements GclAventraRestClient {

    public MockGclAventraRestClient(BehaviorDelegate<GclAventraRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclAventraAllData>> getAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraAllDataResponse(filter)).getAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclAventraAllCertificates>> getAllCertificates(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getAventraAllCertificatesResponse(filter)).getAllCertificates(containerId, readerId, filter);
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
        return delegate.returningResponse(MockResponseFactory.getGclAventraAuthenticationAlgoRefsResponse()).getAuthenticationAlgoRefs(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<String>>> getSignAlgoRefs(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraSignAlgoRefsResponse()).getSignAlgoRefs(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraRootCertificateResponse()).getRootCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraSigningCertificateResponse()).getSigningCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getIssuerCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraIssuerCertificateResponse()).getIssuerCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getEncryptionCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraEncryptionCertificateResponse()).getEncryptionCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<Object>> resetPin(String containerId, String readerId, GclAventraPinResetRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclAventraResetPinResponse(request.getPuk())).resetPin(containerId, readerId, request);
    }
}