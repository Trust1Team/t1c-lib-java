package com.t1t.t1c.containers.smartcards.eid.dni;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclDnieInfo {

    @SerializedName("firstLastName")
    @Expose
    private String firstLastName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("idesp")
    @Expose
    private String idesp;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("secondLastName")
    @Expose
    private String secondLastName;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;

    /**
     * @return The firstLastName
     */
    public String getFirstLastName() {
        return firstLastName;
    }

    /**
     * @param firstLastName The firstLastName
     */
    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public GclDnieInfo withFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
        return this;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public GclDnieInfo withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return The idesp
     */
    public String getIdesp() {
        return idesp;
    }

    /**
     * @param idesp The idesp
     */
    public void setIdesp(String idesp) {
        this.idesp = idesp;
    }

    public GclDnieInfo withIdesp(String idesp) {
        this.idesp = idesp;
        return this;
    }

    /**
     * @return The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public GclDnieInfo withNumber(String number) {
        this.number = number;
        return this;
    }

    /**
     * @return The secondLastName
     */
    public String getSecondLastName() {
        return secondLastName;
    }

    /**
     * @param secondLastName The secondLastName
     */
    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public GclDnieInfo withSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
        return this;
    }

    /**
     * @return The serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber The serialNumber
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public GclDnieInfo withSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(firstLastName).append(firstName).append(idesp).append(number).append(secondLastName).append(serialNumber).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclDnieInfo)) {
            return false;
        }
        GclDnieInfo rhs = ((GclDnieInfo) other);
        return new EqualsBuilder().append(firstLastName, rhs.firstLastName).append(firstName, rhs.firstName).append(idesp, rhs.idesp).append(number, rhs.number).append(secondLastName, rhs.secondLastName).append(serialNumber, rhs.serialNumber).isEquals();
    }

}
