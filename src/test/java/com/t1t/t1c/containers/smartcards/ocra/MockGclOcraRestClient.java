package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.utils.CryptUtil;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclOcraRestClient extends AbstractMockRestClient<GclOcraRestClient> implements GclOcraRestClient {

    public MockGclOcraRestClient(BehaviorDelegate<GclOcraRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclOcraAllData>> getOcraAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOcraAllDataResponse(filter)).getOcraAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<Long>> ocraChallenge(String containerId, String readerId, GclOcraChallengeData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOcraChallengeResponse(request.getPin())).ocraChallenge(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> readCounter(String containerId, String readerId, String pin) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclOcraCounterResponse(pin)).readCounter(containerId, readerId, pin);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getPin())).verifyPin(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(CryptUtil.encrypt("1111"))).verifyPin(containerId, readerId);
    }
}