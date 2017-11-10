package com.t1t.t1c.configuration;

import com.t1t.t1c.utils.UriUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public class LibConfig {

    private String version;
    private String build;
    private boolean tokenCompatible;
    // Custom properties
    private Environment environment;
    private String gclClientUri;
    private String gatewayUri;
    private String dsContextPath;
    private String dsDownloadContextPath;
    private String dsDownloadUri;
    private String dsUri;
    private String apiKey;
    private Integer defaultPollingIntervalInSeconds = 2;
    private Integer defaultPollingTimeoutInSeconds = 60;
    private boolean hardwarePinPadForced = false;
    // Dynamic properties
    private String jwt;

    public LibConfig() {
    }

    public LibConfig(Environment environment, String version, String build) {
        this.environment = environment;
        this.version = version;
        this.build = build;
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

    public boolean isTokenCompatible() {
        return tokenCompatible;
    }

    public void setTokenCompatible(boolean tokenCompatible) {
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

    public String getGatewayUri() {
        return gatewayUri;
    }

    public void setGatewayUri(String gatewayUri) {
        this.gatewayUri = gatewayUri;
    }

    public String getDsContextPath() {
        return dsContextPath;
    }

    public void setDsContextPath(String dsContextPath) {
        this.dsContextPath = dsContextPath;
    }

    public String getDsDownloadContextPath() {
        return dsDownloadContextPath;
    }

    public void setDsDownloadContextPath(String dsDownloadContextPath) {
        this.dsDownloadContextPath = dsDownloadContextPath;
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

    public LibConfig withJwt(String jwt) {
        setJwt(jwt);
        return this;
    }

    public Integer getDefaultPollingIntervalInSeconds() {
        return defaultPollingIntervalInSeconds;
    }

    public void setDefaultPollingIntervalInSeconds(Integer defaultPollingIntervalInSeconds) {
        this.defaultPollingIntervalInSeconds = defaultPollingIntervalInSeconds;
    }

    public Integer getDefaultPollingTimeoutInSeconds() {
        return defaultPollingTimeoutInSeconds;
    }

    public void setDefaultPollingTimeoutInSeconds(Integer defaultPollingTimeoutInSeconds) {
        this.defaultPollingTimeoutInSeconds = defaultPollingTimeoutInSeconds;
    }

    public boolean isHardwarePinPadForced() {
        return hardwarePinPadForced;
    }

    public void setHardwarePinPadForced(boolean hardwarePinPadForced) {
        this.hardwarePinPadForced = hardwarePinPadForced;
    }

    public String getDsUri() {
        if (StringUtils.isBlank(this.dsUri)) {
            if (StringUtils.isNoneBlank(this.gatewayUri, this.dsContextPath)) {
                this.dsUri = UriUtils.uriFinalSlashAppender(this.gatewayUri) + UriUtils.uriFinalSlashAppender(UriUtils.uriLeadingSlashRemover(this.dsContextPath));
            }
        }
        return this.dsUri;
    }

    public String getDsDownloadUri() {
        if (StringUtils.isBlank(this.dsDownloadUri)) {
            if (StringUtils.isNoneBlank(this.gatewayUri, this.dsDownloadContextPath)) {
                this.dsDownloadUri = UriUtils.uriFinalSlashAppender(this.gatewayUri) + UriUtils.uriFinalSlashAppender(UriUtils.uriLeadingSlashRemover(this.dsDownloadContextPath));
            }
        }
        return this.dsDownloadUri;
    }
}
