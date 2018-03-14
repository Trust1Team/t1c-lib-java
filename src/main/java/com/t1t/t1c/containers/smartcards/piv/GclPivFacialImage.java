package com.t1t.t1c.containers.smartcards.piv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclPivFacialImage {

    @SerializedName("image")
    @Expose
    private String image;

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

    public GclPivFacialImage withImage(String image) {
        this.image = image;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(image).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclPivFacialImage)) {
            return false;
        }
        GclPivFacialImage rhs = ((GclPivFacialImage) other);
        return new EqualsBuilder().append(image, rhs.image).isEquals();
    }

}
