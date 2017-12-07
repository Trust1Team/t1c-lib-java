package com.t1t.t1c.containers.smartcards.emv;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class EmvContainer extends GenericContainer<EmvContainer, GclEmvRestClient, GclEmvAllData, AllCertificates> {

    public EmvContainer(LibConfig config, GclReader reader, GclEmvRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public EmvContainer createInstance(LibConfig config, GclReader reader, GclEmvRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.EMV;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("applications", "application-data");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Collections.emptyList();
    }

    @Override
    public GclEmvAllData getAllData() throws EmvContainerException {
        return getAllData(null, null);
    }

    @Override
    public GclEmvAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws EmvContainerException {
        try {
            return RestExecutor.returnData(httpClient.getEmvAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("could not dump card data", ex);
        }
    }

    @Override
    public GclEmvAllData getAllData(Boolean... parseCertificates) throws EmvContainerException {
        return getAllData(null, null);
    }

    @Override
    public AllCertificates getAllCertificates() throws EmvContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws EmvContainerException {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws EmvContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public Boolean verifyPin(String... pin) throws EmvContainerException, VerifyPinException {
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
            throw ExceptionFactory.emvContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws EmvContainerException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws EmvContainerException {
        throw ExceptionFactory.unsupportedOperationException("container has no sign capabilities");
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
    public Class<GclEmvAllData> getAllDataClass() {
        return GclEmvAllData.class;
    }

    @Override
    public Class<AllCertificates> getAllCertificateClass() {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    public List<GclEmvApplication> getApplications() {
        try {
            return RestExecutor.returnData(httpClient.getEmvApplications(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("could not retrieve applications", ex);
        }
    }

    public GclEmvApplicationData getApplicationData() {
        try {
            return RestExecutor.returnData(httpClient.getEmvApplicationData(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("could not retrieve application data", ex);
        }
    }

    public GclEmvPublicKeyCertificate getIssuerPublicKeyCertificate(String aid) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(aid), "aid must not be null");
            return RestExecutor.returnData(httpClient.getEmvIssuerPublicKeyCertificate(getTypeId(), reader.getId(), new GclEmvAidRequest().withAid(aid)));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("could not retrieve issuer public key certificate", ex);
        }
    }

    public GclEmvPublicKeyCertificate getIccPublicKeyCertificate(String aid) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(aid), "aid must not be null");
            return RestExecutor.returnData(httpClient.getEmvIccPublicKeyCertificate(getTypeId(), reader.getId(), new GclEmvAidRequest().withAid(aid)));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("could not retrieve ICC public key certificate", ex);
        }
    }
}