package com.t1t.t1c.ocv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OcvCertificate {

    @SerializedName("certificate")
    @Expose
    private String certificate;
    @SerializedName("order")
    @Expose
    private Long order;

    /**
     * @return The certificate
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * @param certificate The certificate
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public OcvCertificate withCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    /**
     * @return The order
     */
    public Long getOrder() {
        return order;
    }

    /**
     * @param order The order
     */
    public void setOrder(Long order) {
        this.order = order;
    }

    public OcvCertificate withOrder(Long order) {
        this.order = order;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(certificate).append(order).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OcvCertificate) == false) {
            return false;
        }
        OcvCertificate rhs = ((OcvCertificate) other);
        return new EqualsBuilder().append(certificate, rhs.certificate).append(order, rhs.order).isEquals();
    }

}
