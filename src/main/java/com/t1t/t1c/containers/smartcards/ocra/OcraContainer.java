package com.t1t.t1c.containers.smartcards.ocra;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.PinUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

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
    public GclOcraAllData getAllData() throws OcraContainerException {
        return getAllData(null, null);
    }

    @Override
    public GclOcraAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws OcraContainerException {
        try {
            return RestExecutor.returnData(httpClient.getOcraAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)));
        } catch (RestException ex) {
            throw ExceptionFactory.ocraContainerException("could not dump card data", ex);
        }
    }

    @Override
    public GclOcraAllData getAllData(Boolean... parseCertificates) throws OcraContainerException {
        return getAllData(null, null);
    }

    @Override
    public AllCertificates getAllCertificates() throws OcraContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws OcraContainerException {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws OcraContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public Boolean verifyPin(String... pin) throws OcraContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId())));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.ocraContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws OcraContainerException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws OcraContainerException {
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

    public Long getChallengeOTP(String challenge, String... pin) throws OcraContainerException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(challenge), "challenge must not be null or empty");
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            GclOcraChallengeData request = new GclOcraChallengeData().withChallenge(challenge);
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                request.setPin(pin[0]);
            }
            return RestExecutor.returnData(httpClient.ocraChallenge(getTypeId(), reader.getId(), request));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.ocraContainerException("Could not verify pin with container", ex);
        }
    }

    public String readCounter() throws OcraContainerException {
        try {
            return RestExecutor.returnData(httpClient.readCounter(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.ocraContainerException("could not retrieve read counter", ex);
        }
    }
}