package com.t1t.t1c.containers.readerapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclReaderApiCcidRequest {

    @SerializedName("feature")
    @Expose
    private String feature;
    @SerializedName("apdu")
    @Expose
    private String apdu;

    /**
     * @return The feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * @param feature The feature
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    public GclReaderApiCcidRequest withFeature(String feature) {
        this.feature = feature;
        return this;
    }

    /**
     * @return The apdu
     */
    public String getApdu() {
        return apdu;
    }

    /**
     * @param apdu The apdu
     */
    public void setApdu(String apdu) {
        this.apdu = apdu;
    }

    public GclReaderApiCcidRequest withApdu(String apdu) {
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
        if ((other instanceof GclReaderApiCcidRequest) == false) {
            return false;
        }
        GclReaderApiCcidRequest rhs = ((GclReaderApiCcidRequest) other);
        return new EqualsBuilder().append(feature, rhs.feature).append(apdu, rhs.apdu).isEquals();
    }

}
