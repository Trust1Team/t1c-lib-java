package com.t1t.t1c.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.security.PublicKey;

public class T1cPublicKey {

    private String derEncoded;
    private PublicKey parsed;

    /**
     * @return The derEncoded
     */
    public String getDerEncoded() {
        return derEncoded;
    }

    /**
     * @param derEncoded The derEncoded
     */
    public void setDerEncoded(String derEncoded) {
        this.derEncoded = derEncoded;
    }

    public T1cPublicKey withDerEncoded(String derEncoded) {
        this.derEncoded = derEncoded;
        return this;
    }

    /**
     * @return The parsed
     */
    public PublicKey getParsed() {
        return parsed;
    }

    /**
     * @param parsed The parsed
     */
    public void setParsed(PublicKey parsed) {
        this.parsed = parsed;
    }

    public T1cPublicKey withParsed(PublicKey parsed) {
        this.parsed = parsed;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(derEncoded).append(parsed).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof T1cPublicKey)) {
            return false;
        }
        T1cPublicKey rhs = ((T1cPublicKey) other);
        return new EqualsBuilder().append(derEncoded, rhs.derEncoded).append(parsed, rhs.parsed).isEquals();
    }

}
