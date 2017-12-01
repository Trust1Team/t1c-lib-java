package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.t1t.t1c.mock.AbstractMockRestClient;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclAventraRestClient extends AbstractMockRestClient<GclAventraRestClient> implements GclAventraRestClient {

    public MockGclAventraRestClient(BehaviorDelegate<GclAventraRestClient> delegate) {
        super(delegate);
    }
}