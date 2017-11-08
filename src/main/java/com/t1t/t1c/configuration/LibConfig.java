package com.t1t.t1c.configuration;

import com.t1t.t1c.utils.UriUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public class LibConfig {

    private String version;
    private String build;
    private Boolean tokenCompatible;
    // Custom properties
    private Environment environment;
    private String gclClientUri;
    private String gatewayUri;
    private String dsContextPath;
    private String dsDownloadContextPath;
    private String dsDownloadUri;
    private String dsUri;
    private String apiKey;
    private Integer defaultPollingInterval = 30;
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

    public Boolean getTokenCompatible() {
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

    public Integer getDefaultPollingInterval() {
        return defaultPollingInterval;
    }

    public void setDefaultPollingInterval(Integer defaultPollingInterval) {
        this.defaultPollingInterval = defaultPollingInterval;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibConfig)) return false;

        LibConfig libConfig = (LibConfig) o;

        if (version != null ? !version.equals(libConfig.version) : libConfig.version != null) return false;
        if (build != null ? !build.equals(libConfig.build) : libConfig.build != null) return false;
        if (environment != libConfig.environment) return false;
        if (gclClientUri != null ? !gclClientUri.equals(libConfig.gclClientUri) : libConfig.gclClientUri != null)
            return false;
        if (gatewayUri != null ? !gatewayUri.equals(libConfig.gatewayUri) : libConfig.gatewayUri != null) return false;
        if (dsContextPath != null ? !dsContextPath.equals(libConfig.dsContextPath) : libConfig.dsContextPath != null)
            return false;
        if (dsDownloadContextPath != null ? !dsDownloadContextPath.equals(libConfig.dsDownloadContextPath) : libConfig.dsDownloadContextPath != null)
            return false;
        if (apiKey != null ? !apiKey.equals(libConfig.apiKey) : libConfig.apiKey != null) return false;
        return jwt != null ? jwt.equals(libConfig.jwt) : libConfig.jwt == null;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (build != null ? build.hashCode() : 0);
        result = 31 * result + (environment != null ? environment.hashCode() : 0);
        result = 31 * result + (gclClientUri != null ? gclClientUri.hashCode() : 0);
        result = 31 * result + (gatewayUri != null ? gatewayUri.hashCode() : 0);
        result = 31 * result + (dsContextPath != null ? dsContextPath.hashCode() : 0);
        result = 31 * result + (dsDownloadContextPath != null ? dsDownloadContextPath.hashCode() : 0);
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        result = 31 * result + (jwt != null ? jwt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LibConfig{" +
                "version='" + version + '\'' +
                ", build='" + build + '\'' +
                ", environment=" + environment +
                ", gclClientUri='" + gclClientUri + '\'' +
                ", gatewayUri='" + gatewayUri + '\'' +
                ", dsContextPath='" + dsContextPath + '\'' +
                ", dsDownloadContextPath='" + dsDownloadContextPath + '\'' +
                ", dsDownloadUri='" + dsDownloadUri + '\'' +
                ", dsUri='" + dsUri + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
