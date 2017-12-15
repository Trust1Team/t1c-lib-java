package com.t1t.t1c.mock;

import com.t1t.t1c.ds.*;
import com.t1t.t1c.model.DsPublicKeyEncoding;
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
    public Call<DsSystemStatus> getInfo() {
        return delegate.returningResponse(new DsSystemStatus()).getInfo();
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
    public Call<DsPublicKey> getPubKey(String encoding) {
        if (encoding.equalsIgnoreCase(DsPublicKeyEncoding.DER.getQueryParamValue())) {
            return delegate.returningResponse(getPublicKeyResponseDer()).getPubKey(null);
        } else {
            return delegate.returningResponse(getPublicKeyResponsePem()).getPubKey(encoding);
        }
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