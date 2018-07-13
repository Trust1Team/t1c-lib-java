package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class GclPublicKeys {

    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("ds")
    @Expose
    private List<GclDsPublicKey> ds = new ArrayList<GclDsPublicKey>();
    @SerializedName("ssl")
    @Expose
    private String ssl;

    /**
     * @return The device
     */
    public String getDevice() {
        return device;
    }

    /**
     * @param device The device
     */
    public void setDevice(String device) {
        this.device = device;
    }

    public GclPublicKeys withDevice(String device) {
        this.device = device;
        return this;
    }

    /**
     * @return The ds
     */
    public List<GclDsPublicKey> getDs() {
        return ds;
    }

    /**
     * @param ds The ds
     */
    public void setDs(List<GclDsPublicKey> ds) {
        this.ds = ds;
    }

    public GclPublicKeys withDs(List<GclDsPublicKey> ds) {
        this.ds = ds;
        return this;
    }

    /**
     * @return The ssl
     */
    public String getSsl() {
        return ssl;
    }

    /**
     * @param ssl The ssl
     */
    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public GclPublicKeys withSsl(String ssl) {
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
        if (!(other instanceof GclPublicKeys)) {
            return false;
        }
        GclPublicKeys rhs = ((GclPublicKeys) other);
        return new EqualsBuilder().append(device, rhs.device).append(ds, rhs.ds).append(ssl, rhs.ssl).isEquals();
    }

}
