package com.t1t.t1c.core;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import com.t1t.t1c.rest.RestExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
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
    public Core(GclRestClient gclRestClient, GclAdminRestClient gclAdminRestClient) {
        this.gclAdminRestClient = gclAdminRestClient;
        this.gclRestClient = gclRestClient;
    }

    @Override
    public PlatformInfo getPlatformInfo() {
        return new PlatformInfo();
    }

    @Override
    public String getVersion() {
        RestExecutor.executeCall(gclRestClient.getV1Status());
    }

    @Override
    public Boolean activate() {
        return connFactory.getGclAdminClient().activate();
    }

    @Override
    public String getPubKey() {
        return connFactory.getGclAdminClient().getPublicKey();
    }

    @Override
    public void setPubKey(String publicKey) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(publicKey), "Public key must be provided");
        connFactory.getGclAdminClient().setPublicKey(publicKey);
    }

    @Override
    public GclStatus getInfo() {
        return connFactory.getGclClient().getInfo();
    }

    @Override
    public List<GclContainer> getContainers() {
        return connFactory.getGclClient().getContainers();
    }

    @Override
    public GclReader pollCardInserted() {
        return pollCardInserted(null, null);
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds) {
        return pollCardInserted(pollIntervalInSeconds, null);
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) {
        List<GclReader> readers = pollReadersWithCards(pollIntervalInSeconds, pollTimeoutInSeconds);
        return CollectionUtils.isNotEmpty(readers) ? readers.get(0) : null;
    }

    @Override
    public List<GclReader> pollReadersWithCards() {
        return pollReadersWithCards(null, null);
    }

    @Override
    public List<GclReader> pollReaders() {
        return pollReaders(null, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) {
        return pollReadersWithCards(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds) {
        return pollReaders(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = connFactory.getGclClient().getReadersWithInsertedCard();
        }
        return readers;
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = connFactory.getGclClient().getReaders();
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
    public GclReader getReader(String readerId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(readerId), "Reader ID is required");
        return connFactory.getGclClient().getReader(readerId);
    }

    @Override
    public List<GclReader> getReaders() {
        return connFactory.getGclClient().getReaders();
    }

    @Override
    public List<GclReader> getReadersWithoutInsertedCard() {
        return connFactory.getGclClient().getReadersWithoutInsertedCard();
    }

    @Override
    public List<GclReader> getReadersWithInsertedCard() {
        return connFactory.getGclClient().getReadersWithInsertedCard();
    }

    @Override
    public String getUrl() {
        return connFactory.getConfig().getGclClientUri();
    }

    private int getPollingIntervalInMillis(Integer pollIntervalInSeconds) {
        Preconditions.checkArgument(pollIntervalInSeconds == null || pollIntervalInSeconds > 0, "Polling interval must be greater than 0");
        return 1000 * connFactory.getConfig().getDefaultPollingTimeoutInSeconds();
    }

    private int getPollingTimeoutInMillis(Integer pollTimeoutInSeconds) {
        Preconditions.checkArgument(pollTimeoutInSeconds == null || (pollTimeoutInSeconds > 0 && pollTimeoutInSeconds < 60), "Polling timout must be a value between 0 & 60");
        return 1000 * connFactory.getConfig().getDefaultPollingTimeoutInSeconds();
    }

    //TODO
    private void checkJwtValidity() {
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
    }
}
