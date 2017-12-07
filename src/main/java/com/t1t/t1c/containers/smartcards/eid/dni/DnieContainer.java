package com.t1t.t1c.containers.smartcards.eid.dni;

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
public class DnieContainer extends GenericContainer<DnieContainer, GclDniRestClient, DnieAllData, DnieAllCertificates> {

    public DnieContainer (LibConfig config, GclReader reader, GclDniRestClient gclDniRestClient) {
        super(config, reader, gclDniRestClient, null);
    }

    @Override
    public DnieContainer createInstance(LibConfig config, GclReader reader, GclDniRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.DNIE;
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
    public DnieAllData getAllData() throws DnieContainerException {
        return getAllData(null, null);
    }

    @Override
    public DnieAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws DnieContainerException {
        try {
            return new DnieAllData(RestExecutor.returnData(httpClient.getDnieAllData(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("could not dump card data", ex);
        }
    }

    @Override
    public DnieAllData getAllData(Boolean... parseCertificates) throws DnieContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public DnieAllCertificates getAllCertificates() throws DnieContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public DnieAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws DnieContainerException {
        try {
            return new DnieAllCertificates(RestExecutor.returnData(httpClient.getDnieAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("could not dump card certificates", ex);
        }
    }

    @Override
    public DnieAllCertificates getAllCertificates(Boolean... parseCertificates) throws DnieContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws DnieContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId())));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.dnieContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo,  String... pin) throws DnieContainerException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.dnieContainerException("could not authenticate", ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws DnieContainerException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.dnieContainerException("could not sign", ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws DnieContainerException {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId())) ,parse);
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("could not retrieve authentication certificate", ex);
        }
    }

    public T1cCertificate getIntermediateCertificate(Boolean... parse) throws DnieContainerException {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIntermediateCertificate(getTypeId(), reader.getId())) ,parse);
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("could not retrieve intermediate certificate", ex);
        }
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) throws DnieContainerException {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId())) ,parse);
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("could not retrieve signing certificate", ex);
        }
    }

    public GclDnieInfo getInfo() throws DnieContainerException {
        try {
            return RestExecutor.returnData(httpClient.getDnieInfo(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("could not retrieve DNIE info", ex);
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
    public Class<DnieAllData> getAllDataClass() {
        return DnieAllData.class;
    }

    @Override
    public Class<DnieAllCertificates> getAllCertificatesClass() {
        return DnieAllCertificates.class;
    }
}