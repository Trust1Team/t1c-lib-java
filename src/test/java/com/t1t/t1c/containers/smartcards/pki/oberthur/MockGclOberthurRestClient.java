package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.t1t.t1c.mock.AbstractMockRestClient;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclOberthurRestClient extends AbstractMockRestClient<GclOberthurRestClient> implements GclOberthurRestClient {

    public MockGclOberthurRestClient(BehaviorDelegate<GclOberthurRestClient> delegate) {
        super(delegate);
    }
}