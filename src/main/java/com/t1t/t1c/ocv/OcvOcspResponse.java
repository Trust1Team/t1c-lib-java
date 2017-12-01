
package com.t1t.t1c.ocv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OcvOcspResponse {

    @SerializedName("ocspLocation")
    @Expose
    private String ocspLocation;
    @SerializedName("status")
    @Expose
    private Boolean status;

    /**
     * 
     * @return
     *     The ocspLocation
     */
    public String getOcspLocation() {
        return ocspLocation;
    }

    /**
     * 
     * @param ocspLocation
     *     The ocspLocation
     */
    public void setOcspLocation(String ocspLocation) {
        this.ocspLocation = ocspLocation;
    }

    public OcvOcspResponse withOcspLocation(String ocspLocation) {
        this.ocspLocation = ocspLocation;
        return this;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public OcvOcspResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ocspLocation).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OcvOcspResponse) == false) {
            return false;
        }
        OcvOcspResponse rhs = ((OcvOcspResponse) other);
        return new EqualsBuilder().append(ocspLocation, rhs.ocspLocation).append(status, rhs.status).isEquals();
    }

}
