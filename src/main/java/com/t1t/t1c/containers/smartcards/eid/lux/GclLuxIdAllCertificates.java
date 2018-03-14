package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllCertificates;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class GclLuxIdAllCertificates implements AllCertificates {

    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("non_repudiation_certificate")
    @Expose
    private String nonRepudiationCertificate;
    @SerializedName("root_certificates")
    @Expose
    private List<String> rootCertificates = new ArrayList<String>();

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

    public GclLuxIdAllCertificates withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
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

    public GclLuxIdAllCertificates withNonRepudiationCertificate(String nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * @return The rootCertificates
     */
    public List<String> getRootCertificates() {
        return rootCertificates;
    }

    /**
     * @param rootCertificates The root_certificates
     */
    public void setRootCertificates(List<String> rootCertificates) {
        this.rootCertificates = rootCertificates;
    }

    public GclLuxIdAllCertificates withRootCertificates(List<String> rootCertificates) {
        this.rootCertificates = rootCertificates;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationCertificate).append(nonRepudiationCertificate).append(rootCertificates).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclLuxIdAllCertificates)) {
            return false;
        }
        GclLuxIdAllCertificates rhs = ((GclLuxIdAllCertificates) other);
        return new EqualsBuilder().append(authenticationCertificate, rhs.authenticationCertificate).append(nonRepudiationCertificate, rhs.nonRepudiationCertificate).append(rootCertificates, rhs.rootCertificates).isEquals();
    }

}
