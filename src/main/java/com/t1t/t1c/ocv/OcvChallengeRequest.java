package com.t1t.t1c.ocv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OcvChallengeRequest {

    @SerializedName("digestAlgorithm")
    @Expose
    private String digestAlgorithm;
    @SerializedName("hash")
    @Expose
    private String hash;

    /**
     * @return The digestAlgorithm
     */
    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    /**
     * @param digestAlgorithm The digestAlgorithm
     */
    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public OcvChallengeRequest withDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
        return this;
    }

    /**
     * @return The hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash The hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    public OcvChallengeRequest withHash(String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(digestAlgorithm).append(hash).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OcvChallengeRequest) == false) {
            return false;
        }
        OcvChallengeRequest rhs = ((OcvChallengeRequest) other);
        return new EqualsBuilder().append(digestAlgorithm, rhs.digestAlgorithm).append(hash, rhs.hash).isEquals();
    }

}
