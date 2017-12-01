
package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclLuxIdSignatureImage {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("raw_data")
    @Expose
    private String rawData;

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public GclLuxIdSignatureImage withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     * 
     * @return
     *     The rawData
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * 
     * @param rawData
     *     The raw_data
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public GclLuxIdSignatureImage withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(image).append(rawData).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclLuxIdSignatureImage) == false) {
            return false;
        }
        GclLuxIdSignatureImage rhs = ((GclLuxIdSignatureImage) other);
        return new EqualsBuilder().append(image, rhs.image).append(rawData, rhs.rawData).isEquals();
    }

}
