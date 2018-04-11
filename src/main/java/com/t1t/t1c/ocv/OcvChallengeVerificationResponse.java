
package com.t1t.t1c.ocv;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class OcvChallengeVerificationResponse {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("digestAlgorithm")
    @Expose
    private String digestAlgorithm;
    @SerializedName("hash")
    @Expose
    private String hash;

    /**
     * 
     * @return
     *     The result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    public OcvChallengeVerificationResponse withResult(Boolean result) {
        this.result = result;
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

    public OcvChallengeVerificationResponse withDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
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

    public OcvChallengeVerificationResponse withHash(String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(result).append(digestAlgorithm).append(hash).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OcvChallengeVerificationResponse) == false) {
            return false;
        }
        OcvChallengeVerificationResponse rhs = ((OcvChallengeVerificationResponse) other);
        return new EqualsBuilder().append(result, rhs.result).append(digestAlgorithm, rhs.digestAlgorithm).append(hash, rhs.hash).isEquals();
    }

}
