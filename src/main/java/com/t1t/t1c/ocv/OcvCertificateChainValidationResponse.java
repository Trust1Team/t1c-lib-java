
package com.t1t.t1c.ocv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OcvCertificateChainValidationResponse {

    @SerializedName("ocspResponse")
    @Expose
    private OcvOcspResponse ocspResponse;
    @SerializedName("crlResponse")
    @Expose
    private OcvCrlResponse crlResponse;

    /**
     * 
     * @return
     *     The ocspResponse
     */
    public OcvOcspResponse getOcspResponse() {
        return ocspResponse;
    }

    /**
     * 
     * @param ocspResponse
     *     The ocspResponse
     */
    public void setOcspResponse(OcvOcspResponse ocspResponse) {
        this.ocspResponse = ocspResponse;
    }

    public OcvCertificateChainValidationResponse withOcspResponse(OcvOcspResponse ocspResponse) {
        this.ocspResponse = ocspResponse;
        return this;
    }

    /**
     * 
     * @return
     *     The crlResponse
     */
    public OcvCrlResponse getCrlResponse() {
        return crlResponse;
    }

    /**
     * 
     * @param crlResponse
     *     The crlResponse
     */
    public void setCrlResponse(OcvCrlResponse crlResponse) {
        this.crlResponse = crlResponse;
    }

    public OcvCertificateChainValidationResponse withCrlResponse(OcvCrlResponse crlResponse) {
        this.crlResponse = crlResponse;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ocspResponse).append(crlResponse).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OcvCertificateChainValidationResponse) == false) {
            return false;
        }
        OcvCertificateChainValidationResponse rhs = ((OcvCertificateChainValidationResponse) other);
        return new EqualsBuilder().append(ocspResponse, rhs.ocspResponse).append(crlResponse, rhs.crlResponse).isEquals();
    }

}
