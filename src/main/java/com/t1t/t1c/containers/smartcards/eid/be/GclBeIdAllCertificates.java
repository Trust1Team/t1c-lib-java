package com.t1t.t1c.containers.smartcards.eid.be;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllCertificates;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclBeIdAllCertificates implements AllCertificates {

    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("citizen_certificate")
    @Expose
    private String citizenCertificate;
    @SerializedName("non_repudiation_certificate")
    @Expose
    private String nonRepudiationCertificate;
    @SerializedName("root_certificate")
    @Expose
    private String rootCertificate;
    @SerializedName("rrn_certificate")
    @Expose
    private String rrnCertificate;

    /**
     * @return The authenticationCertificate
     */
    public String getAuthenticationCertificate() {
        return authenticationCertificate;
    }

    /**
     * @param authenticationCertificate The authentication_certificate
     */
    public void setAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public GclBeIdAllCertificates withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The citizenCertificate
     */
    public String getCitizenCertificate() {
        return citizenCertificate;
    }

    /**
     * @param citizenCertificate The citizen_certificate
     */
    public void setCitizenCertificate(String citizenCertificate) {
        this.citizenCertificate = citizenCertificate;
    }

    public GclBeIdAllCertificates withCitizenCertificate(String citizenCertificate) {
        this.citizenCertificate = citizenCertificate;
        return this;
    }

    /**
     * @return The nonRepudiationCertificate
     */
    public String getNonRepudiationCertificate() {
        return nonRepudiationCertificate;
    }

    /**
     * @param nonRepudiationCertificate The non_repudiation_certificate
     */
    public void setNonRepudiationCertificate(String nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
    }

    public GclBeIdAllCertificates withNonRepudiationCertificate(String nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * @return The rootCertificate
     */
    public String getRootCertificate() {
        return rootCertificate;
    }

    /**
     * @param rootCertificate The root_certificate
     */
    public void setRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
    }

    public GclBeIdAllCertificates withRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
        return this;
    }

    /**
     * @return The rrnCertificate
     */
    public String getRrnCertificate() {
        return rrnCertificate;
    }

    /**
     * @param rrnCertificate The rrn_certificate
     */
    public void setRrnCertificate(String rrnCertificate) {
        this.rrnCertificate = rrnCertificate;
    }

    public GclBeIdAllCertificates withRrnCertificate(String rrnCertificate) {
        this.rrnCertificate = rrnCertificate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationCertificate).append(citizenCertificate).append(nonRepudiationCertificate).append(rootCertificate).append(rrnCertificate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclBeIdAllCertificates) == false) {
            return false;
        }
        GclBeIdAllCertificates rhs = ((GclBeIdAllCertificates) other);
        return new EqualsBuilder().append(authenticationCertificate, rhs.authenticationCertificate).append(citizenCertificate, rhs.citizenCertificate).append(nonRepudiationCertificate, rhs.nonRepudiationCertificate).append(rootCertificate, rhs.rootCertificate).append(rrnCertificate, rhs.rrnCertificate).isEquals();
    }

}
