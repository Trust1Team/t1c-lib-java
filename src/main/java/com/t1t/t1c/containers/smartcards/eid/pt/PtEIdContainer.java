package com.t1t.t1c.containers.smartcards.eid.pt;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PtEIdContainer extends GenericContainer<PtEIdContainer, GclPtIdRestClient, PtIdAllData, PtIdAllCertificates> {

    public PtEIdContainer(LibConfig config, GclReader reader, GclPtIdRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public PtEIdContainer createInstance(LibConfig config, GclReader reader, GclPtIdRestClient httpClient, String pacePin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pacePin = pacePin;
        this.type = ContainerType.PT;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("id", "root-certificate", "root-authentication-certificate", "authentication-certificate", "root-non-repudiation-certificate", "non-repudiation-certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("root-certificate", "root-authentication-certificate", "authentication-certificate", "root-non-repudiation-certificate", "non-repudiation-certificate");
    }

    @Override
    public PtIdAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        return new PtIdAllData(RestExecutor.returnData(httpClient.getPtIdAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public PtIdAllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        return new PtIdAllCertificates(RestExecutor.returnData(httpClient.getPtIdAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
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

    @Override
    public ContainerType getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return type.getId();
    }

    @Override
    public Class<PtIdAllData> getAllDataClass() {
        return PtIdAllData.class;
    }

    @Override
    public Class<PtIdAllCertificates> getAllCertificatesClass() {
        return PtIdAllCertificates.class;
    }

    public GclPtIdData getPtIdData() throws RestException, NoConsentException {
        return getPtIdData(null);
    }

    public GclPtIdData getPtIdData(Boolean includePhoto) throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getPtIdData(getTypeId(), reader.getId(), includePhoto), config.isConsentRequired());
    }

    public String getPtIdPhoto() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getPtIdPhoto(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    public T1cCertificate getRootCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRootAuthenticationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRootNonRepudiationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootNonRepudiationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRootCertificate() throws RestException, NoConsentException {
        return getRootCertificate(null);
    }

    public T1cCertificate getRootAuthenticationCertificate() throws RestException, NoConsentException {
        return getRootAuthenticationCertificate(null);
    }

    public T1cCertificate getRootNonRepudiationCertificate() throws RestException, NoConsentException {
        return getRootNonRepudiationCertificate(null);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(null);
    }

    public T1cCertificate getNonRepudiationCertificate() throws RestException, NoConsentException {
        return getNonRepudiationCertificate(null);
    }

    public GclPtIdAddress getAddress(String pin) throws VerifyPinException, NoConsentException, RestException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            if (StringUtils.isNotEmpty(pin)) {
                return RestExecutor.returnData(httpClient.getAddress(type.getId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
            } else {
                return RestExecutor.returnData(httpClient.getAddress(type.getId(), reader.getId()), config.isConsentRequired());
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public GclPtIdAddress getAddress() throws VerifyPinException, NoConsentException, RestException {
        return getAddress(null);
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        PtIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getRootNonRepudiationCertificate(), certs.getNonRepudiationCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        PtIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getRootAuthenticationCertificate(), certs.getAuthenticationCertificate());
    }

    @Override
    public ContainerData dumpData(String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        PtIdAllData allData = getAllData(true);
        GclPtIdData id = allData.getId();
        if (StringUtils.isNotEmpty(pin)) {
            GclPtIdAddress address = getAddress(pin);
            data.setMunicipality(address.getMunicipalityDescription());
            data.setStreetAndNumber(address.getStreetType() + " " + address.getStreetName() + " " + address.getDoorNo());
            data.setZipCode(address.getZip4() + "-" + address.getZip3());
        }
        data.setGivenName(id.getName());
        data.setSurName(id.getSurname());
        data.setFullName(id.getName() + " " + id.getSurname());
        data.setDateOfBirth(id.getDateOfBirth());
        data.setGender(id.getGender());

        data.setNationality(id.getNationality());
        data.setBase64Picture(id.getPhoto());
        data.setValidityStartDate(id.getValidityBeginDate());
        data.setValidityEndDate(id.getValidityEndDate());
        data.setDocumentId(id.getDocumentNumber());

        data.setAuthenticationCertificateChain(orderCertificates(allData.getAuthenticationCertificate(), allData.getRootAuthenticationCertificate()));
        data.setSigningCertificateChain(orderCertificates(allData.getNonRepudiationCertificate(), allData.getRootNonRepudiationCertificate()));
        data.setAllCertificates(getAllCertificatesMap(allData));
        return data;
    }

    private Map<String, T1cCertificate> getAllCertificatesMap(PtIdAllData data) {
        Map<String, T1cCertificate> certMap = new HashMap<>();
        certMap.put("root-certificate", data.getRootCertificate());
        certMap.put("root-authentication-certificate", data.getRootAuthenticationCertificate());
        certMap.put("root-non-repudiation-certificate", data.getRootNonRepudiationCertificate());
        certMap.put("authentication-certificate", data.getAuthenticationCertificate());
        certMap.put("non-repudiation-certificate", data.getNonRepudiationCertificate());
        return certMap;
    }
}