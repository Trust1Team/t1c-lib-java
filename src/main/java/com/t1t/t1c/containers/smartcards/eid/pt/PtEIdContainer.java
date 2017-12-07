package com.t1t.t1c.containers.smartcards.eid.pt;

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
public class PtEIdContainer extends GenericContainer<PtEIdContainer, GclPtIdRestClient, PtIdAllData, PtIdAllCertificates> {

    public PtEIdContainer(LibConfig config, GclReader reader, GclPtIdRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public PtEIdContainer createInstance(LibConfig config, GclReader reader, GclPtIdRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.PT;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("id", "root-certificate", "root-authentication-certificate", "authentication-certificate", "root-non-repudiation-certificate", "non-repudiation-certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("root-certificate", "root-authentication-certificate", "authentication-certificate", "root-non-repudiation-certificate", "non-repudiation-certificate");
    }

    @Override
    public PtIdAllData getAllData() throws PtIdContainerException {
        return getAllData(null, null);
    }

    @Override
    public PtIdAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws PtIdContainerException {
        try {
            return new PtIdAllData(RestExecutor.returnData(httpClient.getPtIdAllData(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    public PtIdAllData getAllData(Boolean... parseCertificates) throws PtIdContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public PtIdAllCertificates getAllCertificates() throws PtIdContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public PtIdAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws PtIdContainerException {
        try {
            return new PtIdAllCertificates(RestExecutor.returnData(httpClient.getPtIdAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams))), parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve all certificates", ex);
        }
    }

    @Override
    public PtIdAllCertificates getAllCertificates(Boolean... parseCertificates) throws PtIdContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws PtIdContainerException, VerifyPinException {
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
            throw ExceptionFactory.ptIdContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws PtIdContainerException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.ptIdContainerException("Could not authenticate with container", ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws PtIdContainerException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.ptIdContainerException("Could not authenticate with container", ex);
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
    public Class<PtIdAllData> getAllDataClass() {
        return PtIdAllData.class;
    }

    @Override
    public Class<PtIdAllCertificates> getAllCertificatesClass() {
        return PtIdAllCertificates.class;
    }

    public GclPtIdData getPtIdData() {
        return getPtIdData(null);
    }

    public GclPtIdData getPtIdData(Boolean includePhoto) {
        try {
            return RestExecutor.returnData(httpClient.getPtIdData(getTypeId(), reader.getId(), includePhoto));
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve ID data", ex);
        }
    }

    public String getPtIdPhoto() {
        try {
            return RestExecutor.returnData(httpClient.getPtIdPhoto(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve picture", ex);
        }
    }

    public T1cCertificate getRootCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve root certificate", ex);
        }
    }

    public T1cCertificate getRootAuthenticationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootAuthenticationCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve root authentication certificate", ex);
        }
    }

    public T1cCertificate getRootNonRepudiationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootNonRepudiationCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve root non-repudiation certificate", ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve authentication certificate", ex);
        }
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("could not retrieve non-repudiation certificate", ex);
        }
    }

    public GclPtIdAddress getAddress(String... pin) {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.returnData(httpClient.getAddress(type.getId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0])));
            } else {
                return RestExecutor.returnData(httpClient.getAddress(type.getId(), reader.getId()));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.ptIdContainerException("Could not verify pin with container", ex);
        }
    }
}