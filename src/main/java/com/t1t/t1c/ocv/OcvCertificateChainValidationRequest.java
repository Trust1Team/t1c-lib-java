package com.t1t.t1c.ocv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class OcvCertificateChainValidationRequest {

    @SerializedName("certificateChain")
    @Expose
    private List<OcvCertificate> certificateChain = new ArrayList<OcvCertificate>();

    /**
     * @return The certificateChain
     */
    public List<OcvCertificate> getCertificateChain() {
        return certificateChain;
    }

    /**
     * @param certificateChain The certificateChain
     */
    public void setCertificateChain(List<OcvCertificate> certificateChain) {
        this.certificateChain = certificateChain;
    }

    public OcvCertificateChainValidationRequest withCertificateChain(List<OcvCertificate> certificateChain) {
        this.certificateChain = certificateChain;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(certificateChain).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof OcvCertificateChainValidationRequest)) {
            return false;
        }
        OcvCertificateChainValidationRequest rhs = ((OcvCertificateChainValidationRequest) other);
        return new EqualsBuilder().append(certificateChain, rhs.certificateChain).isEquals();
    }

}
