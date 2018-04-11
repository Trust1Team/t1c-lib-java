package com.t1t.t1c.containers.readerapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclReaderApiOpenSessionRequest {

    @SerializedName("timeout")
    @Expose
    private Long timeout;

    /**
     * @return The timeout
     */
    public Long getTimeout() {
        return timeout;
    }

    /**
     * @param timeout The timeout
     */
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public GclReaderApiOpenSessionRequest withTimeout(Long timeout) {
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
        if ((other instanceof GclReaderApiOpenSessionRequest) == false) {
            return false;
        }
        GclReaderApiOpenSessionRequest rhs = ((GclReaderApiOpenSessionRequest) other);
        return new EqualsBuilder().append(timeout, rhs.timeout).isEquals();
    }

}
