package com.t1t.t1c.core;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.ContainerUtil;
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
    public PlatformInfo getPlatformInfo() throws GclCoreException {
        return new PlatformInfo();
    }

    @Override
    public String getVersion() throws GclCoreException {
        try {
            return getInfo().getVersion();
        } catch (GclCoreException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving version", ex);
        }
    }

    @Override
    public Boolean activate() throws GclCoreException {
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.activate()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error activating the GCL", ex);
        }
    }

    @Override
    public GclPubKey getPubKey() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclRestClient.getPublicKey());
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving GCL public key", ex);
        }
    }

    @Override
    public Boolean setPubKey(String publicKey) throws GclCoreException {
        try {
            GclUpdatePublicKeyRequest keyReq = new GclUpdatePublicKeyRequest();
            keyReq.setCertificate(publicKey);
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.setPublicKey(keyReq)));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error setting GCL admin public key", ex);
        }
    }

    @Override
    public GclStatus getInfo() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclRestClient.getV1Status());
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving GCL core info", ex);
        }
    }

    @Override
    public List<GclContainer> getContainers() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclRestClient.getContainers());
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving list of available containers", ex);
        }
    }

    @Override
    public GclReader pollCardInserted() throws GclCoreException {
        return pollCardInserted(null, null);
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds) throws GclCoreException {
        return pollCardInserted(pollIntervalInSeconds, null);
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException {
        List<GclReader> readers = pollReadersWithCards(pollIntervalInSeconds, pollTimeoutInSeconds);
        return CollectionUtils.isNotEmpty(readers) ? readers.get(0) : null;
    }

    @Override
    public List<GclReader> pollReadersWithCards() throws GclCoreException {
        return pollReadersWithCards(null, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws GclCoreException {
        return pollReadersWithCards(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = getReadersWithInsertedCard();
            if (CollectionUtils.isEmpty(readers)) {
                try {
                    Thread.sleep(pollInterval);
                    totalTime += pollInterval;
                } catch (InterruptedException ex) {
                    log.warn("error sleeping through polling interval: ", ex);
                }
            }
        }
        return readers;
    }

    @Override
    public List<GclReader> pollReaders() throws GclCoreException {
        return pollReaders(null, null);
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws GclCoreException {
        return pollReaders(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws GclCoreException {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = getReaders();
            if (CollectionUtils.isEmpty(readers)) {
                try {
                    Thread.sleep(pollInterval);
                    totalTime += pollInterval;
                } catch (InterruptedException ex) {
                    log.warn("error sleeping through polling interval: ", ex);
                }
            }
        }
        return readers;
    }

    @Override
    public List<GclReader> getAuthenticationCapableReaders() throws GclCoreException {
        List<GclReader> readers = new ArrayList<>();
        List<GclReader> readersWithCards = getReadersWithInsertedCard();
        for (GclReader reader : readersWithCards) {
            if (ContainerUtil.canAuthenticate(reader.getCard())) {
                readers.add(reader);
            }
        }
        return readers;
    }

    @Override
    public List<GclReader> getSignCapableReaders() throws GclCoreException {
        List<GclReader> readers = new ArrayList<>();
        List<GclReader> readersWithCards = getReadersWithInsertedCard();
        for (GclReader reader : readersWithCards) {
            if (ContainerUtil.canSign((reader.getCard()))) {
                readers.add(reader);
            }
        }
        return readers;
    }

    @Override
    public List<GclReader> getPinVerificationCapableReaders() throws GclCoreException {
        List<GclReader> readers = new ArrayList<>();
        List<GclReader> readersWithCards = getReadersWithInsertedCard();
        for (GclReader reader : readersWithCards) {
            if (ContainerUtil.canVerifyPin(reader.getCard())) {
                readers.add(reader);
            }
        }
        return readers;
    }

    @Override
    public GclReader getReader(String readerId) throws GclCoreException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(readerId), "Reader ID is required");
        try {
            return RestExecutor.returnData(gclRestClient.getCardReader(readerId));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving reader with id \"" + readerId + "\"", ex);
        }
    }

    @Override
    public List<GclReader> getReaders() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclRestClient.getCardReaders());
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving card readers", ex);
        }
    }

    @Override
    public List<GclReader> getReadersWithoutInsertedCard() throws GclCoreException {
        return getReaders(false);
    }

    @Override
    public List<GclReader> getReadersWithInsertedCard() throws GclCoreException {
        return getReaders(true);
    }

    private List<GclReader> getReaders(boolean cardInserted) {
        try {
            return RestExecutor.returnData(gclRestClient.getCardInsertedReaders(cardInserted));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving card readers without cards", ex);
        }
    }

    private int getPollingIntervalInMillis(Integer pollIntervalInSeconds) throws GclCoreException {
        int interval = config.getDefaultPollingIntervalInSeconds();
        if (pollIntervalInSeconds != null) {
            Preconditions.checkArgument(pollIntervalInSeconds > 0 && pollIntervalInSeconds < 60, "Polling interval must be a value between 0 & 60");
            interval = pollIntervalInSeconds;
        }
        return 1000 * interval;
    }

    private int getPollingTimeoutInMillis(Integer pollTimeoutInSeconds) throws GclCoreException {
        int timeout = config.getDefaultPollingTimeoutInSeconds();
        if (pollTimeoutInSeconds != null) {
            Preconditions.checkArgument(pollTimeoutInSeconds > 0 && pollTimeoutInSeconds < 60, "Polling timout must be a value between 0 & 60");
            timeout = pollTimeoutInSeconds;
        }
        return 1000 * timeout;
    }

/*    //TODO
    private void checkJwtValidity() throws GclCoreException {
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
