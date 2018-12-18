package com.t1t.t1c.containers.smartcards.emv;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class EmvContainer extends SmartCardContainer<EmvContainer, GclEmvRestClient, GclEmvAllData, AllCertificates> {

    public EmvContainer(final LibConfig config, final GclReader reader, final String containerVersion, final GclEmvRestClient httpClient) {
        super(config, reader, containerVersion, httpClient);
    }

    @Override
    public EmvContainer createInstance(final LibConfig config, final GclReader reader, final String containerVersion, final GclEmvRestClient httpClient, final GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.containerVersion = new ContainerVersion(ContainerType.EMV, containerVersion);
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
    public GclEmvAllData getAllData(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getEmvAllData(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
    }

    @Override
    public AllCertificates getAllCertificates(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    @Override
    public Boolean verifyPin(final String pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
        } catch (final RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public String authenticate(final String data, final DigestAlgorithm algo, final String pin) throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no signing capabilities");
    }

    @Override
    public String sign(final String data, final DigestAlgorithm algo, final String pin) throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("container has no signing capabilities");
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
        return RestExecutor.returnData(httpClient.getEmvApplications(getContainerUrlId(), reader.getId()), config.isConsentRequired());
    }

    public GclEmvApplicationData getApplicationData() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getEmvApplicationData(getContainerUrlId(), reader.getId()), config.isConsentRequired());
    }

    public GclEmvPublicKeyCertificate getIssuerPublicKeyCertificate(final String aid) throws RestException, NoConsentException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(aid), "aid must not be null");
        return RestExecutor.returnData(httpClient.getEmvIssuerPublicKeyCertificate(getContainerUrlId(), reader.getId(), new GclEmvAidRequest().withAid(aid)), config.isConsentRequired());
    }

    public GclEmvPublicKeyCertificate getIccPublicKeyCertificate(final String aid) throws RestException, NoConsentException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(aid), "aid must not be null");
        return RestExecutor.returnData(httpClient.getEmvIccPublicKeyCertificate(getContainerUrlId(), reader.getId(), new GclEmvAidRequest().withAid(aid)), config.isConsentRequired());
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
    public ContainerData dumpData(final String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        final ContainerData data = new ContainerData();
        final GclEmvAllData allData = getAllData(true);

        data.setFullName(allData.getApplicationData().getName());

        data.setCountry(allData.getApplicationData().getCountry());

        data.setValidityStartDate(allData.getApplicationData().getEffectiveDate());
        data.setValidityEndDate(allData.getApplicationData().getExpirationDate());
        data.setDocumentId(allData.getApplicationData().getPan());

        return data;
    }
}