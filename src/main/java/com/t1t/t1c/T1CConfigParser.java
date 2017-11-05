package com.t1t.t1c;

import com.t1t.t1c.core.pojo.Environment;
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
import java.util.Optional;
import java.util.Properties;

/**
 * Created by michallispashidis on 31/10/2017.
 * The following constructors are provided:
 * <ul>
 *     <li>Constructor using filePath (json config file)</li>
 *     <li>Constructor using LibConfig (POJO)</li>
 * </ul>
 *
 */
public class T1CConfigParser implements Serializable {
    private static Logger _LOG = LoggerFactory.getLogger(T1CConfigParser.class.getName());
    private LibConfig appConfig;
    private static Config config;

    //config by param - enriching with property file info
    public T1CConfigParser(LibConfig config){
        setAppConfig(config);
        printAppConfig();
    }

    //config by path param
    public T1CConfigParser(Path optionalPath){
        LibConfig configObj = new LibConfig();
        //resolve configuration
        if(optionalPath!=null && optionalPath.toFile().exists()){
            config = ConfigFactory.parseFile(optionalPath.toFile());
            this.appConfig.setEnvironment(getEnvironment());
            this.appConfig.setApikey(getConsmerApiKey());
            this.appConfig.setGclClientURI(getGCLClientURI());
            this.appConfig.setDsURI(getDSClientURI());
            setAppConfig(configObj);
            printAppConfig();
        }else throw ExceptionFactory.systemErrorException("T1C Client config file not found on: "+ optionalPath.toAbsolutePath());
    }


    public Environment getEnvironment(){return (Environment) config.getAnyRef(IConfig.LIB_ENVIRONMENT);}
    public String getGCLClientURI(){return config.getString(IConfig.LIB_GCL_CLIENT_URI);}
    public String getConsmerApiKey() {return config.getString(IConfig.LIB_API_KEY);}
    public String getDSClientURI(){return config.getString(IConfig.LIB_DS_CLIENT_URI);}

    //read compiled property file
    private Optional<Properties> readProperties() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        if(is!=null) {
            try { properties.load(is);return Optional.of(properties); }
            catch (IOException e) {ExceptionFactory.systemErrorException("T1C client properties can not be loaded: " + e.getMessage()); return Optional.empty();}
        }else return Optional.empty();
    }

    public LibConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(LibConfig appConfig) {
        this.appConfig = appConfig;
        //resolve compiled properties
        Optional<Properties> optProps = readProperties();
        if (optProps.isPresent()) resolveProperties(optProps.get()); else _LOG.debug("No property file found");
    }

    /**
     * Resolves compile time properties and adds them to the app config.
     * @param optProperties
     */
    private void resolveProperties(Properties optProperties){
        if(this.getAppConfig()==null)setAppConfig(new LibConfig());
        this.getAppConfig().setBuild(optProperties.getProperty(IConfig.PROP_BUILD_DATE));
        this.getAppConfig().setVersion(optProperties.getProperty(IConfig.PROP_FILE_VERSION));
    }

    public void printAppConfig (){
        _LOG.debug("===== T1C Client configuration ==============================");
        _LOG.debug("Build: {}", appConfig.getBuild());
        _LOG.debug("Version: {}", appConfig.getVersion());
        _LOG.debug("Environment: {}", appConfig.getEnvironment());
        _LOG.debug("Consumer api-key: {}", appConfig.getApikey());
        _LOG.debug("GCL client URI: {}", appConfig.getGclClientURI());
        _LOG.debug("DS client URI: {}", appConfig.getDsURI());
        _LOG.debug("=============================================================");
    }

    /**
     * Validates the configuration.
     * You can add application startup validation to the method and force validation after instantiation.
     */
    public void validateConfig (){
        if(StringUtils.isEmpty(this.appConfig.getGclClientURI())) throw ExceptionFactory.configException("GCL URL not provided.");
    }
}
