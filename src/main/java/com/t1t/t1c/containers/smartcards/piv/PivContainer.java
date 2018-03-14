package com.t1t.t1c.containers.smartcards.piv;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.NoConsentException;
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
public class PivContainer extends GenericContainer<PivContainer, GclPivRestClient, PivAllData, PivAllCertificates> {

    public PivContainer(LibConfig config, GclReader reader, GclPivRestClient httpClient, String pin) {
        super(config, reader, httpClient, pin);
    }

    @Override
    public PivContainer createInstance(LibConfig config, GclReader reader, GclPivRestClient httpClient, String pin) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "PIN required to initialize PIV container");
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.PIV;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("printed-information", "facial-image", "authentication-certificate", "signing_certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("authentication-certificate", "signing_certificate");
    }

    @Override
    public PivAllData getAllData() throws RestException, NoConsentException {
        return getAllData(null, null);
    }

    @Override
    public PivAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        return new PivAllData(RestExecutor.returnData(httpClient.getAllData(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public PivAllData getAllData(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public PivAllCertificates getAllCertificates() throws RestException, NoConsentException {
        return getAllCertificates(null, null);
    }

    @Override
    public PivAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        return new PivAllCertificates(RestExecutor.returnData(httpClient.getAllCertificates(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public PivAllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0])), config.isConsentRequired()));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)), config.isConsentRequired()));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)), config.isConsentRequired());
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
    public Class<PivAllData> getAllDataClass() {
        return PivAllData.class;
    }

    @Override
    public Class<PivAllCertificates> getAllCertificatesClass() {
        return PivAllCertificates.class;
    }

    public GclPivPrintedInformation getPrintedInformation() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getPrintedInformation(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)), config.isConsentRequired());
    }

    public GclPivFacialImage getFacialImage() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getFacialImage(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)), config.isConsentRequired());
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)), config.isConsentRequired()), parse);
    }

    public List<DigestAlgorithm> getAllAlgoRefsForAuthentication() throws RestException, NoConsentException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getAuthenticationAlgoRefs(getTypeId(), reader.getId()), config.isConsentRequired()));
    }

    public List<DigestAlgorithm> getAllAlgoRefsForSigning() throws RestException, NoConsentException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getSignAlgoRefs(getTypeId(), reader.getId()), config.isConsentRequired()));
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        return getCertChainMap(getSigningCertificate(true));
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        return getCertChainMap(getAuthenticationCertificate(true));
    }

    @Override
    public ContainerData dumpData(String... pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        PivAllData allData = getAllData(true);
        GclPivPrintedInformation info = allData.getPrintedInformation();
        data.setFullName(info.getName());

        if (allData.getFacialImage() != null) {
            data.setBase64Picture(allData.getFacialImage().getImage());
        }
        data.setValidityEndDate(info.getExpirationDate());
        data.setDocumentId(info.getAgencyCardSerialNumber());

        data.setAuthenticationCertificateChain(getCertChainMap(allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(getCertChainMap(allData.getSigningCertificate()));
        data.setCertificateChains(Arrays.asList(data.getSigningCertificateChain(), data.getAuthenticationCertificateChain()));
        data.setAllCertificates(getAllCertificatesMap(allData));
        return data;
    }

    private Map<Integer, T1cCertificate> getCertChainMap(T1cCertificate cert) throws VerifyPinException, NoConsentException, RestException {
        Map<Integer, T1cCertificate> certChain = new HashMap<>();
        certChain.put(0, cert);
        return certChain;
    }

    private Map<String, T1cCertificate> getAllCertificatesMap(PivAllData data) {
        Map<String, T1cCertificate> certMap = new HashMap<>();
        certMap.put("authentication-certificate", data.getAuthenticationCertificate());
        certMap.put("signing-certificate", data.getSigningCertificate());
        return certMap;
    }

    /*public List<String> getAllKeyRefs() {
        return Arrays.asList("authenticate", "sign", "encrypt");
    }*/


}
