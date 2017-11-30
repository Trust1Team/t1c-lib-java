
package t1c.core;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclInfo {

    @SerializedName("activated")
    @Expose
    private Boolean activated;
    @SerializedName("arch")
    @Expose
    private String arch;
    @SerializedName("citrix")
    @Expose
    private Boolean citrix;
    @SerializedName("consent")
    @Expose
    private Boolean consent;
    @SerializedName("log_level")
    @Expose
    private String logLevel;
    @SerializedName("managed")
    @Expose
    private Boolean managed;
    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("version")
    @Expose
    private String version;

    /**
     * 
     * @return
     *     The activated
     */
    public Boolean getActivated() {
        return activated;
    }

    /**
     * 
     * @param activated
     *     The activated
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public GclInfo withActivated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    /**
     * 
     * @return
     *     The arch
     */
    public String getArch() {
        return arch;
    }

    /**
     * 
     * @param arch
     *     The arch
     */
    public void setArch(String arch) {
        this.arch = arch;
    }

    public GclInfo withArch(String arch) {
        this.arch = arch;
        return this;
    }

    /**
     * 
     * @return
     *     The citrix
     */
    public Boolean getCitrix() {
        return citrix;
    }

    /**
     * 
     * @param citrix
     *     The citrix
     */
    public void setCitrix(Boolean citrix) {
        this.citrix = citrix;
    }

    public GclInfo withCitrix(Boolean citrix) {
        this.citrix = citrix;
        return this;
    }

    /**
     * 
     * @return
     *     The consent
     */
    public Boolean getConsent() {
        return consent;
    }

    /**
     * 
     * @param consent
     *     The consent
     */
    public void setConsent(Boolean consent) {
        this.consent = consent;
    }

    public GclInfo withConsent(Boolean consent) {
        this.consent = consent;
        return this;
    }

    /**
     * 
     * @return
     *     The logLevel
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * 
     * @param logLevel
     *     The log_level
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public GclInfo withLogLevel(String logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    /**
     * 
     * @return
     *     The managed
     */
    public Boolean getManaged() {
        return managed;
    }

    /**
     * 
     * @param managed
     *     The managed
     */
    public void setManaged(Boolean managed) {
        this.managed = managed;
    }

    public GclInfo withManaged(Boolean managed) {
        this.managed = managed;
        return this;
    }

    /**
     * 
     * @return
     *     The os
     */
    public String getOs() {
        return os;
    }

    /**
     * 
     * @param os
     *     The os
     */
    public void setOs(String os) {
        this.os = os;
    }

    public GclInfo withOs(String os) {
        this.os = os;
        return this;
    }

    /**
     * 
     * @return
     *     The uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 
     * @param uid
     *     The uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    public GclInfo withUid(String uid) {
        this.uid = uid;
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

    public GclInfo withVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(activated).append(arch).append(citrix).append(consent).append(logLevel).append(managed).append(os).append(uid).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclInfo) == false) {
            return false;
        }
        GclInfo rhs = ((GclInfo) other);
        return new EqualsBuilder().append(activated, rhs.activated).append(arch, rhs.arch).append(citrix, rhs.citrix).append(consent, rhs.consent).append(logLevel, rhs.logLevel).append(managed, rhs.managed).append(os, rhs.os).append(uid, rhs.uid).append(version, rhs.version).isEquals();
    }

}
