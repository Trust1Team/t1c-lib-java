package com.t1t.t1c.containers.smartcards.eid.be;

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
import org.apache.commons.lang3.StringUtils;

import java.util.*;


/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class BeIdContainer extends GenericContainer<BeIdContainer, GclBeIdRestClient, BeIdAllData, BeIdAllCertificates> {

    private static final String SPACE = " ";

    public BeIdContainer(LibConfig config, GclReader reader, GclBeIdRestClient gclBeIdRestClient) {
        super(config, reader, gclBeIdRestClient, null);
    }

    /*Dynamic instance creation*/
    @Override
    public BeIdContainer createInstance(LibConfig config, GclReader reader, GclBeIdRestClient httpClient, String pacePin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pacePin = pacePin;
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
    public BeIdAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        GclBeIdAllData data = RestExecutor.returnData(httpClient.getBeIdAllData(type.getId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new BeIdAllData(data, parseCertificates);
    }

    @Override
    public BeIdAllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        GclBeIdAllCertificates data = RestExecutor.returnData(httpClient.getBeIdAllCertificates(type.getId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new BeIdAllCertificates(data, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String pin) throws VerifyPinException, NoConsentException, RestException {
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
    public String authenticate(String data, DigestAlgorithm algo, String pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public GclBeIdRn getRnData() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getRnData(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public GclBeIdAddress getBeIdAddress() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getBeIdAddress(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public String getBeIdPicture() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getBeIdPicture(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public T1cCertificate getRootCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getCitizenCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getCitizenCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRrnCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRrnCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRootCertificate() throws RestException, NoConsentException {
        return getRootCertificate(null);
    }

    public T1cCertificate getCitizenCertificate() throws RestException, NoConsentException {
        return getCitizenCertificate(null);
    }

    public T1cCertificate getNonRepudiationCertificate() throws RestException, NoConsentException {
        return getNonRepudiationCertificate(null);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(null);
    }

    public T1cCertificate getRrnCertificate() throws RestException, NoConsentException {
        return getRrnCertificate(null);
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
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        BeIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getNonRepudiationCertificate(), certs.getCitizenCertificate(), certs.getRootCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        BeIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getAuthenticationCertificate(), certs.getCitizenCertificate(), certs.getRootCertificate());
    }

    @Override
    public ContainerData dumpData(String pin) throws RestException, UnsupportedOperationException, NoConsentException {
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