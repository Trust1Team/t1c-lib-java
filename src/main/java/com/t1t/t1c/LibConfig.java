package com.t1t.t1c;

import com.t1t.t1c.core.pojo.Environment;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public class LibConfig {
    private String version;
    private String build;
    // Custom properties
    private Environment environment;
    private String gclClientURI;
    private String dsURI;
    private String apikey;
    // Dynamic properties
    private String jwt;

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

    public String getGclClientURI() {return gclClientURI;}

    public void setGclClientURI(String gclClientURI) {this.gclClientURI = gclClientURI;}

    public String getApikey() {return apikey;}

    public void setApikey(String apikey) {this.apikey = apikey;}

    public String getJwt() {return jwt;}

    public void setJwt(String jwt) {this.jwt = jwt;}

    public String getDsURI() {return dsURI;}

    public void setDsURI(String dsURI) {this.dsURI = dsURI;}

    @Override
    public String toString() {
        return "LibConfig{" +
                "version='" + version + '\'' +
                ", build='" + build + '\'' +
                ", environment=" + environment +
                ", gclClientURI='" + gclClientURI + '\'' +
                ", dsURI='" + dsURI + '\'' +
                ", apikey='" + apikey + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
