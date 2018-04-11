
package com.t1t.t1c.ocv;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class OcvChallengeVerificationRequest {

    @SerializedName("base64Certificate")
    @Expose
    private String base64Certificate;
    @SerializedName("digestAlgorithm")
    @Expose
    private String digestAlgorithm;
    @SerializedName("base64Signature")
    @Expose
    private String base64Signature;
    @SerializedName("hash")
    @Expose
    private String hash;

    /**
     * 
     * @return
     *     The base64Certificate
     */
    public String getBase64Certificate() {
        return base64Certificate;
    }

    /**
     * 
     * @param base64Certificate
     *     The base64Certificate
     */
    public void setBase64Certificate(String base64Certificate) {
        this.base64Certificate = base64Certificate;
    }

    public OcvChallengeVerificationRequest withBase64Certificate(String base64Certificate) {
        this.base64Certificate = base64Certificate;
        return this;
    }

    /**
     * 
     * @return
     *     The digestAlgorithm
     */
    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    /**
     * 
     * @param digestAlgorithm
     *     The digestAlgorithm
     */
    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public OcvChallengeVerificationRequest withDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
        return this;
    }

    /**
     * 
     * @return
     *     The base64Signature
     */
    public String getBase64Signature() {
        return base64Signature;
    }

    /**
     * 
     * @param base64Signature
     *     The base64Signature
     */
    public void setBase64Signature(String base64Signature) {
        this.base64Signature = base64Signature;
    }

    public OcvChallengeVerificationRequest withBase64Signature(String base64Signature) {
        this.base64Signature = base64Signature;
        return this;
    }

    /**
     * 
     * @return
     *     The hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * 
     * @param hash
     *     The hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    public OcvChallengeVerificationRequest withHash(String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(base64Certificate).append(digestAlgorithm).append(base64Signature).append(hash).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OcvChallengeVerificationRequest) == false) {
            return false;
        }
        OcvChallengeVerificationRequest rhs = ((OcvChallengeVerificationRequest) other);
        return new EqualsBuilder().append(base64Certificate, rhs.base64Certificate).append(digestAlgorithm, rhs.digestAlgorithm).append(base64Signature, rhs.base64Signature).append(hash, rhs.hash).isEquals();
    }

}
