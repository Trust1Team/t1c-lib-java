package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
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
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class AventraContainer extends GenericContainer<AventraContainer, GclAventraRestClient, AventraAllData, AventraAllCertificates> {

    public AventraContainer(LibConfig config, GclReader reader, GclAventraRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public AventraContainer createInstance(LibConfig config, GclReader reader, GclAventraRestClient httpClient, String pacePin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pacePin = pacePin;
        this.type = ContainerType.AVENTRA;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("applet-info", "root-certificate", "authentication-certificate",
                "encryption-certificate", "issuer-certificate", "signing-certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("root-certificate", "authentication-certificate", "encryption-certificate", "issuer-certificate", "signing-certificate");
    }

    @Override
    public AventraAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws GenericContainerException {
        return new AventraAllData(RestExecutor.returnData(httpClient.getAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public AventraAllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws GenericContainerException {
        return new AventraAllCertificates(RestExecutor.returnData(httpClient.getAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
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
    public Class<AventraAllData> getAllDataClass() {
        return AventraAllData.class;
    }

    @Override
    public Class<AventraAllCertificates> getAllCertificatesClass() {
        return AventraAllCertificates.class;
    }

    public List<String> getAllKeyRefs() {
        return Arrays.asList("authenticate", "sign", "encrypt");
    }

    public List<DigestAlgorithm> getAllAlgoRefsForAuthentication() throws RestException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getAuthenticationAlgoRefs(getTypeId(), reader.getId()), config.isConsentRequired()));
    }

    public List<DigestAlgorithm> getAllAlgoRefsForSigning() throws RestException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getSignAlgoRefs(getTypeId(), reader.getId()), config.isConsentRequired()));
    }

    public T1cCertificate getRootCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getIssuerCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIssuerCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getEncryptionCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getEncryptionCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRootCertificate() throws RestException {
        return getRootCertificate(null);
    }

    public T1cCertificate getIssuerCertificate() throws RestException {
        return getIssuerCertificate(null);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException {
        return getAuthenticationCertificate(null);
    }

    public T1cCertificate getSigningCertificate() throws RestException {
        return getSigningCertificate(null);
    }

    public T1cCertificate getEncryptionCertificate() throws RestException {
        return getEncryptionCertificate(null);
    }

    public Boolean resetPin(String puk, String pin, String keyRef) throws RestException {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(puk), "PUK must not be null or empty");
            Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "New PIN must not be null or empty");
            Preconditions.checkArgument(StringUtils.isNotEmpty(keyRef), "keyRef must not be null or empty");
            Preconditions.checkArgument(getAllKeyRefs().contains(keyRef), "keyRef must be one of: " + getAllKeyRefs().toString());
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.resetPin(getTypeId(), reader.getId(), new GclAventraPinResetRequest().withNewPin(pin).withPuk(puk).withPrivateKeyReference(keyRef)), config.isConsentRequired()));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        AventraAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getRootCertificate(), certs.getIssuerCertificate(), certs.getAuthenticationCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        AventraAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getRootCertificate(), certs.getIssuerCertificate(), certs.getSigningCertificate());
    }

    @Override
    public ContainerData dumpData(String pin) throws RestException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        AventraAllData allData = getAllData(true);
        data.setDocumentId(allData.getAppletInfo().getSerial());

        data.setAuthenticationCertificateChain(orderCertificates(allData.getRootCertificate(), allData.getIssuerCertificate(), allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(orderCertificates(allData.getRootCertificate(), allData.getIssuerCertificate(), allData.getSigningCertificate()));
        data.setAllCertificates(getCertMap(allData));
        return data;
    }

    private Map<String, T1cCertificate> getCertMap(AventraAllData data) {
        Map<String, T1cCertificate> certMap = new HashMap<>();
        certMap.put("root-certificate", data.getRootCertificate());
        certMap.put("issuer-certificate", data.getIssuerCertificate());
        certMap.put("authentication-certificate", data.getAuthenticationCertificate());
        certMap.put("signing-certificate", data.getSigningCertificate());
        certMap.put("encryption-certificate", data.getEncryptionCertificate());
        return certMap;
    }
}
