package com.t1t.t1c;

import com.t1t.t1c.core.pojo.Environment;
import com.t1t.t1c.exceptions.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Created by michallispashidis on 31/10/2017.
 * Priority to filePath in constructor, by default uses the application.properties filePath.
 */
public class Config implements Serializable {
    private static com.typesafe.config.Config config;
    private static Properties properties;
    private static Logger _LOG = LoggerFactory.getLogger(Config.class.getName());
    private LibConfig appConfig;

    //config by param
    public Config (LibConfig config){ setAppConfig(config);}

    //config by path param
    public Config(Path optionalPath){
        Path configFilePath = null;
        if(optionalPath!=null){ configFilePath = optionalPath;}
        if(config==null) throw ExceptionFactory.systemErrorException("T1C Client config file not found on: "+ configFilePath.toAbsolutePath());
        else{
            LibConfig config = new LibConfig();
            config.setBuild(getBuildDate());
            //config.setEnvironment(getEnvironment());
            config.setVersion(getVersion());
            setAppConfig(config);
        }
    }

    public String getVersion(){return properties.getProperty(IConfig.PROP_FILE_VERSION);}
    public String getBuildDate(){return properties.getProperty(IConfig.PROP_FILE_DATE);}
    public String getEnvironment(){return config.getString(IConfig.APP_ENVIRONMENT);}

    public LibConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(LibConfig appConfig) {
        this.appConfig = appConfig;
        _LOG.debug("===== T1C Client configuration ==============================");
        _LOG.debug("Build: {}", appConfig.getBuild());
        _LOG.debug("version: {}", appConfig.getVersion());
        _LOG.debug("environment: {}", appConfig.getEnvironment());
        _LOG.debug("=============================================================");
    }
}

class LibConfig {
    private Environment environment;
    private String version;
    private String build;

    public LibConfig() {}

    public LibConfig(Environment environment, String version, String build) {
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

    @Override
    public String toString() {
        return "LibConfig{" +
                "environment=" + environment +
                ", version='" + version + '\'' +
                ", build='" + build + '\'' +
                '}';
    }
}
