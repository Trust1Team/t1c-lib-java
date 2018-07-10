
package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclResetPinRequest {

    @SerializedName("puk")
    @Expose
    private String puk;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("os_dialog")
    @Expose
    private Boolean osDialog;
    @SerializedName("reset_only")
    @Expose
    private Boolean resetOnly;

    /**
     * 
     * @return
     *     The puk
     */
    public String getPuk() {
        return puk;
    }

    /**
     * 
     * @param puk
     *     The puk
     */
    public void setPuk(String puk) {
        this.puk = puk;
    }

    public GclResetPinRequest withPuk(String puk) {
        this.puk = puk;
        return this;
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
        this.pin = pin;
    }

    public GclResetPinRequest withPin(String pin) {
        this.pin = pin;
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

    public GclResetPinRequest withOsDialog(Boolean osDialog) {
        this.osDialog = osDialog;
        return this;
    }

    /**
     * 
     * @return
     *     The resetOnly
     */
    public Boolean getResetOnly() {
        return resetOnly;
    }

    /**
     * 
     * @param resetOnly
     *     The reset_only
     */
    public void setResetOnly(Boolean resetOnly) {
        this.resetOnly = resetOnly;
    }

    public GclResetPinRequest withResetOnly(Boolean resetOnly) {
        this.resetOnly = resetOnly;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(puk).append(pin).append(osDialog).append(resetOnly).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclResetPinRequest)) {
            return false;
        }
        GclResetPinRequest rhs = ((GclResetPinRequest) other);
        return new EqualsBuilder().append(puk, rhs.puk).append(pin, rhs.pin).append(osDialog, rhs.osDialog).append(resetOnly, rhs.resetOnly).isEquals();
    }

}
