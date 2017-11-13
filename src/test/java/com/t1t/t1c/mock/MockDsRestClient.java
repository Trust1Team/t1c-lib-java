package com.t1t.t1c.mock;

import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.rest.DsRestClient;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import static com.t1t.t1c.MockResponseFactory.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockDsRestClient implements DsRestClient {

    private final BehaviorDelegate<DsRestClient> delegate;

    public MockDsRestClient(BehaviorDelegate<DsRestClient> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<DsInfo> getInfo() {
        return delegate.returningResponse(new DsInfo()).getInfo();
    }

    @Override
    public Call<DsDevice> getDevice(String deviceId) {
        return delegate.returningResponse(getDsDevice()).getDevice(deviceId);
    }

    @Override
    public Call<DsToken> getJWT() {
        return delegate.returningResponse(getToken()).getJWT();
    }

    @Override
    public Call<DsToken> refreshJWT(DsTokenRefreshRequest request) {
        return delegate.returningResponse(getToken()).refreshJWT(request);
    }

    @Override
    public Call<DsPublicKey> getPubKey() {
        return delegate.returningResponse(getPubKey()).getPubKey();
    }

    @Override
    public Call<DsDownloadPath> getDownloadLink(DsDownloadRequest request) {
        return delegate.returningResponse(getDownloadPath()).getDownloadLink(request);
    }

    @Override
    public Call<DsToken> register(String deviceId, DsDeviceRegistrationRequest request) {
        return delegate.returningResponse(getToken()).register(deviceId, request);
    }

    @Override
    public Call<DsToken> sync(String deviceId, DsDeviceRegistrationRequest request) {
        return delegate.returningResponse(getToken()).sync(deviceId, request);
    }
}