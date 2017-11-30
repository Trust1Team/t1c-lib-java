
package com.t1t.t1c.containers.smartcards.ocra;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclOcraAllData implements AllData
{

    @SerializedName("counter")
    @Expose
    private String counter;

    /**
     * 
     * @return
     *     The counter
     */
    public String getCounter() {
        return counter;
    }

    /**
     * 
     * @param counter
     *     The counter
     */
    public void setCounter(String counter) {
        this.counter = counter;
    }

    public GclOcraAllData withCounter(String counter) {
        this.counter = counter;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(counter).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclOcraAllData) == false) {
            return false;
        }
        GclOcraAllData rhs = ((GclOcraAllData) other);
        return new EqualsBuilder().append(counter, rhs.counter).isEquals();
    }

}
