package com.t1t.t1c.mock;

import com.t1t.t1c.core.GclAdminCertificates;
import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclLoadContainersRequest;
import com.t1t.t1c.core.GclUpdatePublicKeyRequest;
import com.t1t.t1c.ds.DsAtrList;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import static com.t1t.t1c.MockResponseFactory.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclRestAdminClient implements GclAdminRestClient {

    private final BehaviorDelegate<GclAdminRestClient> delegate;

    public MockGclRestAdminClient(BehaviorDelegate<GclAdminRestClient> service) {
        this.delegate = service;
    }

    @Override
    public Call<T1cResponse<Object>> activate() {
        return delegate.returningResponse(getSuccessResponseWithoutData()).activate();
    }

    @Override
    public Call<T1cResponse<Object>> setDsPublicKey(GclUpdatePublicKeyRequest request) {
        return delegate.returningResponse(getSuccessResponseWithoutData()).activate();
    }

    @Override
    public Call<T1cResponse<GclAdminCertificates>> getCertificates() throws RestException {
        return delegate.returningResponse(getGclAdminCertificatesResponse()).getCertificates();
    }

    @Override
    public Call<T1cResponse<String>> getDsCertificate() throws RestException {
        return delegate.returningResponse(getGclAdminDsCertificateResponse()).getDsCertificate();
    }

    @Override
    public Call<T1cResponse<String>> getDeviceCertificate() throws RestException {
        return delegate.returningResponse(getGclAdminDeviceCertificateResponse()).getDeviceCertificate();
    }

    @Override
    public Call<T1cResponse<String>> getSslCertificate() throws RestException {
        return delegate.returningResponse(getGclAdminSslCertificateResponse()).getSslCertificate();
    }

    @Override
    public Call<T1cResponse<Object>> loadContainers(GclLoadContainersRequest gclLoadContainersRequest) throws RestException {
        return delegate.returningResponse(getSuccessResponseWithoutData()).loadContainers(gclLoadContainersRequest);
    }

    @Override
    public Call<T1cResponse<Object>> loadAtrList(DsAtrList atrList) throws RestException {
        return delegate.returningResponse(getSuccessResponseWithoutData()).loadAtrList(atrList);
    }
}