package com.t1t.t1c.mock;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclConsent;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import com.t1t.t1c.rest.GclRestClient;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

import static com.t1t.t1c.MockResponseFactory.*;

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
        return delegate.returningResponse(getGclV1Status()).getV1Status();
    }

    @Override
    public Call<T1cResponse<GclStatus>> getV2Status() {
        return delegate.returningResponse(getGclV1Status()).getV2Status();
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
    public Call<T1cResponse<Object>> getConsent(GclConsent consent) {
        return delegate.returningResponse(getSuccessResponse()).getConsent(consent);
    }
}