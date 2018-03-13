package com.t1t.t1c.containers.remoteloading;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclRemoteLoadingOpenSessionRequest {

    @SerializedName("timeout")
    @Expose
    private Integer timeout;

    /**
     * @return The timeout
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * @param timeout The timeout
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public GclRemoteLoadingOpenSessionRequest withTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(timeout).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclRemoteLoadingOpenSessionRequest)) {
            return false;
        }
        GclRemoteLoadingOpenSessionRequest rhs = ((GclRemoteLoadingOpenSessionRequest) other);
        return new EqualsBuilder().append(timeout, rhs.timeout).isEquals();
    }

}
