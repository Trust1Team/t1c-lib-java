package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.*;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.utils.CryptUtil;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclLuxIdRestClient extends AbstractMockRestClient<GclLuxIdRestClient> implements GclLuxIdRestClient {

    public MockGclLuxIdRestClient(BehaviorDelegate<GclLuxIdRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllDataResponse(null)).getLuxIdAllData(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(String containerId, String readerId, Map<String, String> headers, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllDataResponse(filter)).getLuxIdAllData(containerId, readerId, headers, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllCertificatesResponse(null)).getLuxIdAllCertificates(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(String containerId, String readerId, Map<String, String> headers, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAllCertificatesResponse(filter)).getLuxIdAllCertificates(containerId, readerId, headers, filter);
    }

    @Override
    public Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdBiometricResponse()).getLuxIdBiometric(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdPictureResponse()).getLuxIdPicture(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdSignatureImageResponse()).getLuxIdSignatureImage(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, Map<String, String> headers, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getPin())).verifyPin(containerId, readerId, headers, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(CryptUtil.encrypt("1111"))).verifyPin(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, Map<String, String> headers, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).authenticate(containerId, readerId, headers, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, Map<String, String> headers, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).authenticate(containerId, readerId, headers, request);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdNonRepudiationCertificateResponse()).getAuthenticationCertificate(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId, Map<String, String> headers) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getLuxIdRootCertificatesResponse()).getRootCertificates(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<List<DigestAlgorithm>>> getAvailableSignAlgos(String containerId, String readerId, Map<String, String> headers) {
        return delegate.returningResponse(MockResponseFactory.getSupportedAlgorithms(ContainerType.LUXID)).getAvailableSignAlgos(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<List<DigestAlgorithm>>> getAvailableAuthenticateAlgos(String containerId, String readerId, Map<String, String> headers) {
        return delegate.returningResponse(MockResponseFactory.getSupportedAlgorithms(ContainerType.LUXID)).getAvailableAuthenticateAlgos(containerId, readerId, headers);
    }

    @Override
    public Call<T1cResponse<Integer>> getPinTryCount(String containerId, String readerId, Map<String, String> headerMap, GclPinTryCounterRequest request) {
        return delegate.returningResponse(MockResponseFactory.getPinTryCount(request)).getPinTryCount(containerId, readerId, headerMap, request);
    }

    @Override
    public Call<T1cResponse<Boolean>> changePin(String containerId, String readerId, Map<String, String> headerMap, GclChangePinRequest request) {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getOldPin())).changePin(containerId, readerId, headerMap, request);
    }

    @Override
    public Call<T1cResponse<Boolean>> resetPin(String containerId, String readerId, Map<String, String> headerMap, GclResetPinRequest request) {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getPuk())).resetPin(containerId, readerId, headerMap, request);
    }
}