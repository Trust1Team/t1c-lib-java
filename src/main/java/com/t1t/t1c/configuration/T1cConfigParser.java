package com.t1t.t1c.configuration;

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
    /*Logger*/
    private static Logger _LOG = LoggerFactory.getLogger(T1cConfigParser.class.getName());
    /*Uris*/
    private static final String URI_GATEWAY = "https://apim.t1t.be";
    private static final String URI_T1C_GCL = "https://localhost:10443/v1/";
    /*Context paths*/
    private static final String CONTEXT_DS = "/trust1team/gclds/v1";
    private static final String CONTEXT_OCV = "/trust1team/ocv-api/v1";
    /*Custom configurations*/
    private static final int DEFAULT_POLLING_INTERVAL = 5;
    private static final int DEFAULT_POLLING_TIMEOUT = 30;

    private static Config config;
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
            this.appConfig.setEnvironment(getEnvironment());
            this.appConfig.setApiKey(getConsmerApiKey());
            this.appConfig.setGclClientUri(getGCLClientURI());
            this.appConfig.setDsContextPath(getDsContextPath());
            this.appConfig.setOcvContextPath(getOcvContextPath());
            this.appConfig.setDefaultPollingIntervalInSeconds(getDefaultPollingIntervalInSeconds());
            this.appConfig.setDefaultPollingTimeoutInSeconds(getDefaultPollingTimeoutInSeconds());
            setAppConfig(configObj);
            validateConfig();
            printAppConfig();
        } else
            throw ExceptionFactory.systemErrorException("T1C Client config file not found on: " + optionalPath.toAbsolutePath());
    }


    public Environment getEnvironment() {
        return (Environment) config.getAnyRef(IConfig.LIB_ENVIRONMENT);
    }
    public String getGCLClientURI() {
        return config.getString(IConfig.LIB_GCL_CLIENT_URI);
    }
    public String getConsmerApiKey() {
        return config.getString(IConfig.LIB_API_KEY);
    }
    public String getGatewayUri() { return config.getString(IConfig.LIB_GATEWAY_URI); }
    public String getDsContextPath() {
        return config.getString(IConfig.LIB_DS_CONTEXT_PATH);
    }
    public String getOcvContextPath() { return config.getString(IConfig.LIB_OCV_CONTEXT_PATH); }
    public Integer getDefaultPollingIntervalInSeconds() {
        return config.getInt(IConfig.LIB_DEFAULT_POLLING_INTERVAL);
    }
    public Integer getDefaultPollingTimeoutInSeconds() {
        return config.getInt(IConfig.LIB_DEFAULT_POLLING_TIMEOUT);
    }
    public Integer getDefaultSessionTimeout() {
        return config.getInt(IConfig.LIB_DEFAULT_SESSION_TIMEOUT);
    }
    public LibConfig getAppConfig() {
        return appConfig;
    }
    public void setAppConfig(LibConfig appConfig) { this.appConfig = appConfig;resolveProperties(readProperties()); }

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
        _LOG.debug("===== T1C Client configuration ==============================");
        _LOG.debug("Build: {}", appConfig.getBuild());
        _LOG.debug("Version: {}", appConfig.getVersion());
        _LOG.debug("Environment: {}", appConfig.getEnvironment());
        _LOG.debug("Consumer api-key: {}", appConfig.getApiKey());
        _LOG.debug("GCL client URI: {}", appConfig.getGclClientUri());
        _LOG.debug("DS client URI: {}", appConfig.getDsUri());
        _LOG.debug("DS client Context path: {}", appConfig.getDsContextPath());
        _LOG.debug("OCV client URI: {}", appConfig.getOcvUri());
        _LOG.debug("OCV client Context path: {}", appConfig.getOcvContextPath());
        _LOG.debug("Default polling interval (seconds): {}", appConfig.getDefaultPollingIntervalInSeconds());
        _LOG.debug("Default polling timeout (seconds): {}", appConfig.getDefaultPollingTimeoutInSeconds());
        _LOG.debug("Default session timeout (seconds): {}", appConfig.getSessionTimeout());
        _LOG.debug("=============================================================");
    }

    /**
     * Validates the configuration.
     * Empty values are configured with PROD defaults
     * When the apikey is not provided, the client will not interact with cloud services,
     * this is the case for managed T1C instances.
     */
    public void validateConfig() {
        if (this.appConfig.getEnvironment()==null) this.appConfig.setEnvironment(Environment.PROD);
        if (StringUtils.isEmpty(this.appConfig.getGclClientUri())) this.appConfig.setGclClientUri(URI_T1C_GCL);
        if (StringUtils.isEmpty(this.appConfig.getOcvUri())) this.appConfig.setOcvUri(URI_GATEWAY);
        if (StringUtils.isEmpty(this.appConfig.getDsUri())) this.appConfig.setDsUri(URI_GATEWAY);
        if (StringUtils.isEmpty(this.appConfig.getDsContextPath())) this.appConfig.setDsContextPath(CONTEXT_DS);
        if (StringUtils.isEmpty(this.appConfig.getOcvContextPath())) this.appConfig.setOcvContextPath(CONTEXT_OCV);
        if (StringUtils.isEmpty(this.appConfig.getApiKey())) this.appConfig.setApiKey(""); // for managed instances - when DS and OCV are not used
        if (this.appConfig.getDefaultPollingIntervalInSeconds()==null) this.appConfig.setDefaultPollingIntervalInSeconds(DEFAULT_POLLING_INTERVAL);
        if (this.appConfig.getDefaultPollingTimeoutInSeconds()==null) this.appConfig.setDefaultPollingTimeoutInSeconds(DEFAULT_POLLING_TIMEOUT);
        if (this.appConfig.isHardwarePinPadForced()==null) this.appConfig.setHardwarePinPadForced(false);
        if (this.appConfig.isTokenCompatible()==null) this.appConfig.setTokenCompatible(false);
        if (this.appConfig.getSessionTimeout() == null || this.getAppConfig().getSessionTimeout() <= 0) this.appConfig.setSessionTimeout(60);
    }

    /**
     * Parse compile time properties file
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
}
