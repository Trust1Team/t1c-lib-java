package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclAuthenticateOrSignData {

    @SerializedName("algorithm_reference")
    @Expose
    private String algorithmReference;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("pin")
    @Expose
    private String pin;

    /**
     * @return The algorithmReference
     */
    public String getAlgorithmReference() {
        return algorithmReference;
    }

    /**
     * @param algorithmReference The algorithm_reference
     */
    public void setAlgorithmReference(String algorithmReference) {
        this.algorithmReference = algorithmReference;
    }

    public GclAuthenticateOrSignData withAlgorithmReference(String algorithmReference) {
        this.algorithmReference = algorithmReference;
        return this;
    }

    /**
     * @return The data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(String data) {
        this.data = data;
    }

    public GclAuthenticateOrSignData withData(String data) {
        this.data = data;
        return this;
    }

    /**
     * @return The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    public GclAuthenticateOrSignData withPin(String pin) {
        this.pin = pin;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(algorithmReference).append(data).append(pin).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclAuthenticateOrSignData) == false) {
            return false;
        }
        GclAuthenticateOrSignData rhs = ((GclAuthenticateOrSignData) other);
        return new EqualsBuilder().append(algorithmReference, rhs.algorithmReference).append(data, rhs.data).append(pin, rhs.pin).isEquals();
    }

}
