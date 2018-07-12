package com.t1t.t1c.containers.smartcards.ocra;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.ContainerVersion;
import com.t1t.t1c.containers.SmartCardContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclPace;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.PinUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class OcraContainer extends SmartCardContainer<OcraContainer, GclOcraRestClient, GclOcraAllData, AllCertificates> {

    public OcraContainer(LibConfig config, GclReader reader, String containerVersion, GclOcraRestClient httpClient) {
        super(config, reader, containerVersion, httpClient);
    }

    @Override
    public OcraContainer createInstance(LibConfig config, GclReader reader, String containerVersion, GclOcraRestClient httpClient, GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.containerVersion = new ContainerVersion(ContainerType.OCRA, containerVersion);
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Collections.singletonList("counter");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Collections.emptyList();
    }

    @Override
    public GclOcraAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getOcraAllData(getContainerVersionId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    @Override
    public Boolean verifyPin(String pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getContainerVersionId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String pin) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no signing capabilities");
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String pin) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no signing capabilities");
    }

    @Override
    public Class<GclOcraAllData> getAllDataClass() {
        return GclOcraAllData.class;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    public Long getChallengeOTP(String challenge, String pin) throws VerifyPinException, NoConsentException, RestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(challenge), "challenge must not be null or empty");
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            GclOcraChallengeData request = new GclOcraChallengeData().withChallenge(challenge);
            if (StringUtils.isNotEmpty(pin)) {
                request.setPin(pin);
            }
            return RestExecutor.returnData(httpClient.ocraChallenge(getContainerVersionId(), reader.getId(), request), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public Long getChallengeOTP(String challenge) throws VerifyPinException, NoConsentException, RestException {
        return getChallengeOTP(challenge, null);
    }

    public String readCounter(String pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            String pinToUse = PinUtil.getEncryptedPinIfPresent(pin);
            return RestExecutor.returnData(httpClient.readCounter(getContainerVersionId(), reader.getId(), pinToUse), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public String readCounter() throws VerifyPinException, NoConsentException, RestException {
        return readCounter(null);
    }

    @Override
    public ContainerData dumpData(String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        throw ExceptionFactory.unsupportedOperationException("Container does not implement data dump");
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide certificate chains");
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide certificate chains");
    }
}