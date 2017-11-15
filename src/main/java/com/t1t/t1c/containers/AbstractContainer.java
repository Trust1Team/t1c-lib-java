package com.t1t.t1c.containers;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.gcl.IGclClient;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.rest.AbstractRestClient;
import com.t1t.t1c.rest.ContainerRestClient;
import com.t1t.t1c.services.FactoryService;
import com.t1t.t1c.utils.ContainerUtil;
import com.t1t.t1c.utils.CertificateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by michallispashidis on 31/10/2017.
 * Template Method pattern (behavioural)
 * => prio: BE, LUXID, LUXTRUST
 */
public abstract class AbstractContainer extends AbstractRestClient<ContainerRestClient> implements GenericContainer {

    private String readerId;
    private ContainerType type;
    private String pin;

    protected AbstractContainer(String readerId, ContainerType type, ContainerRestClient httpClient, String... pin) {
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
        this.type = type;
        if (pin.length > 0) {
            this.pin = pin[0];
        }
    }

    protected LibConfig getConfig() {
        return FactoryService.getConfig();
    }

    protected abstract Logger getLogger();

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
    public AllData getAllData() throws GenericContainerException {
        return getAllData(new ArrayList<String>(), true);
    }

    @Override
    public abstract AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException;

    @Override
    public AllCertificates getAllCertificates() throws GenericContainerException {
        return getAllCertificates(new ArrayList<String>(), true);
    }

    public abstract AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException;

    @Override
    public boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        if (ContainerUtil.canVerifyPin(type)) {
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
                    } else
                        return isCallSuccessful(executeCall(getHttpClient().verifyPin(type.getId(), readerId)));
                }
            } catch (RestException ex) {
                checkPinExceptionMessage(ex);
                throw ExceptionFactory.genericContainerException("Could not verify pin with generic container", ex);
            }
        } else {
            throw ExceptionFactory.verifyPinException("Container does not support pin verification");
        }
    }

    @Override
    public String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException {
        if (ContainerUtil.canAuthenticate(type)) {
            try {
                if (StringUtils.isNotEmpty(this.pin)) {
                    return returnData(getHttpClient().authenticateSecured(type.getId(), readerId, pin, data));
                } else return returnData(getHttpClient().authenticate(type.getId(), readerId, data));
            } catch (RestException ex) {
                throw ExceptionFactory.genericContainerException("Could not authenticate with generic container", ex);
            }
        }
        else {
            throw ExceptionFactory.authenticateException("Container does not support authenticate");
        }
    }

    @Override
    public String sign(GclAuthenticateOrSignData data) throws GenericContainerException {
        if (ContainerUtil.canSign(type)) {
            try {
                if (StringUtils.isNotEmpty(pin)) {
                    return returnData(getHttpClient().signSecured(type.getId(), readerId, pin, data));
                } else return returnData(getHttpClient().sign(type.getId(), readerId, data));
            } catch (RestException ex) {
                throw ExceptionFactory.genericContainerException("Could not authenticate with generic container", ex);
            }
        } else {
            throw ExceptionFactory.signingException("Container does not support signing");
        }
    }

    protected T1cCertificate getRootCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getSecuredRootCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getRootCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve root certificate from container", ex);
        }
    }


    protected T1cCertificate getAuthenticationCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getSecuredAuthenticationCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getAuthenticationCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    protected T1cCertificate getNonRepudiationCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getSecuredNonRepudiationCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getNonRepudiationCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    protected T1cCertificate getSigningCertificate(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getSecuredSigningCertificate(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getSigningCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.genericContainerException("Could not retrieve RRN certificate from container", ex);
        }
    }

    public List<T1cCertificate> getRootCertificates(boolean parse) throws GenericContainerException {
        try {
            if (StringUtils.isNotEmpty(this.pin)) {
                return CertificateUtil.createT1cCertificates(returnData(getHttpClient().getSecuredRootCertificates(getTypeId(), getReaderId(), getPin())), parse);
            } else
                return CertificateUtil.createT1cCertificates(returnData(getHttpClient().getRootCertificates(getTypeId(), getReaderId())), parse);
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

    private void pinEnforcementCheck(String... pin) {
        boolean pinPresent = pin.length > 0 && StringUtils.isNotBlank(pin[0]);
        boolean hardwarePinPadPresent = FactoryService.getGclClient().getReader(readerId).getPinpad();
        if (FactoryService.getConfig().isHardwarePinPadForced()) {
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

    protected void checkPinExceptionMessage(RestException ex) {
        if (StringUtils.isNotEmpty(ex.getJsonError())) {
            try {
                GclError error = new Gson().fromJson(ex.getJsonError(), GclError.class);
                throw ExceptionFactory.verifyPinException(error.getDescription());
            } catch (JsonSyntaxException e) {
                getLogger().error("Couldn't decode error message: ", e);
            }
        }
    }
}
