
package com.t1t.t1c.ds;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DsDownloadRequest {

    @SerializedName("os")
    @Expose
    private DsOs os;

    /**
     * 
     * @return
     *     The os
     */
    public DsOs getOs() {
        return os;
    }

    /**
     * 
     * @param os
     *     The os
     */
    public void setOs(DsOs os) {
        this.os = os;
    }

    public DsDownloadRequest withOs(DsOs os) {
        this.os = os;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(os).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsDownloadRequest) == false) {
            return false;
        }
        DsDownloadRequest rhs = ((DsDownloadRequest) other);
        return new EqualsBuilder().append(os, rhs.os).isEquals();
    }

}
