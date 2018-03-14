package com.t1t.t1c.containers.smartcards.mobib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class GclMobibContract {

    @SerializedName("authenticator_kvc")
    @Expose
    private Integer authenticatorKvc;
    @SerializedName("authenticator_value")
    @Expose
    private Integer authenticatorValue;
    @SerializedName("journey_interchanges_allowed")
    @Expose
    private Boolean journeyInterchangesAllowed;
    @SerializedName("operator_map")
    @Expose
    private Integer operatorMap;
    @SerializedName("passengers_max")
    @Expose
    private Integer passengersMax;
    @SerializedName("price_amount")
    @Expose
    private Integer priceAmount;
    @SerializedName("provider")
    @Expose
    private Integer provider;
    @SerializedName("restrict_code")
    @Expose
    private Integer restrictCode;
    @SerializedName("restrict_time")
    @Expose
    private Integer restrictTime;
    @SerializedName("sale_date")
    @Expose
    private String saleDate;
    @SerializedName("sale_sam_count")
    @Expose
    private Integer saleSamCount;
    @SerializedName("sale_sam_id")
    @Expose
    private Integer saleSamId;
    @SerializedName("spatials")
    @Expose
    private List<GclMobibSpatial> spatials = new ArrayList<GclMobibSpatial>();
    @SerializedName("tariff")
    @Expose
    private GclMobibTariff tariff;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("validity_duration")
    @Expose
    private GclMobibValidityDuration validityDuration;
    @SerializedName("validity_start_date")
    @Expose
    private String validityStartDate;
    @SerializedName("vehicle_class_allowed")
    @Expose
    private Integer vehicleClassAllowed;
    @SerializedName("version")
    @Expose
    private Integer version;

    /**
     * @return The authenticatorKvc
     */
    public Integer getAuthenticatorKvc() {
        return authenticatorKvc;
    }

    /**
     * @param authenticatorKvc The authenticator_kvc
     */
    public void setAuthenticatorKvc(Integer authenticatorKvc) {
        this.authenticatorKvc = authenticatorKvc;
    }

    public GclMobibContract withAuthenticatorKvc(Integer authenticatorKvc) {
        this.authenticatorKvc = authenticatorKvc;
        return this;
    }

    /**
     * @return The authenticatorValue
     */
    public Integer getAuthenticatorValue() {
        return authenticatorValue;
    }

    /**
     * @param authenticatorValue The authenticator_value
     */
    public void setAuthenticatorValue(Integer authenticatorValue) {
        this.authenticatorValue = authenticatorValue;
    }

    public GclMobibContract withAuthenticatorValue(Integer authenticatorValue) {
        this.authenticatorValue = authenticatorValue;
        return this;
    }

    /**
     * @return The journeyInterchangesAllowed
     */
    public Boolean getJourneyInterchangesAllowed() {
        return journeyInterchangesAllowed;
    }

    /**
     * @param journeyInterchangesAllowed The journey_interchanges_allowed
     */
    public void setJourneyInterchangesAllowed(Boolean journeyInterchangesAllowed) {
        this.journeyInterchangesAllowed = journeyInterchangesAllowed;
    }

    public GclMobibContract withJourneyInterchangesAllowed(Boolean journeyInterchangesAllowed) {
        this.journeyInterchangesAllowed = journeyInterchangesAllowed;
        return this;
    }

    /**
     * @return The operatorMap
     */
    public Integer getOperatorMap() {
        return operatorMap;
    }

    /**
     * @param operatorMap The operator_map
     */
    public void setOperatorMap(Integer operatorMap) {
        this.operatorMap = operatorMap;
    }

    public GclMobibContract withOperatorMap(Integer operatorMap) {
        this.operatorMap = operatorMap;
        return this;
    }

    /**
     * @return The passengersMax
     */
    public Integer getPassengersMax() {
        return passengersMax;
    }

    /**
     * @param passengersMax The passengers_max
     */
    public void setPassengersMax(Integer passengersMax) {
        this.passengersMax = passengersMax;
    }

    public GclMobibContract withPassengersMax(Integer passengersMax) {
        this.passengersMax = passengersMax;
        return this;
    }

    /**
     * @return The priceAmount
     */
    public Integer getPriceAmount() {
        return priceAmount;
    }

    /**
     * @param priceAmount The price_amount
     */
    public void setPriceAmount(Integer priceAmount) {
        this.priceAmount = priceAmount;
    }

    public GclMobibContract withPriceAmount(Integer priceAmount) {
        this.priceAmount = priceAmount;
        return this;
    }

    /**
     * @return The provider
     */
    public Integer getProvider() {
        return provider;
    }

    /**
     * @param provider The provider
     */
    public void setProvider(Integer provider) {
        this.provider = provider;
    }

    public GclMobibContract withProvider(Integer provider) {
        this.provider = provider;
        return this;
    }

    /**
     * @return The restrictCode
     */
    public Integer getRestrictCode() {
        return restrictCode;
    }

    /**
     * @param restrictCode The restrict_code
     */
    public void setRestrictCode(Integer restrictCode) {
        this.restrictCode = restrictCode;
    }

    public GclMobibContract withRestrictCode(Integer restrictCode) {
        this.restrictCode = restrictCode;
        return this;
    }

    /**
     * @return The restrictTime
     */
    public Integer getRestrictTime() {
        return restrictTime;
    }

    /**
     * @param restrictTime The restrict_time
     */
    public void setRestrictTime(Integer restrictTime) {
        this.restrictTime = restrictTime;
    }

    public GclMobibContract withRestrictTime(Integer restrictTime) {
        this.restrictTime = restrictTime;
        return this;
    }

    /**
     * @return The saleDate
     */
    public String getSaleDate() {
        return saleDate;
    }

    /**
     * @param saleDate The sale_date
     */
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public GclMobibContract withSaleDate(String saleDate) {
        this.saleDate = saleDate;
        return this;
    }

    /**
     * @return The saleSamCount
     */
    public Integer getSaleSamCount() {
        return saleSamCount;
    }

    /**
     * @param saleSamCount The sale_sam_count
     */
    public void setSaleSamCount(Integer saleSamCount) {
        this.saleSamCount = saleSamCount;
    }

    public GclMobibContract withSaleSamCount(Integer saleSamCount) {
        this.saleSamCount = saleSamCount;
        return this;
    }

    /**
     * @return The saleSamId
     */
    public Integer getSaleSamId() {
        return saleSamId;
    }

    /**
     * @param saleSamId The sale_sam_id
     */
    public void setSaleSamId(Integer saleSamId) {
        this.saleSamId = saleSamId;
    }

    public GclMobibContract withSaleSamId(Integer saleSamId) {
        this.saleSamId = saleSamId;
        return this;
    }

    /**
     * @return The spatials
     */
    public List<GclMobibSpatial> getSpatials() {
        return spatials;
    }

    /**
     * @param spatials The spatials
     */
    public void setSpatials(List<GclMobibSpatial> spatials) {
        this.spatials = spatials;
    }

    public GclMobibContract withSpatials(List<GclMobibSpatial> spatials) {
        this.spatials = spatials;
        return this;
    }

    /**
     * @return The tariff
     */
    public GclMobibTariff getTariff() {
        return tariff;
    }

    /**
     * @param tariff The tariff
     */
    public void setTariff(GclMobibTariff tariff) {
        this.tariff = tariff;
    }

    public GclMobibContract withTariff(GclMobibTariff tariff) {
        this.tariff = tariff;
        return this;
    }

    /**
     * @return The typeId
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * @param typeId The type_id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public GclMobibContract withTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    /**
     * @return The validityDuration
     */
    public GclMobibValidityDuration getValidityDuration() {
        return validityDuration;
    }

    /**
     * @param validityDuration The validity_duration
     */
    public void setValidityDuration(GclMobibValidityDuration validityDuration) {
        this.validityDuration = validityDuration;
    }

    public GclMobibContract withValidityDuration(GclMobibValidityDuration validityDuration) {
        this.validityDuration = validityDuration;
        return this;
    }

    /**
     * @return The validityStartDate
     */
    public String getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * @param validityStartDate The validity_start_date
     */
    public void setValidityStartDate(String validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

    public GclMobibContract withValidityStartDate(String validityStartDate) {
        this.validityStartDate = validityStartDate;
        return this;
    }

    /**
     * @return The vehicleClassAllowed
     */
    public Integer getVehicleClassAllowed() {
        return vehicleClassAllowed;
    }

    /**
     * @param vehicleClassAllowed The vehicle_class_allowed
     */
    public void setVehicleClassAllowed(Integer vehicleClassAllowed) {
        this.vehicleClassAllowed = vehicleClassAllowed;
    }

    public GclMobibContract withVehicleClassAllowed(Integer vehicleClassAllowed) {
        this.vehicleClassAllowed = vehicleClassAllowed;
        return this;
    }

    /**
     * @return The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public GclMobibContract withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticatorKvc).append(authenticatorValue).append(journeyInterchangesAllowed).append(operatorMap).append(passengersMax).append(priceAmount).append(provider).append(restrictCode).append(restrictTime).append(saleDate).append(saleSamCount).append(saleSamId).append(spatials).append(tariff).append(typeId).append(validityDuration).append(validityStartDate).append(vehicleClassAllowed).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclMobibContract)) {
            return false;
        }
        GclMobibContract rhs = ((GclMobibContract) other);
        return new EqualsBuilder().append(authenticatorKvc, rhs.authenticatorKvc).append(authenticatorValue, rhs.authenticatorValue).append(journeyInterchangesAllowed, rhs.journeyInterchangesAllowed).append(operatorMap, rhs.operatorMap).append(passengersMax, rhs.passengersMax).append(priceAmount, rhs.priceAmount).append(provider, rhs.provider).append(restrictCode, rhs.restrictCode).append(restrictTime, rhs.restrictTime).append(saleDate, rhs.saleDate).append(saleSamCount, rhs.saleSamCount).append(saleSamId, rhs.saleSamId).append(spatials, rhs.spatials).append(tariff, rhs.tariff).append(typeId, rhs.typeId).append(validityDuration, rhs.validityDuration).append(validityStartDate, rhs.validityStartDate).append(vehicleClassAllowed, rhs.vehicleClassAllowed).append(version, rhs.version).isEquals();
    }

}
