package com.t1t.t1c.containers.smartcards.piv;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.utils.PinUtil;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclPivRestClient extends AbstractMockRestClient<GclPivRestClient> implements GclPivRestClient {

    public MockGclPivRestClient(BehaviorDelegate<GclPivRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclPivAllData>> getAllData(String containerId, String readerId, GclVerifyPinRequest request, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivAllDataResponse(filter)).getAllData(containerId, readerId, request, filter);
    }

    @Override
    public Call<T1cResponse<GclPivAllCertificates>> getAllCertificates(String containerId, String readerId, GclVerifyPinRequest request, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivAllCertificatesResponse(filter)).getAllCertificates(containerId, readerId, request, filter);
    }

    @Override
    public Call<T1cResponse<GclPivPrintedInformation>> getPrintedInformation(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivPrintedInformationResponse()).getPrintedInformation(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclPivFacialImage>> getFacialImage(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivFacialImageResponse()).getFacialImage(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivSigningCertificateResponse()).getSigningCertificate(containerId, readerId, request);
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
    public Call<T1cResponse<List<String>>> getAuthenticationAlgoRefs(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivAuthenticationAlgoRefsResponse()).getAuthenticationAlgoRefs(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<List<String>>> getSignAlgoRefs(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclPivSignAlgoRefsResponse()).getSignAlgoRefs(containerId, readerId);
    }
}