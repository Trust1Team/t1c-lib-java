package com.t1t.t1c.containers.smartcards.pki.luxtrust;


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

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxTrustContainer extends SmartCardContainer<LuxTrustContainer, GclLuxTrustRestClient, LuxTrustAllData, LuxTrustAllCertificates> {

    public LuxTrustContainer(final LibConfig config, final GclReader reader, final String containerVersion, final GclLuxTrustRestClient gclLuxTrustRestClient) {
        super(config, reader, containerVersion, gclLuxTrustRestClient);
    }

    @Override
    public LuxTrustContainer createInstance(final LibConfig config, final GclReader reader, final String containerVersion, final GclLuxTrustRestClient httpClient, final GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.containerVersion = new ContainerVersion(ContainerType.LUXTRUST, containerVersion);
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("activated", "signing-certificate", "root-certificates", "authentication-certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("activated", "signing-certificate", "root-certificates", "authentication-certificate");
    }

    @Override
    public LuxTrustAllData getAllData(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        final GclLuxTrustAllData data = RestExecutor.returnData(httpClient.getLuxTrustAllData(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxTrustAllData(data, parseCertificates);
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates(final List<String> filterParams, final Boolean parseCertificates) throws RestException, NoConsentException {
        final GclLuxTrustAllCertificates certs = RestExecutor.returnData(httpClient.getLuxTrustAllCertificates(getContainerUrlId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxTrustAllCertificates(certs, parseCertificates);
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
                this.authenticateAlgos = Arrays.asList(DigestAlgorithm.SHA1, DigestAlgorithm.SHA256);
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
                this.signAlgos = Arrays.asList(DigestAlgorithm.SHA1, DigestAlgorithm.SHA256);
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

    public Boolean isActivated() throws RestException, NoConsentException {
        return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.isLuxTrustActivated(getContainerUrlId(), reader.getId()), config.isConsentRequired()));
    }

    public List<T1cCertificate> getRootCertificates(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(final Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getContainerUrlId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public List<T1cCertificate> getRootCertificates() throws RestException, NoConsentException {
        return getRootCertificates(null);
    }

    public T1cCertificate getSigningCertificate() throws RestException, NoConsentException {
        return getSigningCertificate(null);
    }

    public T1cCertificate getAuthenticationCertificate() throws RestException, NoConsentException {
        return getAuthenticationCertificate(null);
    }

    @Override
    public Class<LuxTrustAllData> getAllDataClass() {
        return LuxTrustAllData.class;
    }

    @Override
    public Class<LuxTrustAllCertificates> getAllCertificatesClass() {
        return LuxTrustAllCertificates.class;
    }

    @Override
    public ContainerData dumpData(final String pin) throws RestException, UnsupportedOperationException {
        final ContainerData data = new ContainerData();
        final LuxTrustAllData allData = getAllData(true);
        data.setAllCertificates(getCertificatesMap(allData));
        data.setAuthenticationCertificateChain(getCertChain(allData.getRootCertificates(), allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(getCertChain(allData.getRootCertificates(), allData.getSigningCertificate()));
        return data;
    }

    private Map<Integer, T1cCertificate> getCertChain(final List<T1cCertificate> rootCerts, final T1cCertificate... additionalCerts) {
        final List<T1cCertificate> collatedCerts = new ArrayList<>(rootCerts);
        collatedCerts.addAll(Arrays.asList(additionalCerts));
        return orderCertificates(collatedCerts);
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        final LuxTrustAllCertificates certs = getAllCertificates(true);
        final List<T1cCertificate> certsToOrder = new ArrayList<>(certs.getRootCertificates());
        certsToOrder.add(certs.getSigningCertificate());
        return orderCertificates(certsToOrder);
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        final LuxTrustAllCertificates certs = getAllCertificates(true);
        final List<T1cCertificate> certsToOrder = new ArrayList<>(certs.getRootCertificates());
        certsToOrder.add(certs.getAuthenticationCertificate());
        return orderCertificates(certsToOrder);
    }

    private Map<String, T1cCertificate> getCertificatesMap(final LuxTrustAllData allData) {
        final Map<String, T1cCertificate> certs = new HashMap<>();
        for (int i = 0; i < allData.getRootCertificates().size(); i++) {
            certs.put("root-certificate-" + i + 1, allData.getRootCertificates().get(i));
        }
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("signing-certificate", allData.getSigningCertificate());
        return certs;
    }
}