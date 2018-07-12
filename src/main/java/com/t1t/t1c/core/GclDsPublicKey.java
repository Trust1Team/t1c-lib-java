
package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclDsPublicKey {

    @SerializedName("ns")
    @Expose
    private String ns;
    @SerializedName("base64")
    @Expose
    private String base64;

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

    public GclDsPublicKey withNs(String ns) {
        this.ns = ns;
        return this;
    }

    /**
     * 
     * @return
     *     The base64
     */
    public String getBase64() {
        return base64;
    }

    /**
     * 
     * @param base64
     *     The base64
     */
    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public GclDsPublicKey withBase64(String base64) {
        this.base64 = base64;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ns).append(base64).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclDsPublicKey)) {
            return false;
        }
        GclDsPublicKey rhs = ((GclDsPublicKey) other);
        return new EqualsBuilder().append(ns, rhs.ns).append(base64, rhs.base64).isEquals();
    }

}
