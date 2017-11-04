package com.t1t.t1c;

import com.t1t.t1c.core.pojo.Environment;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
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
 *     <li>Constructor using ConfigObject (POJO)</li>
 * </ul>
 *
 */
public class T1CConfigParser implements Serializable {
    private static Logger _LOG = LoggerFactory.getLogger(T1CConfigParser.class.getName());
    private ConfigObject appConfig;
    private static Config config;

    //config by param - enriching with property file info
    public T1CConfigParser(ConfigObject config){
        setAppConfig(config);
        printAppConfig();
    }

    //config by path param
    public T1CConfigParser(Path optionalPath){
        ConfigObject configObj = new ConfigObject();
        //resolve configuration
        if(optionalPath!=null && optionalPath.toFile().exists()){
            config = ConfigFactory.parseFile(optionalPath.toFile());
            this.appConfig.setEnvironment(getEnvironment());
            setAppConfig(configObj);
            printAppConfig();
        }else throw ExceptionFactory.systemErrorException("T1C Client config file not found on: "+ optionalPath.toAbsolutePath());
    }

    //read compiled property file
    private Optional<Properties> readProperties() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        if(is!=null) {
            try { properties.load(is);return Optional.of(properties); }
            catch (IOException e) {ExceptionFactory.systemErrorException("T1C client properties can not be loaded: " + e.getMessage()); return Optional.empty();}
        }else return Optional.empty();
    }

    public ConfigObject getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(ConfigObject appConfig) {
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
        if(this.getAppConfig()==null)setAppConfig(new ConfigObject());
        this.getAppConfig().setBuild(optProperties.getProperty(IConfig.PROP_BUILD_DATE));
        this.getAppConfig().setVersion(optProperties.getProperty(IConfig.PROP_FILE_VERSION));
    }

    public Environment getEnvironment(){return (Environment) config.getAnyRef(IConfig.LIB_ENVIRONMENT);}
    public String getGCLClientURI(){return config.getString(IConfig.LIB_GCL_CLIENT_URI);}

    public void printAppConfig (){
        _LOG.debug("===== T1C Client configuration ==============================");
        _LOG.debug("Build: {}", appConfig.getBuild());
        _LOG.debug("Version: {}", appConfig.getVersion());
        _LOG.debug("Environment: {}", appConfig.getEnvironment());
        _LOG.debug("GCL client URI: {}", appConfig.getGclClientURI());
        _LOG.debug("=============================================================");
    }
}

class ConfigObject {
    private String version;
    private String build;
    // Custom properties
    private Environment environment;
    private String gclClientURI;

    public ConfigObject() {}

    public ConfigObject(Environment environment, String version, String build) {
        this.environment = environment;
        this.version = version;
        this.build = build;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getGclClientURI() {return gclClientURI;}

    public void setGclClientURI(String gclClientURI) {this.gclClientURI = gclClientURI;}

    @Override
    public String toString() {
        return "ConfigObject{" +
                "version='" + version + '\'' +
                ", build='" + build + '\'' +
                ", environment=" + environment +
                ", gclClientURI='" + gclClientURI + '\'' +
                '}';
    }
}
