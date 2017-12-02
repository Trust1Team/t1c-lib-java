package com.t1t.t1c.containers.smartcards.eid.be;

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

import java.util.Arrays;
import java.util.List;


/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class BeIdContainer extends GenericContainer<BeIdContainer, GclBeIdRestClient> {

    private final ContainerType type = ContainerType.BEID;

    public BeIdContainer(LibConfig config, GclReader reader, GclBeIdRestClient gclBeIdRestClient) {
        super(config, reader, gclBeIdRestClient, null);
    }

    /*Dynamic instance creation*/
    @Override
    protected BeIdContainer createInstance(LibConfig config, GclReader reader, GclBeIdRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        return this;
    }

    @Override
    protected List<String> getAllDataFilters() {
        return Arrays.asList("address", "rn", "picture", "root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    protected List<String> getAllCertificateFilters() {
        return Arrays.asList("root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    protected AllData getAllData() throws BeIdContainerException {
        return getAllData(null, null);
    }

    @Override
    protected AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws BeIdContainerException {
        try {
            GclBeIdAllData data = RestExecutor.returnData(httpClient.getBeIdAllData(reader.getId(), type.getId(), createFilterParams(filterParams)));
            return new BeIdAllData(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    protected AllData getAllData(Boolean... parseCertificates) throws BeIdContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    protected AllCertificates getAllCertificates() throws BeIdContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    protected AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws BeIdContainerException {
        try {
            GclBeIdAllCertificates data = RestExecutor.returnData(httpClient.getBeIdAllCertificates(reader.getId(), type.getId(), createFilterParams(filterParams)));
            return new BeIdAllCertificates(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    protected AllCertificates getAllCertificates(Boolean... parseCertificates) throws BeIdContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    protected Boolean verifyPin(String... pin) throws BeIdContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId())));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.beIdContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    protected String authenticate(GclAuthenticateOrSignData data) throws BeIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.authenticate(type.getId(), reader.getId(), data));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not authenticate with container", ex);
        }
    }

    @Override
    protected String sign(GclAuthenticateOrSignData data) throws BeIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.sign(type.getId(), reader.getId(), data));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not authenticate with container", ex);
        }
    }


    public GclBeIdRn getRnData() {
        try {
            return RestExecutor.returnData(httpClient.getRnData(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve RnData from BeId container", ex);
        }
    }

    public GclBeIdAddress getBeIdAddress() {
        try {
            return RestExecutor.returnData(httpClient.getBeIdAddress(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve address data from BeId container", ex);
        }
    }

    public String getBeIdPicture() {
        try {
            return RestExecutor.returnData(httpClient.getBeIdPicture(getTypeId(), reader.getId()));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve picture data from BeId container", ex);
        }
    }

    public T1cCertificate getRootCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRootCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve root certificate from container", ex);
        }
    }

    public T1cCertificate getCitizenCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getCitizenCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve root certificate from container", ex);
        }
    }

    public T1cCertificate getNonRepudiationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getNonRepudiationCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve root certificate from container", ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve root certificate from container", ex);
        }
    }

    public T1cCertificate getRrnCertificate(Boolean... parse) {
        try {
            return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getRrnCertificate(getTypeId(), reader.getId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve root certificate from container", ex);
        }
    }


    @Override
    public ContainerType getType() {
        return ContainerType.BEID;
    }

    @Override
    public String getTypeId() {
        return getType().getId();
    }
}