package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclSafeNetRequest {

    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("slot_id")
    @Expose
    private Integer slotId;
    @SerializedName("pin")
    @Expose
    private String pin;

    /**
     * @return The module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module The module
     */
    public void setModule(String module) {
        this.module = module;
    }

    public GclSafeNetRequest withModule(String module) {
        this.module = module;
        return this;
    }

    /**
     * @return The slotId
     */
    public Integer getSlotId() {
        return slotId;
    }

    /**
     * @param slotId The slot_id
     */
    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public GclSafeNetRequest withSlotId(Integer slotId) {
        this.slotId = slotId;
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

    public GclSafeNetRequest withPin(String pin) {
        this.pin = pin;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(module).append(slotId).append(pin).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclSafeNetRequest)) {
            return false;
        }
        GclSafeNetRequest rhs = ((GclSafeNetRequest) other);
        return new EqualsBuilder().append(module, rhs.module).append(slotId, rhs.slotId).append(pin, rhs.pin).isEquals();
    }

}
