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
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxTrustContainer extends GenericContainer<LuxTrustContainer, GclLuxTrustRestClient> {

    private final ContainerType type = ContainerType.LUXTRUST;

    public LuxTrustContainer(LibConfig config, GclReader reader, GclLuxTrustRestClient gclLuxTrustRestClient, String pin) {
        super(config, reader, gclLuxTrustRestClient, pin);
    }

    @Override
    public LuxTrustContainer createInstance(LibConfig config, GclReader reader, GclLuxTrustRestClient httpClient, String pin) {
        if (StringUtils.isEmpty(pin)) {
            throw ExceptionFactory.luxTrustContainerException("PIN is required to initialize Lux ID container");
        }
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
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
    public AllData getAllData() throws LuxTrustContainerException {
        return getAllData(null, null);
    }

    @Override
    public AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws LuxTrustContainerException {
        try {
            GclLuxTrustAllData data = RestExecutor.returnData(httpClient.getLuxTrustAllData(getTypeId(), reader.getId(), this.pin, createFilterParams(filterParams)));
            return new LuxTrustAllData(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    public AllData getAllData(Boolean... parseCertificates) throws LuxTrustContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public AllCertificates getAllCertificates() throws LuxTrustContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws LuxTrustContainerException {
        try {
            GclLuxTrustAllCertificates certs = RestExecutor.returnData(httpClient.getLuxTrustAllCertificates(getTypeId(), reader.getId(), this.pin, createFilterParams(filterParams)));
            return new LuxTrustAllCertificates(certs, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve all certificates", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws LuxTrustContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws LuxTrustContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), this.pin, new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), this.pin)));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.luxTrustContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(GclAuthenticateOrSignData data) throws LuxTrustContainerException {
        try {
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), this.pin, data));
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("Could not authenticate with container", ex);
        }
    }

    @Override
    public String sign(GclAuthenticateOrSignData data) throws LuxTrustContainerException {
        try {
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), this.pin, data));
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("Could not authenticate with container", ex);
        }
    }

    public Boolean isActivated() {
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.isLuxTrustActivated(getTypeId(), reader.getId(), this.pin)));
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not verify activation status", ex);
        }
    }

    public List<T1cCertificate> getRootCertificates(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getRootCertificates(getTypeId(), reader.getId(), this.pin)), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve root certificates", ex);
        }
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId(), this.pin)), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("could not retrieve signing certificate", ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId(), this.pin)), parse);
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
}