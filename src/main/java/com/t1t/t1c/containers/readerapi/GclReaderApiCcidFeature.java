
package com.t1t.t1c.containers.readerapi;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclReaderApiCcidFeature {

    @SerializedName("control_code")
    @Expose
    private Integer controlCode;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * 
     * @return
     *     The controlCode
     */
    public Integer getControlCode() {
        return controlCode;
    }

    /**
     * 
     * @param controlCode
     *     The control_code
     */
    public void setControlCode(Integer controlCode) {
        this.controlCode = controlCode;
    }

    public GclReaderApiCcidFeature withControlCode(Integer controlCode) {
        this.controlCode = controlCode;
        return this;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
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
        if ((other instanceof GclReaderApiCcidFeature) == false) {
            return false;
        }
        GclReaderApiCcidFeature rhs = ((GclReaderApiCcidFeature) other);
        return new EqualsBuilder().append(controlCode, rhs.controlCode).append(id, rhs.id).isEquals();
    }

}
