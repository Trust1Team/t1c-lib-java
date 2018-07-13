package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.ContainerVersion;
import com.t1t.t1c.containers.SmartCardContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclPace;
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
public class AventraContainer extends SmartCardContainer<AventraContainer, GclAventraRestClient, AventraAllData, AventraAllCertificates> {

    public AventraContainer(LibConfig config, GclReader reader, String containerVersion, GclAventraRestClient httpClient) {
        super(config, reader, containerVersion, httpClient);
    }

    @Override
    public AventraContainer createInstance(LibConfig config, GclReader reader, String containerVersion, GclAventraRestClient httpClient, GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.containerVersion = new ContainerVersion(ContainerType.AVENTRA, containerVersion);
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
        return new AventraAllData(RestExecutor.returnData(httpClient.getAllData(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public AventraAllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws GenericContainerException {
        return new AventraAllCertificates(RestExecutor.returnData(httpClient.getAllCertificates(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public Boolean verifyPin(String pin) throws GenericContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.authenticateAlgos)) {
            try {
                this.authenticateAlgos = RestExecutor.returnData(httpClient.getAvailableAuthenticateAlgos(getContainerUrlId(), reader.getId()), config.isConsentRequired());
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
            return RestExecutor.returnData(httpClient.authenticate(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.signAlgos)) {
            try {
                this.signAlgos = RestExecutor.returnData(httpClient.getAvailableSignAlgos(getContainerUrlId(), reader.getId()), config.isConsentRequired());
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
            return RestExecutor.returnData(httpClient.sign(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
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
        return getAlgorithms(RestExecutor.returnData(httpClient.getAuthenticationAlgoRefs(getContainerUrlId(), reader.getId()), config.isConsentRequired()));
    }

    public List<DigestAlgorithm> getAllAlgoRefsForSigning() throws RestException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getSignAlgoRefs(getContainerUrlId(), reader.getId()), config.isConsentRequired()));
    }

    public T1cCertificate getRootCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getIssuerCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIssuerCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getEncryptionCertificate(Boolean parse) throws RestException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getEncryptionCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
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
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.resetPin(getContainerUrlId(), reader.getId(), new GclAventraPinResetRequest().withNewPin(pin).withPuk(puk).withPrivateKeyReference(keyRef)), config.isConsentRequired()));
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
