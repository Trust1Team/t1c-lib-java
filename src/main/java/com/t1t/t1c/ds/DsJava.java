package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsJava {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("specificationVersion")
    @Expose
    private String specificationVersion;

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

    public DsJava withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * @return The specificationVersion
     */
    public String getSpecificationVersion() {
        return specificationVersion;
    }

    /**
     * @param specificationVersion The specificationVersion
     */
    public void setSpecificationVersion(String specificationVersion) {
        this.specificationVersion = specificationVersion;
    }

    public DsJava withSpecificationVersion(String specificationVersion) {
        this.specificationVersion = specificationVersion;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(version).append(specificationVersion).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsJava) == false) {
            return false;
        }
        DsJava rhs = ((DsJava) other);
        return new EqualsBuilder().append(version, rhs.version).append(specificationVersion, rhs.specificationVersion).isEquals();
    }

}
