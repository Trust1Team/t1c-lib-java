
package com.t1t.t1c.containers.smartcards.mobib;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclMobibValidityDuration {

    @SerializedName("unit")
    @Expose
    private Integer unit;
    @SerializedName("value")
    @Expose
    private Integer value;

    /**
     * 
     * @return
     *     The unit
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit
     *     The unit
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public GclMobibValidityDuration withUnit(Integer unit) {
        this.unit = unit;
        return this;
    }

    /**
     * 
     * @return
     *     The value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    public GclMobibValidityDuration withValue(Integer value) {
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
