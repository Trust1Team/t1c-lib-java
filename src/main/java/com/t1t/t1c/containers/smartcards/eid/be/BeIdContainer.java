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
public class BeIdContainer extends GenericContainer<BeIdContainer, GclBeIdRestClient, BeIdAllData, BeIdAllCertificates> {

    private static final String PRIVATE_KEY_REFERENCE = "non-repudiation";

    public BeIdContainer(LibConfig config, GclReader reader, GclBeIdRestClient gclBeIdRestClient) {
        super(config, reader, gclBeIdRestClient, null);
    }

    /*Dynamic instance creation*/
    @Override
    public BeIdContainer createInstance(LibConfig config, GclReader reader, GclBeIdRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.BEID;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("address", "rn", "picture", "root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    public BeIdAllData getAllData() throws BeIdContainerException {
        return getAllData(null, null);
    }

    @Override
    public BeIdAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws BeIdContainerException {
        try {
            GclBeIdAllData data = RestExecutor.returnData(httpClient.getBeIdAllData(type.getId(), reader.getId(), createFilterParams(filterParams)));
            return new BeIdAllData(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    public BeIdAllData getAllData(Boolean... parseCertificates) throws BeIdContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public BeIdAllCertificates getAllCertificates() throws BeIdContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    public BeIdAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws BeIdContainerException {
        try {
            GclBeIdAllCertificates data = RestExecutor.returnData(httpClient.getBeIdAllCertificates(type.getId(), reader.getId(), createFilterParams(filterParams)));
            return new BeIdAllCertificates(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    public BeIdAllCertificates getAllCertificates(Boolean... parseCertificates) throws BeIdContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws BeIdContainerException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), new GclVerifyPinRequest().withPrivateKeyReference(PRIVATE_KEY_REFERENCE).withPin(pin[0]))));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId())));
            }
        } catch (RestException ex) {
            PinUtil.checkPinExceptionMessage(ex);
            throw ExceptionFactory.beIdContainerException("Could not verify pin with container", ex);
        }
    }

    @Override
    public String authenticate(GclAuthenticateOrSignData data) throws BeIdContainerException {
        try {
            return RestExecutor.returnData(httpClient.authenticate(type.getId(), reader.getId(), data));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not authenticate with container", ex);
        }
    }

    @Override
    public String sign(GclAuthenticateOrSignData data) throws BeIdContainerException {
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

    @Override
    public Class<BeIdAllData> getAllDataClass() {
        return BeIdAllData.class;
    }

    @Override
    public Class<BeIdAllCertificates> getAllCertificateClass() {
        return BeIdAllCertificates.class;
    }
}