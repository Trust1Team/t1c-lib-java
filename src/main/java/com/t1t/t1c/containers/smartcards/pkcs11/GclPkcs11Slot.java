package com.t1t.t1c.containers.smartcards.pkcs11;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclPkcs11Slot {

    @SerializedName("slot_id")
    @Expose
    private Long slotId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("flags")
    @Expose
    private Long flags;
    @SerializedName("hardware_version")
    @Expose
    private String hardwareVersion;
    @SerializedName("firmware_version")
    @Expose
    private String firmwareVersion;

    /**
     * @return The slotId
     */
    public Long getSlotId() {
        return slotId;
    }

    /**
     * @param slotId The slot_id
     */
    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public GclPkcs11Slot withSlotId(Long slotId) {
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

    public GclPkcs11Slot withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return The flags
     */
    public Long getFlags() {
        return flags;
    }

    /**
     * @param flags The flags
     */
    public void setFlags(Long flags) {
        this.flags = flags;
    }

    public GclPkcs11Slot withFlags(Long flags) {
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

    public GclPkcs11Slot withHardwareVersion(String hardwareVersion) {
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

    public GclPkcs11Slot withFirmwareVersion(String firmwareVersion) {
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
        if (!(other instanceof GclPkcs11Slot)) {
            return false;
        }
        GclPkcs11Slot rhs = ((GclPkcs11Slot) other);
        return new EqualsBuilder().append(slotId, rhs.slotId).append(description, rhs.description).append(flags, rhs.flags).append(hardwareVersion, rhs.hardwareVersion).append(firmwareVersion, rhs.firmwareVersion).isEquals();
    }

}
