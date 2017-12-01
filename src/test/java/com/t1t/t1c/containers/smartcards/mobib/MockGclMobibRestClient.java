package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclMobibRestClient extends AbstractMockRestClient<GclMobibRestClient> implements GclMobibRestClient {

    public MockGclMobibRestClient(BehaviorDelegate<GclMobibRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<Boolean>> getMobibStatus(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<String>> getMobibPicture(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclMobibCardIssuing>> getMobibCardIssuing(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<List<GclMobibContract>>> getMobibContracts(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclMobibAllData>> getMobibAllData(String containerId, String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclMobibAllData>> getMobibAllData(String containerId, String readerId, String filter) {
        return null;
    }
}