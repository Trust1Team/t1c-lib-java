package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
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
    public Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAllDataResponse(null)).getLuxTrustAllData(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAllDataResponse(filter)).getLuxTrustAllData(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAllCertificatesResponse(null)).getLuxTrustAllCertificates(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAllCertificatesResponse(filter)).getLuxTrustAllCertificates(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<Object>> isLuxTrustActivated(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getSuccessResponse()).isLuxTrustActivated(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin, GclVerifyPinRequest request) {
        return delegate.returningResponse(MockResponseFactory.getLuxTrustVerifyPinResponse()).verifyPin(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxTrustVerifyPinResponse()).verifyPin(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse()).authenticate(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse()).authenticate(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustAuthenticationCertificate()).getAuthenticationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustSigningCertificate()).getAuthenticationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getGclLuxTrustRootCertificates()).getRootCertificates(containerId, readerId, pin);
    }
}