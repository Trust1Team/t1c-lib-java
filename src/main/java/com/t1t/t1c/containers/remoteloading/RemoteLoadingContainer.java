package com.t1t.t1c.containers.remoteloading;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.FunctionalContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.rest.RestExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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

    public String getAtr() {
        return getAtr(null);
    }

    public String getAtr(String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.getAtr(getTypeId(), reader.getId(), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not retrieve ATR", ex);
        }
    }

    public GclRemoteLoadingCommand executeApduCall(GclRemoteLoadingApdu apdu) {
        return executeApduCall(apdu, null);
    }

    public GclRemoteLoadingCommand executeApduCall(GclRemoteLoadingApdu apdu, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeApduCall(getTypeId(), reader.getId(), apdu, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute APDU call", ex);
        }
    }

    public List<GclRemoteLoadingCommand> executeApduCalls(List<GclRemoteLoadingApdu> apdus) {
        return executeApduCalls(apdus, null);
    }

    public List<GclRemoteLoadingCommand> executeApduCalls(List<GclRemoteLoadingApdu> apdus, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeApduCalls(getTypeId(), reader.getId(), apdus, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute APDU calls", ex);
        }
    }

    public GclRemoteLoadingCommand executeCcid(String feature, String apdu) {
        return executeCcid(feature, apdu, null);
    }

    public GclRemoteLoadingCommand executeCcid(String feature, String apdu, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeCcid(getTypeId(), reader.getId(), new GclRemoteLoadingCcidRequest().withApdu(apdu).withFeature(feature), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not retrieve CCID feature availability", ex);
        }
    }

    public List<GclRemoteLoadingCcidFeature> getCcidFeatures() {
        return getCcidFeatures(null);
    }

    public List<GclRemoteLoadingCcidFeature> getCcidFeatures(String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.getCcidFeatures(getTypeId(), reader.getId(), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not retrieve CCID features", ex);
        }
    }

    public GclRemoteLoadingCommand executeCommand(String command) {
        return executeCommand(command, null);
    }

    public GclRemoteLoadingCommand executeCommand(String command, String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.executeCommand(getTypeId(), reader.getId(), new GclRemoteLoadingCommandRequest().withTx(command), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute command", ex);
        }
    }

    public List<GclRemoteLoadingCommand> executeCommands(List<String> commands) {
        return executeCommands(commands, null);
    }

    public List<GclRemoteLoadingCommand> executeCommands(List<String> commands, String sessionId) {
        try {
            List<GclRemoteLoadingCommandRequest> commandRequests = new ArrayList<>();
            for (String command : commands) {
                commandRequests.add(new GclRemoteLoadingCommandRequest().withTx(command));
            }
            return RestExecutor.returnData(httpClient.executeCommands(getTypeId(), reader.getId(), commandRequests, sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not execute commands", ex);
        }
    }

    public Boolean isCardPresent() {
        return isCardPresent(null);
    }

    public Boolean isCardPresent(String sessionId) {
        try {
            return RestExecutor.returnData(httpClient.isPresent(getTypeId(), reader.getId(), sessionId));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could determine if card is present", ex);
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
            throw ExceptionFactory.remoteLoadingContainerException("could not open session", ex);
        }
    }

    public Boolean closeSession() {
        return closeSession(null);
    }

    public Boolean closeSession(String sessionId) {
        try {
            if (StringUtils.isNotEmpty(sessionId)) {
                return sessionId.equals(RestExecutor.returnData(httpClient.closeSession(getTypeId(), reader.getId(), sessionId)));
            }
            else return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.closeSession(getTypeId(), reader.getId(), sessionId)));
        } catch (RestException ex) {
            throw ExceptionFactory.remoteLoadingContainerException("could not close session", ex);
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
