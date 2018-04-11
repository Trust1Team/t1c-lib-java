
package com.t1t.t1c.core;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclAdminCertificates {

    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("ds")
    @Expose
    private String ds;
    @SerializedName("ssl")
    @Expose
    private String ssl;

    /**
     * 
     * @return
     *     The device
     */
    public String getDevice() {
        return device;
    }

    /**
     * 
     * @param device
     *     The device
     */
    public void setDevice(String device) {
        this.device = device;
    }

    public GclAdminCertificates withDevice(String device) {
        this.device = device;
        return this;
    }

    /**
     * 
     * @return
     *     The ds
     */
    public String getDs() {
        return ds;
    }

    /**
     * 
     * @param ds
     *     The ds
     */
    public void setDs(String ds) {
        this.ds = ds;
    }

    public GclAdminCertificates withDs(String ds) {
        this.ds = ds;
        return this;
    }

    /**
     * 
     * @return
     *     The ssl
     */
    public String getSsl() {
        return ssl;
    }

    /**
     * 
     * @param ssl
     *     The ssl
     */
    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public GclAdminCertificates withSsl(String ssl) {
        this.ssl = ssl;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(device).append(ds).append(ssl).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclAdminCertificates) == false) {
            return false;
        }
        GclAdminCertificates rhs = ((GclAdminCertificates) other);
        return new EqualsBuilder().append(device, rhs.device).append(ds, rhs.ds).append(ssl, rhs.ssl).isEquals();
    }

}
