package com.t1t.t1c.containers.remoteloading;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclRemoteLoadingRestClient extends AbstractMockRestClient<GclRemoteLoadingRestClient> implements GclRemoteLoadingRestClient {

    public MockGclRemoteLoadingRestClient(BehaviorDelegate<GclRemoteLoadingRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<String>> getAtr(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingAtrResponse()).getAtr(containerId, readerId, sessionId);
    }

    @Override
    public Call<T1cResponse<GclRemoteLoadingCommand>> executeApduCall(String containerId, String readerId, GclRemoteLoadingApdu apdu, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingCommandResponse()).executeApduCall(containerId, readerId, apdu, sessionId);
    }

    @Override
    public Call<T1cResponse<List<GclRemoteLoadingCommand>>> executeApduCalls(String containerId, String readerId, List<GclRemoteLoadingApdu> apdus, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingCommandsResponses(apdus.size())).executeApduCalls(containerId, readerId, apdus, sessionId);
    }

    @Override
    public Call<T1cResponse<GclRemoteLoadingCommand>> executeCcid(String containerId, String readerId, GclRemoteLoadingCcidRequest request, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingCommandResponse()).executeCcid(containerId, readerId, request, sessionId);
    }

    @Override
    public Call<T1cResponse<List<GclRemoteLoadingCcidFeature>>> getCcidFeatures(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingCcidFeaturesResponse()).getCcidFeatures(containerId, readerId, sessionId);
    }

    @Override
    public Call<T1cResponse<GclRemoteLoadingCommand>> executeCommand(String containerId, String readerId, GclRemoteLoadingCommandRequest request, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingCommandResponse()).executeCommand(containerId, readerId, request, sessionId);
    }

    @Override
    public Call<T1cResponse<List<GclRemoteLoadingCommand>>> executeCommands(String containerId, String readerId, List<GclRemoteLoadingCommandRequest> request, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingCommandsResponses(request.size())).executeCommands(containerId, readerId, request, sessionId);
    }

    @Override
    public Call<T1cResponse<Boolean>> isPresent(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingIsPresentResponse()).isPresent(containerId, readerId, sessionId);
    }

    @Override
    public Call<T1cResponse<String>> openSession(String containerId, String readerId, GclRemoteLoadingOpenSessionRequest request) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingOpenSessionResponse()).openSession(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> closeSession(String containerId, String readerId, String sessionId) {
        return delegate.returningResponse(MockResponseFactory.getRemoteLoadingCloseSessionResponse(sessionId)).closeSession(containerId, readerId, sessionId);
    }
}