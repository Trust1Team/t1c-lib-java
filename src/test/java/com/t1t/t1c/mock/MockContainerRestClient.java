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

    private final BehaviorDelegate<ContainerRestClient> containerDelegate;

    public MockContainerRestClient(BehaviorDelegate<ContainerRestClient> containerDelegate) {
        this.containerDelegate = containerDelegate;
    }

    @Override
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getRootCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getCitizenCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getCitizenCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getNonRepudiationCertificate(containerId, readerId);
    }

    /*@Override
    public Call<T1cResponse<String>> getRrnCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getRrnCertificate(containerId, readerId);
    }*/

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getSigningCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getIssuerCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getIssuerCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getEncryptionCertificate(String containerId, String readerId) {
        return containerDelegate.returningResponse(getCertificateResponse()).getEncryptionCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) {
        return containerDelegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) {
        return containerDelegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, GclAuthenticateOrSignData request) {
        return containerDelegate.returningResponse(getSignedHashResponse()).authenticate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, GclAuthenticateOrSignData request) {
        return containerDelegate.returningResponse(getSignedHashResponse()).sign(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getCertificateResponse()).getRootCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getCitizenCertificate(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getCertificateResponse()).getCitizenCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getCertificateResponse()).getAuthenticationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getCertificateResponse()).getNonRepudiationCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getCertificateResponse()).getSigningCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getIssuerCertificate(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getCertificateResponse()).getIssuerCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> getEncryptionCertificate(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getCertificateResponse()).getEncryptionCertificate(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin, GclVerifyPinRequest request) {
        return containerDelegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin) {
        return containerDelegate.returningResponse(getSuccessResponse()).verifyPin(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return containerDelegate.returningResponse(getSignedHashResponse()).authenticate(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) {
        return containerDelegate.returningResponse(getSignedHashResponse()).sign(containerId, readerId, pin, request);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId) {
        List<String> certs = Arrays.asList(getCertificateResponse().getData(), getCertificateResponse().getData());
        T1cResponse<List<String>> response = new T1cResponse<List<String>>().withSuccess(true).withData(certs);
        return containerDelegate.returningResponse(response).getRootCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<String>>> getRootCertificates(String containerId, String readerId, String pin) {
        List<String> certs = Arrays.asList(getCertificateResponse().getData(), getCertificateResponse().getData());
        T1cResponse<List<String>> response = new T1cResponse<List<String>>().withSuccess(true).withData(certs);
        return containerDelegate.returningResponse(response).getRootCertificates(containerId, readerId, pin);
    }
}