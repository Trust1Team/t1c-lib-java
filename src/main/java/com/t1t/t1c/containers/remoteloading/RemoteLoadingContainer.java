package com.t1t.t1c.containers.remoteloading;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.FunctionalContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.rest.RestExecutor;
import retrofit2.Call;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class RemoteLoadingContainer extends FunctionalContainer<RemoteLoadingContainer, GclRemoteLoadingRestClient> {

    public RemoteLoadingContainer(LibConfig config, GclReader reader, GclRemoteLoadingRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public RemoteLoadingContainer createInstance(LibConfig config, GclReader reader, GclRemoteLoadingRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.READER_API;
        return this;
    }

    public String getAtr(String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.getAtr(getTypeId(), reader.getId(), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not retrieve ATR", ex);
        }
    }

    public GclRemoteLoadingCommand executeApduCall(GclRemoteLoadingApdu apdu, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeApduCall(getTypeId(), reader.getId(), apdu, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute APDU call", ex);
        }
    }

    public List<GclRemoteLoadingCommand> executeApduCalls(List<GclRemoteLoadingApdu> apdus, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeApduCalls(getTypeId(), reader.getId(), apdus, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute APDU calls", ex);
        }
    }

    public GclRemoteLoadingCommand isCcidFeatureAvailable(GclRemoteLoadingCcidRequest request, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.getCcid(getTypeId(), reader.getId(), request, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not retrieve CCID feature availability", ex);
        }
    }

    public List<GclRemoteLoadingCcidFeature> getCcidFeatures(String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.getCcidFeatures(getTypeId(), reader.getId(), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not retrieve CCID features", ex);
        }
    }

    public GclRemoteLoadingCommand executeCommand(GclRemoteLoadingCommandRequest request, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeCommand(getTypeId(), reader.getId(), request, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute command", ex);
        }
    }

    public List<GclRemoteLoadingCommand> executeCommands(List<GclRemoteLoadingCommandRequest> request, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeCommands(getTypeId(), reader.getId(), request, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute commands", ex);
        }
    }

    public Boolean isCardPresent(String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.isPresent(getTypeId(), reader.getId(), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute command", ex);
        }
    }

    public String openSession(Integer timeout) {
        try {
            Integer timeoutToUse;
            if (timeout != null) {
                Preconditions.checkArgument(timeout > 0, "timeout value must be greater than 0");
                timeoutToUse = timeout;
            }
            else {
                timeoutToUse = config.getSessionTimeout();
            }
            return RestExecutor.returnData(httpClient.openSession(getTypeId(), reader.getId(), new GclRemoteLoadingOpenSessionRequest().withTimeout(timeoutToUse)));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute command", ex);
        }
    }

    public String closeSession(String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.closeSession(getTypeId(), reader.getId(), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute command", ex);
        }
    }

    @Override
    public ContainerType getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return type.getId();
    }
}
