
package com.t1t.t1c.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.utils.PinUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GclVerifyPinRequest {

    private String pin;
    private Boolean pinPad;
    @SerializedName("os_dialog")
    @Expose
    private Boolean osDialog;
    private String privateKeyReference;

    public GclVerifyPinRequest(String pin, Boolean pinPad, Boolean osDialog) {
        setPin(pin);
        this.pinPad = pinPad;
        this.osDialog = osDialog;
    }

    /**
     * 
     * @return
     *     The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * 
     * @param pin
     *     The pin
     */
    public void setPin(String pin) {
        if (StringUtils.isNotEmpty(pin)) {
            this.pin = PinUtil.encryptPin(pin);
        }
        else this.pin = pin;
    }

    public GclVerifyPinRequest withPin(String pin) {
        setPin(pin);
        return this;
    }

    public Boolean getOsDialog() {
        return osDialog;
    }

    public void setOsDialog(Boolean osDialog) {
        this.osDialog = osDialog;
    }

    public GclVerifyPinRequest withOsDialog(Boolean osDialog) {
        this.osDialog = osDialog;
        return this;
    }

    public Boolean getPinPad() {
        return pinPad;
    }

    public void setPinPad(Boolean pinPad) {
        this.pinPad = pinPad;
    }

    public GclVerifyPinRequest withPinPad(Boolean pinPad) {
        this.pinPad = pinPad;
        return this;
    }

    /**
     * 
     * @return
     *     The privateKeyReference
     */
    public String getPrivateKeyReference() {
        return privateKeyReference;
    }

    /**
     * 
     * @param privateKeyReference
     *     The private_key_reference
     */
    public void setPrivateKeyReference(String privateKeyReference) {
        this.privateKeyReference = privateKeyReference;
    }

    public GclVerifyPinRequest withPrivateKeyReference(String privateKeyReference) {
        this.privateKeyReference = privateKeyReference;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pin).append(privateKeyReference).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclVerifyPinRequest)) {
            return false;
        }
        GclVerifyPinRequest rhs = ((GclVerifyPinRequest) other);
        return new EqualsBuilder().append(pin, rhs.pin).append(privateKeyReference, rhs.privateKeyReference).isEquals();
    }

}
