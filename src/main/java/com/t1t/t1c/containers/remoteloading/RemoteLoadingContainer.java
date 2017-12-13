package com.t1t.t1c.containers.remoteloading;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.FunctionalContainer;
import com.t1t.t1c.core.GclReader;
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

    public String getAtr(String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.getAtr(getTypeId(), reader.getId(), sessionId));
    }

    public GclRemoteLoadingCommand executeApduCall(GclRemoteLoadingApdu apdu) throws RestException {
        return executeApduCall(apdu, null);
    }

    public GclRemoteLoadingCommand executeApduCall(GclRemoteLoadingApdu apdu, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeApduCall(getTypeId(), reader.getId(), apdu, sessionId));
    }

    public List<GclRemoteLoadingCommand> executeApduCalls(List<GclRemoteLoadingApdu> apdus) throws RestException {
        return executeApduCalls(apdus, null);
    }

    public List<GclRemoteLoadingCommand> executeApduCalls(List<GclRemoteLoadingApdu> apdus, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeApduCalls(getTypeId(), reader.getId(), apdus, sessionId));
    }

    public GclRemoteLoadingCommand executeCcid(String feature, String apdu) throws RestException {
        return executeCcid(feature, apdu, null);
    }

    public GclRemoteLoadingCommand executeCcid(String feature, String apdu, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeCcid(getTypeId(), reader.getId(), new GclRemoteLoadingCcidRequest().withApdu(apdu).withFeature(feature), sessionId));
    }

    public List<GclRemoteLoadingCcidFeature> getCcidFeatures() throws RestException {
        return getCcidFeatures(null);
    }

    public List<GclRemoteLoadingCcidFeature> getCcidFeatures(String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.getCcidFeatures(getTypeId(), reader.getId(), sessionId));
    }

    public GclRemoteLoadingCommand executeCommand(String command) throws RestException {
        return executeCommand(command, null);
    }

    public GclRemoteLoadingCommand executeCommand(String command, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeCommand(getTypeId(), reader.getId(), new GclRemoteLoadingCommandRequest().withTx(command), sessionId));
    }

    public List<GclRemoteLoadingCommand> executeCommands(List<String> commands) throws RestException {
        return executeCommands(commands, null);
    }

    public List<GclRemoteLoadingCommand> executeCommands(List<String> commands, String sessionId) throws RestException {
        List<GclRemoteLoadingCommandRequest> commandRequests = new ArrayList<>();
        for (String command : commands) {
            commandRequests.add(new GclRemoteLoadingCommandRequest().withTx(command));
        }
        return RestExecutor.returnData(httpClient.executeCommands(getTypeId(), reader.getId(), commandRequests, sessionId));
    }

    public Boolean isCardPresent() throws RestException {
        return isCardPresent(null);
    }

    public Boolean isCardPresent(String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.isPresent(getTypeId(), reader.getId(), sessionId));
    }

    public String openSession(Integer timeout) throws RestException {
        Integer timeoutToUse;
        if (timeout != null) {
            Preconditions.checkArgument(timeout > 0, "timeout value must be greater than 0");
            timeoutToUse = timeout;
        } else {
            timeoutToUse = config.getSessionTimeout();
        }
        return RestExecutor.returnData(httpClient.openSession(getTypeId(), reader.getId(), new GclRemoteLoadingOpenSessionRequest().withTimeout(timeoutToUse)));
    }

    public Boolean closeSession() throws RestException {
        return closeSession(null);
    }

    public Boolean closeSession(String sessionId) throws RestException {
        if (StringUtils.isNotEmpty(sessionId)) {
            return sessionId.equals(RestExecutor.returnData(httpClient.closeSession(getTypeId(), reader.getId(), sessionId)));
        } else {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.closeSession(getTypeId(), reader.getId(), sessionId)));
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
