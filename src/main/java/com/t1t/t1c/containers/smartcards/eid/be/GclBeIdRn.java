package com.t1t.t1c.containers.smartcards.eid.be;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclBeIdRn {

    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("birth_location")
    @Expose
    private String birthLocation;
    @SerializedName("card_delivery_municipality")
    @Expose
    private String cardDeliveryMunicipality;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("card_validity_date_begin")
    @Expose
    private String cardValidityDateBegin;
    @SerializedName("card_validity_date_end")
    @Expose
    private String cardValidityDateEnd;
    @SerializedName("chip_number")
    @Expose
    private String chipNumber;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("first_names")
    @Expose
    private String firstNames;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("national_number")
    @Expose
    private String nationalNumber;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("noble_condition")
    @Expose
    private String nobleCondition;
    @SerializedName("picture_hash")
    @Expose
    private String pictureHash;
    @SerializedName("raw_data")
    @Expose
    private String rawData;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("special_status")
    @Expose
    private String specialStatus;
    @SerializedName("third_name")
    @Expose
    private String thirdName;
    @SerializedName("version")
    @Expose
    private Long version;

    /**
     * @return The birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate The birth_date
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public GclBeIdRn withBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    /**
     * @return The birthLocation
     */
    public String getBirthLocation() {
        return birthLocation;
    }

    /**
     * @param birthLocation The birth_location
     */
    public void setBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
    }

    public GclBeIdRn withBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
        return this;
    }

    /**
     * @return The cardDeliveryMunicipality
     */
    public String getCardDeliveryMunicipality() {
        return cardDeliveryMunicipality;
    }

    /**
     * @param cardDeliveryMunicipality The card_delivery_municipality
     */
    public void setCardDeliveryMunicipality(String cardDeliveryMunicipality) {
        this.cardDeliveryMunicipality = cardDeliveryMunicipality;
    }

    public GclBeIdRn withCardDeliveryMunicipality(String cardDeliveryMunicipality) {
        this.cardDeliveryMunicipality = cardDeliveryMunicipality;
        return this;
    }

    /**
     * @return The cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber The card_number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public GclBeIdRn withCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    /**
     * @return The cardValidityDateBegin
     */
    public String getCardValidityDateBegin() {
        return cardValidityDateBegin;
    }

    /**
     * @param cardValidityDateBegin The card_validity_date_begin
     */
    public void setCardValidityDateBegin(String cardValidityDateBegin) {
        this.cardValidityDateBegin = cardValidityDateBegin;
    }

    public GclBeIdRn withCardValidityDateBegin(String cardValidityDateBegin) {
        this.cardValidityDateBegin = cardValidityDateBegin;
        return this;
    }

    /**
     * @return The cardValidityDateEnd
     */
    public String getCardValidityDateEnd() {
        return cardValidityDateEnd;
    }

    /**
     * @param cardValidityDateEnd The card_validity_date_end
     */
    public void setCardValidityDateEnd(String cardValidityDateEnd) {
        this.cardValidityDateEnd = cardValidityDateEnd;
    }

    public GclBeIdRn withCardValidityDateEnd(String cardValidityDateEnd) {
        this.cardValidityDateEnd = cardValidityDateEnd;
        return this;
    }

    /**
     * @return The chipNumber
     */
    public String getChipNumber() {
        return chipNumber;
    }

    /**
     * @param chipNumber The chip_number
     */
    public void setChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
    }

    public GclBeIdRn withChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
        return this;
    }

    /**
     * @return The documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType The document_type
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public GclBeIdRn withDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    /**
     * @return The firstNames
     */
    public String getFirstNames() {
        return firstNames;
    }

    /**
     * @param firstNames The first_names
     */
    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    public GclBeIdRn withFirstNames(String firstNames) {
        this.firstNames = firstNames;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public GclBeIdRn withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The nationalNumber
     */
    public String getNationalNumber() {
        return nationalNumber;
    }

    /**
     * @param nationalNumber The national_number
     */
    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public GclBeIdRn withNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
        return this;
    }

    /**
     * @return The nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality The nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public GclBeIdRn withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    /**
     * @return The nobleCondition
     */
    public String getNobleCondition() {
        return nobleCondition;
    }

    /**
     * @param nobleCondition The noble_condition
     */
    public void setNobleCondition(String nobleCondition) {
        this.nobleCondition = nobleCondition;
    }

    public GclBeIdRn withNobleCondition(String nobleCondition) {
        this.nobleCondition = nobleCondition;
        return this;
    }

    /**
     * @return The pictureHash
     */
    public String getPictureHash() {
        return pictureHash;
    }

    /**
     * @param pictureHash The picture_hash
     */
    public void setPictureHash(String pictureHash) {
        this.pictureHash = pictureHash;
    }

    public GclBeIdRn withPictureHash(String pictureHash) {
        this.pictureHash = pictureHash;
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

    public GclBeIdRn withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    /**
     * @return The sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex The sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    public GclBeIdRn withSex(String sex) {
        this.sex = sex;
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

    public GclBeIdRn withSignature(String signature) {
        this.signature = signature;
        return this;
    }

    /**
     * @return The specialStatus
     */
    public String getSpecialStatus() {
        return specialStatus;
    }

    /**
     * @param specialStatus The special_status
     */
    public void setSpecialStatus(String specialStatus) {
        this.specialStatus = specialStatus;
    }

    public GclBeIdRn withSpecialStatus(String specialStatus) {
        this.specialStatus = specialStatus;
        return this;
    }

    /**
     * @return The thirdName
     */
    public String getThirdName() {
        return thirdName;
    }

    /**
     * @param thirdName The third_name
     */
    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public GclBeIdRn withThirdName(String thirdName) {
        this.thirdName = thirdName;
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

    public GclBeIdRn withVersion(Long version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(birthDate).append(birthLocation).append(cardDeliveryMunicipality).append(cardNumber).append(cardValidityDateBegin).append(cardValidityDateEnd).append(chipNumber).append(documentType).append(firstNames).append(name).append(nationalNumber).append(nationality).append(nobleCondition).append(pictureHash).append(rawData).append(sex).append(signature).append(specialStatus).append(thirdName).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclBeIdRn)) {
            return false;
        }
        GclBeIdRn rhs = ((GclBeIdRn) other);
        return new EqualsBuilder().append(birthDate, rhs.birthDate).append(birthLocation, rhs.birthLocation).append(cardDeliveryMunicipality, rhs.cardDeliveryMunicipality).append(cardNumber, rhs.cardNumber).append(cardValidityDateBegin, rhs.cardValidityDateBegin).append(cardValidityDateEnd, rhs.cardValidityDateEnd).append(chipNumber, rhs.chipNumber).append(documentType, rhs.documentType).append(firstNames, rhs.firstNames).append(name, rhs.name).append(nationalNumber, rhs.nationalNumber).append(nationality, rhs.nationality).append(nobleCondition, rhs.nobleCondition).append(pictureHash, rhs.pictureHash).append(rawData, rhs.rawData).append(sex, rhs.sex).append(signature, rhs.signature).append(specialStatus, rhs.specialStatus).append(thirdName, rhs.thirdName).append(version, rhs.version).isEquals();
    }

}
