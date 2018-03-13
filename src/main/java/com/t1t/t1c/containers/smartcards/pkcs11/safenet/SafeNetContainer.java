package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.*;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class SafeNetContainer extends GenericContainer<SafeNetContainer, GclSafeNetRestClient, SafeNetAllData, AllCertificates> {

    private String modulePath;

    // Default constructor for testing purposes
    public SafeNetContainer(GclReader reader, GclSafeNetRestClient httpClient) {
        this.config = new LibConfig();
        this.reader = reader;
        this.httpClient = httpClient;
        this.type = ContainerType.SAFENET;
        this.modulePath = "/usr/local/lib/libeTPkcs11.dylib";
    }

    public SafeNetContainer(LibConfig config, GclReader reader, GclSafeNetRestClient httpClient, SafeNetContainerConfiguration safeNetConfig) {
        super(config, reader, httpClient, null);
        configureModulePath(safeNetConfig);
    }

    @Override
    public SafeNetContainer createInstance(LibConfig config, GclReader reader, GclSafeNetRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.SAFENET;
        if (this.modulePath == null) {
            configureModulePath(new SafeNetContainerConfiguration());
        }
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Collections.emptyList();
    }

    @Override
    public SafeNetAllData getAllData() throws GenericContainerException {
        return new SafeNetAllData(getSafeNetSlots());
    }

    @Override
    public SafeNetAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return getAllData();
    }

    @Override
    public SafeNetAllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return getAllData();
    }

    @Override
    public AllCertificates getAllCertificates() throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have certificate dump implementation");
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return getAllCertificates();
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return getAllCertificates();
    }

    @Override
    public Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have PIN verification implementation");
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have authentication implementation");
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have signing implementation");
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
    public Class<SafeNetAllData> getAllDataClass() {
        return SafeNetAllData.class;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
        throw ExceptionFactory.unsupportedOperationException("Container does not have certificate dump implementation");
    }

    public SafeNetCertificates getSafeNetCertificates(Integer slotId, String pin, Boolean... parse) throws VerifyPinException, NoConsentException, RestException {
        Preconditions.checkNotNull(slotId, "slotId must be provided");
        Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "PIN must be provided");
        try {
            return new SafeNetCertificates(CertificateUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getSafeNetCertificates(getTypeId(), reader.getId(), new GclSafeNetRequest().withModule(modulePath).withSlotId(slotId).withPin(pin)), config.isConsentRequired()), parse));
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public GclSafeNetInfo getSafeNetInfo() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getSafeNetInfo(getTypeId(), reader.getId(), new GclSafeNetRequest().withModule(modulePath)), config.isConsentRequired());
    }

    public List<GclSafeNetSlot> getSafeNetSlots() throws RestException, NoConsentException {
        return getSafeNetSlots(null);
    }

    public List<GclSafeNetSlot> getSafeNetSlotsWithTokensPresent(boolean tokenPresent) throws RestException, NoConsentException {
        return getSafeNetSlots(tokenPresent);
    }

    public String getModulePath() {
        return modulePath;
    }

    private List<GclSafeNetSlot> getSafeNetSlots(Boolean tokenPresent) {
        return RestExecutor.returnData(httpClient.getSafeNetSlots(getTypeId(), reader.getId(), new GclSafeNetRequest().withModule(modulePath), tokenPresent), config.isConsentRequired());
    }

    private void configureModulePath(SafeNetContainerConfiguration safeNetConfig) {
        File driver = null;
        SafeNetContainerConfiguration containerConfig = safeNetConfig;
        if (containerConfig == null) {
            containerConfig = new SafeNetContainerConfiguration();
        }
        if (SystemUtils.IS_OS_MAC) {
            driver = containerConfig.getMac().toFile();
        }
        if (SystemUtils.IS_OS_WINDOWS) {
            driver = containerConfig.getWindows().toFile();
        }
        if (SystemUtils.IS_OS_LINUX) {
            driver = containerConfig.getLinux().toFile();
        }
        Preconditions.checkArgument(driver != null, "No configuration found for OS: " + SystemUtils.OS_NAME);
        Preconditions.checkArgument(driver.exists(), "Driver not found: " + driver.getAbsolutePath());
        modulePath = driver.getAbsolutePath();
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide certificate chains");
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide certificate chains");
    }

    @Override
    public ContainerData dumpData(String... pin) throws RestException, NoConsentException, UnsupportedOperationException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide data dump");
    }
}