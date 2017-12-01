
package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsSystemStatus {

    @SerializedName("configFile")
    @Expose
    private String configFile;
    @SerializedName("build")
    @Expose
    private String build;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("environemnt")
    @Expose
    private String environemnt;
    @SerializedName("storageAppName")
    @Expose
    private String storageAppName;
    @SerializedName("storageServiceAccount")
    @Expose
    private String storageServiceAccount;
    @SerializedName("storageCertPath")
    @Expose
    private String storageCertPath;
    @SerializedName("storageBucket")
    @Expose
    private String storageBucket;
    @SerializedName("storageDownloadPrefix")
    @Expose
    private String storageDownloadPrefix;
    @SerializedName("fileOsx")
    @Expose
    private String fileOsx;
    @SerializedName("fileWin32")
    @Expose
    private String fileWin32;
    @SerializedName("fileWin64")
    @Expose
    private String fileWin64;
    @SerializedName("fileDefaultVersion")
    @Expose
    private String fileDefaultVersion;
    @SerializedName("securityEnabled")
    @Expose
    private String securityEnabled;
    @SerializedName("securityPrivateKeyAvailable")
    @Expose
    private Boolean securityPrivateKeyAvailable;

    /**
     * 
     * @return
     *     The configFile
     */
    public String getConfigFile() {
        return configFile;
    }

    /**
     * 
     * @param configFile
     *     The configFile
     */
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public DsSystemStatus withConfigFile(String configFile) {
        this.configFile = configFile;
        return this;
    }

    /**
     * 
     * @return
     *     The build
     */
    public String getBuild() {
        return build;
    }

    /**
     * 
     * @param build
     *     The build
     */
    public void setBuild(String build) {
        this.build = build;
    }

    public DsSystemStatus withBuild(String build) {
        this.build = build;
        return this;
    }

    /**
     * 
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public DsSystemStatus withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * @return
     *     The environemnt
     */
    public String getEnvironemnt() {
        return environemnt;
    }

    /**
     * 
     * @param environemnt
     *     The environemnt
     */
    public void setEnvironemnt(String environemnt) {
        this.environemnt = environemnt;
    }

    public DsSystemStatus withEnvironemnt(String environemnt) {
        this.environemnt = environemnt;
        return this;
    }

    /**
     * 
     * @return
     *     The storageAppName
     */
    public String getStorageAppName() {
        return storageAppName;
    }

    /**
     * 
     * @param storageAppName
     *     The storageAppName
     */
    public void setStorageAppName(String storageAppName) {
        this.storageAppName = storageAppName;
    }

    public DsSystemStatus withStorageAppName(String storageAppName) {
        this.storageAppName = storageAppName;
        return this;
    }

    /**
     * 
     * @return
     *     The storageServiceAccount
     */
    public String getStorageServiceAccount() {
        return storageServiceAccount;
    }

    /**
     * 
     * @param storageServiceAccount
     *     The storageServiceAccount
     */
    public void setStorageServiceAccount(String storageServiceAccount) {
        this.storageServiceAccount = storageServiceAccount;
    }

    public DsSystemStatus withStorageServiceAccount(String storageServiceAccount) {
        this.storageServiceAccount = storageServiceAccount;
        return this;
    }

    /**
     * 
     * @return
     *     The storageCertPath
     */
    public String getStorageCertPath() {
        return storageCertPath;
    }

    /**
     * 
     * @param storageCertPath
     *     The storageCertPath
     */
    public void setStorageCertPath(String storageCertPath) {
        this.storageCertPath = storageCertPath;
    }

    public DsSystemStatus withStorageCertPath(String storageCertPath) {
        this.storageCertPath = storageCertPath;
        return this;
    }

    /**
     * 
     * @return
     *     The storageBucket
     */
    public String getStorageBucket() {
        return storageBucket;
    }

    /**
     * 
     * @param storageBucket
     *     The storageBucket
     */
    public void setStorageBucket(String storageBucket) {
        this.storageBucket = storageBucket;
    }

    public DsSystemStatus withStorageBucket(String storageBucket) {
        this.storageBucket = storageBucket;
        return this;
    }

    /**
     * 
     * @return
     *     The storageDownloadPrefix
     */
    public String getStorageDownloadPrefix() {
        return storageDownloadPrefix;
    }

    /**
     * 
     * @param storageDownloadPrefix
     *     The storageDownloadPrefix
     */
    public void setStorageDownloadPrefix(String storageDownloadPrefix) {
        this.storageDownloadPrefix = storageDownloadPrefix;
    }

    public DsSystemStatus withStorageDownloadPrefix(String storageDownloadPrefix) {
        this.storageDownloadPrefix = storageDownloadPrefix;
        return this;
    }

    /**
     * 
     * @return
     *     The fileOsx
     */
    public String getFileOsx() {
        return fileOsx;
    }

    /**
     * 
     * @param fileOsx
     *     The fileOsx
     */
    public void setFileOsx(String fileOsx) {
        this.fileOsx = fileOsx;
    }

    public DsSystemStatus withFileOsx(String fileOsx) {
        this.fileOsx = fileOsx;
        return this;
    }

    /**
     * 
     * @return
     *     The fileWin32
     */
    public String getFileWin32() {
        return fileWin32;
    }

    /**
     * 
     * @param fileWin32
     *     The fileWin32
     */
    public void setFileWin32(String fileWin32) {
        this.fileWin32 = fileWin32;
    }

    public DsSystemStatus withFileWin32(String fileWin32) {
        this.fileWin32 = fileWin32;
        return this;
    }

    /**
     * 
     * @return
     *     The fileWin64
     */
    public String getFileWin64() {
        return fileWin64;
    }

    /**
     * 
     * @param fileWin64
     *     The fileWin64
     */
    public void setFileWin64(String fileWin64) {
        this.fileWin64 = fileWin64;
    }

    public DsSystemStatus withFileWin64(String fileWin64) {
        this.fileWin64 = fileWin64;
        return this;
    }

    /**
     * 
     * @return
     *     The fileDefaultVersion
     */
    public String getFileDefaultVersion() {
        return fileDefaultVersion;
    }

    /**
     * 
     * @param fileDefaultVersion
     *     The fileDefaultVersion
     */
    public void setFileDefaultVersion(String fileDefaultVersion) {
        this.fileDefaultVersion = fileDefaultVersion;
    }

    public DsSystemStatus withFileDefaultVersion(String fileDefaultVersion) {
        this.fileDefaultVersion = fileDefaultVersion;
        return this;
    }

    /**
     * 
     * @return
     *     The securityEnabled
     */
    public String getSecurityEnabled() {
        return securityEnabled;
    }

    /**
     * 
     * @param securityEnabled
     *     The securityEnabled
     */
    public void setSecurityEnabled(String securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    public DsSystemStatus withSecurityEnabled(String securityEnabled) {
        this.securityEnabled = securityEnabled;
        return this;
    }

    /**
     * 
     * @return
     *     The securityPrivateKeyAvailable
     */
    public Boolean getSecurityPrivateKeyAvailable() {
        return securityPrivateKeyAvailable;
    }

    /**
     * 
     * @param securityPrivateKeyAvailable
     *     The securityPrivateKeyAvailable
     */
    public void setSecurityPrivateKeyAvailable(Boolean securityPrivateKeyAvailable) {
        this.securityPrivateKeyAvailable = securityPrivateKeyAvailable;
    }

    public DsSystemStatus withSecurityPrivateKeyAvailable(Boolean securityPrivateKeyAvailable) {
        this.securityPrivateKeyAvailable = securityPrivateKeyAvailable;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(configFile).append(build).append(version).append(environemnt).append(storageAppName).append(storageServiceAccount).append(storageCertPath).append(storageBucket).append(storageDownloadPrefix).append(fileOsx).append(fileWin32).append(fileWin64).append(fileDefaultVersion).append(securityEnabled).append(securityPrivateKeyAvailable).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsSystemStatus) == false) {
            return false;
        }
        DsSystemStatus rhs = ((DsSystemStatus) other);
        return new EqualsBuilder().append(configFile, rhs.configFile).append(build, rhs.build).append(version, rhs.version).append(environemnt, rhs.environemnt).append(storageAppName, rhs.storageAppName).append(storageServiceAccount, rhs.storageServiceAccount).append(storageCertPath, rhs.storageCertPath).append(storageBucket, rhs.storageBucket).append(storageDownloadPrefix, rhs.storageDownloadPrefix).append(fileOsx, rhs.fileOsx).append(fileWin32, rhs.fileWin32).append(fileWin64, rhs.fileWin64).append(fileDefaultVersion, rhs.fileDefaultVersion).append(securityEnabled, rhs.securityEnabled).append(securityPrivateKeyAvailable, rhs.securityPrivateKeyAvailable).isEquals();
    }

}
