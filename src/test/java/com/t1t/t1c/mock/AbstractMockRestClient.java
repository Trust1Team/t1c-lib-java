package com.t1t.t1c.mock;

import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public abstract class AbstractMockRestClient<T> {

    protected final BehaviorDelegate<T> delegate;

    public AbstractMockRestClient(BehaviorDelegate<T> delegate) {
        this.delegate = delegate;
    }

}