
package com.t1t.t1c.containers.remoteloading;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclRemoteLoadingCcidRequest {

    @SerializedName("feature")
    @Expose
    private String feature;
    @SerializedName("apdu")
    @Expose
    private String apdu;

    /**
     * 
     * @return
     *     The feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * 
     * @param feature
     *     The feature
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    public GclRemoteLoadingCcidRequest withFeature(String feature) {
        this.feature = feature;
        return this;
    }

    /**
     * 
     * @return
     *     The apdu
     */
    public String getApdu() {
        return apdu;
    }

    /**
     * 
     * @param apdu
     *     The apdu
     */
    public void setApdu(String apdu) {
        this.apdu = apdu;
    }

    public GclRemoteLoadingCcidRequest withApdu(String apdu) {
        this.apdu = apdu;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(feature).append(apdu).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclRemoteLoadingCcidRequest) == false) {
            return false;
        }
        GclRemoteLoadingCcidRequest rhs = ((GclRemoteLoadingCcidRequest) other);
        return new EqualsBuilder().append(feature, rhs.feature).append(apdu, rhs.apdu).isEquals();
    }

}
