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
    public Call<DsToken> refreshJWT(DsTokenRefreshRequest request) {
        return delegate.returningResponse(getToken()).refreshJWT(request);
    }

    @Override
    public Call<DsPublicKey> getPubKey(String deviceId, String encoding) {
        if (encoding.equalsIgnoreCase(DsPublicKeyEncoding.DER.getQueryParamValue())) {
            return delegate.returningResponse(getPublicKeyResponseDer()).getPubKey(deviceId,null);
        } else {
            return delegate.returningResponse(getPublicKeyResponsePem()).getPubKey(deviceId, encoding);
        }
    }

    @Override
    public Call<DsDownloadLink> getDownloadLink(DsDownloadRequest request) {
        return delegate.returningResponse(getDownloadLinkResponse(request.getProxyDomain())).getDownloadLink(request);
    }

    @Override
    public Call<DsRegistrationSyncResponse> register(String deviceId, DsDeviceRegistrationRequest request) {
        return delegate.returningResponse(getDsRegistrationResponse()).register(deviceId, request);
    }

    @Override
    public Call<DsRegistrationSyncResponse> sync(String deviceId, DsDeviceRegistrationRequest request) {
        return delegate.returningResponse(getDsSyncResponse()).sync(deviceId, request);
    }
}