package com.t1t.t1c.mock;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.*;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclRestClient implements GclRestClient {

    private final BehaviorDelegate<GclRestClient> delegate;

    public MockGclRestClient(BehaviorDelegate<GclRestClient> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<T1cResponse<GclStatus>> getV1Status() {
        return delegate.returningResponse(MockResponseFactory.getGclV1StatusResponse()).getV1Status();
    }

    @Override
    public Call<T1cResponse<String>> getPublicKey() {
        return delegate.returningResponse(MockResponseFactory.getGclAdminCertificateResponse()).getPublicKey();
    }

    @Override
    public Call<T1cResponse<List<GclReader>>> getCardReaders() {
        return delegate.returningResponse(MockResponseFactory.getGclReadersResponse(null)).getCardReaders();
    }

    @Override
    public Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(boolean cardInserted) {
        return delegate.returningResponse(MockResponseFactory.getGclReadersResponse(cardInserted)).getCardInsertedReaders(cardInserted);
    }

    @Override
    public Call<T1cResponse<GclReader>> getCardReader(String readerId) {
        return delegate.returningResponse(MockResponseFactory.getGclReaderResponse(readerId)).getCardReader(readerId);
    }

    @Override
    public Call<T1cResponse<List<GclContainer>>> getContainers() {
        return delegate.returningResponse(MockResponseFactory.getAllContainersResponse()).getContainers();
    }
}