package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

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
public class MockGclSafeNetRestClient extends AbstractMockRestClient<GclSafeNetRestClient> implements GclSafeNetRestClient {

    public MockGclSafeNetRestClient(BehaviorDelegate<GclSafeNetRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<List<String>>> getSafeNetCertificates(String containerId, String readerId, GclSafeNetRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclSafeNetInfo>> getSafeNetInfo(String containerId, String readerId, GclSafeNetRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<List<GclSafeNetSlot>>> getSafeNetSlots(String containerId, String readerId, GclSafeNetRequest request, Boolean tokenPresent) throws RestException {
        return null;
    }
}