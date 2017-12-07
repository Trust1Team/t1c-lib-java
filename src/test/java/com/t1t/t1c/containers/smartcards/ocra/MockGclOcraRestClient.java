package com.t1t.t1c.containers.smartcards.ocra;

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
public class MockGclOcraRestClient extends AbstractMockRestClient<GclOcraRestClient> implements GclOcraRestClient {

    public MockGclOcraRestClient(BehaviorDelegate<GclOcraRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclOcraAllData>> getOcraAllData(String containerId, String readerId) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<GclOcraAllData>> getOcraAllData(String containerId, String readerId, String filter) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> ocraChallenge(String containerId, String readerId, GclOcraChallengeData request) throws RestException {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getOcraReadCounter(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return null;
    }
}