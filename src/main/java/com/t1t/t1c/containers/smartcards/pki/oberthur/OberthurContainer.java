package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class OberthurContainer extends GenericContainer<OberthurContainer, GclOberthurRestClient, OberthurAllData, OberthurAllData> {

    public OberthurContainer(LibConfig config, GclReader reader, GclOberthurRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public OberthurContainer createInstance(LibConfig config, GclReader reader, GclOberthurRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
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
    public OberthurAllData getAllData() throws GenericContainerException {
        return getAllData(null, null);
    }

    @Override
    public OberthurAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return new OberthurAllData(RestExecutor.returnData(httpClient.getAllData(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
    }

    @Override
    public OberthurAllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public OberthurAllData getAllCertificates() throws GenericContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public OberthurAllData getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return new OberthurAllData(RestExecutor.returnData(httpClient.getAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
    }

    @Override
    public OberthurAllData getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
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
    public Class<OberthurAllData> getAllDataClass() {
        return OberthurAllData.class;
    }

    @Override
    public Class<OberthurAllData> getAllCertificatesClass() {
        return OberthurAllData.class;
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

    @Override
    public ContainerData dumpData() throws RestException, UnsupportedOperationException {
        //TODO
        return null;
    }
}
