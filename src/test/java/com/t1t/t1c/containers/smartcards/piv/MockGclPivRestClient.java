package com.t1t.t1c.containers.smartcards.piv;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
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
        return null;
    }

    @Override
    public Call<T1cResponse<GclPivAllCertificates>> getAllCertificates(String containerId, String readerId, GclVerifyPinRequest request, String filter) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclPivPrintedInformation>> getPrintedInformation(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclPivFacialImage>> getFacialImage(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getSigningCertificate(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<List<String>>> getAuthenticationAlgoRefs(String containerId, String readerId) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<List<String>>> getSignAlgoRefs(String containerId, String readerId) throws RestException {
        return null;
    }
}