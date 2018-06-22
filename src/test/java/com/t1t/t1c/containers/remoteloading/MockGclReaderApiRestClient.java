package com.t1t.t1c.containers.readerapi;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.containers.functional.readerapi.*;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclReaderApiRestClient extends AbstractMockRestClient<GclReaderApiRestClient> implements GclReaderApiRestClient {

    public MockGclReaderApiRestClient(BehaviorDelegate<GclReaderApiRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<String>> getAtr(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiAtrResponse()).getAtr(containerId, readerId, sessionId);
    }

    @Override
    public Call<T1cResponse<GclReaderApiCommand>> executeApduCall(String containerId, String readerId, GclReaderApiApdu apdu, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiCommandResponse()).executeApduCall(containerId, readerId, apdu, sessionId);
    }

    @Override
    public Call<T1cResponse<List<GclReaderApiCommand>>> executeApduCalls(String containerId, String readerId, List<GclReaderApiApdu> apdus, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiCommandsResponses(apdus.size())).executeApduCalls(containerId, readerId, apdus, sessionId);
    }

    @Override
    public Call<T1cResponse<GclReaderApiCommand>> executeCcid(String containerId, String readerId, GclReaderApiCcidRequest request, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiCommandResponse()).executeCcid(containerId, readerId, request, sessionId);
    }

    @Override
    public Call<T1cResponse<List<GclReaderApiCcidFeature>>> getCcidFeatures(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiCcidFeaturesResponse()).getCcidFeatures(containerId, readerId, sessionId);
    }

    @Override
    public Call<T1cResponse<GclReaderApiCommand>> executeCommand(String containerId, String readerId, GclReaderApiCommandRequest request, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiCommandResponse()).executeCommand(containerId, readerId, request, sessionId);
    }

    @Override
    public Call<T1cResponse<List<GclReaderApiCommand>>> executeCommands(String containerId, String readerId, List<GclReaderApiCommandRequest> request, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiCommandsResponses(request.size())).executeCommands(containerId, readerId, request, sessionId);
    }

    @Override
    public Call<T1cResponse<Boolean>> isPresent(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiIsPresentResponse()).isPresent(containerId, readerId, sessionId);
    }

    @Override
    public Call<T1cResponse<String>> openSession(String containerId, String readerId, GclReaderApiOpenSessionRequest request) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiOpenSessionResponse()).openSession(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> closeSession(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getReaderApiCloseSessionResponse(sessionId)).closeSession(containerId, readerId, sessionId);
    }
}