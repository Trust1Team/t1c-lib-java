package com.t1t.t1c.containers.smartcards.mobib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclMobibValidityDuration {

    @SerializedName("unit")
    @Expose
    private Long unit;
    @SerializedName("value")
    @Expose
    private Long value;

    /**
     * @return The unit
     */
    public Long getUnit() {
        return unit;
    }

    /**
     * @param unit The unit
     */
    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public GclMobibValidityDuration withUnit(Long unit) {
        this.unit = unit;
        return this;
    }

    /**
     * @return The value
     */
    public Long getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(Long value) {
        this.value = value;
    }

    public GclMobibValidityDuration withValue(Long value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(unit).append(value).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclMobibValidityDuration) == false) {
            return false;
        }
        GclMobibValidityDuration rhs = ((GclMobibValidityDuration) other);
        return new EqualsBuilder().append(unit, rhs.unit).append(value, rhs.value).isEquals();
    }

}
