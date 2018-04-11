package com.t1t.t1c.core;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.exceptions.JsonConversionException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class Core extends AbstractCore {

    private static final Logger log = LoggerFactory.getLogger(Core.class);

    private GclRestClient gclRestClient;
    private GclAdminRestClient gclAdminRestClient;
    private GclCitrixRestClient gclCitrixRestClient;
    private LibConfig config;

    public Core(GclRestClient gclRestClient, GclAdminRestClient gclAdminRestClient, GclCitrixRestClient gclCitrixRestClient, LibConfig config) {
        this.gclAdminRestClient = gclAdminRestClient;
        this.gclRestClient = gclRestClient;
        this.config = config;
        this.gclCitrixRestClient = gclCitrixRestClient;
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
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.activate(), config.isConsentRequired()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error activating the GCL", ex);
        }
    }

    @Override
    public String getDsPubKey() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclAdminRestClient.getDsCertificate(), config.isConsentRequired());
        } catch (RestException ex) {
            GclError error = ex.getGclError();
            // If the error code returned is 201, that means the public has not been set (yet), return null
            if (error != null && error.getCode() == 201) {
                return null;
            }
            throw ExceptionFactory.gclCoreException("error retrieving GCL public key", ex);
        }
    }

    @Override
    public Boolean setDsPubKey(String publicKey) throws GclCoreException {
        try {
            GclUpdatePublicKeyRequest keyReq = new GclUpdatePublicKeyRequest();
            keyReq.setCertificate(publicKey);
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.setDsPublicKey(keyReq), config.isConsentRequired()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error setting GCL admin public key", ex);
        }
    }

    @Override
    public GclInfo getInfo() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclRestClient.getV1Status(), config.isConsentRequired());
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving GCL core info", ex);
        }
    }

    @Override
    public List<GclContainer> getContainers() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclRestClient.getV1Containers(), config.isConsentRequired());
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
            if (checkCitrix()) {
                return RestExecutor.returnData(gclCitrixRestClient.getCardReader(config.getAgentPort(), readerId), config.isConsentRequired());
            } else {
                return RestExecutor.returnData(gclRestClient.getCardReader(readerId), config.isConsentRequired());
            }
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error retrieving reader with id \"" + readerId + "\"", ex);
        }
    }

    @Override
    public List<GclReader> getReaders() throws GclCoreException {
        try {
            if (checkCitrix()) {
                return RestExecutor.returnData(gclCitrixRestClient.getCardReaders(config.getAgentPort()), config.isConsentRequired());
            } else {
                return RestExecutor.returnData(gclRestClient.getCardReaders(), config.isConsentRequired());
            }
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

    @Override
    public List<GclAgent> getAgents(Map<String, String> filterParams) throws GclCoreException {
        if (config.isCitrix()) {
            try {
                try {
                    return RestExecutor.returnData(gclCitrixRestClient.getAgents(filterParams), false);
                } catch (JsonConversionException ex) {
                    if (ex.isObjectInsteadOfArray()) {
                        return Collections.singletonList(RestExecutor.returnData(gclCitrixRestClient.getAgent(filterParams), false));
                    } else {
                        throw ExceptionFactory.gclCoreException("Error retrieving available agents", ex);
                    }
                }
            } catch (RestException ex) {
                throw ExceptionFactory.gclCoreException("Error retrieving available agents", ex);
            }
        } else {
            throw ExceptionFactory.unsupportedOperationException("Not a citrix environment");
        }
    }

    @Override
    public List<GclAgent> getAgents() throws GclCoreException {
        return getAgents(Collections.<String, String>emptyMap());
    }

    @Override
    public boolean getConsent(String title, String codeWord) throws GclCoreException {
        return getConsent(title, codeWord, config.getDefaultConsentDuration(), GclConsent.AlertLevel.WARNING, GclConsent.AlertPosition.STANDARD, GclConsent.Type.READER, config.getDefaultConsentTimeout());
    }

    @Override
    public boolean getConsent(final String title, final String codeWord, final Integer durationInDays, final GclConsent.AlertLevel alertLevel, final GclConsent.AlertPosition alertPosition, final GclConsent.Type consentType, final Integer timeoutInSeconds) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(title), "Title is required!");
        Preconditions.checkArgument(StringUtils.isNotEmpty(codeWord), "Code word is required!");
        Preconditions.checkArgument(timeoutInSeconds == null || timeoutInSeconds <= config.getDefaultConsentTimeout(), "Consent dialog timeout may not exceed default value!");
        GclConsent request = new GclConsent()
                .withTitle(title)
                .withText(codeWord)
                .withDays(getDurationInDays(durationInDays))
                .withAlertLevel(getAlertLevel(alertLevel))
                .withAlertPosition(getAlertPosition(alertPosition))
                .withType(getConsentType(consentType))
                .withTimeout(getTimeoutInSeconds(timeoutInSeconds));
        try {
            if (checkCitrix()) {
                return RestExecutor.returnData(gclCitrixRestClient.getConsent(config.getAgentPort(), request), false);
            } else {
                return RestExecutor.returnData(gclRestClient.getConsent(request), false);
            }
        } catch (RestException ex) {
            if (ex.getHttpCode().equals(404)) {
                throw ExceptionFactory.unsupportedOperationException("Consent functionality not available");
            }
            throw ExceptionFactory.gclCoreException("Failed to obtain consent: ", ex);
        }
    }

    private Integer getDurationInDays(final Integer durationInDays) {
        return durationInDays == null ? config.getDefaultConsentDuration() : durationInDays;
    }

    private Integer getTimeoutInSeconds(final Integer timeoutInSeconds) {
        return timeoutInSeconds == null ? config.getDefaultConsentTimeout() : timeoutInSeconds;
    }

    private GclConsent.AlertLevel getAlertLevel(final GclConsent.AlertLevel alertLevel) {
        return alertLevel == null ? GclConsent.AlertLevel.WARNING : alertLevel;
    }

    private GclConsent.AlertPosition getAlertPosition(final GclConsent.AlertPosition alertPosition) {
        return alertPosition == null ? GclConsent.AlertPosition.STANDARD : alertPosition;
    }

    private GclConsent.Type getConsentType(final GclConsent.Type consentType) {
        return consentType == null ? GclConsent.Type.READER : consentType;
    }

    private List<GclReader> getReaders(boolean cardInserted) {
        try {
            if (checkCitrix()) {
                return RestExecutor.returnData(gclCitrixRestClient.getCardInsertedReaders(config.getAgentPort(), cardInserted), config.isConsentRequired());
            } else {
                return RestExecutor.returnData(gclRestClient.getCardInsertedReaders(cardInserted), config.isConsentRequired());
            }
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

    private boolean checkCitrix() {
        return config.isCitrix() && config.getAgentPort() != null;
    }

/*    //TODO
    private void checkJwtValidity() throws GclCoreException {
        if (config.getEnvironment() != Environment.DEV) {
            JwtConsumer consumer = new JwtConsumerBuilder().setRequireExpirationTime().setSkipSignatureVerification().setSkipAllDefaultValidators().setDisableRequireSignature().setRelaxVerificationKeyValidation().build();
            String jwt = config.getGclJwt();
            if (StringUtils.isNotEmpty(jwt)) {
                try {
                    JwtContext context = consumer.process(jwt);
                    NumericDate refreshTreshold = NumericDate.now();
                    refreshTreshold.addSeconds(-240);
                    if (context.getJwtClaims().getExpirationTime().isOnOrAfter(refreshTreshold)) {
                        jwt = ConnectionFactory.getDsClient().refreshJWT(jwt);
                        if (StringUtils.isNotEmpty(jwt)) {
                            config.setGclJwt(jwt);
                            ConnectionFactory.setConfig(config);
                            setHttpClient(ConnectionFactory.getGclAdminRestClient());
                        }
                    }
                } catch (InvalidJwtException | MalformedClaimException ex) {
                    log.error("Token invalid: ", ex);
                    jwt = ConnectionFactory.getDsClient().getJWT();
                    if (StringUtils.isNotEmpty(jwt)) {
                        config.setGclJwt(jwt);
                        ConnectionFactory.setConfig(config);
                        setHttpClient(ConnectionFactory.getGclAdminRestClient());
                    }
                }
            } else {
                jwt = ConnectionFactory.getDsClient().getJWT();
                if (StringUtils.isNotEmpty(jwt)) {
                    config.setGclJwt(jwt);
                    ConnectionFactory.setConfig(config);
                    setHttpClient(ConnectionFactory.getGclAdminRestClient());
                }
            }
        }
    }*/
}
