package com.t1t.t1c.mock;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.core.GclStatus;
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
        return null;
    }

    @Override
    public Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(boolean cardInserted) {
        return null;
    }

    @Override
    public Call<T1cResponse<GclReader>> getCardReader(String readerId) {
        return null;
    }

    @Override
    public Call<T1cResponse<List<GclContainer>>> getContainers() {
        return null;
    }

    /*@Override
    public Call<T1cResponse<GclStatus>> getV1Status() {
        return delegate.returningResponse(getGclV1Status()).getV1Status();
    }

    @Override
    public Call<T1cResponse<List<GclReader>>> getCardReaders() {
        return delegate.returningResponse(getAllReaders(true)).getCardReaders();
    }

    @Override
    public Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(boolean cardInserted) {
        if (cardInserted) {
            return delegate.returningResponse(getAllReaders(false)).getCardInsertedReaders(true);
        } else {
            return delegate.returningResponse(getListOfEmptyReaders()).getCardInsertedReaders(false);
        }
    }

    @Override
    public Call<T1cResponse<GclReader>> getCardReader(String readerId) {
        return delegate.returningResponse(getReaderWithCardId(readerId)).getCardReader(readerId);
    }

    @Override
    public Call<T1cResponse<List<GclContainer>>> getContainers() {
        return delegate.returningResponse(getAllContainers()).getContainers();
    }

    @Override
    public Call<T1cResponse<String>> getPublicKey() {
        try {
            return delegate.returning(MockResponseFactory.getGclAdminCertificateResponse()).getPublicKey();
        } catch (IOException ex) {
            return null;
        }
    }*/
}