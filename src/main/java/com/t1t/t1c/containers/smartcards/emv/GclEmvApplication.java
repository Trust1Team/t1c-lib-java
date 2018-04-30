package com.t1t.t1c.containers.smartcards.emv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclEmvApplication {

    @SerializedName("aid")
    @Expose
    private String aid;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("priority")
    @Expose
    private Long priority;

    /**
     * @return The aid
     */
    public String getAid() {
        return aid;
    }

    /**
     * @param aid The aid
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    public GclEmvApplication withAid(String aid) {
        this.aid = aid;
        return this;
    }

    /**
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public GclEmvApplication withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * @return The priority
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * @param priority The priority
     */
    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public GclEmvApplication withPriority(Long priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aid).append(label).append(priority).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclEmvApplication)) {
            return false;
        }
        GclEmvApplication rhs = ((GclEmvApplication) other);
        return new EqualsBuilder().append(aid, rhs.aid).append(label, rhs.label).append(priority, rhs.priority).isEquals();
    }

}
