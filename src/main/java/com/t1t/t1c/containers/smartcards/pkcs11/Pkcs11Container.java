package com.t1t.t1c.containers.smartcards.pkcs11;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.ContainerVersion;
import com.t1t.t1c.containers.SmartCardContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclPace;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.*;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.PinUtil;
import com.t1t.t1c.utils.PkiUtil;
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
public class Pkcs11Container extends SmartCardContainer<Pkcs11Container, GclPkcs11RestClient, Pkcs11AllData, AllCertificates> {

    private String modulePath;

    // Default constructor for testing purposes
    public Pkcs11Container(final GclReader reader, final String containerVersion, final GclPkcs11RestClient httpClient) {
        this.config = new LibConfig();
        this.reader = reader;
        this.httpClient = httpClient;
        this.containerVersion = new ContainerVersion(ContainerType.PKCS11, containerVersion);
        this.modulePath = "/usr/local/lib/libeTPkcs11.dylib";
    }

    public Pkcs11Container(final LibConfig config, final GclReader reader, final String containerVersion, final GclPkcs11RestClient httpClient, final ModuleConfiguration pkcs11Config) {
        super(config, reader, containerVersion, httpClient);
        configureModulePath(pkcs11Config);
    }

    @Override
    public Pkcs11Container createInstance(final LibConfig config, final GclReader reader, final String containerVersion, final GclPkcs11RestClient httpClient, final GclPace pace) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pace = pace;
        this.containerVersion = new ContainerVersion(ContainerType.PKCS11, containerVersion);
        if (this.modulePath == null) {
            configureModulePath(new ModuleConfiguration());
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
    public Pkcs11AllData getAllData(final List<String> filterParams, final Boolean parseCertificates) throws GenericContainerException {
        return new Pkcs11AllData(getPkcs11Slots());
    }

    @Override
    public AllCertificates getAllCertificates(final List<String> filterParams, final Boolean parseCertificates) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have certificate dump implementation");
    }

    @Override
    public Boolean verifyPin(final String pin) throws GenericContainerException, VerifyPinException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have PIN verification implementation");
    }

    @Override
    public List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have authentication implementation");
    }

    @Override
    public String authenticate(final String data, final DigestAlgorithm algo, final String pin) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have authentication implementation");
    }

    @Override
    public List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have signing implementation");
    }

    @Override
    public String sign(final String data, final DigestAlgorithm algo, final String pin) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("Container does not have signing implementation");
    }

    @Override
    public Class<Pkcs11AllData> getAllDataClass() {
        return Pkcs11AllData.class;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
        throw ExceptionFactory.unsupportedOperationException("Container does not have certificate dump implementation");
    }

    public Pkcs11Certificates getPkcs11Certificates(final Long slotId, final String pin, final Boolean parse) throws VerifyPinException, NoConsentException, RestException {
        Preconditions.checkNotNull(slotId, "slotId must be provided");
        Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "PIN must be provided");
        try {
            return new Pkcs11Certificates(PkiUtil.createT1cCertificates(RestExecutor.returnData(httpClient.getPkcs11Certificates(getContainerUrlId(), reader.getId(), new GclPkcs11Request().withModule(modulePath).withSlotId(slotId).withPin(pin)), config.isConsentRequired()), parse));
        } catch (final RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public Pkcs11Certificates getPkcs11Certificates(final Long slotId, final String pin) throws VerifyPinException, NoConsentException, RestException {
        return getPkcs11Certificates(slotId, pin, null);
    }

    public GclPkcs11Info getPkcs11Info() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getPkcs11Info(getContainerUrlId(), reader.getId(), new GclPkcs11Request().withModule(modulePath)), config.isConsentRequired());
    }

    public List<GclPkcs11Slot> getPkcs11Slots() throws RestException, NoConsentException {
        return getPkcs11Slots(null);
    }

    public List<GclPkcs11Slot> getPkcs11SlotsWithTokensPresent(final boolean tokenPresent) throws RestException, NoConsentException {
        return getPkcs11Slots(tokenPresent);
    }

    public String getModulePath() {
        return modulePath;
    }

    private List<GclPkcs11Slot> getPkcs11Slots(final Boolean tokenPresent) {
        return RestExecutor.returnData(httpClient.getPkcs11Slots(getContainerUrlId(), reader.getId(), new GclPkcs11Request().withModule(modulePath), tokenPresent), config.isConsentRequired());
    }

    private void configureModulePath(final ModuleConfiguration pkcs11Config) {
        File driver = null;
        ModuleConfiguration containerConfig = pkcs11Config;
        if (containerConfig == null) {
            containerConfig = new ModuleConfiguration();
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
    public ContainerData dumpData(final String pin) throws RestException, NoConsentException, UnsupportedOperationException {
        throw ExceptionFactory.unsupportedOperationException("Container does not provide data dump");
    }
}