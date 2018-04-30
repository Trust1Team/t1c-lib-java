package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclPace;
import com.t1t.t1c.core.GclPrivateKeyReference;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.PinUtil;
import com.t1t.t1c.utils.PkiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class OberthurContainer extends GenericContainer<OberthurContainer, GclOberthurRestClient, OberthurAllData, OberthurAllData> {

    public OberthurContainer(LibConfig config, GclReader reader, GclOberthurRestClient httpClient) {
        super(config, reader, httpClient);
    }

    @Override
    public OberthurContainer createInstance(LibConfig config, GclReader reader, GclOberthurRestClient httpClient, GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.type = ContainerType.OBERTHUR;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("root-certificate", "authentication-certificate",
                "encryption-certificate", "issuer-certificate", "signing-certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return getAllDataFilters();
    }

    @Override
    public OberthurAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws GenericContainerException {
        return new OberthurAllData(RestExecutor.returnData(httpClient.getAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public OberthurAllData getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws GenericContainerException {
        return new OberthurAllData(RestExecutor.returnData(httpClient.getAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public Boolean verifyPin(String pin) throws GenericContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            if (StringUtils.isNotEmpty(pin)) {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), GclPrivateKeyReference.SIGN, pin)), config.isConsentRequired()));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId()), config.isConsentRequired()));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.authenticateAlgos)) {
            try {
                this.authenticateAlgos = RestExecutor.returnData(httpClient.getAvailableAuthenticateAlgos(), config.isConsentRequired());
            } catch (RestException ex) {
                //Fall back to the container default
                this.authenticateAlgos = Arrays.asList(DigestAlgorithm.SHA1, DigestAlgorithm.SHA256);
            }
        }
        return this.authenticateAlgos;
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String pin) throws GenericContainerException {
        try {
            isAuthenticateAlgorithmSupported(algo);
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.signAlgos)) {
            try {
                this.signAlgos = RestExecutor.returnData(httpClient.getAvailableSignAlgos(), config.isConsentRequired());
            } catch (RestException ex) {
                //Fall back to the container default
                this.signAlgos = Arrays.asList(DigestAlgorithm.SHA1, DigestAlgorithm.SHA256);
            }
        }
        return this.signAlgos;
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String pin) throws GenericContainerException {
        try {
            isSignAlgorithmSupported(algo);
            Preconditions.checkNotNull(data, "data to sign must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
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
    public Class<OberthurAllData> getAllDataClass() {
        return OberthurAllData.class;
    }

    @Override
    public Class<OberthurAllData> getAllCertificatesClass() {
        return OberthurAllData.class;
    }

    public List<DigestAlgorithm> getAllAlgoRefsForAuthentication() throws RestException, NoConsentException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getAuthenticationAlgoRefs(getTypeId(), reader.getId()), config.isConsentRequired()));
    }

    public List<DigestAlgorithm> getAllAlgoRefsForSigning() throws RestException, NoConsentException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getSignAlgoRefs(getTypeId(), reader.getId()), config.isConsentRequired()));
    }

    public T1cCertificate getRootCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getIssuerCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIssuerCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getEncryptionCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getEncryptionCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRootCertificate() throws RestException, NoConsentException {
        return getRootCertificate(null);
    }

    public T1cCertificate getIssuerCertificate() throws RestException, NoConsentException {
        return getIssuerCertificate(null);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(null);
    }

    public T1cCertificate getSigningCertificate() throws RestException, NoConsentException {
        return getSigningCertificate(null);
    }

    public T1cCertificate getEncryptionCertificate() throws RestException, NoConsentException {
        return getEncryptionCertificate(null);
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        OberthurAllData certs = getAllCertificates(true);
        return orderCertificates(certs.getRootCertificate(), certs.getIssuerCertificate(), certs.getAuthenticationCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        OberthurAllData certs = getAllCertificates(true);
        return orderCertificates(certs.getRootCertificate(), certs.getIssuerCertificate(), certs.getSigningCertificate());
    }

    @Override
    public ContainerData dumpData(String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        OberthurAllData allData = getAllData(true);

        data.setAuthenticationCertificateChain(orderCertificates(allData.getRootCertificate(), allData.getIssuerCertificate(), allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(orderCertificates(allData.getRootCertificate(), allData.getIssuerCertificate(), allData.getSigningCertificate()));
        data.setAllCertificates(getCertMap(allData));
        return data;
    }

    private Map<String, T1cCertificate> getCertMap(OberthurAllData data) {
        Map<String, T1cCertificate> certMap = new HashMap<>();
        certMap.put("root-certificate", data.getRootCertificate());
        certMap.put("issuer-certificate", data.getIssuerCertificate());
        certMap.put("authentication-certificate", data.getAuthenticationCertificate());
        certMap.put("signing-certificate", data.getSigningCertificate());
        certMap.put("encryption-certificate", data.getEncryptionCertificate());
        return certMap;
    }
}
