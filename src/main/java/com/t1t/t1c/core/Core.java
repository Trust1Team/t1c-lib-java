package com.t1t.t1c.core;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.ds.DsAtrList;
import com.t1t.t1c.ds.DsContainerResponse;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclCoreException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.T1cAdminPublicKeys;
import com.t1t.t1c.model.T1cPublicKey;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.ClipboardUtil;
import com.t1t.t1c.utils.ContainerUtil;
import com.t1t.t1c.utils.CryptUtil;
import com.t1t.t1c.utils.PkiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.datatransfer.Transferable;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class Core extends AbstractCore {

    private static final Logger log = LoggerFactory.getLogger(Core.class);

    private static final Integer DOWNLOAD_STATUS_POLL_INTERVAL = 250;

    private static final String USER_PROP_NAME = "user.name";

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
        } catch (RestException ex) {
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
    public T1cPublicKey getDevicePubKey() throws GclCoreException {
        return getDevicePubKey(null);
    }

    @Override
    public T1cPublicKey getDevicePubKey(Boolean parse) throws GclCoreException {
        try {
            return PkiUtil.createT1cPublicKey(RestExecutor.returnData(gclAdminRestClient.getDeviceCertificate(), config.isConsentRequired()), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("Error retrieving device public key", ex);
        }
    }

    @Override
    public T1cPublicKey getSslPubKey() throws GclCoreException {
        return getSslPubKey(null);
    }

    @Override
    public T1cPublicKey getSslPubKey(Boolean parse) throws GclCoreException {
        try {
            return PkiUtil.createT1cPublicKey(RestExecutor.returnData(gclAdminRestClient.getSslCertificate(), config.isConsentRequired()), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("Error retrieving SSL public key", ex);
        }
    }

    @Override
    public Map<String, T1cPublicKey> getDsPubKeys() throws GclCoreException {
        return getDsPubKeys(null);
    }

    @Override
    public Map<String, T1cPublicKey> getDsPubKeys(Boolean parse) throws GclCoreException {
        try {
            List<GclDsPublicKey> keys = RestExecutor.returnData(gclAdminRestClient.getDsCertificates(), config.isConsentRequired());
            Map<String, T1cPublicKey> pubKeys = new HashMap<>();
            for (GclDsPublicKey pb : keys) {
                pubKeys.put(pb.getNs(), PkiUtil.createT1cPublicKey(pb.getBase64(), parse));
            }
            return pubKeys;
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
    public T1cAdminPublicKeys getAdminPublicKeys() throws GclCoreException {
        return getAdminPublicKeys(null);
    }

    @Override
    public T1cAdminPublicKeys getAdminPublicKeys(Boolean parse) throws GclCoreException {
        try {
            return new T1cAdminPublicKeys(RestExecutor.returnData(gclAdminRestClient.getCertificates(), config.isConsentRequired()), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("Error retrieving admin certificates", ex);
        }
    }

    @Override
    public Boolean setDsPubKey(String encryptedPublicKey, String encryptedAesKey, String namespace) throws GclCoreException {
        try {
            GclUpdatePublicKeyRequest keyReq = new GclUpdatePublicKeyRequest()
                    .withEncryptedPublicKey(encryptedPublicKey)
                    .withEncryptedAesKey(encryptedAesKey)
                    .withNs(namespace);
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.setDsPublicKey(keyReq), config.isConsentRequired()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("error setting GCL admin public key", ex);
        }
    }

    @Override
    public GclInfo getInfo() throws GclCoreException {
        try {
            return RestExecutor.returnData(gclRestClient.getStatus(), config.isConsentRequired());
        } catch (RestException ex) {
            if (ex.getCause() instanceof ConnectException) {
                throw ExceptionFactory.gclCoreException("GCL core not found at " + config.getGclClientUri(), ex);
            }
            throw ExceptionFactory.gclCoreException("error retrieving GCL core info", ex);
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
    public List<GclAgent> resolveAgent() throws GclCoreException {
        String encryptedUsername = CryptUtil.encrypt(System.getProperty(USER_PROP_NAME)) + "blah";
        return getAgents(encryptedUsername);
    }

    @Override
    public List<GclAgent> resolveAgent(String challenge) throws GclCoreException {
        if (config.isCitrix()) {
            try {
                Transferable clipboardBackup = ClipboardUtil.saveStringToClipboard(challenge);
                List<GclAgent> agents = RestExecutor.returnData(gclCitrixRestClient.resolveAgent(new GclAgentResolutionRequest().withChallenge(challenge)), false);
                ClipboardUtil.setClipboarContents(clipboardBackup);
                return agents;
            } catch (RestException ex) {
                throw ExceptionFactory.gclCoreException("Error retrieving available agents", ex);
            }
        } else {
            throw ExceptionFactory.unsupportedOperationException("Not a citrix environment");
        }
    }

    @Override
    public List<GclAgent> getAgents(String usernameToFilter) throws GclCoreException {
        if (config.isCitrix()) {
            try {
                return RestExecutor.returnData(gclCitrixRestClient.getAgents(new GclAgentRequestFilter().withUsername(usernameToFilter)), false);
            } catch (RestException ex) {
                throw ExceptionFactory.gclCoreException("Error retrieving available agents", ex);
            }
        } else {
            throw ExceptionFactory.unsupportedOperationException("Not a citrix environment");
        }
    }

    @Override
    public List<GclAgent> getAgents() throws GclCoreException {
        return getAgents(null);
    }

    @Override
    public boolean getConsent(String title, String codeWord) throws GclCoreException {
        return getConsent(title, codeWord, config.getDefaultConsentDuration(), GclAlertLevel.WARNING, GclAlertPosition.STANDARD, GclConsentType.READER, config.getDefaultConsentTimeout());
    }

    @Override
    public boolean getConsent(final String title, final String codeWord, final Integer durationInDays, final GclAlertLevel alertLevel, final GclAlertPosition alertPosition, final GclConsentType consentType, final Integer timeoutInSeconds) {
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

    @Override
    public boolean loadContainers(List<DsContainerResponse> containerResponses) throws GclCoreException {
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.loadContainers(new GclLoadContainersRequest().withContainerResponses(containerResponses))));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("Failed to load containers: ", ex);
        }
    }

    @Override
    public boolean loadAtrList(DsAtrList atrList) throws GclCoreException {
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.loadAtrList(atrList)));
        } catch (RestException ex) {
            throw ExceptionFactory.gclCoreException("Failed to load ATR list: ", ex);
        }
    }

    @Override
    public GclInfo pollContainerDownloadStatus(final List<DsContainerResponse> containers) throws GclCoreException {
        int totalTime = 0;
        int pollTimeout = getDownloadStatusPollingTimeoutInMillis();
        boolean downloadsComplete;
        do {
            GclInfo info = getInfo();
            downloadsComplete = checkIfDownloadsCompleted(info.getContainers(), containers);
            if (!downloadsComplete) {
                try {
                    Thread.sleep(DOWNLOAD_STATUS_POLL_INTERVAL);
                    totalTime += DOWNLOAD_STATUS_POLL_INTERVAL;
                } catch (InterruptedException ex) {
                    log.warn("error sleeping through polling interval: ", ex);
                }
            }
        } while (!downloadsComplete && totalTime < pollTimeout);
        if (!downloadsComplete) {
            throw ExceptionFactory.containerLoadingTimeoutExceeded();
        }
        return getInfo();
    }

    private boolean checkIfDownloadsCompleted(List<GclContainerInfo> currentContainers, List<DsContainerResponse> containersToLoad) {
        boolean completed;
        int errored = 0;
        int missing = 0;
        int ongoing = 0;
        int installed = 0;
        for (DsContainerResponse ctl : containersToLoad) {
            boolean found = false;
            for (GclContainerInfo cc : currentContainers) {
                if (ctl.getName().equalsIgnoreCase(cc.getName()) && ctl.getVersion().equalsIgnoreCase(cc.getVersion())) {
                    found = true;
                    switch (cc.getStatus()) {
                        case ERROR:
                        case DOWNLOAD_ERROR:
                            errored++;
                            break;
                        case INIT:
                        case DOWNLOADING:
                            ongoing++;
                            break;
                        case INSTALLED:
                            installed++;
                            break;
                    }
                }
            }
            if (!found) {
                missing++;
            }
        }
        if (errored > 0 || missing > 0) {
            throw ExceptionFactory.containerLoadingFailed(currentContainers);
        }
        completed = ongoing == 0 && installed == containersToLoad.size();
        return completed;
    }

    private Long getDurationInDays(final Integer durationInDays) {
        return durationInDays == null ? config.getDefaultConsentDuration().longValue() : durationInDays.longValue();
    }

    private Long getTimeoutInSeconds(final Integer timeoutInSeconds) {
        return timeoutInSeconds == null ? config.getDefaultConsentTimeout().longValue() : timeoutInSeconds.longValue();
    }

    private GclAlertLevel getAlertLevel(final GclAlertLevel alertLevel) {
        return alertLevel == null ? GclAlertLevel.WARNING : alertLevel;
    }

    private GclAlertPosition getAlertPosition(final GclAlertPosition alertPosition) {
        return alertPosition == null ? GclAlertPosition.STANDARD : alertPosition;
    }

    private GclConsentType getConsentType(final GclConsentType consentType) {
        return consentType == null ? GclConsentType.READER : consentType;
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

    private int getPollingIntervalInMillis(Integer pollIntervalInSeconds) {
        int interval = config.getDefaultPollingIntervalInSeconds();
        if (pollIntervalInSeconds != null) {
            Preconditions.checkArgument(pollIntervalInSeconds > 0 && pollIntervalInSeconds < 60, "Polling interval must be a value between 0 & 60");
            interval = pollIntervalInSeconds;
        }
        return 1000 * interval;
    }

    private int getPollingTimeoutInMillis(Integer pollTimeoutInSeconds) {
        int timeout = config.getDefaultPollingTimeoutInSeconds();
        if (pollTimeoutInSeconds != null) {
            Preconditions.checkArgument(pollTimeoutInSeconds > 0 && pollTimeoutInSeconds < 60, "Polling timout must be a value between 0 & 60");
            timeout = pollTimeoutInSeconds;
        }
        return 1000 * timeout;
    }

    private int getDownloadStatusPollingTimeoutInMillis() {
        int timeout = config.getContainerDownloadTimeout();
        return 1000 * timeout;
    }

    private boolean checkCitrix() {
        return config.isCitrix() && config.getAgentPort() != null;
    }
}
