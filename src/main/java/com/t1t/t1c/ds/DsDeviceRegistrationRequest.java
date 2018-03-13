package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsDeviceRegistrationRequest {

    @SerializedName("os")
    @Expose
    private DsOs os;
    @SerializedName("managed")
    @Expose
    private Boolean managed;
    @SerializedName("browser")
    @Expose
    private DsBrowser browser;
    @SerializedName("desktopApplication")
    @Expose
    private DsDesktopApplication desktopApplication;
    @SerializedName("ua")
    @Expose
    private String ua;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("activated")
    @Expose
    private Boolean activated;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;

    /**
     * @return The os
     */
    public DsOs getOs() {
        return os;
    }

    /**
     * @param os The os
     */
    public void setOs(DsOs os) {
        this.os = os;
    }

    public DsDeviceRegistrationRequest withOs(DsOs os) {
        this.os = os;
        return this;
    }

    /**
     * @return The managed
     */
    public Boolean getManaged() {
        return managed;
    }

    /**
     * @param managed The managed
     */
    public void setManaged(Boolean managed) {
        this.managed = managed;
    }

    public DsDeviceRegistrationRequest withManaged(Boolean managed) {
        this.managed = managed;
        return this;
    }

    /**
     * @return The browser
     */
    public DsBrowser getBrowser() {
        return browser;
    }

    /**
     * @param browser The browser
     */
    public void setBrowser(DsBrowser browser) {
        this.browser = browser;
    }

    public DsDeviceRegistrationRequest withBrowser(DsBrowser browser) {
        this.browser = browser;
        return this;
    }

    /**
     * @return The desktopApplication
     */
    public DsDesktopApplication getDesktopApplication() {
        return desktopApplication;
    }

    /**
     * @param desktopApplication The desktopApplication
     */
    public void setDesktopApplication(DsDesktopApplication desktopApplication) {
        this.desktopApplication = desktopApplication;
    }

    public DsDeviceRegistrationRequest withDesktopApplication(DsDesktopApplication desktopApplication) {
        this.desktopApplication = desktopApplication;
        return this;
    }

    /**
     * @return The ua
     */
    public String getUa() {
        return ua;
    }

    /**
     * @param ua The ua
     */
    public void setUa(String ua) {
        this.ua = ua;
    }

    public DsDeviceRegistrationRequest withUa(String ua) {
        this.ua = ua;
        return this;
    }

    /**
     * @return The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DsDeviceRegistrationRequest withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public DsDeviceRegistrationRequest withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * @return The activated
     */
    public Boolean getActivated() {
        return activated;
    }

    /**
     * @param activated The activated
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public DsDeviceRegistrationRequest withActivated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    /**
     * @return The manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer The manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public DsDeviceRegistrationRequest withManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(os).append(managed).append(browser).append(desktopApplication).append(ua).append(uuid).append(version).append(activated).append(manufacturer).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DsDeviceRegistrationRequest)) {
            return false;
        }
        DsDeviceRegistrationRequest rhs = ((DsDeviceRegistrationRequest) other);
        return new EqualsBuilder().append(os, rhs.os).append(managed, rhs.managed).append(browser, rhs.browser).append(desktopApplication, rhs.desktopApplication).append(ua, rhs.ua).append(uuid, rhs.uuid).append(version, rhs.version).append(activated, rhs.activated).append(manufacturer, rhs.manufacturer).isEquals();
    }

}
