
package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclChangePinRequest {

    @SerializedName("old_pin")
    @Expose
    private String oldPin;
    @SerializedName("new_pin")
    @Expose
    private String newPin;
    @SerializedName("os_dialog")
    @Expose
    private Boolean osDialog;

    /**
     * 
     * @return
     *     The oldPin
     */
    public String getOldPin() {
        return oldPin;
    }

    /**
     * 
     * @param oldPin
     *     The old_pin
     */
    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public GclChangePinRequest withOldPin(String oldPin) {
        this.oldPin = oldPin;
        return this;
    }

    /**
     * 
     * @return
     *     The newPin
     */
    public String getNewPin() {
        return newPin;
    }

    /**
     * 
     * @param newPin
     *     The new_pin
     */
    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public GclChangePinRequest withNewPin(String newPin) {
        this.newPin = newPin;
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

    public GclChangePinRequest withOsDialog(Boolean osDialog) {
        this.osDialog = osDialog;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(oldPin).append(newPin).append(osDialog).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclChangePinRequest)) {
            return false;
        }
        GclChangePinRequest rhs = ((GclChangePinRequest) other);
        return new EqualsBuilder().append(oldPin, rhs.oldPin).append(newPin, rhs.newPin).append(osDialog, rhs.osDialog).isEquals();
    }

}
