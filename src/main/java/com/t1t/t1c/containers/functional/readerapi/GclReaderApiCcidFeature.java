package com.t1t.t1c.containers.functional.readerapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclReaderApiCcidFeature {

    @SerializedName("control_code")
    @Expose
    private Long controlCode;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * @return The controlCode
     */
    public Long getControlCode() {
        return controlCode;
    }

    /**
     * @param controlCode The control_code
     */
    public void setControlCode(Long controlCode) {
        this.controlCode = controlCode;
    }

    public GclReaderApiCcidFeature withControlCode(Long controlCode) {
        this.controlCode = controlCode;
        return this;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public GclReaderApiCcidFeature withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(controlCode).append(id).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclReaderApiCcidFeature)) {
            return false;
        }
        GclReaderApiCcidFeature rhs = ((GclReaderApiCcidFeature) other);
        return new EqualsBuilder().append(controlCode, rhs.controlCode).append(id, rhs.id).isEquals();
    }

}
