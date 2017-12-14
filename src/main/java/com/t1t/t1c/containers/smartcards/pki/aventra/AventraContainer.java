package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.containers.smartcards.piv.PivAllCertificates;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;
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
    public AventraContainer createInstance(LibConfig config, GclReader reader, GclAventraRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
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
    public AventraAllData getAllData() throws GenericContainerException {
        return getAllData(null, null);
    }

    @Override
    public AventraAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return new AventraAllData(RestExecutor.returnData(httpClient.getAllData(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
    }

    @Override
    public AventraAllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public AventraAllCertificates getAllCertificates() throws GenericContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public AventraAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return new AventraAllCertificates(RestExecutor.returnData(httpClient.getAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
    }

    @Override
    public AventraAllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPrivateKeyReference("sign").withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId())));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkArgument(algo != null
                    && (algo.equals(DigestAlgorithm.SHA1) || algo.equals(DigestAlgorithm.SHA256)), "algorithmReference must be provided and must be one of: SHA1, SHA256");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkArgument(algo != null
                    && (algo.equals(DigestAlgorithm.SHA1) || algo.equals(DigestAlgorithm.SHA256)), "algorithmReference must be provided and must be one of: SHA1, SHA256");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
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
        return getAlgorithms(RestExecutor.returnData(httpClient.getAuthenticationAlgoRefs(getTypeId(), reader.getId())));
    }

    public List<DigestAlgorithm> getAllAlgoRefsForSigning() throws RestException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getSignAlgoRefs(getTypeId(), reader.getId())));
    }

    public T1cCertificate getRootCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getIssuerCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIssuerCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getEncryptionCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getEncryptionCertificate(getTypeId(), reader.getId())), parse);
    }

    public Boolean resetPin(String puk, String pin, String keyRef) throws RestException {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(puk), "PUK must not be null or empty");
            Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "New PIN must not be null or empty");
            Preconditions.checkArgument(StringUtils.isNotEmpty(keyRef), "keyRef must not be null or empty");
            Preconditions.checkArgument(getAllKeyRefs().contains(keyRef), "keyRef must be one of: " + getAllKeyRefs().toString());
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.resetPin(getTypeId(), reader.getId(), new GclAventraPinResetRequest().withNewPin(pin).withPuk(puk).withPrivateKeyReference(keyRef))));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, RestException {
        AventraAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getRootCertificate(), certs.getIssuerCertificate(), certs.getAuthenticationCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, RestException {
        AventraAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getRootCertificate(), certs.getIssuerCertificate(), certs.getSigningCertificate());
    }

    @Override
    public ContainerData dumpData(String... pin) throws RestException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        AventraAllData allData = getAllData(true);
        data.setDocumentId(allData.getAppletInfo().getSerial());

        data.setAuthenticationCertificateChain(orderCertificates(allData.getRootCertificate(), allData.getIssuerCertificate(), allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(orderCertificates(allData.getRootCertificate(), allData.getIssuerCertificate(), allData.getSigningCertificate()));
        data.setCertificateChains(Arrays.asList(data.getAuthenticationCertificateChain(), data.getSigningCertificateChain(), orderCertificates(allData.getRootCertificate(), allData.getIssuerCertificate(), allData.getEncryptionCertificate())));
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
