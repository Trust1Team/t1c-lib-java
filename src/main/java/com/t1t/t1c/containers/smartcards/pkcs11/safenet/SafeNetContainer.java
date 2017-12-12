package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.DigestAlgorithm;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class SafeNetContainer extends GenericContainer<SafeNetContainer, GclSafeNetRestClient, AllData, AllCertificates> {

    private SafeNetContainerConfiguration safeNetConfig;

    public SafeNetContainer(LibConfig config, GclReader reader, GclSafeNetRestClient httpClient, SafeNetContainerConfiguration safeNetConfig) {
        super(config, reader, httpClient, null);
        this.safeNetConfig = safeNetConfig;
    }

    @Override
    public SafeNetContainer createInstance(LibConfig config, GclReader reader, GclSafeNetRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.SAFENET;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return null;
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return null;
    }

    @Override
    public AllData getAllData() throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates() throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        return null;
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        return null;
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        return null;
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
    public Class<AllData> getAllDataClass() {
        return null;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
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