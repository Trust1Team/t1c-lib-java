package com.t1t.t1c.containers;

import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 *
 * Virtual container.
 *
 * //TODO
 */
public abstract class GenericContainer<T extends GenericContainer> implements GclContainer{
    /*Properties*/
    protected String readerId;
    protected transient String pin;
    /*Instantiation*/
    public GenericContainer() {}
    public GenericContainer(String readerId, ContainerRestClient httpClient, String pin) { createInstance(readerId, httpClient, pin); }
    protected abstract T createInstance(String readerId, ContainerRestClient httpClient, String pin);
    /*Data Related*/
    protected abstract List<String> getAllDataFilters();
    protected abstract List<String> getAllCertificateFilters();
    protected abstract AllData getAllData() throws GenericContainerException;
    protected abstract AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllData getAllData(Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates() throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException;
    /*Token Functionality*/
    protected abstract Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;
    protected abstract String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;
    protected abstract String sign(GclAuthenticateOrSignData data) throws GenericContainerException;

    protected String createFilterParams(List<String> params) {
        StringBuilder sb = new StringBuilder("");
        Iterator it = params.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(",");
            }
        }
        return StringUtils.isEmpty(sb.toString()) ? null : sb.toString();
    }

    protected void pinEnforcementCheck(String... pin) {
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
}
