package com.t1t.t1c.containers.smartcards.emv;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class EmvContainer extends GenericContainer<EmvContainer, GclEmvRestClient, GclEmvAllData, AllCertificates> {

    public EmvContainer(LibConfig config, GclReader reader, GclEmvRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public EmvContainer createInstance(LibConfig config, GclReader reader, GclEmvRestClient httpClient, String pacePin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pacePin = pacePin;
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
    public GclEmvAllData getAllData() throws RestException, NoConsentException {
        return getAllData(null, null);
    }

    @Override
    public GclEmvAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getEmvAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
    }

    @Override
    public GclEmvAllData getAllData(Boolean... parseCertificates) throws RestException, NoConsentException {
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
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId()), config.isConsentRequired()));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, NoConsentException, RestException {
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
    public Class<AllCertificates> getAllCertificatesClass() {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    public List<GclEmvApplication> getApplications() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getEmvApplications(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public GclEmvApplicationData getApplicationData() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getEmvApplicationData(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public GclEmvPublicKeyCertificate getIssuerPublicKeyCertificate(String aid) throws RestException, NoConsentException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(aid), "aid must not be null");
        return RestExecutor.returnData(httpClient.getEmvIssuerPublicKeyCertificate(getTypeId(), reader.getId(), new GclEmvAidRequest().withAid(aid)), config.isConsentRequired());
    }

    public GclEmvPublicKeyCertificate getIccPublicKeyCertificate(String aid) throws RestException, NoConsentException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(aid), "aid must not be null");
        return RestExecutor.returnData(httpClient.getEmvIccPublicKeyCertificate(getTypeId(), reader.getId(), new GclEmvAidRequest().withAid(aid)), config.isConsentRequired());
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide certificate chains");
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide certificate chains");
    }

    @Override
    public ContainerData dumpData(String... pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        GclEmvAllData allData = getAllData(true);

        data.setFullName(allData.getApplicationData().getName());

        data.setCountry(allData.getApplicationData().getCountry());

        data.setValidityStartDate(allData.getApplicationData().getEffectiveDate());
        data.setValidityEndDate(allData.getApplicationData().getExpirationDate());
        data.setDocumentId(allData.getApplicationData().getPan());

        return data;
    }
}