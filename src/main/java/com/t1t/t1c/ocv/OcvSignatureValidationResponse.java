package com.t1t.t1c.ocv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OcvSignatureValidationResponse
        extends OcvSignatureValidationRequest {

    @SerializedName("result")
    @Expose
    private Boolean result;

    /**
     * @return The result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    public OcvSignatureValidationResponse withResult(Boolean result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(result).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof OcvSignatureValidationResponse)) {
            return false;
        }
        OcvSignatureValidationResponse rhs = ((OcvSignatureValidationResponse) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(result, rhs.result).isEquals();
    }

}
