package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MobibContainer extends GenericContainer<MobibContainer, GclMobibRestClient, GclMobibAllData, AllCertificates> {

    public MobibContainer(LibConfig config, GclReader reader, GclMobibRestClient httpClient) {
        super(config, reader, httpClient);
    }

    @Override
    public MobibContainer createInstance(LibConfig config, GclReader reader, GclMobibRestClient httpClient, GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.type = ContainerType.MOBIB;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("active", "card-issuing", "contracts", "picture");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Collections.emptyList();
    }

    @Override
    public GclMobibAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getMobibAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    @Override
    public Boolean verifyPin(String pin) throws RestException, NoConsentException, VerifyPinException {
        throw ExceptionFactory.unsupportedOperationException("container has no verify PIN capabilities");
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
    public ContainerType getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return type.getId();
    }

    @Override
    public Class<GclMobibAllData> getAllDataClass() {
        return GclMobibAllData.class;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    public Boolean getStatus() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getMobibStatus(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public String getPicture() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getMobibPicture(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public GclMobibCardIssuing getCardIssuing() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getMobibCardIssuing(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public List<GclMobibContract> getContracts() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getMobibContracts(getTypeId(), reader.getId()), config.isConsentRequired());
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
    public ContainerData dumpData(String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        GclMobibAllData allData = getAllData(true);

        data.setFullName(allData.getCardIssuing().getCardHolderName());
        data.setDateOfBirth(allData.getCardIssuing().getCardHolderBirthDate());

        data.setBase64Picture(allData.getPicture());
        data.setValidityStartDate(allData.getCardIssuing().getCardHolderStartDate());
        data.setValidityEndDate(allData.getCardIssuing().getCardExpirationDate());
        data.setDocumentId(allData.getCardIssuing().getCardHolderId());

        return data;
    }
}