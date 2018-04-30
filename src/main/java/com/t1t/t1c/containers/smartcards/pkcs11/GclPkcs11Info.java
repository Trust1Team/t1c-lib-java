package com.t1t.t1c.containers.smartcards.pkcs11;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclPkcs11Info {

    @SerializedName("cryptoki_version")
    @Expose
    private String cryptokiVersion;
    @SerializedName("manufacturer_id")
    @Expose
    private String manufacturerId;
    @SerializedName("flags")
    @Expose
    private Long flags;
    @SerializedName("library_description")
    @Expose
    private String libraryDescription;
    @SerializedName("library_version")
    @Expose
    private String libraryVersion;

    /**
     * @return The cryptokiVersion
     */
    public String getCryptokiVersion() {
        return cryptokiVersion;
    }

    /**
     * @param cryptokiVersion The cryptoki_version
     */
    public void setCryptokiVersion(String cryptokiVersion) {
        this.cryptokiVersion = cryptokiVersion;
    }

    public GclPkcs11Info withCryptokiVersion(String cryptokiVersion) {
        this.cryptokiVersion = cryptokiVersion;
        return this;
    }

    /**
     * @return The manufacturerId
     */
    public String getManufacturerId() {
        return manufacturerId;
    }

    /**
     * @param manufacturerId The manufacturer_id
     */
    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public GclPkcs11Info withManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
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

    public GclPkcs11Info withFlags(Long flags) {
        this.flags = flags;
        return this;
    }

    /**
     * @return The libraryDescription
     */
    public String getLibraryDescription() {
        return libraryDescription;
    }

    /**
     * @param libraryDescription The library_description
     */
    public void setLibraryDescription(String libraryDescription) {
        this.libraryDescription = libraryDescription;
    }

    public GclPkcs11Info withLibraryDescription(String libraryDescription) {
        this.libraryDescription = libraryDescription;
        return this;
    }

    /**
     * @return The libraryVersion
     */
    public String getLibraryVersion() {
        return libraryVersion;
    }

    /**
     * @param libraryVersion The library_version
     */
    public void setLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public GclPkcs11Info withLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cryptokiVersion).append(manufacturerId).append(flags).append(libraryDescription).append(libraryVersion).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclPkcs11Info)) {
            return false;
        }
        GclPkcs11Info rhs = ((GclPkcs11Info) other);
        return new EqualsBuilder().append(cryptokiVersion, rhs.cryptokiVersion).append(manufacturerId, rhs.manufacturerId).append(flags, rhs.flags).append(libraryDescription, rhs.libraryDescription).append(libraryVersion, rhs.libraryVersion).isEquals();
    }

}
