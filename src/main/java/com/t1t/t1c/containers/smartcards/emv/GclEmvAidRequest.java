
package com.t1t.t1c.containers.smartcards.emv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclEmvAidRequest {

    @SerializedName("aid")
    @Expose
    private String aid;

    /**
     * 
     * @return
     *     The aid
     */
    public String getAid() {
        return aid;
    }

    /**
     * 
     * @param aid
     *     The aid
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    public GclEmvAidRequest withAid(String aid) {
        this.aid = aid;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aid).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclEmvAidRequest) == false) {
            return false;
        }
        GclEmvAidRequest rhs = ((GclEmvAidRequest) other);
        return new EqualsBuilder().append(aid, rhs.aid).isEquals();
    }

}
