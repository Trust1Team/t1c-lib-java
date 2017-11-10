package com.t1t.t1c.mock;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.rest.ContainerRestClient;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import static com.t1t.t1c.MockResponseFactory.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockContainerRestClient implements ContainerRestClient {

    private final BehaviorDelegate<ContainerRestClient> delegate;

    public MockContainerRestClient(BehaviorDelegate<ContainerRestClient> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getRootCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getCitizenCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getCitizenCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getNonRepudiationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRrnCertifcate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getRrnCertifcate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertifcate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getSigningCertifcate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getIssuerCertifcate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getIssuerCertifcate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getEncryptionCertifcate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getEncryptionCertifcate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) {
        return delegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) {
        return delegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(getSignedHashResponse()).authenticate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(getSignedHashResponse()).sign(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredRootCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredRootCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredCitizenCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredCitizenCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredAuthenticationCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredAuthenticationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredNonRepudiationCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredNonRepudiationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredRrnCertifcate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredRrnCertifcate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredSigningCertifcate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredSigningCertifcate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredIssuerCertifcate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredIssuerCertifcate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSecuredEncryptionCertifcate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSecuredEncryptionCertifcate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPinSecured(String containerId, String readerId, String pin, GclVerifyPinRequest request) {
        return delegate.returningResponse(getSuccessResponse()).verifyPinSecured(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPinSecured(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getSuccessResponse()).verifyPinSecured(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> authenticateSecured(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(getSignedHashResponse()).authenticateSecured(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<String>> signSecured(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(getSignedHashResponse()).signSecured(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<GclBeIdRn>> getRnData(String containerId, String readerId) {
        return delegate.returningResponse(getRnDataResponse()).getRnData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIdAddress>> getBeIdAddress(String containerId, String readerId) {
        return delegate.returningResponse(getBeIdAddressResponse()).getBeIdAddress(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getBeIdPicture(String containerId, String readerId) {
        return delegate.returningResponse(getBeIdPictureResponse()).getBeIdPicture(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getBeIdAllDataResponse()).getBeIdAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(String containerId, String readerId) {
        return delegate.returningResponse(getBeIdAllDataResponse()).getBeIdAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIDAllCertificates>> getBeIdAllCertificates(String containerId, String readerId) {
        return delegate.returningResponse(getBeIdAllCertsResponse()).getBeIdAllCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIDAllCertificates>> getBeIdAllCertificates(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getBeIdAllCertsResponse()).getBeIdAllCertificates(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getLuxIdAllDataResponse()).getLuxIdAllData(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(getLuxIdAllDataResponse()).getLuxIdAllData(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getLuxIdAllCertsResponse()).getLuxIdAllCertificates(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(getLuxIdAllCertsResponse()).getLuxIdAllCertificates(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getLuxIdBiometricResponse()).getLuxIdBiometric(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getLuxIdPictureResponse()).getLuxIdPicture(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getLuxIdSignatureImageResponse()).getLuxIdSignatureImage(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getLuxTrustAllDataResponse()).getLuxTrustAllData(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(getLuxTrustAllDataResponse()).getLuxTrustAllData(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getLuxTrustAllCertsResponse()).getLuxTrustAllCertificates(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(String containerId, String readerId, String pin, String filter) {
        return delegate.returningResponse(getLuxTrustAllCertsResponse()).getLuxTrustAllCertificates(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<Object>> isLuxTrustActivated(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getSuccessResponse()).isLuxTrustActivated(containerId, readerId, pin);
    }
}