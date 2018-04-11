package com.t1t.t1c.containers.smartcards.eid.be;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclBeIdAddress {

    @SerializedName("municipality")
    @Expose
    private String municipality;
    @SerializedName("raw_data")
    @Expose
    private String rawData;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("street_and_number")
    @Expose
    private String streetAndNumber;
    @SerializedName("version")
    @Expose
    private Long version;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    /**
     * @return The municipality
     */
    public String getMunicipality() {
        return municipality;
    }

    /**
     * @param municipality The municipality
     */
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public GclBeIdAddress withMunicipality(String municipality) {
        this.municipality = municipality;
        return this;
    }

    /**
     * @return The rawData
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * @param rawData The raw_data
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public GclBeIdAddress withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    /**
     * @return The signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature The signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public GclBeIdAddress withSignature(String signature) {
        this.signature = signature;
        return this;
    }

    /**
     * @return The streetAndNumber
     */
    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    /**
     * @param streetAndNumber The street_and_number
     */
    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public GclBeIdAddress withStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
        return this;
    }

    /**
     * @return The version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public GclBeIdAddress withVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * @return The zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode The zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public GclBeIdAddress withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(municipality).append(rawData).append(signature).append(streetAndNumber).append(version).append(zipcode).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclBeIdAddress)) {
            return false;
        }
        GclBeIdAddress rhs = ((GclBeIdAddress) other);
        return new EqualsBuilder().append(municipality, rhs.municipality).append(rawData, rhs.rawData).append(signature, rhs.signature).append(streetAndNumber, rhs.streetAndNumber).append(version, rhs.version).append(zipcode, rhs.zipcode).isEquals();
    }

}
