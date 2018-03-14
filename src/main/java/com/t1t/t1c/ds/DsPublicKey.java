package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsPublicKey {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("pubkey")
    @Expose
    private String pubkey;

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DsPublicKey withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * @return The pubkey
     */
    public String getPubkey() {
        return pubkey;
    }

    /**
     * @param pubkey The pubkey
     */
    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public DsPublicKey withPubkey(String pubkey) {
        this.pubkey = pubkey;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(success).append(pubkey).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DsPublicKey)) {
            return false;
        }
        DsPublicKey rhs = ((DsPublicKey) other);
        return new EqualsBuilder().append(success, rhs.success).append(pubkey, rhs.pubkey).isEquals();
    }

}
