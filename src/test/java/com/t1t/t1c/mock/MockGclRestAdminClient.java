package com.t1t.t1c.mock;

import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclUpdatePublicKeyRequest;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import static com.t1t.t1c.MockResponseFactory.getSuccessResponseWithoutData;

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
}