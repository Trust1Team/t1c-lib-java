package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.*;
import com.t1t.t1c.exceptions.ExceptionFactory;
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

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxIdContainer extends GenericContainer<LuxIdContainer, GclLuxIdRestClient, LuxIdAllData, LuxIdAllCertificates> {

    private Map<String, String> headers;

    public LuxIdContainer(LibConfig config, GclReader reader, GclLuxIdRestClient gclLuxIdRestClient, GclPace pace) {
        super(config, reader, gclLuxIdRestClient, pace);
    }

    @Override
    public LuxIdContainer createInstance(LibConfig config, GclReader reader, GclLuxIdRestClient httpClient, GclPace pace) throws LuxIdContainerException {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        setPace(pace);
        this.type = ContainerType.LUXID;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("authentication-certificate", "signature-image", "biometric", "non-repudiation-certificate", "picture", "root-certificates");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates");
    }

    @Override
    public LuxIdAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        GclLuxIdAllData data = RestExecutor.returnData(httpClient.getLuxIdAllData(getTypeId(), reader.getId(), this.headers, createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxIdAllData(data, parseCertificates);
    }

    @Override
    public LuxIdAllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        GclLuxIdAllCertificates data = RestExecutor.returnData(httpClient.getLuxIdAllCertificates(getTypeId(), reader.getId(), this.headers, createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxIdAllCertificates(data, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), this.headers, PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.authenticateAlgos)) {
            try {
                this.authenticateAlgos = RestExecutor.returnData(httpClient.getAvailableAuthenticateAlgos(getTypeId(), reader.getId(), headers), config.isConsentRequired());
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
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), this.headers, PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.signAlgos)) {
            try {
                this.signAlgos = RestExecutor.returnData(httpClient.getAvailableSignAlgos(getTypeId(), reader.getId(), headers), config.isConsentRequired());
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
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), this.headers, PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
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

    public GclLuxIdBiometric getBiometric() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getLuxIdBiometric(getTypeId(), reader.getId(), this.headers), config.isConsentRequired());
    }

    public GclLuxIdPicture getPicture() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getLuxIdPicture(getTypeId(), reader.getId(), this.headers), config.isConsentRequired());
    }

    public GclLuxIdSignatureImage getSignatureImage() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getLuxIdSignatureImage(getTypeId(), reader.getId(), this.headers), config.isConsentRequired());
    }

    public List<T1cCertificate> getRootCertificates(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getTypeId(), reader.getId(), this.headers), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId(), this.headers), config.isConsentRequired()), parse);
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId(), this.headers), config.isConsentRequired()), parse);
    }

    public List<T1cCertificate> getRootCertificates() throws RestException, NoConsentException {
        return getRootCertificates(null);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(null);
    }

    public T1cCertificate getNonRepudiationCertificate() throws RestException, NoConsentException {
        return getNonRepudiationCertificate(null);
    }

    @Override
    public Class<LuxIdAllData> getAllDataClass() {
        return LuxIdAllData.class;
    }

    @Override
    public Class<LuxIdAllCertificates> getAllCertificatesClass() {
        return LuxIdAllCertificates.class;
    }

    @Override
    public ContainerData dumpData(String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        LuxIdAllData allData = getAllData(true);

        data.setGivenName(allData.getBiometric().getFirstName());
        data.setSurName(allData.getBiometric().getLastName());
        data.setFullName(allData.getBiometric().getFirstName() + " " + allData.getBiometric().getLastName());
        data.setDateOfBirth(allData.getBiometric().getBirthDate());
        data.setGender(allData.getBiometric().getGender());

        data.setBase64Picture(allData.getPicture().getImage());
        data.setBase64SignatureImage(allData.getSignatureImage().getImage());

        data.setNationality(allData.getBiometric().getNationality());
        data.setValidityStartDate(allData.getBiometric().getValidityStartDate());
        data.setValidityEndDate(allData.getBiometric().getValidityEndDate());
        data.setDocumentId(allData.getBiometric().getDocumentNumber());

        data.setAllCertificates(getCertificatesMap(allData));
        data.setAuthenticationCertificateChain(getCertChain(allData.getRootCertificates(), allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(getCertChain(allData.getRootCertificates(), allData.getNonRepudiationCertificate()));
        return data;
    }

    private Map<Integer, T1cCertificate> getCertChain(List<T1cCertificate> rootCerts, T1cCertificate... additionalCerts) {
        List<T1cCertificate> collatedCerts = new ArrayList<>(rootCerts);
        collatedCerts.addAll(Arrays.asList(additionalCerts));
        return orderCertificates(collatedCerts);
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        LuxIdAllCertificates certs = getAllCertificates(true);
        List<T1cCertificate> certsToOrder = new ArrayList<>(certs.getRootCertificates());
        certsToOrder.add(certs.getNonRepudiationCertificate());
        return orderCertificates(certsToOrder);
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        LuxIdAllCertificates certs = getAllCertificates(true);
        List<T1cCertificate> certsToOrder = new ArrayList<>(certs.getRootCertificates());
        certsToOrder.add(certs.getAuthenticationCertificate());
        return orderCertificates(certsToOrder);
    }

    public Integer getPinTryCounter(GclPinReference reference) throws NoConsentException, RestException {
        return RestExecutor.returnData(httpClient.getPinTryCount(getTypeId(), reader.getId(), headers, new GclPinTryCounterRequest().withPinReference(reference)), config.isConsentRequired());
    }

    public Boolean changePin(String oldPin, String newPin) throws NoConsentException, RestException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), oldPin, newPin);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.changePin(getTypeId(), reader.getId(), headers, PinUtil.createEncryptedPinChangeRequest(reader.getPinpad(), config.isOsPinDialog(), oldPin, newPin)), config.isConsentRequired()));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    /**
     * Unblock the PIN with the PUK code.
     * @param puk the PUK code.
     * @return true if successfull, false if not.
     * @throws NoConsentException when no consent has been granted.
     * @throws RestException when a REST layer exception occurs.
     */
    public Boolean resetPin(String puk) throws NoConsentException, RestException {
        return resetPin(puk, null);
    }

    /**
     * Unblock the card with the PUK code and set a new PIN.
     * @param puk the PUK code.
     * @param newPin the new PIN
     * @return true if successful, false if not
     * @throws NoConsentException when no consent has been granted.
     * @throws RestException when a REST layer exception occurs
     */
    public Boolean resetPin(String puk, String newPin) throws NoConsentException, RestException {

        try {
            if (StringUtils.isEmpty(newPin) && config.isOsPinDialog()) {
                PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), puk);
            } else {
                PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), puk, newPin);
            }
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.resetPin(getTypeId(), reader.getId(), headers, PinUtil.createEncryptedPinResetRequest(reader.getPinpad(), config.isOsPinDialog(), puk, newPin)), config.isConsentRequired()));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    /*public Boolean resetPin(String puk, String newPin) throws VerifyPinException {
        throw ExceptionFactory.unsupportedOperationException("Not yet implemented");
    }

    public Boolean resetPin() throws VerifyPinException {
        return resetPin(null, null);
    }

    public Boolean unblock() {
        return unblock(null);
    }

    public Boolean unblock(String can) {
        throw ExceptionFactory.unsupportedOperationException("Not yet implemented");
    }

    public Boolean unverifyPin() {
        throw ExceptionFactory.unsupportedOperationException("Not yet implemented");
    }*/

    private Map<String, T1cCertificate> getCertificatesMap(LuxIdAllData allData) {
        Map<String, T1cCertificate> certs = new HashMap<>();
        for (int i = 0; i < allData.getRootCertificates().size(); i++) {
            certs.put("root-certificate-" + i + 1, allData.getRootCertificates().get(i));
        }
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("non-repudiation-certificate", allData.getNonRepudiationCertificate());
        return certs;
    }

    public GclPace getPace() {
        return this.pace;
    }

    private void setPace(GclPace pace) {
        if (pace == null || (StringUtils.isEmpty(pace.getCan()) && StringUtils.isEmpty(pace.getPin()) && StringUtils.isEmpty(pace.getPuk()) && StringUtils.isEmpty(pace.getMrz()))) {
            throw ExceptionFactory.luxIdContainerException("PIN, PUK, MRZ or CAN is required to initialize Lux ID container");
        }
        this.pace = pace;
        this.headers = new HashMap<>();
        if (StringUtils.isNotEmpty(pace.getPin())) {
            this.headers.put(ENCRYPTED_PIN_HEADER_NAME, PinUtil.getEncryptedPinIfPresent(pace.getPin()));
        }
        if (StringUtils.isNotEmpty(pace.getPuk())) {
            this.headers.put(ENCRYPTED_PUK_HEADER_NAME, PinUtil.getEncryptedPinIfPresent(pace.getPuk()));
        }
        if (StringUtils.isNotEmpty(pace.getCan())) {
            this.headers.put(ENCRYPTED_CAN_HEADER_NAME, PinUtil.getEncryptedPinIfPresent(pace.getCan()));
        }
        if (StringUtils.isNotEmpty(pace.getMrz())) {
            this.headers.put(ENCRYPTED_MRZ_HEADER_NAME, PinUtil.getEncryptedPinIfPresent(pace.getMrz()));
        }
    }
}