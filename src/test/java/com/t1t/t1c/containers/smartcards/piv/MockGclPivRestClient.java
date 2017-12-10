package com.t1t.t1c.containers.smartcards.piv;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclPivRestClient extends AbstractMockRestClient<GclPivRestClient> implements GclPivRestClient {

    public MockGclPivRestClient(BehaviorDelegate<GclPivRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclPivAllData>> getAllData(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclPivPrintedInformation>> getPrintedInformation(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin, GclVerifyPinRequest request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, String pin) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, String pin, GclAuthenticateOrSignData request) throws RestException {
        return null;
    }
}