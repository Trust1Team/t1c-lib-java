package com.t1t.t1c.containers;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.gcl.IGclClient;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.model.rest.GclCard;
import com.t1t.t1c.model.rest.GclVerifyPinRequest;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.rest.AbstractRestClient;
import com.t1t.t1c.rest.ContainerRestClient;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by michallispashidis on 31/10/2017.
 * Template Method pattern (behavioural)
 * => prio: BE, LUXID, LUXTRUST
 */
public abstract class AbstractContainer extends AbstractRestClient<ContainerRestClient> implements GenericContainer {

    private LibConfig config;
    private String readerId;
    private ContainerType type;
    private String pin;

    protected AbstractContainer(LibConfig config, String readerId, ContainerType type, ContainerRestClient httpClient, String... pin) {
        super(httpClient);
        IGclClient gcl = FactoryService.getGclClient();
        GclCard card = gcl.getReader(readerId).getCard();
        if (card == null) {
            throw ExceptionFactory.genericContainerException("No card inserted in reader");
        }
        this.type = ContainerUtil.determineContainer(card);
        if (this.type != type) {
            throw ExceptionFactory.genericContainerException("Requested container (" + this.type + ") does not match available container for card: " + type);
        }
        if (!ContainerUtil.isContainerAvailable(type, gcl.getContainers())) {
            throw ExceptionFactory.containerNotAvailableException(type);
        }
        this.readerId = readerId;
        this.config = config;
        this.type = type;
        if (pin.length > 0) {
            this.pin = pin[0];
        }
    }

    public AbstractContainer(LibConfig config, String readerId, ContainerRestClient httpClient, String... pin) {
        super(httpClient);
        this.config = config;
        this.readerId = readerId;
        // when instantiating a generic container, we automatically pick the first suitable container type
        IGclClient gcl = FactoryService.getGclClient();
        GclCard card = gcl.getReader(readerId).getCard();
        if (card == null) {
            throw ExceptionFactory.genericContainerException("No card inserted in reader");
        }
        this.type = ContainerUtil.determineContainer(card);
        if (!ContainerUtil.isContainerAvailable(type, gcl.getContainers())) {
            throw ExceptionFactory.containerNotAvailableException(type);
        }
        if (pin.length > 0) {
            this.pin = pin[0];
        }
    }

    protected LibConfig getConfig() {
        return this.config;
    }

    @Override
    public List<String> getAllDataFilters() {
        return type.getDataFilters();
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return type.getCertificateFilters();
    }

    @Override
    public ContainerType getType() {
        return this.type;
    }

    @Override
    public String getTypeId() {
        return this.type.getId();
    }

    @Override
    public String getReaderId() {
        return this.readerId;
    }

    @Override
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public abstract AllData getAllData(List<String> filterParams) throws GenericContainerException;

    public abstract AllCertificates getAllCertificates(List<String> filterParams) throws GenericContainerException;

    @Override
    public boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        pinEnforcementCheck(pin);
        try {
            if (pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                if (StringUtils.isNotEmpty(this.pin)) {
                    return isCallSuccessful(executeCall(getHttpClient().verifyPinSecured(type.getId(), readerId, this.pin, new GclVerifyPinRequest().withPin(pin[0]))));
                } else
                    return isCallSuccessful(executeCall(getHttpClient().verifyPin(type.getId(), readerId, new GclVerifyPinRequest().withPin(pin[0]))));
            } else {
                if (StringUtils.isNotEmpty(this.pin)) {
                    return isCallSuccessful(executeCall(getHttpClient().verifyPinSecured(type.getId(), readerId, this.pin)));
                } else return isCallSuccessful(executeCall(getHttpClient().verifyPin(type.getId(), readerId)));
            }
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not verify pin with generic container", ex);
        }
    }

    @Override
    public String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return returnData(getHttpClient().authenticateSecured(type.getId(), readerId, pin, data));
            } else return returnData(getHttpClient().authenticate(type.getId(), readerId, data));
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not authenticate with generic container", ex);
        }
    }

    @Override
    public String sign(GclAuthenticateOrSignData data) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(pin)) {
                return returnData(getHttpClient().signSecured(type.getId(), readerId, pin, data));
            } else return returnData(getHttpClient().sign(type.getId(), readerId, data));
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not authenticate with generic container", ex);
        }
    }

    protected T1cCertificate getRootCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return createT1cCertificate(returnData(getHttpClient().getSecuredRootCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return createT1cCertificate(returnData(getHttpClient().getRootCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve root certificate from container", ex);
        }
    }


    protected T1cCertificate getAuthenticationCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return createT1cCertificate(returnData(getHttpClient().getSecuredAuthenticationCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return createT1cCertificate(returnData(getHttpClient().getAuthenticationCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    protected T1cCertificate getNonRepudiationCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return createT1cCertificate(returnData(getHttpClient().getSecuredNonRepudiationCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return createT1cCertificate(returnData(getHttpClient().getNonRepudiationCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    protected T1cCertificate getSigningCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return createT1cCertificate(returnData(getHttpClient().getSecuredSigningCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return createT1cCertificate(returnData(getHttpClient().getSigningCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve RRN certificate from container", ex);
        }
    }

    //
    // Utility methods
    //

    protected String createFilterParams(List<String> params) {
        StringBuilder sb = new StringBuilder();
        Iterator it = params.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    protected T1cCertificate createT1cCertificate(String certificate, boolean parse) {
        T1cCertificate cert = new T1cCertificate().withBase64(certificate);
        if (parse) {
            cert.setParsed(CertificateUtil.parseCertificate(certificate));
        }
        return cert;
    }

    private void pinEnforcementCheck(String... pin) {
        boolean pinPresent = pin.length > 0 && StringUtils.isNotBlank(pin[0]);
        boolean hardwarePinPadPresent = FactoryService.getGclClient().getReader(readerId).getPinpad();
        if (config.isHardwarePinPadForced()) {
            if (hardwarePinPadPresent) {
                if (pinPresent) {
                    throw ExceptionFactory.verifyPinException("Strict PIN-pad enforcement is enabled. This request was sent with a PIN, but the reader has a PIN-pad.");
                }
            } else if (!pinPresent) {
                throw ExceptionFactory.verifyPinException("Strict PIN-pad enforcement is enabled. This request was sent without a PIN, but the reader does not have a PIN-pad.");
            }
        } else {
            if (!hardwarePinPadPresent && !pinPresent) {
                throw ExceptionFactory.verifyPinException("The request was sent without a PIN, but the reader doest not have a PIN-pad");
            }
        }
    }
}
