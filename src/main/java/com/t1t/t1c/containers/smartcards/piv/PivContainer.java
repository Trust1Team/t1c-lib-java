package com.t1t.t1c.containers.smartcards.piv;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.DigestAlgorithm;
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
public class PivContainer extends GenericContainer<PivContainer, GclPivRestClient, PivAllData, PivAllCertificates> {

    public PivContainer(LibConfig config, GclReader reader, GclPivRestClient httpClient, String pin) {
        super(config, reader, httpClient, pin);
    }

    @Override
    public PivContainer createInstance(LibConfig config, GclReader reader, GclPivRestClient httpClient, String pin) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "PIN required to initialize PIV container");
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.PIV;
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
    public PivAllData getAllData() throws RestException {
        return getAllData(null, null);
    }

    @Override
    public PivAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        return new PivAllData(RestExecutor.returnData(httpClient.getAllData(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin), createFilterParams(filterParams))), parseCertificates);
    }

    @Override
    public PivAllData getAllData(Boolean... parseCertificates) throws RestException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public PivAllCertificates getAllCertificates() throws RestException {
        return getAllCertificates(null, null);
    }

    @Override
    public PivAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        return new PivAllCertificates(RestExecutor.returnData(httpClient.getAllCertificates(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin), createFilterParams(filterParams))), parseCertificates);
    }

    @Override
    public PivAllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws RestException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin))));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
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
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
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
    public Class<PivAllData> getAllDataClass() {
        return PivAllData.class;
    }

    @Override
    public Class<PivAllCertificates> getAllCertificatesClass() {
        return PivAllCertificates.class;
    }
    
    public GclPivPrintedInformation getPrintedInformation() throws RestException {
        return RestExecutor.returnData(httpClient.getPrintedInformation(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)));
    }

    public GclPivFacialImage getFacialImage() throws RestException {
        return RestExecutor.returnData(httpClient.getFacialImage(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)));
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin))), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) throws RestException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin))), parse);
    }

    public List<DigestAlgorithm> getAllAlgoRefsForAuthentication() throws RestException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getAuthenticationAlgoRefs(getTypeId(), reader.getId())));
    }

    public List<DigestAlgorithm> getAllAlgoRefsForSigning() throws RestException {
        return getAlgorithms(RestExecutor.returnData(httpClient.getSignAlgoRefs(getTypeId(), reader.getId())));
    }

    /*public List<String> getAllKeyRefs() {
        return Arrays.asList("authenticate", "sign", "encrypt");
    }*/


}
