
package com.t1t.t1c.ds;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.core.GclContainerInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DsDeviceRegistrationRequest {

    @SerializedName("managed")
    @Expose
    private Boolean managed;
    @SerializedName("activated")
    @Expose
    private Boolean activated;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("derEncodedPublicKey")
    @Expose
    private String derEncodedPublicKey;
    @SerializedName("os")
    @Expose
    private DsOs os;
    @SerializedName("desktopApplication")
    @Expose
    private DsDesktopApplication desktopApplication;
    @SerializedName("clientInfo")
    @Expose
    private DsClientInfo clientInfo;
    @SerializedName("containerStates")
    @Expose
    private List<GclContainerInfo> containerStates = new ArrayList<GclContainerInfo>();
    @SerializedName("proxyDomain")
    @Expose
    private String proxyDomain;

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

    public DsDeviceRegistrationRequest withManaged(Boolean managed) {
        this.managed = managed;
        return this;
    }

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

    public DsDeviceRegistrationRequest withActivated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    /**
     * 
     * @return
     *     The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 
     * @param uuid
     *     The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DsDeviceRegistrationRequest withUuid(String uuid) {
        this.uuid = uuid;
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

    public DsDeviceRegistrationRequest withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * @return
     *     The derEncodedPublicKey
     */
    public String getDerEncodedPublicKey() {
        return derEncodedPublicKey;
    }

    /**
     * 
     * @param derEncodedPublicKey
     *     The derEncodedPublicKey
     */
    public void setDerEncodedPublicKey(String derEncodedPublicKey) {
        this.derEncodedPublicKey = derEncodedPublicKey;
    }

    public DsDeviceRegistrationRequest withDerEncodedPublicKey(String derEncodedPublicKey) {
        this.derEncodedPublicKey = derEncodedPublicKey;
        return this;
    }

    /**
     * 
     * @return
     *     The os
     */
    public DsOs getOs() {
        return os;
    }

    /**
     * 
     * @param os
     *     The os
     */
    public void setOs(DsOs os) {
        this.os = os;
    }

    public DsDeviceRegistrationRequest withOs(DsOs os) {
        this.os = os;
        return this;
    }

    /**
     * 
     * @return
     *     The desktopApplication
     */
    public DsDesktopApplication getDesktopApplication() {
        return desktopApplication;
    }

    /**
     * 
     * @param desktopApplication
     *     The desktopApplication
     */
    public void setDesktopApplication(DsDesktopApplication desktopApplication) {
        this.desktopApplication = desktopApplication;
    }

    public DsDeviceRegistrationRequest withDesktopApplication(DsDesktopApplication desktopApplication) {
        this.desktopApplication = desktopApplication;
        return this;
    }

    /**
     * 
     * @return
     *     The clientInfo
     */
    public DsClientInfo getClientInfo() {
        return clientInfo;
    }

    /**
     * 
     * @param clientInfo
     *     The clientInfo
     */
    public void setClientInfo(DsClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public DsDeviceRegistrationRequest withClientInfo(DsClientInfo clientInfo) {
        this.clientInfo = clientInfo;
        return this;
    }

    /**
     * 
     * @return
     *     The containerStates
     */
    public List<GclContainerInfo> getContainerStates() {
        return containerStates;
    }

    /**
     * 
     * @param containerStates
     *     The containerStates
     */
    public void setContainerStates(List<GclContainerInfo> containerStates) {
        this.containerStates = containerStates;
    }

    public DsDeviceRegistrationRequest withContainerStates(List<GclContainerInfo> containerStates) {
        this.containerStates = containerStates;
        return this;
    }

    /**
     * 
     * @return
     *     The proxyDomain
     */
    public String getProxyDomain() {
        return proxyDomain;
    }

    /**
     * 
     * @param proxyDomain
     *     The proxyDomain
     */
    public void setProxyDomain(String proxyDomain) {
        this.proxyDomain = proxyDomain;
    }

    public DsDeviceRegistrationRequest withProxyDomain(String proxyDomain) {
        this.proxyDomain = proxyDomain;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(managed).append(activated).append(uuid).append(version).append(derEncodedPublicKey).append(os).append(desktopApplication).append(clientInfo).append(containerStates).append(proxyDomain).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsDeviceRegistrationRequest) == false) {
            return false;
        }
        DsDeviceRegistrationRequest rhs = ((DsDeviceRegistrationRequest) other);
        return new EqualsBuilder().append(managed, rhs.managed).append(activated, rhs.activated).append(uuid, rhs.uuid).append(version, rhs.version).append(derEncodedPublicKey, rhs.derEncodedPublicKey).append(os, rhs.os).append(desktopApplication, rhs.desktopApplication).append(clientInfo, rhs.clientInfo).append(containerStates, rhs.containerStates).append(proxyDomain, rhs.proxyDomain).isEquals();
    }

}
