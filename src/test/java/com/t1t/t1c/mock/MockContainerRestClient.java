package com.t1t.t1c.mock;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.containers.ContainerRestClient;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.Arrays;
import java.util.List;

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
    public Call<T1cResponse<String>> getRrnCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getRrnCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getSigningCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getIssuerCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getIssuerCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getEncryptionCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getEncryptionCertificate(containerId, readerId);
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
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getRootCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getCitizenCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getCitizenCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getAuthenticationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getNonRepudiationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getSigningCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getIssuerCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getIssuerCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getEncryptionCertificate(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getCertificateResponse()).getEncryptionCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin, GclVerifyPinRequest request) {
        return delegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(getSignedHashResponse()).authenticate(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return delegate.returningResponse(getSignedHashResponse()).sign(containerId, readerId, pin, request);
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
        return delegate.returningResponse(getPictureResponse()).getBeIdPicture(containerId, readerId);
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
        return delegate.returningResponse(getLuxTrustAllCertsResponse()).getLuxTrustAllCertificates(containerId, readerId, pin, filter);
    }

    @Override
    public Call<T1cResponse<Object>> isLuxTrustActivated(String containerId, String readerId, String pin) {
        return delegate.returningResponse(getSuccessResponse()).isLuxTrustActivated(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<GclDnieInfo>> getDnieInfo(String containerId, String readerId) {
        return delegate.returningResponse(getDnieInfoResponse()).getDnieInfo(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclDnieAllData>> getDnieAllData(String containerId, String readerId) {
        return delegate.returningResponse(getDnieAllDataResponse()).getDnieAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclDnieAllData>> getDnieAllData(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getDnieAllDataResponse()).getDnieAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(String containerId, String readerId) {
        return delegate.returningResponse(getDnieAllCertificatesResponse()).getDnieAllCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getDnieAllCertificatesResponse()).getDnieAllCertificates(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<String>> getIntermediateCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getIntermediateCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdData>> getPtIdData(String containerId, String readerId, boolean includePhoto) {
        return delegate.returningResponse(getPtIdDataResponse(includePhoto)).getPtIdData(containerId, readerId, includePhoto);
    }

    @Override
    public Call<T1cResponse<String>> getPtIdPhoto(String containerId, String readerId) {
        return delegate.returningResponse(getPictureResponse()).getPtIdPhoto(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRootAuthenticationCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getRootAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRootNonRepudiationCertificate(String containerId, String readerId) {
        return delegate.returningResponse(getCertificateResponse()).getRootNonRepudiationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(String containerId, String readerId) {
        return delegate.returningResponse(getPtIdAllDataResponse()).getPtIdAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getPtIdAllDataResponse()).getPtIdAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(String containerId, String readerId) {
        return delegate.returningResponse(getPtIdAllCertificatesResponse()).getPtIdAllCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getPtIdAllCertificatesResponse()).getPtIdAllCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<GclEmvApplication>>> getEmvApplications(String containerId, String readerId) {
        return delegate.returningResponse(getEmvApplicationResponse()).getEmvApplications(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclEmvApplicationData>> getEmvApplicationData(String containerId, String readerId) {
        return delegate.returningResponse(getEmvAppDataResponse()).getEmvApplicationData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclEmvCertificate>> getEmvIssuerPublicKeyCertificate(String containerId, String readerId, GclEmvAidRequest request) {
        return delegate.returningResponse(getEmvCertResponse(request)).getEmvIssuerPublicKeyCertificate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclEmvCertificate>> getEmvIccPublicKeyCertificate(String containerId, String readerId, GclEmvAidRequest request) {
        return delegate.returningResponse(getEmvCertResponse(request)).getEmvIccPublicKeyCertificate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclEmvAllData>> getEmvAllData(String containerId, String readerId) {
        return delegate.returningResponse(getEmvAllDataResponse()).getEmvAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclEmvAllData>> getEmvAllData(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getEmvAllDataResponse()).getEmvAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<Boolean>> getMobibStatus(String containerId, String readerId) {
        return delegate.returningResponse(getBooleanResponse()).getMobibStatus(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getMobibPicture(String containerId, String readerId) {
        return delegate.returningResponse(getPictureResponse()).getMobibPicture(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclMobibCardIssuing>> getMobibCardIssuing(String containerId, String readerId) {
        return delegate.returningResponse(getMobibCardIssuingResponse()).getMobibCardIssuing(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<GclMobibContract>>> getMobibContracts(String containerId, String readerId) {
        return delegate.returningResponse(getMobibContractResponse()).getMobibContracts(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclMobibAllData>> getMobibAllData(String containerId, String readerId) {
        return delegate.returningResponse(getMobibAllDataResponse()).getMobibAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclMobibAllData>> getMobibAllData(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getMobibAllDataResponse()).getMobibAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclOcraAllData>> getOcraAllData(String containerId, String readerId) {
        return delegate.returningResponse(getGclOcraAllDataResponse()).getOcraAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclOcraAllData>> getOcraAllData(String containerId, String readerId, String filter) {
        return delegate.returningResponse(getGclOcraAllDataResponse()).getOcraAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<String>> ocraChallenge(String containerId, String readerId, GclOcraChallengeData request) {
        return delegate.returningResponse(getStringResponse()).ocraChallenge(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> getOcraReadCounter(String containerId, String readerId, GclVerifyPinRequest request) {
        return delegate.returningResponse(getStringResponse()).getOcraReadCounter(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<List<String>>> getSafeNetCertificates(String containerId, String readerId, GclSafeNetRequest request) {
        return delegate.returningResponse(getStringListResponse()).getSafeNetCertificates(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclSafeNetInfo>> getSafeNetInfo(String containerId, String readerId, GclSafeNetRequest request) {
        return delegate.returningResponse(getSafeNetInfoResponse()).getSafeNetInfo(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<List<GclSafeNetSlot>>> getSafeNetSlots(String containerId, String readerId, GclSafeNetRequest request, Boolean tokenPresent) {
        return delegate.returningResponse(getSafeNetSlotsResponse(tokenPresent)).getSafeNetSlots(containerId, readerId, request, tokenPresent);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId) {
        List<String> certs = Arrays.asList(getCertificateResponse().getData(), getCertificateResponse().getData());
        T1cResponse<List<String>> response = new T1cResponse<List<String>>().withSuccess(true).withData(certs);
        return delegate.returningResponse(response).getRootCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId, String pin) {
        List<String> certs = Arrays.asList(getCertificateResponse().getData(), getCertificateResponse().getData());
        T1cResponse<List<String>> response = new T1cResponse<List<String>>().withSuccess(true).withData(certs);
        return delegate.returningResponse(response).getRootCertificates(containerId, readerId, pin);
    }
}