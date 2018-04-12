package com.t1t.t1c.configuration;

import com.t1t.t1c.containers.smartcards.pkcs11.ModuleConfiguration;
import com.t1t.t1c.utils.UriUtils;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public class LibConfig {

    // Compile time properties
    private String version;
    private String build;

    // URI's

    private String authUri;
    private String dsUri;
    private String gclClientUri;
    private String ocvUri;
    private String proxyDomain;

    // Auth

    private String apiKey;

    // Post-initialization values

    private Boolean citrix;
    private Boolean consentRequired = false;
    private Long contextToken;
    private String gclJwt;

    // General Config

    private Environment environment;
    private Integer agentPort;
    private String clientFingerprintDirectoryPath;
    private Integer containerDownloadTimeout;
    private Integer defaultConsentDuration;
    private Integer defaultConsentTimeout;
    private Integer defaultPollingIntervalInSeconds;
    private Integer defaultPollingTimeoutInSeconds;
    private Boolean hardwarePinPadForced = false;
    private Boolean implicitDownloads = false;
    private Boolean localTestMode = false;
    private Boolean osPinDialog = false;
    private ModuleConfiguration pkcs11Config;
    private Integer sessionTimeout;
    private Boolean syncManaged = true;

    // Dynamic properties

    public LibConfig() {
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

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getAuthUri() {
        return authUri;
    }

    public void setAuthUri(String authUri) {
        this.authUri = authUri;
    }

    public String getDsUri() {
        return dsUri;
    }

    public void setDsUri(String dsUri) {
        this.dsUri = UriUtils.uriFinalSlashAppender(dsUri);
    }

    public String getGclClientUri() {
        return gclClientUri;
    }

    public void setGclClientUri(String gclClientUri) {
        this.gclClientUri = UriUtils.uriFinalSlashAppender(gclClientUri);
    }

    public String getOcvUri() {
        return ocvUri;
    }

    public void setOcvUri(String ocvUri) {
        this.ocvUri = UriUtils.uriFinalSlashAppender(ocvUri);
    }

    public String getProxyDomain() {
        return proxyDomain;
    }

    public void setProxyDomain(String proxyDomain) {
        this.proxyDomain = proxyDomain;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Boolean isCitrix() {
        return citrix;
    }

    public void setCitrix(Boolean citrix) {
        this.citrix = citrix;
    }

    public Boolean isConsentRequired() {
        return consentRequired;
    }

    public void setConsentRequired(Boolean consentRequired) {
        this.consentRequired = consentRequired;
    }

    public Long getContextToken() {
        return contextToken;
    }

    public void setContextToken(Long contextToken) {
        this.contextToken = contextToken;
    }

    public String getGclJwt() {
        return gclJwt;
    }

    public void setGclJwt(String gclJwt) {
        this.gclJwt = gclJwt;
    }

    public Integer getAgentPort() {
        return agentPort;
    }

    public void setAgentPort(Integer agentPort) {
        this.agentPort = agentPort;
    }

    public String getClientFingerprintDirectoryPath() {
        return clientFingerprintDirectoryPath;
    }

    public void setClientFingerprintDirectoryPath(String clientFingerprintDirectoryPath) {
        this.clientFingerprintDirectoryPath = clientFingerprintDirectoryPath;
    }

    public Integer getContainerDownloadTimeout() {
        return containerDownloadTimeout;
    }

    public void setContainerDownloadTimeout(Integer containerDownloadTimeout) {
        this.containerDownloadTimeout = containerDownloadTimeout;
    }

    public Integer getDefaultConsentDuration() {
        return defaultConsentDuration;
    }

    public void setDefaultConsentDuration(Integer defaultConsentDuration) {
        this.defaultConsentDuration = defaultConsentDuration;
    }

    public Integer getDefaultConsentTimeout() {
        return defaultConsentTimeout;
    }

    public void setDefaultConsentTimeout(Integer defaultConsentTimeout) {
        this.defaultConsentTimeout = defaultConsentTimeout;
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

    public Boolean isImplicitDownloads() {
        return implicitDownloads;
    }

    public void setImplicitDownloads(Boolean implicitDownloads) {
        this.implicitDownloads = implicitDownloads;
    }

    public Boolean isLocalTestMode() {
        return localTestMode;
    }

    public void setLocalTestMode(Boolean localTestMode) {
        this.localTestMode = localTestMode;
    }

    public Boolean isOsPinDialog() {
        return osPinDialog;
    }

    public void setOsPinDialog(Boolean osPinDialog) {
        this.osPinDialog = osPinDialog;
    }

    public ModuleConfiguration getPkcs11Config() {
        return pkcs11Config;
    }

    public void setPkcs11Config(ModuleConfiguration pkcs11Config) {
        this.pkcs11Config = pkcs11Config;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Boolean isSyncManaged() {
        return syncManaged;
    }

    public void setSyncManaged(Boolean syncManaged) {
        this.syncManaged = syncManaged;
    }
}
