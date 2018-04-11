
package com.t1t.t1c.ds;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DsContainerStorage {

    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("storagePath")
    @Expose
    private String storagePath;
    @SerializedName("os")
    @Expose
    private String os;

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

    public DsContainerStorage withHash(String hash) {
        this.hash = hash;
        return this;
    }

    /**
     * 
     * @return
     *     The storagePath
     */
    public String getStoragePath() {
        return storagePath;
    }

    /**
     * 
     * @param storagePath
     *     The storagePath
     */
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public DsContainerStorage withStoragePath(String storagePath) {
        this.storagePath = storagePath;
        return this;
    }

    /**
     * 
     * @return
     *     The os
     */
    public String getOs() {
        return os;
    }

    /**
     * 
     * @param os
     *     The os
     */
    public void setOs(String os) {
        this.os = os;
    }

    public DsContainerStorage withOs(String os) {
        this.os = os;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hash).append(storagePath).append(os).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsContainerStorage) == false) {
            return false;
        }
        DsContainerStorage rhs = ((DsContainerStorage) other);
        return new EqualsBuilder().append(hash, rhs.hash).append(storagePath, rhs.storagePath).append(os, rhs.os).isEquals();
    }

}
