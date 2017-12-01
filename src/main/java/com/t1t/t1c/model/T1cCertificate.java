
package com.t1t.t1c.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.security.cert.Certificate;

@Generated("org.jsonschema2pojo")
public class T1cCertificate {

    @SerializedName("base64")
    @Expose
    private String base64;
    @SerializedName("parsed")
    @Expose
    private Certificate parsed;

    /**
     * 
     * @return
     *     The base64
     */
    public String getBase64() {
        return base64;
    }

    /**
     * 
     * @param base64
     *     The base64
     */
    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public T1cCertificate withBase64(String base64) {
        this.base64 = base64;
        return this;
    }

    /**
     * 
     * @return
     *     The parsed
     */
    public Certificate getParsed() {
        return parsed;
    }

    /**
     * 
     * @param parsed
     *     The parsed
     */
    public void setParsed(Certificate parsed) {
        this.parsed = parsed;
    }

    public T1cCertificate withParsed(Certificate parsed) {
        this.parsed = parsed;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(base64).append(parsed).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof T1cCertificate) == false) {
            return false;
        }
        T1cCertificate rhs = ((T1cCertificate) other);
        return new EqualsBuilder().append(base64, rhs.base64).append(parsed, rhs.parsed).isEquals();
    }

}
