package com.t1t.t1c.containers.smartcards.mobib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclMobibCounter {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("journeys")
    @Expose
    private Integer journeys;

    /**
     * @return The time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    public GclMobibCounter withTime(String time) {
        this.time = time;
        return this;
    }

    /**
     * @return The type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public GclMobibCounter withType(Integer type) {
        this.type = type;
        return this;
    }

    /**
     * @return The journeys
     */
    public Integer getJourneys() {
        return journeys;
    }

    /**
     * @param journeys The journeys
     */
    public void setJourneys(Integer journeys) {
        this.journeys = journeys;
    }

    public GclMobibCounter withJourneys(Integer journeys) {
        this.journeys = journeys;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(time).append(type).append(journeys).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclMobibCounter) == false) {
            return false;
        }
        GclMobibCounter rhs = ((GclMobibCounter) other);
        return new EqualsBuilder().append(time, rhs.time).append(type, rhs.type).append(journeys, rhs.journeys).isEquals();
    }

}
