package com.t1t.t1c.containers.smartcards.eid.dni;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.utils.PinUtil;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclDnieRestClient extends AbstractMockRestClient<GclDniRestClient> implements GclDniRestClient {

    public MockGclDnieRestClient(BehaviorDelegate<GclDniRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclDnieInfo>> getDnieInfo(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieInfoResponse()).getDnieInfo(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclDnieAllData>> getDnieAllData(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieAllDataResponse(null)).getDnieAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclDnieAllData>> getDnieAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieAllDataResponse(filter)).getDnieAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieAllCertificatesResponse(null)).getDnieAllCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieAllCertificatesResponse(filter)).getDnieAllCertificates(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getIntermediateCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieIntermediateCertificateResponse()).getIntermediateCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclDnieSigningCertificateResponse()).getSigningCertificate(containerId, readerId);
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
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).sign(containerId, readerId, request);
    }
}