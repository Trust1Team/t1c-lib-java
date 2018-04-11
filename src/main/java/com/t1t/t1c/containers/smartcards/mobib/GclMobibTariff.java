package com.t1t.t1c.containers.smartcards.mobib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclMobibTariff {

    @SerializedName("counter")
    @Expose
    private GclMobibCounter counter;
    @SerializedName("multimodal")
    @Expose
    private Boolean multimodal;
    @SerializedName("nameref")
    @Expose
    private Long nameref;

    /**
     * @return The counter
     */
    public GclMobibCounter getCounter() {
        return counter;
    }

    /**
     * @param counter The counter
     */
    public void setCounter(GclMobibCounter counter) {
        this.counter = counter;
    }

    public GclMobibTariff withCounter(GclMobibCounter counter) {
        this.counter = counter;
        return this;
    }

    /**
     * @return The multimodal
     */
    public Boolean getMultimodal() {
        return multimodal;
    }

    /**
     * @param multimodal The multimodal
     */
    public void setMultimodal(Boolean multimodal) {
        this.multimodal = multimodal;
    }

    public GclMobibTariff withMultimodal(Boolean multimodal) {
        this.multimodal = multimodal;
        return this;
    }

    /**
     * @return The nameref
     */
    public Long getNameref() {
        return nameref;
    }

    /**
     * @param nameref The nameref
     */
    public void setNameref(Long nameref) {
        this.nameref = nameref;
    }

    public GclMobibTariff withNameref(Long nameref) {
        this.nameref = nameref;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(counter).append(multimodal).append(nameref).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclMobibTariff) == false) {
            return false;
        }
        GclMobibTariff rhs = ((GclMobibTariff) other);
        return new EqualsBuilder().append(counter, rhs.counter).append(multimodal, rhs.multimodal).append(nameref, rhs.nameref).isEquals();
    }

}
