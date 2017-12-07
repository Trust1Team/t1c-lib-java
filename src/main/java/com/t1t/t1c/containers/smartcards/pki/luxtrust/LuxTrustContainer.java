package com.t1t.t1c.containers.smartcards.pki.luxtrust;


import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;

import java.util.Arrays;
import java.util.List;

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
    public LuxTrustAllData getAllData() throws LuxTrustContainerException {
        return getAllData(null, null);
    }

    @Override
    public LuxTrustAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws LuxTrustContainerException {
        try {
            GclLuxTrustAllData data = RestExecutor.returnData(httpClient.getLuxTrustAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)));
            return new LuxTrustAllData(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    public LuxTrustAllData getAllData(Boolean... parseCertificates) throws LuxTrustContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates() throws LuxTrustContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws LuxTrustContainerException {
        try {
            GclLuxTrustAllCertificates certs = RestExecutor.returnData(httpClient.getLuxTrustAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)));
            return new LuxTrustAllCertificates(certs, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve all certificates", ex);
        }
    }

    @Override
    public LuxTrustAllCertificates getAllCertificates(Boolean... parseCertificates) throws LuxTrustContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws LuxTrustContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId())));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.luxTrustContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws LuxTrustContainerException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkArgument(algo != null
                    && (algo.equals(DigestAlgorithm.SHA1) || algo.equals(DigestAlgorithm.SHA256)), "algorithmReference must be provided and must be one of: SHA1, SHA256");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.luxTrustContainerException("Could not authenticate with container", ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws LuxTrustContainerException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkArgument(algo != null
                    && (algo.equals(DigestAlgorithm.SHA1) || algo.equals(DigestAlgorithm.SHA256)), "algorithmReference must be provided and must be one of: SHA1, SHA256");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.luxTrustContainerException("Could not authenticate with container", ex);
        }
    }

    public Boolean isActivated() {
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.isLuxTrustActivated(getTypeId(), reader.getId())));
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not verify activation status", ex);
        }
    }

    public List<T1cCertificate> getRootCertificates(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve root certificates", ex);
        }
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve signing certificate", ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve signing certificate", ex);
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
    public Class<LuxTrustAllData> getAllDataClass() {
        return LuxTrustAllData.class;
    }

    @Override
    public Class<LuxTrustAllCertificates> getAllCertificateClass() {
        return LuxTrustAllCertificates.class;
    }
}