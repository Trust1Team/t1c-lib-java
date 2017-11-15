package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.containers.AbstractContainer;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.exceptions.SafeNetContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclSafeNetInfo;
import com.t1t.t1c.model.rest.GclSafeNetRequest;
import com.t1t.t1c.model.rest.GclSafeNetSlot;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.rest.ContainerRestClient;
import com.t1t.t1c.utils.CertificateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class SafeNetContainer extends AbstractContainer implements ISafeNetContainer {

    private SafeNetContainerConfiguration safeNetConfig;
    private String module;

    private static final Logger log = LoggerFactory.getLogger(SafeNetContainer.class);

    public SafeNetContainer(String readerId, ContainerRestClient httpClient, SafeNetContainerConfiguration configuration) {
        super(readerId, ContainerType.SAFENET, httpClient);
        if (configuration != null) {
            safeNetConfig = configuration;
            boolean driverExists = false;
            if (SystemUtils.IS_OS_WINDOWS) {
                module = safeNetConfig.getWindows().toString();
                driverExists = safeNetConfig.getWindows().toFile().exists();
            } else if (SystemUtils.IS_OS_MAC) {
                module = safeNetConfig.getMac().toString();
                driverExists = safeNetConfig.getMac().toFile().exists();
            } else if (SystemUtils.IS_OS_LINUX) {
                module = safeNetConfig.getLinux().toString();
                driverExists = safeNetConfig.getLinux().toFile().exists();
            } else {
                throw ExceptionFactory.unsupportedOperationException("Unsupported OS: " + SystemUtils.OS_NAME);
            }
            if (!driverExists && getConfig().getEnvironment() != Environment.DEV) {
                throw ExceptionFactory.safeNetContainerException("SafeNet container could not find driver at configured location: " + this.module);
            }
        }
        else {
            throw ExceptionFactory.safeNetContainerException("SafeNet container configuration not set");
        }
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        return new SafeNetAllData().withSlots(getSlots());
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("SafeNet Container does not support generic certificate dump, use getCertificates(Integer slotId, String... pin) instead");
    }

    @Override
    public List<T1cCertificate> getCertificates(Integer slotId, String... pin) throws SafeNetContainerException {
        try {
            return CertificateUtil.createT1cCertificates(returnData(getHttpClient().getSafeNetCertificates(getTypeId(), getReaderId(), getCertificateRequest(slotId, pin))));
        } catch (RestException ex) {
            checkPinExceptionMessage(ex);
            throw ExceptionFactory.safeNetContainerException("Could not retrieve certificates", ex);
        }
    }

    @Override
    public GclSafeNetInfo getInfo() throws SafeNetContainerException {
        try {
            return returnData(getHttpClient().getSafeNetInfo(getTypeId(), getReaderId(), getRequest()));
        } catch (RestException ex) {
            throw ExceptionFactory.safeNetContainerException("Could not retrieve info");
        }
    }

    @Override
    public List<GclSafeNetSlot> getSlots() throws SafeNetContainerException {
        return getSlots(null);
    }

    @Override
    public List<GclSafeNetSlot> getSlotsWithToken() throws SafeNetContainerException {
        return getSlots(true);
    }

    private List<GclSafeNetSlot> getSlots(Boolean tokenPresent) throws SafeNetContainerException {
        try {
            return returnData(getHttpClient().getSafeNetSlots(getTypeId(), getReaderId(), getRequest(), tokenPresent));
        } catch (RestException ex) {
            throw ExceptionFactory.safeNetContainerException("Could not retrieve slots");
        }
    }

    private GclSafeNetRequest getCertificateRequest(Integer slotId, String... pin) {
        if (slotId == null) {
            throw ExceptionFactory.safeNetContainerException("Slot ID must be specified");
        }
        GclSafeNetRequest request = getRequest().withSlotId(slotId);
        pinEnforcementCheck(pin);
        if (pin != null && pin.length > 0 && StringUtils.isNotEmpty(pin[0])) {
            return request.withPin(pin[0]);
        }
        return request;
    }

    private GclSafeNetRequest getRequest() {
        return new GclSafeNetRequest().withModule(this.module);
    }
}