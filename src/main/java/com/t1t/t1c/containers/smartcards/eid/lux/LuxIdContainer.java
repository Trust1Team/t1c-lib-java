package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxIdContainer extends GenericContainer<LuxIdContainer, GclLuxIdRestClient, LuxIdAllData, LuxIdAllCertificates> {

    public LuxIdContainer(LibConfig config, GclReader reader, GclLuxIdRestClient gclLuxIdRestClient, String pin) {
        super(config, reader, gclLuxIdRestClient, pin);
    }

    @Override
    public LuxIdContainer createInstance(LibConfig config, GclReader reader, GclLuxIdRestClient httpClient, String pin) throws LuxIdContainerException {
        if (StringUtils.isEmpty(pin)) {
            throw ExceptionFactory.luxIdContainerException("PIN is required to initialize Lux ID container");
        }
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
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
    public LuxIdAllData getAllData() throws RestException, NoConsentException {
        return getAllData(null, null);
    }

    @Override
    public LuxIdAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        GclLuxIdAllData data = RestExecutor.returnData(httpClient.getLuxIdAllData(getTypeId(), reader.getId(), this.pin, createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxIdAllData(data, parseCertificates);
    }

    @Override
    public LuxIdAllData getAllData(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public LuxIdAllCertificates getAllCertificates() throws RestException, NoConsentException {
        return getAllCertificates(null, null);
    }

    @Override
    public LuxIdAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        GclLuxIdAllCertificates data = RestExecutor.returnData(httpClient.getLuxIdAllCertificates(getTypeId(), reader.getId(), this.pin, createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxIdAllCertificates(data, parseCertificates);
    }

    @Override
    public LuxIdAllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), this.pin, new GclVerifyPinRequest(pin[0], reader.getPinpad(), config.isOsPinDialog())), config.isConsentRequired()));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), this.pin), config.isConsentRequired()));
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
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), this.pin, PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)), config.isConsentRequired());
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
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), this.pin, PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)), config.isConsentRequired());
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
        return RestExecutor.returnData(httpClient.getLuxIdBiometric(getTypeId(), reader.getId(), pin), config.isConsentRequired());
    }

    public GclLuxIdPicture getPicture() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getLuxIdPicture(getTypeId(), reader.getId(), pin), config.isConsentRequired());
    }

    public GclLuxIdSignatureImage getSignatureImage() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getLuxIdSignatureImage(getTypeId(), reader.getId(), pin), config.isConsentRequired());
    }

    public List<T1cCertificate> getRootCertificates(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getTypeId(), reader.getId(), pin), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId(), pin), config.isConsentRequired()), parse);
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId(), pin), config.isConsentRequired()), parse);
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
    public ContainerData dumpData(String... pin) throws RestException, NoConsentException, UnsupportedOperationException {
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

        List<Map<Integer, T1cCertificate>> certChains = new ArrayList<>();
        certChains.add(getCertChain(allData.getRootCertificates()));
        certChains.add(data.getAuthenticationCertificateChain());
        certChains.add(data.getSigningCertificateChain());
        data.setCertificateChains(certChains);
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

    private Map<String, T1cCertificate> getCertificatesMap(LuxIdAllData allData) {
        Map<String, T1cCertificate> certs = new HashMap<>();
        for (int i = 0; i < allData.getRootCertificates().size(); i++) {
            certs.put("root-certificate-" + i + 1, allData.getRootCertificates().get(i));
        }
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("non-repudiation-certificate", allData.getNonRepudiationCertificate());
        return certs;
    }
}