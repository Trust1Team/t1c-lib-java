package com.t1t.t1c.containers.smartcards.eid.lux;

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
public class MockGclLuxIdRestClient extends AbstractMockRestClient<GclLuxIdRestClient> implements GclLuxIdRestClient {

    public MockGclLuxIdRestClient(BehaviorDelegate<GclLuxIdRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllDataResponse(null)).getLuxIdAllData(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllDataResponse(filter)).getLuxIdAllData(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllCertificatesResponse(null)).getLuxIdAllCertificates(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllCertificatesResponse(filter)).getLuxIdAllCertificates(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdBiometricResponse()).getLuxIdBiometric(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdPictureResponse()).getLuxIdPicture(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdSignatureImageResponse()).getLuxIdSignatureImage(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin, GclVerifyPinRequest request) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdVerifyPinSuccess()).verifyPin(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdVerifyPinSuccess()).verifyPin(containerId, readerId, pin);
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
        return delegate.returningResponse(MockResponseFactory.getLuxIdAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdNonRepudiationCertificateResponse()).getAuthenticationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId, String pin) {
        return delegate.returningResponse(MockResponseFactory.getLuxIdRootCertificatesResponse()).getRootCertificates(containerId, readerId, pin);
    }
}