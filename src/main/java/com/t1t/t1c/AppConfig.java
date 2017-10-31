package com.t1t.t1c;

import com.t1t.t1c.exceptions.ExceptionFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
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
 * This will load the configuration for a specific environment and produce the config throughout the application.
 * More information about the config Typesafe approach can be found: https://github.com/typesafehub/config#essential-information
 */
public class AppConfig implements Serializable {
    private static Config config;
    private static Properties properties;
    private static Logger _LOG = LoggerFactory.getLogger(AppConfig.class.getName());

    public void initConfig(){
        //read properties file
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        properties = new Properties();
        if(is!=null) {
            try {
                properties.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else throw ExceptionFactory.systemErrorException("T1C Client basic property file not found.");
        String propertyFilePath = properties.getProperty("configuration.file");
        Path configFile = Paths.get(propertyFilePath);
        if(!configFile.toFile().exists()) throw ExceptionFactory.systemErrorException("T1C Client config property file not found.");
        config = ConfigFactory.parseFile(configFile.toFile());
        if(config==null) throw ExceptionFactory.systemErrorException("T1C Client log not found");
        else{
            try {
                _LOG.info("===== T1C Client configuration ==============================");
                _LOG.info("Using configuration file: {}", getConfigurationFile());
                _LOG.info("Build: {}", getBuildDate());
                _LOG.info("version: {}", getVersion());
                _LOG.info("environment: {}", getEnvironment());
                _LOG.info("=============================================================");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getVersion(){return properties.getProperty(IConfig.PROP_FILE_VERSION);}
    public String getBuildDate(){return properties.getProperty(IConfig.PROP_FILE_DATE);}
    public String getConfigurationFile(){return properties.getProperty(IConfig.PROP_FILE_CONFIG_FILE);}
    public String getEnvironment(){return config.getString(IConfig.APP_ENVIRONMENT);}

}
