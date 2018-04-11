package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsDevice {

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

    public DsDevice withUuid(String uuid) {
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

    public DsDevice withActivated(Boolean activated) {
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

    public DsDevice withManaged(Boolean managed) {
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

    public DsDevice withCoreVersion(String coreVersion) {
        this.coreVersion = coreVersion;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(activated).append(managed).append(coreVersion).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsDevice) == false) {
            return false;
        }
        DsDevice rhs = ((DsDevice) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(activated, rhs.activated).append(managed, rhs.managed).append(coreVersion, rhs.coreVersion).isEquals();
    }

}
