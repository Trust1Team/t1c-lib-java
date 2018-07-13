
package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclPinTryCounterRequest {

    @SerializedName("pin_reference")
    @Expose
    private GclPinReference pinReference;

    /**
     * 
     * @return
     *     The pinReference
     */
    public GclPinReference getPinReference() {
        return pinReference;
    }

    /**
     * 
     * @param pinReference
     *     The pin_reference
     */
    public void setPinReference(GclPinReference pinReference) {
        this.pinReference = pinReference;
    }

    public GclPinTryCounterRequest withPinReference(GclPinReference pinReference) {
        this.pinReference = pinReference;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pinReference).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclPinTryCounterRequest)) {
            return false;
        }
        GclPinTryCounterRequest rhs = ((GclPinTryCounterRequest) other);
        return new EqualsBuilder().append(pinReference, rhs.pinReference).isEquals();
    }

}
