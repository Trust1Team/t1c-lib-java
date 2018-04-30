package com.t1t.t1c.containers.smartcards.pkcs11;

import com.t1t.t1c.MockResponseFactory;
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
public class MockGclPkcs11RestClient extends AbstractMockRestClient<GclPkcs11RestClient> implements GclPkcs11RestClient {

    public MockGclPkcs11RestClient(BehaviorDelegate<GclPkcs11RestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<List<String>>> getPkcs11Certificates(String containerId, String readerId, GclPkcs11Request request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPkcs11CertificatesResponse(request.getPin())).getPkcs11Certificates(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<GclPkcs11Info>> getPkcs11Info(String containerId, String readerId, GclPkcs11Request request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPkcs11InfoResponse()).getPkcs11Info(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<List<GclPkcs11Slot>>> getPkcs11Slots(String containerId, String readerId, GclPkcs11Request request, Boolean tokenPresent) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getPkcs11SlotsResponse(tokenPresent)).getPkcs11Slots(containerId, readerId, request, tokenPresent);
    }
}