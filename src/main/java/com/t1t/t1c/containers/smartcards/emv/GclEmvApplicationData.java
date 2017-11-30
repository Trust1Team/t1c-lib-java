
package com.t1t.t1c.containers.smartcards.emv;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclEmvApplicationData {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("effective_date")
    @Expose
    private String effectiveDate;
    @SerializedName("expiration_date")
    @Expose
    private String expirationDate;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pan")
    @Expose
    private String pan;

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public GclEmvApplicationData withCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * 
     * @return
     *     The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 
     * @param countryCode
     *     The country_code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public GclEmvApplicationData withCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    /**
     * 
     * @return
     *     The effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 
     * @param effectiveDate
     *     The effective_date
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public GclEmvApplicationData withEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    /**
     * 
     * @return
     *     The expirationDate
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * 
     * @param expirationDate
     *     The expiration_date
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public GclEmvApplicationData withExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    /**
     * 
     * @return
     *     The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    public GclEmvApplicationData withLanguage(String language) {
        this.language = language;
        return this;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public GclEmvApplicationData withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The pan
     */
    public String getPan() {
        return pan;
    }

    /**
     * 
     * @param pan
     *     The pan
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    public GclEmvApplicationData withPan(String pan) {
        this.pan = pan;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(country).append(countryCode).append(effectiveDate).append(expirationDate).append(language).append(name).append(pan).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclEmvApplicationData) == false) {
            return false;
        }
        GclEmvApplicationData rhs = ((GclEmvApplicationData) other);
        return new EqualsBuilder().append(country, rhs.country).append(countryCode, rhs.countryCode).append(effectiveDate, rhs.effectiveDate).append(expirationDate, rhs.expirationDate).append(language, rhs.language).append(name, rhs.name).append(pan, rhs.pan).isEquals();
    }

}
