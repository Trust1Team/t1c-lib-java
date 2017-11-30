
package com.t1t.t1c.containers.smartcards.mobib;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclMobibCardIssuing {

    @SerializedName("card_expiration_date")
    @Expose
    private String cardExpirationDate;
    @SerializedName("card_holder_birth_date")
    @Expose
    private String cardHolderBirthDate;
    @SerializedName("card_holder_end_date")
    @Expose
    private String cardHolderEndDate;
    @SerializedName("card_holder_id")
    @Expose
    private String cardHolderId;
    @SerializedName("card_holder_name")
    @Expose
    private String cardHolderName;
    @SerializedName("card_holder_start_date")
    @Expose
    private String cardHolderStartDate;
    @SerializedName("card_revalidation_date")
    @Expose
    private String cardRevalidationDate;
    @SerializedName("card_type")
    @Expose
    private Integer cardType;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("language")
    @Expose
    private Integer language;
    @SerializedName("version")
    @Expose
    private Integer version;

    /**
     * 
     * @return
     *     The cardExpirationDate
     */
    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    /**
     * 
     * @param cardExpirationDate
     *     The card_expiration_date
     */
    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public GclMobibCardIssuing withCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
        return this;
    }

    /**
     * 
     * @return
     *     The cardHolderBirthDate
     */
    public String getCardHolderBirthDate() {
        return cardHolderBirthDate;
    }

    /**
     * 
     * @param cardHolderBirthDate
     *     The card_holder_birth_date
     */
    public void setCardHolderBirthDate(String cardHolderBirthDate) {
        this.cardHolderBirthDate = cardHolderBirthDate;
    }

    public GclMobibCardIssuing withCardHolderBirthDate(String cardHolderBirthDate) {
        this.cardHolderBirthDate = cardHolderBirthDate;
        return this;
    }

    /**
     * 
     * @return
     *     The cardHolderEndDate
     */
    public String getCardHolderEndDate() {
        return cardHolderEndDate;
    }

    /**
     * 
     * @param cardHolderEndDate
     *     The card_holder_end_date
     */
    public void setCardHolderEndDate(String cardHolderEndDate) {
        this.cardHolderEndDate = cardHolderEndDate;
    }

    public GclMobibCardIssuing withCardHolderEndDate(String cardHolderEndDate) {
        this.cardHolderEndDate = cardHolderEndDate;
        return this;
    }

    /**
     * 
     * @return
     *     The cardHolderId
     */
    public String getCardHolderId() {
        return cardHolderId;
    }

    /**
     * 
     * @param cardHolderId
     *     The card_holder_id
     */
    public void setCardHolderId(String cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public GclMobibCardIssuing withCardHolderId(String cardHolderId) {
        this.cardHolderId = cardHolderId;
        return this;
    }

    /**
     * 
     * @return
     *     The cardHolderName
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * 
     * @param cardHolderName
     *     The card_holder_name
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public GclMobibCardIssuing withCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        return this;
    }

    /**
     * 
     * @return
     *     The cardHolderStartDate
     */
    public String getCardHolderStartDate() {
        return cardHolderStartDate;
    }

    /**
     * 
     * @param cardHolderStartDate
     *     The card_holder_start_date
     */
    public void setCardHolderStartDate(String cardHolderStartDate) {
        this.cardHolderStartDate = cardHolderStartDate;
    }

    public GclMobibCardIssuing withCardHolderStartDate(String cardHolderStartDate) {
        this.cardHolderStartDate = cardHolderStartDate;
        return this;
    }

    /**
     * 
     * @return
     *     The cardRevalidationDate
     */
    public String getCardRevalidationDate() {
        return cardRevalidationDate;
    }

    /**
     * 
     * @param cardRevalidationDate
     *     The card_revalidation_date
     */
    public void setCardRevalidationDate(String cardRevalidationDate) {
        this.cardRevalidationDate = cardRevalidationDate;
    }

    public GclMobibCardIssuing withCardRevalidationDate(String cardRevalidationDate) {
        this.cardRevalidationDate = cardRevalidationDate;
        return this;
    }

    /**
     * 
     * @return
     *     The cardType
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * 
     * @param cardType
     *     The card_type
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public GclMobibCardIssuing withCardType(Integer cardType) {
        this.cardType = cardType;
        return this;
    }

    /**
     * 
     * @return
     *     The companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 
     * @param companyId
     *     The company_id
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public GclMobibCardIssuing withCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public GclMobibCardIssuing withGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    /**
     * 
     * @return
     *     The language
     */
    public Integer getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    public void setLanguage(Integer language) {
        this.language = language;
    }

    public GclMobibCardIssuing withLanguage(Integer language) {
        this.language = language;
        return this;
    }

    /**
     * 
     * @return
     *     The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public GclMobibCardIssuing withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cardExpirationDate).append(cardHolderBirthDate).append(cardHolderEndDate).append(cardHolderId).append(cardHolderName).append(cardHolderStartDate).append(cardRevalidationDate).append(cardType).append(companyId).append(gender).append(language).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclMobibCardIssuing) == false) {
            return false;
        }
        GclMobibCardIssuing rhs = ((GclMobibCardIssuing) other);
        return new EqualsBuilder().append(cardExpirationDate, rhs.cardExpirationDate).append(cardHolderBirthDate, rhs.cardHolderBirthDate).append(cardHolderEndDate, rhs.cardHolderEndDate).append(cardHolderId, rhs.cardHolderId).append(cardHolderName, rhs.cardHolderName).append(cardHolderStartDate, rhs.cardHolderStartDate).append(cardRevalidationDate, rhs.cardRevalidationDate).append(cardType, rhs.cardType).append(companyId, rhs.companyId).append(gender, rhs.gender).append(language, rhs.language).append(version, rhs.version).isEquals();
    }

}
