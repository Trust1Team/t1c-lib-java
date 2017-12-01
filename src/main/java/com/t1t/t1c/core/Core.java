package com.t1t.t1c.core;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.rest.RestExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class Core extends AbstractCore {
    private static final Logger log = LoggerFactory.getLogger(Core.class);
    private GclRestClient gclRestClient;
    private GclAdminRestClient gclAdminRestClient;
    private LibConfig config;
    public Core(GclRestClient gclRestClient, GclAdminRestClient gclAdminRestClient, LibConfig config) {
        this.gclAdminRestClient = gclAdminRestClient;
        this.gclRestClient = gclRestClient;
        this.config = config;
    }

    @Override
    public PlatformInfo getPlatformInfo() throws RestException {
        return new PlatformInfo();
    }

    @Override
    public String getVersion() throws RestException {
        GclStatus data = RestExecutor.executeCall(gclRestClient.getV1Status()).getData();
        return data.getVersion();
    }

    @Override
    public Boolean activate() throws RestException {
        return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.activate()));
    }

    @Override
    public String getPubKey() throws RestException {
        return RestExecutor.executeCall(gclRestClient.getPublicKey()).getData();
    }

    @Override
    public Boolean setPubKey(String publicKey) throws RestException {
        GclUpdatePublicKeyRequest keyReq = new GclUpdatePublicKeyRequest();
        keyReq.setCertificate(publicKey);
        return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.setPublicKey(keyReq)));
    }

    @Override
    public GclStatus getInfo() throws RestException {
        return RestExecutor.executeCall(gclRestClient.getV1Status()).getData();
    }

    @Override
    public List<GclContainer> getContainers() throws RestException {
        return RestExecutor.executeCall(gclRestClient.getContainers()).getData();
    }

    @Override
    public GclReader pollCardInserted() throws RestException {
        return pollCardInserted(null, null);
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds) throws RestException {
        return pollCardInserted(pollIntervalInSeconds, null);
    }

    @Override
    public GclReader pollCardInserted (Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds)  throws RestException{
        List<GclReader> readers = pollReadersWithCards(pollIntervalInSeconds, pollTimeoutInSeconds);
        return CollectionUtils.isNotEmpty(readers) ? readers.get(0) : null;
    }

    @Override
    public List<GclReader> pollReadersWithCards() throws RestException {
        return pollReadersWithCards(null, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws RestException{
        return pollReadersWithCards(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws RestException {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = RestExecutor.executeCall(gclRestClient.getCardInsertedReaders(true)).getData();
        }
        return readers;
    }

    @Override
    public List<GclReader> pollReaders() throws RestException {
        return pollReaders(null, null);
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws RestException{
        return pollReaders(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws RestException {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = RestExecutor.executeCall(gclRestClient.getCardReaders()).getData();
            if (readers == null) {
                throw ExceptionFactory.gclClientException("GCL not found");
            }
            if (readers.isEmpty()) {
                try {
                    Thread.sleep(pollInterval);
                    totalTime += pollInterval;
                } catch (InterruptedException ex) {
                    log.error("Thread sleep interrupted: ", ex);
                    break;
                }
            }
        }
        return readers;
    }

    @Override
    public List<GclReader> getAuthenticationCapableReaders() throws RestException {
        //TODO
        return null;
    }

    @Override
    public List<GclReader> getSignCapableReaders() throws RestException {
        //TODO
        return null;
    }

    @Override
    public List<GclReader> getPinVerificationCapableReaders() throws RestException {
        //TODO
        return null;
    }

    @Override
    public GclReader getReader(String readerId)  throws RestException{
        Preconditions.checkArgument(StringUtils.isNotEmpty(readerId), "Reader ID is required");
        return RestExecutor.executeCall(gclRestClient.getCardReader(readerId)).getData();
    }

    @Override
    public List<GclReader> getReaders() throws RestException {
        return RestExecutor.executeCall(gclRestClient.getCardReaders()).getData();
    }

    @Override
    public List<GclReader> getReadersWithoutInsertedCard()  throws RestException{
        return RestExecutor.executeCall(gclRestClient.getCardInsertedReaders(false)).getData();
    }

    private int getPollingIntervalInMillis(Integer pollIntervalInSeconds) throws RestException {
        Preconditions.checkArgument(pollIntervalInSeconds == null || pollIntervalInSeconds > 0, "Polling interval must be greater than 0");
        return 1000 * config.getDefaultPollingTimeoutInSeconds();
    }

    private int getPollingTimeoutInMillis(Integer pollTimeoutInSeconds) throws RestException {
        Preconditions.checkArgument(pollTimeoutInSeconds == null || (pollTimeoutInSeconds > 0 && pollTimeoutInSeconds < 60), "Polling timout must be a value between 0 & 60");
        return 1000 * config.getDefaultPollingTimeoutInSeconds();
    }

/*    //TODO
    private void checkJwtValidity() throws RestException {
        if (config.getEnvironment() != Environment.DEV) {
            JwtConsumer consumer = new JwtConsumerBuilder().setRequireExpirationTime().setSkipSignatureVerification().setSkipAllDefaultValidators().setDisableRequireSignature().setRelaxVerificationKeyValidation().build();
            String jwt = config.getJwt();
            if (StringUtils.isNotEmpty(jwt)) {
                try {
                    JwtContext context = consumer.process(jwt);
                    NumericDate refreshTreshold = NumericDate.now();
                    refreshTreshold.addSeconds(-240);
                    if (context.getJwtClaims().getExpirationTime().isOnOrAfter(refreshTreshold)) {
                        jwt = ConnectionFactory.getDsClient().refreshJWT(jwt);
                        if (StringUtils.isNotEmpty(jwt)) {
                            config.setJwt(jwt);
                            ConnectionFactory.setConfig(config);
                            setHttpClient(ConnectionFactory.getGclAdminRestClient());
                        }
                    }
                } catch (InvalidJwtException | MalformedClaimException ex) {
                    log.error("Token invalid: ", ex);
                    jwt = ConnectionFactory.getDsClient().getJWT();
                    if (StringUtils.isNotEmpty(jwt)) {
                        config.setJwt(jwt);
                        ConnectionFactory.setConfig(config);
                        setHttpClient(ConnectionFactory.getGclAdminRestClient());
                    }
                }
            } else {
                jwt = ConnectionFactory.getDsClient().getJWT();
                if (StringUtils.isNotEmpty(jwt)) {
                    config.setJwt(jwt);
                    ConnectionFactory.setConfig(config);
                    setHttpClient(ConnectionFactory.getGclAdminRestClient());
                }
            }
        }
    }*/
}
