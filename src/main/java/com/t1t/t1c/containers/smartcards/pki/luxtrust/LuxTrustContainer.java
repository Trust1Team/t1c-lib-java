package com.t1t.t1c.containers.smartcards.pki.luxtrust;


import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
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

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxTrustContainer extends GenericContainer<LuxTrustContainer, GclLuxTrustRestClient, LuxTrustAllData, LuxTrustAllCertificates> {

    public LuxTrustContainer(LibConfig config, GclReader reader, GclLuxTrustRestClient gclLuxTrustRestClient) {
        super(config, reader, gclLuxTrustRestClient);
    }

    @Override
    public LuxTrustContainer createInstance(LibConfig config, GclReader reader, GclLuxTrustRestClient httpClient, GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.type = ContainerType.LUXTRUST;
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
    public LuxTrustAllData getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        GclLuxTrustAllData data = RestExecutor.returnData(httpClient.getLuxTrustAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxTrustAllData(data, parseCertificates);
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException {
        GclLuxTrustAllCertificates certs = RestExecutor.returnData(httpClient.getLuxTrustAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired());
        return new LuxTrustAllCertificates(certs, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String pin) throws VerifyPinException, NoConsentException, RestException {
        PinUtil.pinEnforcementCheck(reader, config.isOsPinDialog(), config.isHardwarePinPadForced(), pin);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), PinUtil.createEncryptedRequest(reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired()));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.authenticateAlgos)) {
            try {
                this.authenticateAlgos = RestExecutor.returnData(httpClient.getAvailableAuthenticateAlgos(getTypeId(), reader.getId()), config.isConsentRequired());
            } catch (RestException ex) {
                //Fall back to the container default
                this.authenticateAlgos = Arrays.asList(DigestAlgorithm.SHA1, DigestAlgorithm.SHA256);
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
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        if (CollectionUtils.isEmpty(this.signAlgos)) {
            try {
                this.signAlgos = RestExecutor.returnData(httpClient.getAvailableSignAlgos(getTypeId(), reader.getId()), config.isConsentRequired());
            } catch (RestException ex) {
                //Fall back to the container default
                this.signAlgos = Arrays.asList(DigestAlgorithm.SHA1, DigestAlgorithm.SHA256);
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
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.createEncryptedAuthSignData(data, algo.getStringValue(), reader.getPinpad(), config.isOsPinDialog(), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public Boolean isActivated() throws RestException, NoConsentException {
        return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.isLuxTrustActivated(getTypeId(), reader.getId()), config.isConsentRequired()));
    }

    public List<T1cCertificate> getRootCertificates(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean parse) throws RestException, NoConsentException {
        return PkiUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
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
    public ContainerType getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return type.getId();
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
    public ContainerData dumpData(String pin) throws RestException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        LuxTrustAllData allData = getAllData(true);
        data.setAllCertificates(getCertificatesMap(allData));
        data.setAuthenticationCertificateChain(getCertChain(allData.getRootCertificates(), allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(getCertChain(allData.getRootCertificates(), allData.getSigningCertificate()));
        return data;
    }

    private Map<Integer, T1cCertificate> getCertChain(List<T1cCertificate> rootCerts, T1cCertificate... additionalCerts) {
        List<T1cCertificate> collatedCerts = new ArrayList<>(rootCerts);
        collatedCerts.addAll(Arrays.asList(additionalCerts));
        return orderCertificates(collatedCerts);
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        LuxTrustAllCertificates certs = getAllCertificates(true);
        List<T1cCertificate> certsToOrder = new ArrayList<>(certs.getRootCertificates());
        certsToOrder.add(certs.getSigningCertificate());
        return orderCertificates(certsToOrder);
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        LuxTrustAllCertificates certs = getAllCertificates(true);
        List<T1cCertificate> certsToOrder = new ArrayList<>(certs.getRootCertificates());
        certsToOrder.add(certs.getAuthenticationCertificate());
        return orderCertificates(certsToOrder);
    }

    private Map<String, T1cCertificate> getCertificatesMap(LuxTrustAllData allData) {
        Map<String, T1cCertificate> certs = new HashMap<>();
        for (int i = 0; i < allData.getRootCertificates().size(); i++) {
            certs.put("root-certificate-" + i + 1, allData.getRootCertificates().get(i));
        }
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("signing-certificate", allData.getSigningCertificate());
        return certs;
    }
}