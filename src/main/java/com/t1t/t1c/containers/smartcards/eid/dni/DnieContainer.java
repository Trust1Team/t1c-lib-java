package com.t1t.t1c.containers.smartcards.eid.dni;

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
public class DnieContainer extends SmartCardContainer<DnieContainer, GclDniRestClient, DnieAllData, DnieAllCertificates> {

    public DnieContainer(final LibConfig config, final GclReader reader, final String containerVersion, final GclDniRestClient gclDniRestClient) {
        super(config, reader, containerVersion, gclDniRestClient);
    }

    @Override
    public DnieContainer createInstance(final LibConfig config, final GclReader reader, final String containerVersion, final GclDniRestClient httpClient, final GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.containerVersion = new ContainerVersion(ContainerType.DNIE, containerVersion);
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("authentication-certificate", "signing-certificate", "intermediate-certificate", "info");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("authentication-certificate", "signing-certificate", "intermediate-certificate", "info");
    }

    @Override
    public DnieAllData getAllData(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        return new DnieAllData(RestExecutor.returnData(httpClient.getDnieAllData(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);

    }

    @Override
    public DnieAllCertificates getAllCertificates(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        return new DnieAllCertificates(RestExecutor.returnData(httpClient.getDnieAllCertificates(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public Boolean verifyPin(final String pin) throws RestException, NoConsentException, VerifyPinException {
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
            PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (final RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getIntermediateCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIntermediateCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(false);
    }

    public T1cCertificate getIntermediateCertificate() throws RestException, NoConsentException {
        return getIntermediateCertificate(false);
    }

    public T1cCertificate getSigningCertificate() throws RestException, NoConsentException {
        return getSigningCertificate(false);
    }

    public GclDnieInfo getInfo() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getDnieInfo(getContainerUrlId(), reader.getId()), config.isConsentRequired());
    }

    @Override
    public Class<DnieAllData> getAllDataClass() {
        return DnieAllData.class;
    }

    @Override
    public Class<DnieAllCertificates> getAllCertificatesClass() {
        return DnieAllCertificates.class;
    }

    @Override
    public ContainerData dumpData(final String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        final ContainerData data = new ContainerData();
        final DnieAllData allData = getAllData(true);
        data.setAllCertificates(getCertificatesMap(allData));
        data.setSigningCertificateChain(orderCertificates(allData.getIntermediateCertificate(), allData.getSigningCertificate()));
        data.setAuthenticationCertificateChain(orderCertificates(allData.getIntermediateCertificate(), allData.getSigningCertificate()));
        final GclDnieInfo info = allData.getInfo();
        data.setGivenName(info.getFirstName());
        data.setSurName(info.getFirstLastName() + " " + info.getSecondLastName());
        data.setFullName(info.getFirstName() + " " + data.getSurName());
        data.setDocumentId(info.getSerialNumber());
        return data;
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        final DnieAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getSigningCertificate(), certs.getIntermediateCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        final DnieAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getAuthenticationCertificate(), certs.getIntermediateCertificate());
    }

    private Map<String, T1cCertificate> getCertificatesMap(final DnieAllData allData) {
        final Map<String, T1cCertificate> certs = new HashMap<>();
        certs.put("intermediate-certificate", allData.getIntermediateCertificate());
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("signing-certificate", allData.getSigningCertificate());
        return certs;
    }
}