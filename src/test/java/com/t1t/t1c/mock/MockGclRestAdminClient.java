package com.t1t.t1c.mock;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclUpdatePublicKeyRequest;
import com.t1t.t1c.rest.GclAdminRestClient;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import static com.t1t.t1c.MockResponseFactory.getPublicKeyResponse;
import static com.t1t.t1c.MockResponseFactory.getSuccessResponse;

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
        return delegate.returningResponse(getSuccessResponse()).activate();
    }

    @Override
    public Call<T1cResponse<String>> getPublicKey() {
        return delegate.returningResponse(getPublicKeyResponse()).getPublicKey();
    }

    @Override
    public Call<T1cResponse<Object>> setPublicKey(GclUpdatePublicKeyRequest request) {
        return delegate.returningResponse(getSuccessResponse()).activate();
    }
}