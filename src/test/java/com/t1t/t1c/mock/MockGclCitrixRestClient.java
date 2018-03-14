package com.t1t.t1c.mock;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.*;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class MockGclCitrixRestClient implements GclCitrixRestClient {

    private final BehaviorDelegate<GclCitrixRestClient> delegate;

    public MockGclCitrixRestClient(BehaviorDelegate<GclCitrixRestClient> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<T1cResponse<GclInfo>> getV1Status() {
        return delegate.returningResponse(MockResponseFactory.getGclV1StatusResponse()).getV1Status();
    }

    @Override
    public Call<T1cResponse<GclInfo>> getV2Status() throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclV1StatusResponse()).getV2Status();
    }

    @Override
    public Call<T1cResponse<String>> getPublicKey() {
        return delegate.returningResponse(MockResponseFactory.getGclAdminCertificateResponse()).getPublicKey();
    }

    @Override
    public Call<T1cResponse<List<GclReader>>> getCardReaders(Integer agentPort) {
        return delegate.returningResponse(MockResponseFactory.getGclReadersResponse(null)).getCardReaders(agentPort);
    }

    @Override
    public Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(Integer agentPort, boolean cardInserted) {
        return delegate.returningResponse(MockResponseFactory.getGclReadersResponse(cardInserted)).getCardInsertedReaders(agentPort, cardInserted);
    }

    @Override
    public Call<T1cResponse<GclReader>> getCardReader(Integer agentPort, String readerId) {
        return delegate.returningResponse(MockResponseFactory.getGclReaderResponse(readerId)).getCardReader(agentPort, readerId);
    }

    @Override
    public Call<T1cResponse<List<GclContainer>>> getV1Containers() {
        return delegate.returningResponse(MockResponseFactory.getAllContainersResponse()).getV1Containers();
    }

    @Override
    public Call<T1cResponse<List<GclContainer>>> getV2Containers() throws RestException {
        return delegate.returningResponse(MockResponseFactory.getAllContainersResponse()).getV2Containers();
    }

    @Override
    public Call<T1cResponse<List<GclAgent>>> getAgents(Map<String, String> filters) {
        return delegate.returningResponse(MockResponseFactory.getAgentsResponse(filters)).getAgents(filters);
    }

    @Override
    public Call<T1cResponse<GclAgent>> getAgent(Map<String, String> filters) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getAgentResponse(filters)).getAgent(filters);
    }

    @Override
    public Call<T1cResponse<Boolean>> getConsent(Integer agentPort, GclConsent request) {
        return delegate.returningResponse(MockResponseFactory.getConsentResponse()).getConsent(agentPort, request);
    }
}