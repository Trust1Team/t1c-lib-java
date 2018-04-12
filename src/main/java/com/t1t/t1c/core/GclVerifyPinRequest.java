
package com.t1t.t1c.core;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclVerifyPinRequest {

    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("pinpad")
    @Expose
    private Boolean pinpad;
    @SerializedName("os_dialog")
    @Expose
    private Boolean osDialog;
    @SerializedName("private_key_reference")
    @Expose
    private GclPrivateKeyReference privateKeyReference;

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
        this.pin = pin;
    }

    public GclVerifyPinRequest withPin(String pin) {
        this.pin = pin;
        return this;
    }

    /**
     * 
     * @return
     *     The pinpad
     */
    public Boolean getPinpad() {
        return pinpad;
    }

    /**
     * 
     * @param pinpad
     *     The pinpad
     */
    public void setPinpad(Boolean pinpad) {
        this.pinpad = pinpad;
    }

    public GclVerifyPinRequest withPinpad(Boolean pinpad) {
        this.pinpad = pinpad;
        return this;
    }

    /**
     * 
     * @return
     *     The osDialog
     */
    public Boolean getOsDialog() {
        return osDialog;
    }

    /**
     * 
     * @param osDialog
     *     The os_dialog
     */
    public void setOsDialog(Boolean osDialog) {
        this.osDialog = osDialog;
    }

    public GclVerifyPinRequest withOsDialog(Boolean osDialog) {
        this.osDialog = osDialog;
        return this;
    }

    /**
     * 
     * @return
     *     The privateKeyReference
     */
    public GclPrivateKeyReference getPrivateKeyReference() {
        return privateKeyReference;
    }

    /**
     * 
     * @param privateKeyReference
     *     The private_key_reference
     */
    public void setPrivateKeyReference(GclPrivateKeyReference privateKeyReference) {
        this.privateKeyReference = privateKeyReference;
    }

    public GclVerifyPinRequest withPrivateKeyReference(GclPrivateKeyReference privateKeyReference) {
        this.privateKeyReference = privateKeyReference;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pin).append(pinpad).append(osDialog).append(privateKeyReference).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclVerifyPinRequest) == false) {
            return false;
        }
        GclVerifyPinRequest rhs = ((GclVerifyPinRequest) other);
        return new EqualsBuilder().append(pin, rhs.pin).append(pinpad, rhs.pinpad).append(osDialog, rhs.osDialog).append(privateKeyReference, rhs.privateKeyReference).isEquals();
    }

}
