package com.t1t.t1c.configuration;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public class LibConfig {
    // Compile time properties
    private String version;
    private String build;
    // Custom properties
    private Environment environment;
    private String gclClientUri;
    private String dsUri;
    private String dsContextPath;
    private String ocvUri;
    private String ocvContextPath;
    private String apiKey;
    private Integer defaultPollingIntervalInSeconds;
    private Integer defaultPollingTimeoutInSeconds;
    private Boolean hardwarePinPadForced = false;
    private Boolean tokenCompatible = false;
    private Integer sessionTimeout;
    // Dynamic properties
    private String jwt;

    public LibConfig() { }

    public LibConfig(Environment environment, String version, String build) {
        this.environment = environment;
        this.version = version;
        this.build = build;
    }

    public String getVersion() {
        return version;
    }

    void setVersion(String version) {
        this.version = version;
    }

    public String getBuild() {
        return build;
    }

    void setBuild(String build) {
        this.build = build;
    }

    public Boolean isTokenCompatible() {
        return tokenCompatible;
    }

    public void setTokenCompatible(Boolean tokenCompatible) {
        this.tokenCompatible = tokenCompatible;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getGclClientUri() {
        return gclClientUri;
    }

    public void setGclClientUri(String gclClientUri) {
        this.gclClientUri = gclClientUri;
    }

    public String getDsContextPath() {
        return dsContextPath;
    }

    public void setDsContextPath(String dsContextPath) {
        this.dsContextPath = dsContextPath;
    }

    public String getOcvContextPath() {
        return ocvContextPath;
    }

    public void setOcvContextPath(String ocvContextPath) {
        this.ocvContextPath = ocvContextPath;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setDsUri(String dsUri) { this.dsUri = dsUri; }

    public Integer getDefaultPollingIntervalInSeconds() {
        return defaultPollingIntervalInSeconds;
    }

    public void setDefaultPollingIntervalInSeconds(Integer defaultPollingIntervalInSeconds) { this.defaultPollingIntervalInSeconds = defaultPollingIntervalInSeconds; }

    public Integer getDefaultPollingTimeoutInSeconds() {
        return defaultPollingTimeoutInSeconds;
    }

    public void setDefaultPollingTimeoutInSeconds(Integer defaultPollingTimeoutInSeconds) { this.defaultPollingTimeoutInSeconds = defaultPollingTimeoutInSeconds; }

    public Boolean isHardwarePinPadForced() {
        return hardwarePinPadForced;
    }

    public void setHardwarePinPadForced(Boolean hardwarePinPadForced) { this.hardwarePinPadForced = hardwarePinPadForced; }

    public String getDsUri() { return this.dsUri; }

    public String getOcvUri() { return this.ocvUri; }

    public void setOcvUri(String ocvUri) {
        this.ocvUri = ocvUri;
    }

    public Integer getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
