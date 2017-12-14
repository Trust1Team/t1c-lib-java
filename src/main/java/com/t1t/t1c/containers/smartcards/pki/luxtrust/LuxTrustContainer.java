package com.t1t.t1c.containers.smartcards.pki.luxtrust;


import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdAllCertificates;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdAllData;
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
public class LuxTrustContainer extends GenericContainer<LuxTrustContainer, GclLuxTrustRestClient, LuxTrustAllData, LuxTrustAllCertificates> {

    public LuxTrustContainer(LibConfig config, GclReader reader, GclLuxTrustRestClient gclLuxTrustRestClient) {
        super(config, reader, gclLuxTrustRestClient, null);
    }

    @Override
    public LuxTrustContainer createInstance(LibConfig config, GclReader reader, GclLuxTrustRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
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
    public LuxTrustAllData getAllData() throws RestException {
        return getAllData(null, null);
    }

    @Override
    public LuxTrustAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        GclLuxTrustAllData data = RestExecutor.returnData(httpClient.getLuxTrustAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)));
        return new LuxTrustAllData(data, parseCertificates);
    }

    @Override
    public LuxTrustAllData getAllData(Boolean... parseCertificates) throws RestException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates() throws RestException {
        return getAllCertificates(null, null);
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        GclLuxTrustAllCertificates certs = RestExecutor.returnData(httpClient.getLuxTrustAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)));
        return new LuxTrustAllCertificates(certs, parseCertificates);
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws VerifyPinException, RestException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId())));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkArgument(algo != null
                    && (algo.equals(DigestAlgorithm.SHA1) || algo.equals(DigestAlgorithm.SHA256)), "algorithmReference must be provided and must be one of: SHA1, SHA256");
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
            Preconditions.checkArgument(algo != null
                    && (algo.equals(DigestAlgorithm.SHA1) || algo.equals(DigestAlgorithm.SHA256)), "algorithmReference must be provided and must be one of: SHA1, SHA256");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public Boolean isActivated() throws RestException {
        return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.isLuxTrustActivated(getTypeId(), reader.getId())));
    }

    public List<T1cCertificate> getRootCertificates(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId())), parse);
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId())), parse);
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
    public ContainerData dumpData(String... pin) throws RestException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        LuxTrustAllData allData = getAllData(true);
        data.setAllCertificates(getCertificatesMap(allData));
        data.setAuthenticationCertificateChain(getCertChain(allData.getRootCertificates(), allData.getAuthenticationCertificate()));
        data.setSigningCertificateChain(getCertChain(allData.getRootCertificates(), allData.getSigningCertificate()));
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
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, RestException {
        LuxTrustAllCertificates certs = getAllCertificates(true);
        List<T1cCertificate> certsToOrder = new ArrayList<>(certs.getRootCertificates());
        certsToOrder.add(certs.getSigningCertificate());
        return orderCertificates(certsToOrder);
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, RestException {
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