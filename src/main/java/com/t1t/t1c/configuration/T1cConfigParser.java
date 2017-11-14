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
    private static Logger _LOG = LoggerFactory.getLogger(T1cConfigParser.class.getName());
    private static Config config;
    private LibConfig appConfig;

    //config by param - enriching with property file info
    public T1cConfigParser(LibConfig config) {
        setAppConfig(config);
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
            this.appConfig.setGatewayUri(getGatewayUri());
            this.appConfig.setDsContextPath(getDsContextPath());
            this.appConfig.setOcvContexPath(getOcvContextPath());
            this.appConfig.setDefaultPollingIntervalInSeconds(getDefaultPollingIntervalInSeconds());
            this.appConfig.setDefaultPollingTimeoutInSeconds(getDefaultPollingTimeoutInSeconds());
            setAppConfig(configObj);
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

    public String getGatewayUri() {
        return config.getString(IConfig.LIB_GATEWAY_URI);
    }

    public String getDsContextPath() {
        return config.getString(IConfig.LIB_DS_CONTEXT_PATH);
    }

    public String getOcvContextPath() {
        return config.getString(IConfig.LIB_OCV_CONTEXT_PATH);
    }

    public Integer getDefaultPollingIntervalInSeconds() {
        return config.getInt(IConfig.LIB_DEFAULT_POLLING_INTERVAL);
    }

    public Integer getDefaultPollingTimeoutInSeconds() {
        return config.getInt(IConfig.LIB_DEFAULT_POLLING_TIMEOUT);
    }

    //read compiled property file
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

    public LibConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(LibConfig appConfig) {
        this.appConfig = appConfig;
        //resolve compiled properties
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

    public void printAppConfig() {
        _LOG.debug("===== T1C Client configuration ==============================");
        _LOG.debug("Build: {}", appConfig.getBuild());
        _LOG.debug("Version: {}", appConfig.getVersion());
        _LOG.debug("Environment: {}", appConfig.getEnvironment());
        _LOG.debug("Consumer api-key: {}", appConfig.getApiKey());
        _LOG.debug("GCL client URI: {}", appConfig.getGclClientUri());
        _LOG.debug("DS client URI: {}", appConfig.getDsUri());
        _LOG.debug("Default polling interval (seconds): {}", appConfig.getDefaultPollingIntervalInSeconds());
        _LOG.debug("Default polling timeout (seconds): {}", appConfig.getDefaultPollingTimeoutInSeconds());
        _LOG.debug("=============================================================");
    }

    /**
     * Validates the configuration.
     * You can add application startup validation to the method and force validation after instantiation.
     */
    public void validateConfig() {
        if (StringUtils.isEmpty(this.appConfig.getGclClientUri()))
            throw ExceptionFactory.configException("GCL URL not provided.");
    }
}
