package com.t1t.t1c.containers.smartcards.ocra;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
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
public class OcraContainer extends GenericContainer<OcraContainer, GclOcraRestClient, GclOcraAllData, AllCertificates> {

    public OcraContainer(LibConfig config, GclReader reader, GclOcraRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public OcraContainer createInstance(LibConfig config, GclReader reader, GclOcraRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.OCRA;
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
    public GclOcraAllData getAllData() throws RestException, NoConsentException {
        return getAllData(null, null);
    }

    @Override
    public GclOcraAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getOcraAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
    }

    @Override
    public GclOcraAllData getAllData(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllData(null, null);
    }

    @Override
    public AllCertificates getAllCertificates() throws RestException, NoConsentException {
        return getAllCertificates(null, null);
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllCertificates(null, null);
    }

    @Override
    public Boolean verifyPin(String... pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0])), config.isConsentRequired()));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId()), config.isConsentRequired()));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no signing capabilities");
    }

    @Override
    public ContainerType getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return type.getId();
    }

    @Override
    public Class<GclOcraAllData> getAllDataClass() {
        return GclOcraAllData.class;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    public Long getChallengeOTP(String challenge, String... pin) throws VerifyPinException, NoConsentException, RestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(challenge), "challenge must not be null or empty");
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            GclOcraChallengeData request = new GclOcraChallengeData().withChallenge(challenge);
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                request.setPin(pin[0]);
            }
            return RestExecutor.returnData(httpClient.ocraChallenge(getTypeId(), reader.getId(), request), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public String readCounter(String... pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            String pinToUse = pin != null && pin.length > 0 ? pin[0] : null;
            return RestExecutor.returnData(httpClient.readCounter(getTypeId(), reader.getId(), pinToUse), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public ContainerData dumpData(String... pin) throws RestException, NoConsentException, UnsupportedOperationException {
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