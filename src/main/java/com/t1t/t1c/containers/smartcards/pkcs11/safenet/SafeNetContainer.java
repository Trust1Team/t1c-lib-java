package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.containers.ContainerRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class SafeNetContainer extends GenericContainer<SafeNetContainer>{

/*    private SafeNetContainerConfiguration safeNetConfig;
    private String module;*/

    private static final Logger log = LoggerFactory.getLogger(SafeNetContainer.class);

    @Override
    protected SafeNetContainer createInstance(String readerId, ContainerRestClient httpClient, String pin) {
        return null;
    }

    @Override
    protected List<String> getAllDataFilters() {
        return null;
    }

    @Override
    protected List<String> getAllCertificateFilters() {
        return null;
    }

    @Override
    protected AllData getAllData() throws GenericContainerException {
        return null;
    }

    @Override
    protected AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected AllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected AllCertificates getAllCertificates() throws GenericContainerException {
        return null;
    }

    @Override
    protected AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        return null;
    }

    @Override
    protected String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException {
        return null;
    }

    @Override
    protected String sign(GclAuthenticateOrSignData data) throws GenericContainerException {
        return null;
    }

    @Override
    public ContainerType getType() {
        return null;
    }

    @Override
    public String getTypeId() {
        return null;
    }

/*    public SafeNetContainer(String reader, ContainerRestClient httpClient, SafeNetContainerConfiguration configuration) {
        super(reader, ContainerType.SAFENET, httpClient);
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
    }*/

}