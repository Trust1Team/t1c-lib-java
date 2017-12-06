package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustAllData;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxIdContainer extends GenericContainer<LuxIdContainer, GclLuxIdRestClient, LuxIdAllData, LuxIdAllCertificates> {

    public LuxIdContainer(LibConfig config, GclReader reader, GclLuxIdRestClient gclLuxIdRestClient, String pin) {
        super(config, reader, gclLuxIdRestClient, pin);
    }

    @Override
    public LuxIdContainer createInstance(LibConfig config, GclReader reader, GclLuxIdRestClient httpClient, String pin) {
        if (StringUtils.isEmpty(pin)) {
            throw ExceptionFactory.luxIdContainerException("PIN is required to initialize Lux ID container");
        }
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.LUXID;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("authentication-certificate", "signature-image", "biometric", "non-repudiation-certificate", "picture", "root-certificates");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates");
    }

    @Override
    public LuxIdAllData getAllData() throws LuxIdContainerException {
        return getAllData(null, null);
    }

    @Override
    public LuxIdAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws LuxIdContainerException {
        try {
            GclLuxIdAllData data = RestExecutor.returnData(httpClient.getLuxIdAllData(getTypeId(), reader.getId(), this.pin, createFilterParams(filterParams)));
            return new LuxIdAllData(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    public LuxIdAllData getAllData(Boolean... parseCertificates) throws LuxIdContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public LuxIdAllCertificates getAllCertificates() throws LuxIdContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public LuxIdAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws LuxIdContainerException {
        try {
            GclLuxIdAllCertificates data = RestExecutor.returnData(httpClient.getLuxIdAllCertificates(getTypeId(), reader.getId(), this.pin, createFilterParams(filterParams)));
            return new LuxIdAllCertificates(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    public LuxIdAllCertificates getAllCertificates(Boolean... parseCertificates) throws LuxIdContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws LuxIdContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), this.pin, new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), this.pin)));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.luxIdContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(GclAuthenticateOrSignData data) throws LuxIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), this.pin, data));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not authenticate with container", ex);
        }
    }

    @Override
    public String sign(GclAuthenticateOrSignData data) throws LuxIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), this.pin, data));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not authenticate with container", ex);
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

    public GclLuxIdBiometric getBiometric() throws LuxIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.getLuxIdBiometric(getTypeId(), reader.getId(), pin));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve biometrics with container", ex);
        }
    }

    public GclLuxIdPicture getPicture() throws LuxIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.getLuxIdPicture(getTypeId(), reader.getId(), pin));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve picture with container", ex);
        }
    }

    public GclLuxIdSignatureImage getSignatureImage() throws LuxIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.getLuxIdSignatureImage(getTypeId(), reader.getId(), pin));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve signature image with container", ex);
        }
    }

    public List<T1cCertificate> getRootCertificates(Boolean... parse) throws LuxIdContainerException {
        try {
            return CertificateUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getTypeId(), reader.getId(), pin)), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve root certificates from container", ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws LuxIdContainerException {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId(), pin)), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean... parse) throws LuxIdContainerException {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId(), pin)), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    @Override
    public Class<LuxIdAllData> getAllDataClass() {
        return LuxIdAllData.class;
    }

    @Override
    public Class<LuxIdAllCertificates> getAllCertificateClass() {
        return LuxIdAllCertificates.class;
    }
}