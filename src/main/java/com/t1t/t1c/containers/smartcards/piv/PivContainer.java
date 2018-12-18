package com.t1t.t1c.containers.smartcards.piv;

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
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PivContainer extends SmartCardContainer<PivContainer, GclPivRestClient, PivAllData, PivAllCertificates> {

    private String pin;

    public PivContainer(final LibConfig config, final GclReader reader, final String containerVersion, final GclPivRestClient httpClient, final String pin) {
        super(config, reader, containerVersion, httpClient, new GclPace().withPin(pin));
    }

    @Override
    public PivContainer createInstance(final LibConfig config, final GclReader reader, final String containerVersion, final GclPivRestClient httpClient, final GclPace pace) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(pace.getPin()), "PIN required to initialize PIV container");
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.pin = pace.getPin();
        this.containerVersion = new ContainerVersion(ContainerType.PIV, containerVersion);
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
    public PivAllData getAllData(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        return new PivAllData(RestExecutor.returnData(httpClient.getAllData(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), this.pin), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public PivAllCertificates getAllCertificates(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        return new PivAllCertificates(RestExecutor.returnData(httpClient.getAllCertificates(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), this.pin), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
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

    @Override
    public Class<PivAllData> getAllDataClass() {
        return PivAllData.class;
    }

    @Override
    public Class<PivAllCertificates> getAllCertificatesClass() {
        return PivAllCertificates.class;
    }

    public GclPivPrintedInformation getPrintedInformation() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getPrintedInformation(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), this.pin)), config.isConsentRequired());
    }

    public GclPivFacialImage getFacialImage() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getFacialImage(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), this.pin)), config.isConsentRequired());
    }

    public T1cCertificate getAuthenticationCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), this.pin)), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getContainerUrlId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), this.pin)), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(null);
    }

    public T1cCertificate getSigningCertificate() throws RestException, NoConsentException {
        return getSigningCertificate(null);
    }

    public List<DigestAlgorithm> getAllAlgoRefsForAuthentication() throws RestException, NoConsentException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getAuthenticationAlgoRefs(getContainerUrlId(), reader.getId()), config.isConsentRequired()));
    }

    public List<DigestAlgorithm> getAllAlgoRefsForSigning() throws RestException, NoConsentException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getSignAlgoRefs(getContainerUrlId(), reader.getId()), config.isConsentRequired()));
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
    public ContainerData dumpData(final String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        final ContainerData data = new ContainerData();
        final PivAllData allData = getAllData(true);
        final GclPivPrintedInformation info = allData.getPrintedInformation();
        data.setFullName(info.getName());

        if (allData.getFacialImage() != null) {
            data.setBase64Picture(allData.getFacialImage().getImage());
        }
        data.setValidityEndDate(info.getExpirationDate());
        data.setDocumentId(info.getAgencyCardSerialNumber());

        data.setAuthenticationCertificateChain(getCertChainMap(allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(getCertChainMap(allData.getSigningCertificate()));
        data.setAllCertificates(getAllCertificatesMap(allData));
        return data;
    }

    private Map<Integer, T1cCertificate> getCertChainMap(final T1cCertificate cert) throws VerifyPinException, NoConsentException, RestException {
        final Map<Integer, T1cCertificate> certChain = new HashMap<>();
        certChain.put(0, cert);
        return certChain;
    }

    private Map<String, T1cCertificate> getAllCertificatesMap(final PivAllData data) {
        final Map<String, T1cCertificate> certMap = new HashMap<>();
        certMap.put("authentication-certificate", data.getAuthenticationCertificate());
        certMap.put("signing-certificate", data.getSigningCertificate());
        return certMap;
    }

    /*public List<String> getAllKeyRefs() {
        return Arrays.asList("authenticate", "sign", "encrypt");
    }*/


}
