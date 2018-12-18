package com.t1t.t1c.containers.smartcards.eid.be;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.ContainerVersion;
import com.t1t.t1c.containers.SmartCardContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclPace;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.PinUtil;
import com.t1t.t1c.utils.PkiUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class BeIdContainer extends SmartCardContainer<BeIdContainer, GclBeIdRestClient, BeIdAllData, BeIdAllCertificates> {

    private static final String SPACE = " ";

    public BeIdContainer(final LibConfig config, final GclReader reader, final String containerVersion, final GclBeIdRestClient gclBeIdRestClient) {
        super(config, reader, containerVersion, gclBeIdRestClient);
    }

    /*Dynamic instance creation*/
    @Override
    public BeIdContainer createInstance(final LibConfig config, final GclReader reader, final String containerVersion, final GclBeIdRestClient httpClient, final GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.containerVersion = new ContainerVersion(ContainerType.BEID, containerVersion);
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
    public BeIdAllData getAllData(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        final GclBeIdAllData data = RestExecutor.returnData(httpClient.getBeIdAllData(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new BeIdAllData(data, parseCertificates);
    }

    @Override
    public BeIdAllCertificates getAllCertificates(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        final GclBeIdAllCertificates data = RestExecutor.returnData(httpClient.getBeIdAllCertificates(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new BeIdAllCertificates(data, parseCertificates);
    }

    @Override
    public Boolean verifyPin(final String pin) throws VerifyPinException, NoConsentException, RestException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
        } catch (final RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.authenticateAlgos)) {
            try {
                this.authenticateAlgos = RestExecutor.returnData(httpClient.getAvailableAuthenticateAlgos(getContainerUrlId(), reader.getId()), config.isConsentRequired());
            } catch (final RestException ex) {
                //Fall back to the container default
                this.authenticateAlgos = Arrays.asList(DigestAlgorithm.MD5, DigestAlgorithm.SHA1, DigestAlgorithm.SHA256, DigestAlgorithm.SHA512);
            }
        }
        return this.authenticateAlgos;
    }

    @Override
    public String authenticate(final String data, final DigestAlgorithm algo, final String pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            isAuthenticateAlgorithmSupported(algo);
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (final RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.signAlgos)) {
            try {
                this.signAlgos = RestExecutor.returnData(httpClient.getAvailableSignAlgos(getContainerUrlId(), reader.getId()), config.isConsentRequired());
            } catch (final RestException ex) {
                //Fall back to the container default
                this.signAlgos = Arrays.asList(DigestAlgorithm.MD5, DigestAlgorithm.SHA1, DigestAlgorithm.SHA256, DigestAlgorithm.SHA512);
            }
        }
        return this.signAlgos;
    }

    @Override
    public String sign(final String data, final DigestAlgorithm algo, final String pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            isSignAlgorithmSupported(algo);
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (final RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public GclBeIdRn getRnData() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getRnData(getContainerUrlId(), reader.getId()), config.isConsentRequired());
    }

    public GclBeIdAddress getBeIdAddress() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getBeIdAddress(getContainerUrlId(), reader.getId()), config.isConsentRequired());
    }

    public String getBeIdPicture() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getBeIdPicture(getContainerUrlId(), reader.getId()), config.isConsentRequired());
    }

    public T1cCertificate getRootCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getCitizenCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getCitizenCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getNonRepudiationCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getRrnCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRrnCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
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

    public GclBeIdToken getToken() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getBeIdToken(getContainerUrlId(), reader.getId()), config.isConsentRequired());
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
        final BeIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getNonRepudiationCertificate(), certs.getCitizenCertificate(), certs.getRootCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        final BeIdAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getAuthenticationCertificate(), certs.getCitizenCertificate(), certs.getRootCertificate());
    }

    @Override
    public ContainerData dumpData(final String pin) throws RestException, UnsupportedOperationException, NoConsentException {
        final ContainerData data = new ContainerData();
        final BeIdAllData allData = getAllData(true);
        final GclBeIdRn rn = allData.getRn();
        data.setDateOfBirth(rn.getBirthDate());
        data.setNationality(rn.getNationality());
        data.setGivenName(rn.getFirstNames() + SPACE + rn.getThirdName());
        data.setSurName(rn.getName());
        data.setFullName(data.getGivenName() + SPACE + rn.getName());
        data.setGender(rn.getSex());
        final GclBeIdAddress address = allData.getAddress();
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

    private Map<String, T1cCertificate> getCertificatesMap(final BeIdAllData allData) {
        final Map<String, T1cCertificate> certs = new HashMap<>();
        certs.put("root-certificate", allData.getRootCertificate());
        certs.put("citizen-certificate", allData.getCitizenCertificate());
        certs.put("rrn-certificate", allData.getRrnCertificate());
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("non-repudiation-certificate", allData.getNonRepudiationCertificate());
        return certs;
    }
}