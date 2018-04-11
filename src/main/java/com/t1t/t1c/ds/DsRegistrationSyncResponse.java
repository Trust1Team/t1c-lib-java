package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class DsRegistrationSyncResponse {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("activated")
    @Expose
    private Boolean activated;
    @SerializedName("managed")
    @Expose
    private Boolean managed;
    @SerializedName("coreVersion")
    @Expose
    private String coreVersion;
    @SerializedName("contextToken")
    @Expose
    private Long contextToken;
    @SerializedName("containerResponses")
    @Expose
    private List<DsContainerResponse> containerResponses = new ArrayList<DsContainerResponse>();
    @SerializedName("atrList")
    @Expose
    private DsAtrList atrList;

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

    public DsRegistrationSyncResponse withUuid(String uuid) {
        this.uuid = uuid;
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

    public DsRegistrationSyncResponse withActivated(Boolean activated) {
        this.activated = activated;
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

    public DsRegistrationSyncResponse withManaged(Boolean managed) {
        this.managed = managed;
        return this;
    }

    /**
     * @return The coreVersion
     */
    public String getCoreVersion() {
        return coreVersion;
    }

    /**
     * @param coreVersion The coreVersion
     */
    public void setCoreVersion(String coreVersion) {
        this.coreVersion = coreVersion;
    }

    public DsRegistrationSyncResponse withCoreVersion(String coreVersion) {
        this.coreVersion = coreVersion;
        return this;
    }

    /**
     * @return The contextToken
     */
    public Long getContextToken() {
        return contextToken;
    }

    /**
     * @param contextToken The contextToken
     */
    public void setContextToken(Long contextToken) {
        this.contextToken = contextToken;
    }

    public DsRegistrationSyncResponse withContextToken(Long contextToken) {
        this.contextToken = contextToken;
        return this;
    }

    /**
     * @return The containerResponses
     */
    public List<DsContainerResponse> getContainerResponses() {
        return containerResponses;
    }

    /**
     * @param containerResponses The containerResponses
     */
    public void setContainerResponses(List<DsContainerResponse> containerResponses) {
        this.containerResponses = containerResponses;
    }

    public DsRegistrationSyncResponse withContainerResponses(List<DsContainerResponse> containerResponses) {
        this.containerResponses = containerResponses;
        return this;
    }

    /**
     * @return The atrList
     */
    public DsAtrList getAtrList() {
        return atrList;
    }

    /**
     * @param atrList The atrList
     */
    public void setAtrList(DsAtrList atrList) {
        this.atrList = atrList;
    }

    public DsRegistrationSyncResponse withAtrList(DsAtrList atrList) {
        this.atrList = atrList;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(activated).append(managed).append(coreVersion).append(contextToken).append(containerResponses).append(atrList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DsRegistrationSyncResponse)) {
            return false;
        }
        DsRegistrationSyncResponse rhs = ((DsRegistrationSyncResponse) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(activated, rhs.activated).append(managed, rhs.managed).append(coreVersion, rhs.coreVersion).append(contextToken, rhs.contextToken).append(containerResponses, rhs.containerResponses).append(atrList, rhs.atrList).isEquals();
    }

}
