package com.t1t.t1c.containers.functional.readerapi;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.ContainerVersion;
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
public class ReaderApiContainer extends FunctionalContainer<ReaderApiContainer, GclReaderApiRestClient> {

    public ReaderApiContainer(LibConfig config, GclReader reader, String containerVersion, GclReaderApiRestClient httpClient) {
        super(config, reader, containerVersion, httpClient, null);
    }

    @Override
    public ReaderApiContainer createInstance(LibConfig config, GclReader reader, String containerVersion, GclReaderApiRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.containerVersion = new ContainerVersion(ContainerType.READER_API, containerVersion);
        return this;
    }

    public String getAtr() {
        return getAtr(null);
    }

    public String getAtr(String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.getAtr(getContainerUrlId(), reader.getId(), sessionId), config.isConsentRequired());
    }

    public GclReaderApiCommand executeApduCall(GclReaderApiApdu apdu) throws RestException {
        return executeApduCall(apdu, null);
    }

    public GclReaderApiCommand executeApduCall(GclReaderApiApdu apdu, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeApduCall(getContainerUrlId(), reader.getId(), apdu, sessionId), config.isConsentRequired());
    }

    public List<GclReaderApiCommand> executeApduCalls(List<GclReaderApiApdu> apdus) throws RestException {
        return executeApduCalls(apdus, null);
    }

    public List<GclReaderApiCommand> executeApduCalls(List<GclReaderApiApdu> apdus, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeApduCalls(getContainerUrlId(), reader.getId(), apdus, sessionId), config.isConsentRequired());
    }

    public GclReaderApiCommand executeCcid(String feature, String apdu) throws RestException {
        return executeCcid(feature, apdu, null);
    }

    public GclReaderApiCommand executeCcid(String feature, String apdu, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeCcid(getContainerUrlId(), reader.getId(), new GclReaderApiCcidRequest().withApdu(apdu).withFeature(feature), sessionId), config.isConsentRequired());
    }

    public List<GclReaderApiCcidFeature> getCcidFeatures() throws RestException {
        return getCcidFeatures(null);
    }

    public List<GclReaderApiCcidFeature> getCcidFeatures(String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.getCcidFeatures(getContainerUrlId(), reader.getId(), sessionId), config.isConsentRequired());
    }

    public GclReaderApiCommand executeCommand(String command) throws RestException {
        return executeCommand(command, null);
    }

    public GclReaderApiCommand executeCommand(String command, String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.executeCommand(getContainerUrlId(), reader.getId(), new GclReaderApiCommandRequest().withTx(command), sessionId), config.isConsentRequired());
    }

    public List<GclReaderApiCommand> executeCommands(List<String> commands) throws RestException {
        return executeCommands(commands, null);
    }

    public List<GclReaderApiCommand> executeCommands(List<String> commands, String sessionId) throws RestException {
        List<GclReaderApiCommandRequest> commandRequests = new ArrayList<>();
        for (String command : commands) {
            commandRequests.add(new GclReaderApiCommandRequest().withTx(command));
        }
        return RestExecutor.returnData(httpClient.executeCommands(getContainerUrlId(), reader.getId(), commandRequests, sessionId), config.isConsentRequired());
    }

    public Boolean isCardPresent() throws RestException {
        return isCardPresent(null);
    }

    public Boolean isCardPresent(String sessionId) throws RestException {
        return RestExecutor.returnData(httpClient.isPresent(getContainerUrlId(), reader.getId(), sessionId), config.isConsentRequired());
    }

    public String openSession(Integer timeout) throws RestException {
        Long timeoutToUse;
        if (timeout != null) {
            Preconditions.checkArgument(timeout > 0, "timeout value must be greater than 0");
            timeoutToUse = timeout.longValue();
        } else {
            timeoutToUse = config.getSessionTimeout().longValue();
        }
        return RestExecutor.returnData(httpClient.openSession(getContainerUrlId(), reader.getId(), new GclReaderApiOpenSessionRequest().withTimeout(timeoutToUse)), config.isConsentRequired());
    }

    public Boolean closeSession() throws RestException {
        return closeSession(null);
    }

    public Boolean closeSession(String sessionId) throws RestException {
        if (StringUtils.isNotEmpty(sessionId)) {
            return sessionId.equals(RestExecutor.returnData(httpClient.closeSession(getContainerUrlId(), reader.getId(), sessionId), config.isConsentRequired()));
        } else {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.closeSession(getContainerUrlId(), reader.getId(), sessionId), config.isConsentRequired()));
        }
    }
}
