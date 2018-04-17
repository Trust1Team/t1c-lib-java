package com.t1t.t1c.containers.smartcards.eid.pt;

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
public class MockGclPtIdRestClient extends AbstractMockRestClient<GclPtIdRestClient> implements GclPtIdRestClient {

    public MockGclPtIdRestClient(BehaviorDelegate<GclPtIdRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclPtIdData>> getPtIdData(String containerId, String readerId, Boolean includePhoto) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPtIdDataResponse(includePhoto)).getPtIdData(containerId, readerId, includePhoto);
    }

    @Override
    public Call<T1cResponse<String>> getPtIdPhoto(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdPhotoResponse()).getPtIdPhoto(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRootAuthenticationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdRootAuthenticationResponse()).getRootAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRootNonRepudiationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdRootNonRepudiationCertificateResponse()).getRootNonRepudiationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdAllDataResponse(null)).getPtIdAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdAllDataResponse(filter)).getPtIdAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdAllCertificatesResponse(null)).getPtIdAllCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdAllCertificatesResponse(filter)).getPtIdAllCertificates(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdRootCertificateResponse()).getRootCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdNonRepudiationCertificateResponse()).getNonRepudiationCertificate(containerId, readerId);
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

    @Override
    public Call<T1cResponse<GclPtIdAddress>> getAddress(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdAddressResponse(request.getPin())).getAddress(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclPtIdAddress>> getAddress(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPtIdAddressResponse(PinUtil.encryptPin("1111"))).getAddress(containerId, readerId);
    }
}