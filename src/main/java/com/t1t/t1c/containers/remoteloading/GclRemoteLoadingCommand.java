package com.t1t.t1c.containers.remoteloading;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclRemoteLoadingCommand {

    @SerializedName("tx")
    @Expose
    private String tx;
    @SerializedName("rx")
    @Expose
    private String rx;
    @SerializedName("sw")
    @Expose
    private String sw;

    /**
     * @return The tx
     */
    public String getTx() {
        return tx;
    }

    /**
     * @param tx The tx
     */
    public void setTx(String tx) {
        this.tx = tx;
    }

    public GclRemoteLoadingCommand withTx(String tx) {
        this.tx = tx;
        return this;
    }

    /**
     * @return The rx
     */
    public String getRx() {
        return rx;
    }

    /**
     * @param rx The rx
     */
    public void setRx(String rx) {
        this.rx = rx;
    }

    public GclRemoteLoadingCommand withRx(String rx) {
        this.rx = rx;
        return this;
    }

    /**
     * @return The sw
     */
    public String getSw() {
        return sw;
    }

    /**
     * @param sw The sw
     */
    public void setSw(String sw) {
        this.sw = sw;
    }

    public GclRemoteLoadingCommand withSw(String sw) {
        this.sw = sw;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tx).append(rx).append(sw).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclRemoteLoadingCommand) == false) {
            return false;
        }
        GclRemoteLoadingCommand rhs = ((GclRemoteLoadingCommand) other);
        return new EqualsBuilder().append(tx, rhs.tx).append(rx, rhs.rx).append(sw, rhs.sw).isEquals();
    }

}
