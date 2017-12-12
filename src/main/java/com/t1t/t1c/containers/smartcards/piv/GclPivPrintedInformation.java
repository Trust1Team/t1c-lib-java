
package com.t1t.t1c.containers.smartcards.piv;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclPivPrintedInformation {

    @SerializedName("agency_card_serial_number")
    @Expose
    private String agencyCardSerialNumber;
    @SerializedName("employee_affiliation")
    @Expose
    private String employeeAffiliation;
    @SerializedName("expiration_date")
    @Expose
    private String expirationDate;
    @SerializedName("issuer_identification")
    @Expose
    private String issuerIdentification;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("organization_affiliation_line_1")
    @Expose
    private String organizationAffiliationLine1;
    @SerializedName("organization_affiliation_line_2")
    @Expose
    private String organizationAffiliationLine2;

    /**
     * 
     * @return
     *     The agencyCardSerialNumber
     */
    public String getAgencyCardSerialNumber() {
        return agencyCardSerialNumber;
    }

    /**
     * 
     * @param agencyCardSerialNumber
     *     The agency_card_serial_number
     */
    public void setAgencyCardSerialNumber(String agencyCardSerialNumber) {
        this.agencyCardSerialNumber = agencyCardSerialNumber;
    }

    public GclPivPrintedInformation withAgencyCardSerialNumber(String agencyCardSerialNumber) {
        this.agencyCardSerialNumber = agencyCardSerialNumber;
        return this;
    }

    /**
     * 
     * @return
     *     The employeeAffiliation
     */
    public String getEmployeeAffiliation() {
        return employeeAffiliation;
    }

    /**
     * 
     * @param employeeAffiliation
     *     The employee_affiliation
     */
    public void setEmployeeAffiliation(String employeeAffiliation) {
        this.employeeAffiliation = employeeAffiliation;
    }

    public GclPivPrintedInformation withEmployeeAffiliation(String employeeAffiliation) {
        this.employeeAffiliation = employeeAffiliation;
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

    public GclPivPrintedInformation withExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    /**
     * 
     * @return
     *     The issuerIdentification
     */
    public String getIssuerIdentification() {
        return issuerIdentification;
    }

    /**
     * 
     * @param issuerIdentification
     *     The issuer_identification
     */
    public void setIssuerIdentification(String issuerIdentification) {
        this.issuerIdentification = issuerIdentification;
    }

    public GclPivPrintedInformation withIssuerIdentification(String issuerIdentification) {
        this.issuerIdentification = issuerIdentification;
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

    public GclPivPrintedInformation withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The organizationAffiliationLine1
     */
    public String getOrganizationAffiliationLine1() {
        return organizationAffiliationLine1;
    }

    /**
     * 
     * @param organizationAffiliationLine1
     *     The organization_affiliation_line_1
     */
    public void setOrganizationAffiliationLine1(String organizationAffiliationLine1) {
        this.organizationAffiliationLine1 = organizationAffiliationLine1;
    }

    public GclPivPrintedInformation withOrganizationAffiliationLine1(String organizationAffiliationLine1) {
        this.organizationAffiliationLine1 = organizationAffiliationLine1;
        return this;
    }

    /**
     * 
     * @return
     *     The organizationAffiliationLine2
     */
    public String getOrganizationAffiliationLine2() {
        return organizationAffiliationLine2;
    }

    /**
     * 
     * @param organizationAffiliationLine2
     *     The organization_affiliation_line_2
     */
    public void setOrganizationAffiliationLine2(String organizationAffiliationLine2) {
        this.organizationAffiliationLine2 = organizationAffiliationLine2;
    }

    public GclPivPrintedInformation withOrganizationAffiliationLine2(String organizationAffiliationLine2) {
        this.organizationAffiliationLine2 = organizationAffiliationLine2;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(agencyCardSerialNumber).append(employeeAffiliation).append(expirationDate).append(issuerIdentification).append(name).append(organizationAffiliationLine1).append(organizationAffiliationLine2).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclPivPrintedInformation) == false) {
            return false;
        }
        GclPivPrintedInformation rhs = ((GclPivPrintedInformation) other);
        return new EqualsBuilder().append(agencyCardSerialNumber, rhs.agencyCardSerialNumber).append(employeeAffiliation, rhs.employeeAffiliation).append(expirationDate, rhs.expirationDate).append(issuerIdentification, rhs.issuerIdentification).append(name, rhs.name).append(organizationAffiliationLine1, rhs.organizationAffiliationLine1).append(organizationAffiliationLine2, rhs.organizationAffiliationLine2).isEquals();
    }

}
