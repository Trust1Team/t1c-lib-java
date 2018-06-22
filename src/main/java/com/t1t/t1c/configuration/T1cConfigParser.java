package com.t1t.t1c.configuration;

import com.t1t.t1c.containers.smartcards.pkcs11.ModuleConfiguration;
import com.t1t.t1c.core.GclInfo;
import com.t1t.t1c.ds.DsSyncResponseDto;
import com.t1t.t1c.exceptions.ConfigException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by michallispashidis on 31/10/2017.
 * The following constructors are provided:
 * <ul>
 * <li>Constructor using filePath (json config file)</li>
 * <li>Constructor using LibConfig (POJO)</li>
 * </ul>
 */
public class T1cConfigParser implements Serializable {

    /*Uris*/
    private static final String URI_PROXY_DOMAIN = "https://apim.t1t.be";
    private static final String URI_GATEWAY_AUTH = URI_PROXY_DOMAIN + "/apiengineauth/v1";
    private static final String URI_T1C_GCL = "https://localhost:10443/v1/";
    private static final String URI_OCV = URI_PROXY_DOMAIN + "/trust1team/ocv-api/v1";
    private static final String URI_DS = URI_PROXY_DOMAIN + "/trust1team/gclds/v1";
    /*Custom configurations*/
    private static final int DEFAULT_POLLING_INTERVAL = 5;
    private static final int DEFAULT_POLLING_TIMEOUT = 30;
    private static final int DEFAULT_CONSENT_DURATION = 1;
    private static final int DEFAULT_CONSENT_TIMEOUT = 25;
    private static final int DEFAULT_CONTAINER_DOWNLOAD_TIMEOUT = 30;
    private static final int DEFAULT_SESSION_TIMEOUT = 300;
    /*Logger*/
    private static Logger log = LoggerFactory.getLogger(T1cConfigParser.class.getName());
    private Config config;
    private LibConfig appConfig;

    //config by param - enriching with property file info
    public T1cConfigParser(LibConfig config) {
        setAppConfig(config);
        validateConfig();
        printAppConfig();
    }

    //config by path param
    public T1cConfigParser(Path optionalPath) {
        LibConfig configObj = new LibConfig();
        //resolve configuration
        if (optionalPath != null && optionalPath.toFile().exists()) {
            config = ConfigFactory.parseFile(optionalPath.toFile());
            configObj.setEnvironment(getEnvironment());
            configObj.setAuthUri(getAuthUri());
            configObj.setDsUri(getDsUri());
            configObj.setGclClientUri(getGclClientUri());
            configObj.setOcvUri(getOcvUri());
            configObj.setApiKey(getConsmerApiKey());
            configObj.setClientFingerprintDirectoryPath(getClientFingerprintDirectoryPath());
            configObj.setContainerDownloadTimeout(getContainerDownloadTimeout());
            configObj.setDefaultConsentDuration(getDefaultConsentDuration());
            configObj.setDefaultConsentTimeout(getDefaultConsentTimeout());
            configObj.setDefaultPollingIntervalInSeconds(getDefaultPollingIntervalInSeconds());
            configObj.setDefaultPollingTimeoutInSeconds(getDefaultPollingTimeoutInSeconds());
            configObj.setHardwarePinPadForced(getHardwarePinPadForced());
            configObj.setOsPinDialog(getOsPinDialog());
            configObj.setSessionTimeout(getDefaultSessionTimeout());
            configObj.setSyncManaged(getSyncManaged());
            configObj.setPkcs11Config(getPkcs11Config());
            setAppConfig(configObj);
            validateConfig();
            printAppConfig();
        } else
            throw ExceptionFactory.systemErrorException("T1C Client config file not found on: " + optionalPath.toAbsolutePath());
    }

    /**
     * Parse the given configuration object and enrich it with the Core info
     *
     * @param config library configuration object
     * @param info   core info
     * @return parsed and validated library configuration object
     */
    public LibConfig parseConfig(LibConfig config, GclInfo info) {
        config.setCitrix(info.getCitrix());
        config.setConsentRequired(info.getConsent());
        config.setManaged(info.getManaged());
        setAppConfig(config);
        validateConfig();
        printAppConfig();
        return getAppConfig();
    }

    /**
     * Parse the given configuration and enrich it with the JWT obtained from the DS
     *
     * @param config       library configuration object
     * @param syncResponse sync/registration response obtained from the DS
     * @return parsed and validated library configuration object
     */
    public LibConfig parseConfig(LibConfig config, DsSyncResponseDto syncResponse) {
        config.setGclJwt(syncResponse.getGclJwt());
        config.setContextToken(syncResponse.getContextToken());
        setAppConfig(config);
        validateConfig();
        printAppConfig();
        return getAppConfig();
    }

    private ModuleConfiguration getPkcs11Config() {
        return new ModuleConfiguration()
                .withLinux(getConfigPath(IConfig.LIB_PKCS_PATH_LINUX))
                .withMac(getConfigPath(IConfig.LIB_PKCS_PATH_MACOS))
                .withWindows(getConfigPath(IConfig.LIB_PKCS_PATH_WIN));

    }

    private Boolean getLocalTestMode() {
        return getConfigBoolean(IConfig.LIB_TEST_LOCAL_MODE);
    }

    private Boolean getSyncManaged() {
        return getConfigBoolean(IConfig.LIB_GEN_SYNC_MANAGED);
    }

    private Boolean getOsPinDialog() {
        return getConfigBoolean(IConfig.LIB_GEN_OS_PIN_DIALOG);
    }

    private Boolean getImplicitDownloads() {
        return getConfigBoolean(IConfig.LIB_GEN_IMPLICIT_DOWNLOADS);
    }

    private Boolean getHardwarePinPadForced() {
        return getConfigBoolean(IConfig.LIB_GEN_HARDWARE_PINPAD_FORCED);
    }

    private Integer getDefaultConsentTimeout() {
        return getConfigInteger(IConfig.LIB_GEN_CONSENT_TIMEOUT);
    }

    private Integer getDefaultConsentDuration() {
        return getConfigInteger(IConfig.LIB_GEN_CONSENT_DURATION);
    }

    private Integer getContainerDownloadTimeout() {
        return getConfigInteger(IConfig.LIB_GEN_CONTAINER_DOWNLOAD_TIMEOUT);
    }

    private String getClientFingerprintDirectoryPath() {
        return getConfigString(IConfig.LIB_GEN_FINGERPRINT_PATH);
    }

    private Environment getEnvironment() {
        try {
            return Environment.valueOf(getConfigString(IConfig.LIB_ENVIRONMENT));
        } catch (IllegalArgumentException ex) {
            return Environment.PROD;
        }
    }

    private String getAuthUri() {
        return getConfigString(IConfig.LIB_URIS_AUTH);
    }

    private String getDsUri() {
        return getConfigString(IConfig.LIB_URIS_DS);
    }

    private String getGclClientUri() {
        return getConfigString(IConfig.LIB_URIS_GCL_CLIENT);
    }

    private String getOcvUri() {
        return getConfigString(IConfig.LIB_URIS_OCV);
    }

    private String getConsmerApiKey() {
        return getConfigString(IConfig.LIB_AUTH_API_KEY);
    }

    private Integer getDefaultPollingIntervalInSeconds() {
        return getConfigInteger(IConfig.LIB_GEN_POLLING_INTERVAL);
    }

    private Integer getDefaultPollingTimeoutInSeconds() {
        return getConfigInteger(IConfig.LIB_GEN_POLLING_TIMEOUT);
    }

    private Integer getDefaultSessionTimeout() {
        return getConfigInteger(IConfig.LIB_GEN_SESSION_TIMEOUT);
    }

    public LibConfig getAppConfig() {
        return appConfig;
    }

    private void setAppConfig(LibConfig appConfig) {
        this.appConfig = appConfig;
        resolveProperties(readProperties());
    }

    /**
     * Resolves compile time properties and adds them to the app config.
     *
     * @param optProperties
     */
    private void resolveProperties(Properties optProperties) {
        if (this.getAppConfig() == null) setAppConfig(new LibConfig());
        this.getAppConfig().setBuild(optProperties.getProperty(IConfig.PROP_BUILD_DATE));
        this.getAppConfig().setVersion(optProperties.getProperty(IConfig.PROP_FILE_VERSION));
    }

    /**
     * Print client configuration after parsing
     */
    public void printAppConfig() {
        log.debug("===== T1C Client configuration ==============================");
        log.debug("Build: {}", appConfig.getBuild());
        log.debug("Version: {}", appConfig.getVersion());
        log.debug("Environment: {}", appConfig.getEnvironment());
        log.debug("URIs - T1G Auth: {}", appConfig.getAuthUri());
        log.debug("URIs - DS: {}", appConfig.getDsUri());
        log.debug("URIs - GCL: {}", appConfig.getGclClientUri());
        log.debug("URIs - OCV: {}", appConfig.getOcvUri());
        log.debug("Auth - API key: {}", appConfig.getApiKey());
        log.debug("General - Client fingerprint directory path: {}", appConfig.getClientFingerprintDirectoryPath());
        log.debug("General - Default container download timeout (seconds): {}", appConfig.getContainerDownloadTimeout());
        log.debug("General - Default consent duration (days): {}", appConfig.getDefaultConsentDuration());
        log.debug("General - Default consent timeout (seconds): {}", appConfig.getDefaultConsentTimeout());
        log.debug("General - Forced hardware PIN pad: {}", appConfig.isHardwarePinPadForced());
        log.debug("General - OS PIN dialog: {}", appConfig.isOsPinDialog());
        log.debug("General - Default polling interval (seconds): {}", appConfig.getDefaultPollingIntervalInSeconds());
        log.debug("General - Default polling timeout (seconds): {}", appConfig.getDefaultPollingTimeoutInSeconds());
        log.debug("General - Default session timeout (seconds): {}", appConfig.getSessionTimeout());
        log.debug("General - Sync managed: {}", appConfig.isSyncManaged());
        if (appConfig.getPkcs11Config() != null) {
            log.debug("PKCS11 - Module Linux Path: {}", appConfig.getPkcs11Config().getLinux());
            log.debug("PKCS11 - Module Mac OS Path: {}", appConfig.getPkcs11Config().getMac());
            log.debug("PKCS11 - Module Windows Path: {}", appConfig.getPkcs11Config().getWindows());
        }
        log.debug("=============================================================");
    }

    /**
     * Validates the configuration.
     * Empty values are configured with PROD defaults
     * When the apikey is not provided, the client will not interact with cloud services,
     * this is the case for managed T1C instances.
     */
    public void validateConfig() {

        if (this.appConfig.getEnvironment() == null) {
            this.appConfig.setEnvironment(Environment.PROD);
        }
        if (StringUtils.isEmpty(this.appConfig.getGclClientUri())) {
            this.appConfig.setGclClientUri(URI_T1C_GCL);
        }
        if (StringUtils.isEmpty(this.appConfig.getAuthUri())) {
            this.appConfig.setOcvUri(URI_GATEWAY_AUTH);
        }
        if (StringUtils.isEmpty(this.appConfig.getDsUri())) {
            this.appConfig.setDsUri(URI_DS);
        }
        if (StringUtils.isEmpty(appConfig.getOcvUri())) {
            this.appConfig.setOcvUri(URI_OCV);
        }
        if (StringUtils.isEmpty(this.appConfig.getApiKey())) {
            this.appConfig.setApiKey(""); // for managed instances - when DS and OCV are not used
        }
        if (StringUtils.isEmpty(this.appConfig.getClientFingerprintDirectoryPath())) {
            throw ExceptionFactory.initializationException("File path for client fingerprint token required");
        } else {
            Path fingerprintPath = Paths.get(this.appConfig.getClientFingerprintDirectoryPath());
            if (fingerprintPath.toFile().exists()) {
                if (!fingerprintPath.toFile().isDirectory()) {
                    throw ExceptionFactory.initializationException("Client fingerprint directory does not reference a directory");
                }
            } else {
                try {
                    if (fingerprintPath.toFile().mkdir()) {
                        log.info("Client fingerprint folder created");
                    } else {
                        throw ExceptionFactory.initializationException("Client fingerprint directory does not exist, creation attempt failed");
                    }
                } catch (SecurityException ex) {
                    throw ExceptionFactory.initializationException("Client fingerprint directory does not exist, creation attempt failed: " + ex.getMessage());
                }
            }
        }
        if (this.appConfig.getContainerDownloadTimeout() == null || this.appConfig.getContainerDownloadTimeout() <= 0) {
            this.appConfig.setContainerDownloadTimeout(DEFAULT_CONTAINER_DOWNLOAD_TIMEOUT);
        }
        if (this.appConfig.getDefaultConsentDuration() == null || this.appConfig.getDefaultConsentDuration() <= 0) {
            this.appConfig.setDefaultConsentDuration(DEFAULT_CONSENT_DURATION);
        }
        if (this.appConfig.getDefaultConsentTimeout() == null || this.appConfig.getDefaultConsentTimeout() <= 0) {
            this.appConfig.setDefaultConsentTimeout(DEFAULT_CONSENT_TIMEOUT);
        }
        if (this.appConfig.getDefaultPollingIntervalInSeconds() == null || this.appConfig.getDefaultPollingIntervalInSeconds() <= 0) {
            this.appConfig.setDefaultPollingIntervalInSeconds(DEFAULT_POLLING_INTERVAL);
        }
        if (this.appConfig.getDefaultPollingTimeoutInSeconds() == null || this.appConfig.getDefaultPollingTimeoutInSeconds() <= 0) {
            this.appConfig.setDefaultPollingTimeoutInSeconds(DEFAULT_POLLING_TIMEOUT);
        }
        if (this.appConfig.isHardwarePinPadForced() == null) {
            this.appConfig.setHardwarePinPadForced(false);
        }
        if (this.appConfig.isOsPinDialog() == null) {
            this.appConfig.setOsPinDialog(false);
        }
        if (this.appConfig.getSessionTimeout() == null || this.getAppConfig().getSessionTimeout() <= 0) {
            this.appConfig.setSessionTimeout(DEFAULT_SESSION_TIMEOUT);
        }
        if (this.appConfig.isSyncManaged() == null) {
            this.appConfig.setSyncManaged(true);
        }
        if (this.appConfig.isCitrix() == null) {
            this.appConfig.setCitrix(false);
        }
        if (this.appConfig.isConsentRequired() == null) {
            this.appConfig.setConsentRequired(false);
        }
    }

    /**
     * Parse compile time properties file
     *
     * @return
     */
    private Properties readProperties() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        if (is != null) {
            try {
                properties.load(is);
                return properties;
            } catch (IOException e) {
                throw ExceptionFactory.systemErrorException("T1C client properties can not be loaded: " + e.getMessage());
            }
        } else {
            return properties;
        }
    }

    private String getConfigString(String key) {
        try {
            return config.getString(key);
        } catch (ConfigException ex) {
            log.error("Missing configuration value: {}", key);
            return null;
        }
    }

    private boolean getConfigBoolean(String key) {
        try {
            return config.getBoolean(key);
        } catch (ConfigException ex) {
            log.error("Missing configuration value: {}", key);
            return false;
        }
    }

    private Integer getConfigInteger(String key) {
        try {
            return config.getInt(key);
        } catch (ConfigException ex) {
            log.error("Missing configuration value: {}", key);
            return null;
        }
    }

    private Path getConfigPath(String key) {
        try {
            return Paths.get(config.getString(key));
        } catch (ConfigException ex) {
            log.error("Missing configuration value: {}", key);
            return null;
        }
    }
}
