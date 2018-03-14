package com.t1t.t1c.containers.smartcards.eid.pt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclPtIdAddress {

    @SerializedName("abbr_building_type")
    @Expose
    private String abbrBuildingType;
    @SerializedName("abbr_street_type")
    @Expose
    private String abbrStreetType;
    @SerializedName("building_type")
    @Expose
    private String buildingType;
    @SerializedName("civil_parish")
    @Expose
    private String civilParish;
    @SerializedName("civil_parish_description")
    @Expose
    private String civilParishDescription;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("district_description")
    @Expose
    private String districtDescription;
    @SerializedName("door_no")
    @Expose
    private String doorNo;
    @SerializedName("floor")
    @Expose
    private String floor;
    @SerializedName("gen_address_num")
    @Expose
    private String genAddressNum;
    @SerializedName("is_national")
    @Expose
    private Boolean isNational;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("municipality")
    @Expose
    private String municipality;
    @SerializedName("municipality_description")
    @Expose
    private String municipalityDescription;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("postal_locality")
    @Expose
    private String postalLocality;
    @SerializedName("raw_data")
    @Expose
    private String rawData;
    @SerializedName("side")
    @Expose
    private String side;
    @SerializedName("street_name")
    @Expose
    private String streetName;
    @SerializedName("street_type")
    @Expose
    private String streetType;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("zip3")
    @Expose
    private String zip3;
    @SerializedName("zip4")
    @Expose
    private String zip4;

    /**
     * @return The abbrBuildingType
     */
    public String getAbbrBuildingType() {
        return abbrBuildingType;
    }

    /**
     * @param abbrBuildingType The abbr_building_type
     */
    public void setAbbrBuildingType(String abbrBuildingType) {
        this.abbrBuildingType = abbrBuildingType;
    }

    public GclPtIdAddress withAbbrBuildingType(String abbrBuildingType) {
        this.abbrBuildingType = abbrBuildingType;
        return this;
    }

    /**
     * @return The abbrStreetType
     */
    public String getAbbrStreetType() {
        return abbrStreetType;
    }

    /**
     * @param abbrStreetType The abbr_street_type
     */
    public void setAbbrStreetType(String abbrStreetType) {
        this.abbrStreetType = abbrStreetType;
    }

    public GclPtIdAddress withAbbrStreetType(String abbrStreetType) {
        this.abbrStreetType = abbrStreetType;
        return this;
    }

    /**
     * @return The buildingType
     */
    public String getBuildingType() {
        return buildingType;
    }

    /**
     * @param buildingType The building_type
     */
    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public GclPtIdAddress withBuildingType(String buildingType) {
        this.buildingType = buildingType;
        return this;
    }

    /**
     * @return The civilParish
     */
    public String getCivilParish() {
        return civilParish;
    }

    /**
     * @param civilParish The civil_parish
     */
    public void setCivilParish(String civilParish) {
        this.civilParish = civilParish;
    }

    public GclPtIdAddress withCivilParish(String civilParish) {
        this.civilParish = civilParish;
        return this;
    }

    /**
     * @return The civilParishDescription
     */
    public String getCivilParishDescription() {
        return civilParishDescription;
    }

    /**
     * @param civilParishDescription The civil_parish_description
     */
    public void setCivilParishDescription(String civilParishDescription) {
        this.civilParishDescription = civilParishDescription;
    }

    public GclPtIdAddress withCivilParishDescription(String civilParishDescription) {
        this.civilParishDescription = civilParishDescription;
        return this;
    }

    /**
     * @return The district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district The district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    public GclPtIdAddress withDistrict(String district) {
        this.district = district;
        return this;
    }

    /**
     * @return The districtDescription
     */
    public String getDistrictDescription() {
        return districtDescription;
    }

    /**
     * @param districtDescription The district_description
     */
    public void setDistrictDescription(String districtDescription) {
        this.districtDescription = districtDescription;
    }

    public GclPtIdAddress withDistrictDescription(String districtDescription) {
        this.districtDescription = districtDescription;
        return this;
    }

    /**
     * @return The doorNo
     */
    public String getDoorNo() {
        return doorNo;
    }

    /**
     * @param doorNo The door_no
     */
    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public GclPtIdAddress withDoorNo(String doorNo) {
        this.doorNo = doorNo;
        return this;
    }

    /**
     * @return The floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * @param floor The floor
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    public GclPtIdAddress withFloor(String floor) {
        this.floor = floor;
        return this;
    }

    /**
     * @return The genAddressNum
     */
    public String getGenAddressNum() {
        return genAddressNum;
    }

    /**
     * @param genAddressNum The gen_address_num
     */
    public void setGenAddressNum(String genAddressNum) {
        this.genAddressNum = genAddressNum;
    }

    public GclPtIdAddress withGenAddressNum(String genAddressNum) {
        this.genAddressNum = genAddressNum;
        return this;
    }

    /**
     * @return The isNational
     */
    public Boolean getIsNational() {
        return isNational;
    }

    /**
     * @param isNational The is_national
     */
    public void setIsNational(Boolean isNational) {
        this.isNational = isNational;
    }

    public GclPtIdAddress withIsNational(Boolean isNational) {
        this.isNational = isNational;
        return this;
    }

    /**
     * @return The locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * @param locality The locality
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    public GclPtIdAddress withLocality(String locality) {
        this.locality = locality;
        return this;
    }

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

    public GclPtIdAddress withMunicipality(String municipality) {
        this.municipality = municipality;
        return this;
    }

    /**
     * @return The municipalityDescription
     */
    public String getMunicipalityDescription() {
        return municipalityDescription;
    }

    /**
     * @param municipalityDescription The municipality_description
     */
    public void setMunicipalityDescription(String municipalityDescription) {
        this.municipalityDescription = municipalityDescription;
    }

    public GclPtIdAddress withMunicipalityDescription(String municipalityDescription) {
        this.municipalityDescription = municipalityDescription;
        return this;
    }

    /**
     * @return The place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place The place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    public GclPtIdAddress withPlace(String place) {
        this.place = place;
        return this;
    }

    /**
     * @return The postalLocality
     */
    public String getPostalLocality() {
        return postalLocality;
    }

    /**
     * @param postalLocality The postal_locality
     */
    public void setPostalLocality(String postalLocality) {
        this.postalLocality = postalLocality;
    }

    public GclPtIdAddress withPostalLocality(String postalLocality) {
        this.postalLocality = postalLocality;
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

    public GclPtIdAddress withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    /**
     * @return The side
     */
    public String getSide() {
        return side;
    }

    /**
     * @param side The side
     */
    public void setSide(String side) {
        this.side = side;
    }

    public GclPtIdAddress withSide(String side) {
        this.side = side;
        return this;
    }

    /**
     * @return The streetName
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetName The street_name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public GclPtIdAddress withStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    /**
     * @return The streetType
     */
    public String getStreetType() {
        return streetType;
    }

    /**
     * @param streetType The street_type
     */
    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public GclPtIdAddress withStreetType(String streetType) {
        this.streetType = streetType;
        return this;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public GclPtIdAddress withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The zip3
     */
    public String getZip3() {
        return zip3;
    }

    /**
     * @param zip3 The zip3
     */
    public void setZip3(String zip3) {
        this.zip3 = zip3;
    }

    public GclPtIdAddress withZip3(String zip3) {
        this.zip3 = zip3;
        return this;
    }

    /**
     * @return The zip4
     */
    public String getZip4() {
        return zip4;
    }

    /**
     * @param zip4 The zip4
     */
    public void setZip4(String zip4) {
        this.zip4 = zip4;
    }

    public GclPtIdAddress withZip4(String zip4) {
        this.zip4 = zip4;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(abbrBuildingType).append(abbrStreetType).append(buildingType).append(civilParish).append(civilParishDescription).append(district).append(districtDescription).append(doorNo).append(floor).append(genAddressNum).append(isNational).append(locality).append(municipality).append(municipalityDescription).append(place).append(postalLocality).append(rawData).append(side).append(streetName).append(streetType).append(type).append(zip3).append(zip4).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclPtIdAddress)) {
            return false;
        }
        GclPtIdAddress rhs = ((GclPtIdAddress) other);
        return new EqualsBuilder().append(abbrBuildingType, rhs.abbrBuildingType).append(abbrStreetType, rhs.abbrStreetType).append(buildingType, rhs.buildingType).append(civilParish, rhs.civilParish).append(civilParishDescription, rhs.civilParishDescription).append(district, rhs.district).append(districtDescription, rhs.districtDescription).append(doorNo, rhs.doorNo).append(floor, rhs.floor).append(genAddressNum, rhs.genAddressNum).append(isNational, rhs.isNational).append(locality, rhs.locality).append(municipality, rhs.municipality).append(municipalityDescription, rhs.municipalityDescription).append(place, rhs.place).append(postalLocality, rhs.postalLocality).append(rawData, rhs.rawData).append(side, rhs.side).append(streetName, rhs.streetName).append(streetType, rhs.streetType).append(type, rhs.type).append(zip3, rhs.zip3).append(zip4, rhs.zip4).isEquals();
    }

}
