package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsAtrList {

    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("storagePath")
    @Expose
    private String storagePath;

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

    public DsAtrList withHash(String hash) {
        this.hash = hash;
        return this;
    }

    /**
     * @return The storagePath
     */
    public String getStoragePath() {
        return storagePath;
    }

    /**
     * @param storagePath The storagePath
     */
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public DsAtrList withStoragePath(String storagePath) {
        this.storagePath = storagePath;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hash).append(storagePath).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DsAtrList)) {
            return false;
        }
        DsAtrList rhs = ((DsAtrList) other);
        return new EqualsBuilder().append(hash, rhs.hash).append(storagePath, rhs.storagePath).isEquals();
    }

}
