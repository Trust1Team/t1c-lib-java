
package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclUpdatePublicKeyRequest {

    @SerializedName("certificate")
    @Expose
    private String certificate;

    /**
     * 
     * @return
     *     The certificate
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * 
     * @param certificate
     *     The certificate
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public GclUpdatePublicKeyRequest withCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(certificate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclUpdatePublicKeyRequest) == false) {
            return false;
        }
        GclUpdatePublicKeyRequest rhs = ((GclUpdatePublicKeyRequest) other);
        return new EqualsBuilder().append(certificate, rhs.certificate).isEquals();
    }

}
