package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclSafeNetSlot {

    @SerializedName("slot_id")
    @Expose
    private Integer slotId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("flags")
    @Expose
    private Integer flags;
    @SerializedName("hardware_version")
    @Expose
    private String hardwareVersion;
    @SerializedName("firmware_version")
    @Expose
    private String firmwareVersion;

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

    public GclSafeNetSlot withSlotId(Integer slotId) {
        this.slotId = slotId;
        return this;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public GclSafeNetSlot withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return The flags
     */
    public Integer getFlags() {
        return flags;
    }

    /**
     * @param flags The flags
     */
    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public GclSafeNetSlot withFlags(Integer flags) {
        this.flags = flags;
        return this;
    }

    /**
     * @return The hardwareVersion
     */
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    /**
     * @param hardwareVersion The hardware_version
     */
    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public GclSafeNetSlot withHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
        return this;
    }

    /**
     * @return The firmwareVersion
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * @param firmwareVersion The firmware_version
     */
    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public GclSafeNetSlot withFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(slotId).append(description).append(flags).append(hardwareVersion).append(firmwareVersion).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclSafeNetSlot)) {
            return false;
        }
        GclSafeNetSlot rhs = ((GclSafeNetSlot) other);
        return new EqualsBuilder().append(slotId, rhs.slotId).append(description, rhs.description).append(flags, rhs.flags).append(hardwareVersion, rhs.hardwareVersion).append(firmwareVersion, rhs.firmwareVersion).isEquals();
    }

}
