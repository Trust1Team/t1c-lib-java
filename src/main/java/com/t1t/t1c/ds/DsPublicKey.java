
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
    @SerializedName("encryptedPublicKey")
    @Expose
    private String encryptedPublicKey;
    @SerializedName("encryptedAesKey")
    @Expose
    private String encryptedAesKey;
    @SerializedName("ns")
    @Expose
    private String ns;

    /**
     * 
     * @return
     *     The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DsPublicKey withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * 
     * @return
     *     The encryptedPublicKey
     */
    public String getEncryptedPublicKey() {
        return encryptedPublicKey;
    }

    /**
     * 
     * @param encryptedPublicKey
     *     The encryptedPublicKey
     */
    public void setEncryptedPublicKey(String encryptedPublicKey) {
        this.encryptedPublicKey = encryptedPublicKey;
    }

    public DsPublicKey withEncryptedPublicKey(String encryptedPublicKey) {
        this.encryptedPublicKey = encryptedPublicKey;
        return this;
    }

    /**
     * 
     * @return
     *     The encryptedAesKey
     */
    public String getEncryptedAesKey() {
        return encryptedAesKey;
    }

    /**
     * 
     * @param encryptedAesKey
     *     The encryptedAesKey
     */
    public void setEncryptedAesKey(String encryptedAesKey) {
        this.encryptedAesKey = encryptedAesKey;
    }

    public DsPublicKey withEncryptedAesKey(String encryptedAesKey) {
        this.encryptedAesKey = encryptedAesKey;
        return this;
    }

    /**
     * 
     * @return
     *     The ns
     */
    public String getNs() {
        return ns;
    }

    /**
     * 
     * @param ns
     *     The ns
     */
    public void setNs(String ns) {
        this.ns = ns;
    }

    public DsPublicKey withNs(String ns) {
        this.ns = ns;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(success).append(encryptedPublicKey).append(encryptedAesKey).append(ns).toHashCode();
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
        return new EqualsBuilder().append(success, rhs.success).append(encryptedPublicKey, rhs.encryptedPublicKey).append(encryptedAesKey, rhs.encryptedAesKey).append(ns, rhs.ns).isEquals();
    }

}
