
package com.t1t.t1c.containers.smartcards.eid.lux;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclLuxIdBiometric {

    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("documentNumber")
    @Expose
    private String documentNumber;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("issuingState")
    @Expose
    private String issuingState;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("validityEndDate")
    @Expose
    private String validityEndDate;
    @SerializedName("validityStartDate")
    @Expose
    private String validityStartDate;
    @SerializedName("raw_data")
    @Expose
    private String rawData;

    /**
     * 
     * @return
     *     The birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * 
     * @param birthDate
     *     The birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public GclLuxIdBiometric withBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    /**
     * 
     * @return
     *     The documentNumber
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * 
     * @param documentNumber
     *     The documentNumber
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public GclLuxIdBiometric withDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    /**
     * 
     * @return
     *     The documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * 
     * @param documentType
     *     The documentType
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public GclLuxIdBiometric withDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public GclLuxIdBiometric withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    public GclLuxIdBiometric withGender(String gender) {
        this.gender = gender;
        return this;
    }

    /**
     * 
     * @return
     *     The issuingState
     */
    public String getIssuingState() {
        return issuingState;
    }

    /**
     * 
     * @param issuingState
     *     The issuingState
     */
    public void setIssuingState(String issuingState) {
        this.issuingState = issuingState;
    }

    public GclLuxIdBiometric withIssuingState(String issuingState) {
        this.issuingState = issuingState;
        return this;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GclLuxIdBiometric withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * 
     * @return
     *     The nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * 
     * @param nationality
     *     The nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public GclLuxIdBiometric withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    /**
     * 
     * @return
     *     The validityEndDate
     */
    public String getValidityEndDate() {
        return validityEndDate;
    }

    /**
     * 
     * @param validityEndDate
     *     The validityEndDate
     */
    public void setValidityEndDate(String validityEndDate) {
        this.validityEndDate = validityEndDate;
    }

    public GclLuxIdBiometric withValidityEndDate(String validityEndDate) {
        this.validityEndDate = validityEndDate;
        return this;
    }

    /**
     * 
     * @return
     *     The validityStartDate
     */
    public String getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * 
     * @param validityStartDate
     *     The validityStartDate
     */
    public void setValidityStartDate(String validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

    public GclLuxIdBiometric withValidityStartDate(String validityStartDate) {
        this.validityStartDate = validityStartDate;
        return this;
    }

    /**
     * 
     * @return
     *     The rawData
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * 
     * @param rawData
     *     The raw_data
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public GclLuxIdBiometric withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(birthDate).append(documentNumber).append(documentType).append(firstName).append(gender).append(issuingState).append(lastName).append(nationality).append(validityEndDate).append(validityStartDate).append(rawData).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclLuxIdBiometric) == false) {
            return false;
        }
        GclLuxIdBiometric rhs = ((GclLuxIdBiometric) other);
        return new EqualsBuilder().append(birthDate, rhs.birthDate).append(documentNumber, rhs.documentNumber).append(documentType, rhs.documentType).append(firstName, rhs.firstName).append(gender, rhs.gender).append(issuingState, rhs.issuingState).append(lastName, rhs.lastName).append(nationality, rhs.nationality).append(validityEndDate, rhs.validityEndDate).append(validityStartDate, rhs.validityStartDate).append(rawData, rhs.rawData).isEquals();
    }

}
