package com.t1t.t1c.configuration;

import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.utils.UriUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

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
    private String dsDomain;
    private String dsUri;
    private String dsContextPath;
    private String ocvDomain;
    private String ocvUri;
    private String ocvContextPath;
    private String apiKey;
    private Integer defaultPollingIntervalInSeconds;
    private Integer defaultPollingTimeoutInSeconds;
    private Boolean hardwarePinPadForced = false;
    private Boolean tokenCompatible = false;
    private Integer sessionTimeout;
    private Boolean citrix;
    private Integer agentPort;
    private Boolean consentRequired = false;
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

    public String getDsDomain() {
        if (StringUtils.isEmpty(dsDomain) && StringUtils.isNotEmpty(dsUri)) {
            try {
                dsDomain = UriUtils.getDomain(dsUri);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid OCV uri: " + dsUri);
            }
        }
        return dsDomain;
    }

    public void setDsDomain(String dsDomain) {
        if (StringUtils.isNotEmpty(dsDomain) && StringUtils.isNotEmpty(dsContextPath)) {
            try {
                dsUri = UriUtils.getFullUri(dsDomain, dsContextPath);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI domain: " + dsDomain + ", or contextpath: " + dsContextPath);
            }
        }
        this.dsDomain = dsDomain;
    }

    public String getDsContextPath() {
        if (StringUtils.isEmpty(dsContextPath) && StringUtils.isNotEmpty(dsUri)) {
            try {
                dsContextPath = UriUtils.getContextPath(dsUri);
            } catch (MalformedURLException ex) {
                throw ExceptionFactory.configException("Invalid OCV uri: " + dsUri);
            }
        }
        return dsContextPath;
    }

    public void setDsContextPath(String dsContextPath) {
        if (StringUtils.isNotEmpty(dsDomain) && StringUtils.isNotEmpty(dsContextPath)) {
            try {
                dsUri = UriUtils.getFullUri(dsDomain, dsContextPath);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI domain: " + dsDomain + ", or contextpath: " + dsContextPath);
            }
        }
        this.dsContextPath = dsContextPath;
    }

    public String getOcvDomain() {
        if (StringUtils.isEmpty(ocvDomain) && StringUtils.isNotEmpty(ocvUri)) {
            try {
                ocvDomain = UriUtils.getDomain(ocvUri);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid OCV uri: " + ocvUri);
            }
        }
        return ocvDomain;
    }

    public void setOcvDomain(String ocvDomain) {
        if (StringUtils.isNotEmpty(ocvDomain) && StringUtils.isNotEmpty(ocvContextPath)) {
            try {
                ocvUri = UriUtils.getFullUri(ocvDomain, ocvContextPath);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI domain: " + ocvDomain + ", or contextpath: " + ocvContextPath);
            }
        }
        this.ocvDomain = ocvDomain;
    }

    public String getOcvContextPath() {
        if (StringUtils.isEmpty(ocvContextPath) && StringUtils.isNotEmpty(ocvUri)) {
            try {
                ocvContextPath = UriUtils.getContextPath(ocvUri);
            } catch (MalformedURLException ex) {
                throw ExceptionFactory.configException("Invalid OCV uri: " + ocvUri);
            }
        }
        return ocvContextPath;
    }

    public void setOcvContextPath(String ocvContextPath) {
        if (StringUtils.isNotEmpty(ocvDomain) && StringUtils.isNotEmpty(ocvContextPath)) {
            try {
                ocvUri = UriUtils.getFullUri(ocvDomain, ocvContextPath);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI domain: " + ocvDomain + ", or contextpath: " + ocvContextPath);
            }
        }
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

    public Boolean isHardwarePinPadForced() {
        return hardwarePinPadForced;
    }

    public void setHardwarePinPadForced(Boolean hardwarePinPadForced) {
        this.hardwarePinPadForced = hardwarePinPadForced;
    }

    public String getDsUri() {
        if (StringUtils.isEmpty(dsUri) && StringUtils.isNotEmpty(dsDomain) && StringUtils.isNotEmpty(dsContextPath)) {
            try {
                this.dsUri = UriUtils.getFullUri(dsDomain, dsContextPath);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI domain: " + dsDomain + ", or contextpath: " + dsContextPath);
            }
        }
        return this.dsUri;
    }

    public void setDsUri(String dsUri) {
        if (StringUtils.isNotEmpty(dsUri)) {
            try {
                String domain = UriUtils.getDomain(dsUri);
                String contextPath = UriUtils.getContextPath(dsUri);
                dsDomain = domain;
                dsContextPath = contextPath;
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI: " + dsUri);
            }
        }
        this.dsUri = dsUri;
    }

    public String getOcvUri() {
        if (StringUtils.isEmpty(ocvUri) && StringUtils.isNotEmpty(ocvDomain) && StringUtils.isNotEmpty(ocvContextPath)) {
            try {
                this.ocvUri = UriUtils.getFullUri(ocvDomain, ocvContextPath);
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI domain: " + ocvDomain + ", or contextpath: " + ocvContextPath);
            }
        }
        return this.ocvUri;
    }

    public void setOcvUri(String ocvUri) {
        if (StringUtils.isNotEmpty(ocvUri)) {
            try {
                String domain = UriUtils.getDomain(ocvUri);
                String contextPath = UriUtils.getContextPath(ocvUri);
                ocvDomain = domain;
                ocvContextPath = contextPath;
            } catch (MalformedURLException | URISyntaxException ex) {
                throw ExceptionFactory.configException("Invalid URI: " + ocvUri);
            }
        }
        this.ocvUri = ocvUri;
    }

    public Integer getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Boolean getCitrix() {
        return citrix;
    }

    public void setCitrix(Boolean citrix) {
        this.citrix = citrix;
    }

    public Integer getAgentPort() {
        return agentPort;
    }

    public void setAgentPort(Integer agentPort) {
        this.agentPort = agentPort;
    }

    public Boolean getConsentRequired() {
        return consentRequired;
    }

    public void setConsentRequired(Boolean consentRequired) {
        this.consentRequired = consentRequired;
    }
}
