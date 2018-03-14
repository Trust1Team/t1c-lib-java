package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclAventraAppletInfo {

    @SerializedName("change_counter")
    @Expose
    private Integer changeCounter;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serial")
    @Expose
    private String serial;
    @SerializedName("version")
    @Expose
    private String version;

    /**
     * @return The changeCounter
     */
    public Integer getChangeCounter() {
        return changeCounter;
    }

    /**
     * @param changeCounter The change_counter
     */
    public void setChangeCounter(Integer changeCounter) {
        this.changeCounter = changeCounter;
    }

    public GclAventraAppletInfo withChangeCounter(Integer changeCounter) {
        this.changeCounter = changeCounter;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public GclAventraAppletInfo withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @param serial The serial
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    public GclAventraAppletInfo withSerial(String serial) {
        this.serial = serial;
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

    public GclAventraAppletInfo withVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(changeCounter).append(name).append(serial).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclAventraAppletInfo)) {
            return false;
        }
        GclAventraAppletInfo rhs = ((GclAventraAppletInfo) other);
        return new EqualsBuilder().append(changeCounter, rhs.changeCounter).append(name, rhs.name).append(serial, rhs.serial).append(version, rhs.version).isEquals();
    }

}
