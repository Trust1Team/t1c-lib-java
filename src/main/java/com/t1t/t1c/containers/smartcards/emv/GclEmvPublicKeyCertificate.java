
package com.t1t.t1c.containers.smartcards.emv;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclEmvPublicKeyCertificate {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("exponent")
    @Expose
    private String exponent;
    @SerializedName("remainder")
    @Expose
    private String remainder;

    /**
     * 
     * @return
     *     The data
     */
    public String getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(String data) {
        this.data = data;
    }

    public GclEmvPublicKeyCertificate withData(String data) {
        this.data = data;
        return this;
    }

    /**
     * 
     * @return
     *     The exponent
     */
    public String getExponent() {
        return exponent;
    }

    /**
     * 
     * @param exponent
     *     The exponent
     */
    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public GclEmvPublicKeyCertificate withExponent(String exponent) {
        this.exponent = exponent;
        return this;
    }

    /**
     * 
     * @return
     *     The remainder
     */
    public String getRemainder() {
        return remainder;
    }

    /**
     * 
     * @param remainder
     *     The remainder
     */
    public void setRemainder(String remainder) {
        this.remainder = remainder;
    }

    public GclEmvPublicKeyCertificate withRemainder(String remainder) {
        this.remainder = remainder;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(data).append(exponent).append(remainder).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclEmvPublicKeyCertificate) == false) {
            return false;
        }
        GclEmvPublicKeyCertificate rhs = ((GclEmvPublicKeyCertificate) other);
        return new EqualsBuilder().append(data, rhs.data).append(exponent, rhs.exponent).append(remainder, rhs.remainder).isEquals();
    }

}
