package com.t1t.t1c.containers.smartcards.piv;

import com.t1t.t1c.mock.AbstractMockRestClient;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclPivRestClient extends AbstractMockRestClient<GclPivRestClient> implements GclPivRestClient {

    public MockGclPivRestClient(BehaviorDelegate<GclPivRestClient> delegate) {
        super(delegate);
    }
}