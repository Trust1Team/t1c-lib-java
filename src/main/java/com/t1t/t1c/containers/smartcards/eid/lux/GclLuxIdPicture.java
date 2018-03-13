package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclLuxIdPicture {

    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("raw_data")
    @Expose
    private String rawData;
    @SerializedName("width")
    @Expose
    private Integer width;

    /**
     * @return The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    public GclLuxIdPicture withHeight(Integer height) {
        this.height = height;
        return this;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public GclLuxIdPicture withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     * @return The rawData
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * @param rawData The raw_data
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public GclLuxIdPicture withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    /**
     * @return The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    public GclLuxIdPicture withWidth(Integer width) {
        this.width = width;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(height).append(image).append(rawData).append(width).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclLuxIdPicture)) {
            return false;
        }
        GclLuxIdPicture rhs = ((GclLuxIdPicture) other);
        return new EqualsBuilder().append(height, rhs.height).append(image, rhs.image).append(rawData, rhs.rawData).append(width, rhs.width).isEquals();
    }

}
