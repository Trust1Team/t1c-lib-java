package com.t1t.t1c.mock;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.*;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

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
    public Call<T1cResponse<GclInfo>> getStatus() throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclStatusResponse()).getStatus();
    }

    @Override
    public Call<T1cResponse<String>> getPublicKey() {
        return delegate.returningResponse(MockResponseFactory.getGclAdminCertificatesResponse()).getPublicKey();
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
    public Call<T1cResponse<List<GclAgent>>> getAgents(GclAgentRequestFilter request) {
        return delegate.returningResponse(MockResponseFactory.getAgentsResponse(request.getUsername())).getAgents(request);
    }

    @Override
    public Call<T1cResponse<List<GclAgent>>> resolveAgent(GclAgentResolutionRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getAgentsResponse(request.getChallenge())).resolveAgent(request);
    }

    @Override
    public Call<T1cResponse<Boolean>> getConsent(Integer agentPort, GclConsent request) {
        return delegate.returningResponse(MockResponseFactory.getConsentResponse()).getConsent(agentPort, request);
    }
}