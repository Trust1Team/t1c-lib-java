package com.t1t.t1c.containers.smartcards.eid.dni;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
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
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class DnieContainer extends GenericContainer<DnieContainer, GclDniRestClient, DnieAllData, DnieAllCertificates> {

    public DnieContainer(LibConfig config, GclReader reader, GclDniRestClient gclDniRestClient) {
        super(config, reader, gclDniRestClient, null);
    }

    @Override
    public DnieContainer createInstance(LibConfig config, GclReader reader, GclDniRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.type = ContainerType.DNIE;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("authentication-certificate", "signing-certificate", "intermediate-certificate", "info");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("authentication-certificate", "signing-certificate", "intermediate-certificate", "info");
    }

    @Override
    public DnieAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        return new DnieAllData(RestExecutor.returnData(httpClient.getDnieAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);

    }

    @Override
    public DnieAllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        return new DnieAllCertificates(RestExecutor.returnData(httpClient.getDnieAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public Boolean verifyPin(String pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            if (StringUtils.isNotEmpty(pin)) {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId()), config.isConsentRequired()));
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
                this.authenticateAlgos = Arrays.asList(DigestAlgorithm.MD5, DigestAlgorithm.SHA1, DigestAlgorithm.SHA256, DigestAlgorithm.SHA512);
            }
        }
        return this.authenticateAlgos;
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String pin) throws VerifyPinException, NoConsentException, RestException {
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
                this.signAlgos = Arrays.asList(DigestAlgorithm.MD5, DigestAlgorithm.SHA1, DigestAlgorithm.SHA256, DigestAlgorithm.SHA512);
            }
        }
        return this.signAlgos;
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            isSignAlgorithmSupported(algo);
            Preconditions.checkNotNull(data, "data to sign must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getIntermediateCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIntermediateCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(false);
    }

    public T1cCertificate getIntermediateCertificate() throws RestException, NoConsentException {
        return getIntermediateCertificate(false);
    }

    public T1cCertificate getSigningCertificate() throws RestException, NoConsentException {
        return getSigningCertificate(false);
    }

    public GclDnieInfo getInfo() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getDnieInfo(getTypeId(), reader.getId()), config.isConsentRequired());
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
    public Class<DnieAllData> getAllDataClass() {
        return DnieAllData.class;
    }

    @Override
    public Class<DnieAllCertificates> getAllCertificatesClass() {
        return DnieAllCertificates.class;
    }

    @Override
    public ContainerData dumpData(String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        DnieAllData allData = getAllData(true);
        data.setAllCertificates(getCertificatesMap(allData));
        data.setSigningCertificateChain(orderCertificates(allData.getIntermediateCertificate(), allData.getSigningCertificate()));
        data.setAuthenticationCertificateChain(orderCertificates(allData.getIntermediateCertificate(), allData.getSigningCertificate()));
        GclDnieInfo info = allData.getInfo();
        data.setGivenName(info.getFirstName());
        data.setSurName(info.getFirstLastName() + " " + info.getSecondLastName());
        data.setFullName(info.getFirstName() + " " + data.getSurName());
        data.setDocumentId(info.getSerialNumber());
        return data;
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        DnieAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getSigningCertificate(), certs.getIntermediateCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        DnieAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getAuthenticationCertificate(), certs.getIntermediateCertificate());
    }

    private Map<String, T1cCertificate> getCertificatesMap(DnieAllData allData) {
        Map<String, T1cCertificate> certs = new HashMap<>();
        certs.put("intermediate-certificate", allData.getIntermediateCertificate());
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("signing-certificate", allData.getSigningCertificate());
        return certs;
    }
}