package com.t1t.t1c.containers.smartcards.eid.be;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;

import java.util.*;


/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class BeIdContainer extends GenericContainer<BeIdContainer, GclBeIdRestClient, BeIdAllData, BeIdAllCertificates> {

    private static final String PRIVATE_KEY_REFERENCE = "non-repudiation";
    private static final String SPACE = " ";

    public BeIdContainer(LibConfig config, GclReader reader, GclBeIdRestClient gclBeIdRestClient) {
        super(config, reader, gclBeIdRestClient, null);
    }

    /*Dynamic instance creation*/
    @Override
    public BeIdContainer createInstance(LibConfig config, GclReader reader, GclBeIdRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.BEID;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("address", "rn", "picture", "root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    public BeIdAllData getAllData() throws RestException {
        return getAllData(null, null);
    }

    @Override
    public BeIdAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        GclBeIdAllData data = RestExecutor.returnData(httpClient.getBeIdAllData(type.getId(), reader.getId(), createFilterParams(filterParams)));
        return new BeIdAllData(data, parseCertificates);
    }

    @Override
    public BeIdAllData getAllData(Boolean... parseCertificates) throws RestException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public BeIdAllCertificates getAllCertificates() throws RestException {
        return getAllCertificates(null, null);
    }

    @Override
    public BeIdAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        GclBeIdAllCertificates data = RestExecutor.returnData(httpClient.getBeIdAllCertificates(type.getId(), reader.getId(), createFilterParams(filterParams)));
        return new BeIdAllCertificates(data, parseCertificates);
    }

    @Override
    public BeIdAllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws VerifyPinException, RestException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), new GclVerifyPinRequest().withPrivateKeyReference(PRIVATE_KEY_REFERENCE).withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId())));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }


    public GclBeIdRn getRnData() throws RestException {
        return RestExecutor.returnData(httpClient.getRnData(getTypeId(), reader.getId()));
    }

    public GclBeIdAddress getBeIdAddress() throws RestException {
        return RestExecutor.returnData(httpClient.getBeIdAddress(getTypeId(), reader.getId()));
    }

    public String getBeIdPicture() throws RestException {
        return RestExecutor.returnData(httpClient.getBeIdPicture(getTypeId(), reader.getId()));
    }

    public T1cCertificate getRootCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getCitizenCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getCitizenCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getRrnCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRrnCertificate(getTypeId(), reader.getId())), parse);
    }


    @Override
    public ContainerType getType() {
        return ContainerType.BEID;
    }

    @Override
    public String getTypeId() {
        return getType().getId();
    }

    @Override
    public Class<BeIdAllData> getAllDataClass() {
        return BeIdAllData.class;
    }

    @Override
    public Class<BeIdAllCertificates> getAllCertificatesClass() {
        return BeIdAllCertificates.class;
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, RestException {
        BeIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getNonRepudiationCertificate(), certs.getCitizenCertificate(), certs.getRootCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, RestException {
        BeIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getAuthenticationCertificate(), certs.getCitizenCertificate(), certs.getRootCertificate());
    }

    @Override
    public ContainerData dumpData(String... pin) throws RestException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        BeIdAllData allData = getAllData(true);
        GclBeIdRn rn = allData.getRn();
        data.setDateOfBirth(rn.getBirthDate());
        data.setNationality(rn.getNationality());
        data.setGivenName(rn.getFirstNames() + SPACE + rn.getThirdName());
        data.setSurName(rn.getName());
        data.setFullName(data.getGivenName() + SPACE + rn.getName());
        data.setGender(rn.getSex());
        GclBeIdAddress address = allData.getAddress();
        data.setStreetAndNumber(address.getStreetAndNumber());
        data.setMunicipality(address.getMunicipality());
        data.setZipCode(address.getZipcode());
        data.setBase64Picture(allData.getPicture());
        data.setDocumentId(rn.getCardNumber());
        data.setValidityStartDate(rn.getCardValidityDateBegin());
        data.setValidityEndDate(rn.getCardValidityDateEnd());
        data.setAuthenticationCertificateChain(orderCertificates(allData.getAuthenticationCertificate(), allData.getCitizenCertificate(), allData.getRootCertificate()));
        data.setSigningCertificateChain(orderCertificates(allData.getNonRepudiationCertificate(), allData.getCitizenCertificate(), allData.getRootCertificate()));
        List<Map<Integer, T1cCertificate>> certChains = new ArrayList<>();
        certChains.add(data.getAuthenticationCertificateChain());
        certChains.add(data.getSigningCertificateChain());
        certChains.add(orderCertificates(allData.getCitizenCertificate(), allData.getRootCertificate()));
        certChains.add(orderCertificates(allData.getRrnCertificate(), allData.getRootCertificate()));
        data.setCertificateChains(certChains);
        data.setAllCertificates(getCertificatesMap(allData));
        return data;
    }

    private Map<String, T1cCertificate> getCertificatesMap(BeIdAllData allData) {
        Map<String, T1cCertificate> certs = new HashMap<>();
        certs.put("root-certificate", allData.getRootCertificate());
        certs.put("citizen-certificate", allData.getCitizenCertificate());
        certs.put("rrn-certificate", allData.getRrnCertificate());
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("non-repudiation-certificate", allData.getNonRepudiationCertificate());
        return certs;
    }
}