
package com.t1t.t1c.containers.smartcards.pki.aventra;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclAventraPinResetRequest {

    @SerializedName("puk")
    @Expose
    private String puk;
    @SerializedName("new_pin")
    @Expose
    private String newPin;
    @SerializedName("private_key_reference")
    @Expose
    private String privateKeyReference;

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

    public GclAventraPinResetRequest withPuk(String puk) {
        this.puk = puk;
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

    public GclAventraPinResetRequest withNewPin(String newPin) {
        this.newPin = newPin;
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

    public GclAventraPinResetRequest withPrivateKeyReference(String privateKeyReference) {
        this.privateKeyReference = privateKeyReference;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(puk).append(newPin).append(privateKeyReference).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclAventraPinResetRequest) == false) {
            return false;
        }
        GclAventraPinResetRequest rhs = ((GclAventraPinResetRequest) other);
        return new EqualsBuilder().append(puk, rhs.puk).append(newPin, rhs.newPin).append(privateKeyReference, rhs.privateKeyReference).isEquals();
    }

}
