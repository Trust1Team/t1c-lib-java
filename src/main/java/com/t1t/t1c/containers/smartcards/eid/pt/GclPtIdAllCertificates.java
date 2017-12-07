package com.t1t.t1c.containers.smartcards.eid.pt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllCertificates;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclPtIdAllCertificates implements AllCertificates {

    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("non_repudiation_certificate")
    @Expose
    private String nonRepudiationCertificate;
    @SerializedName("root_authentication_certificate")
    @Expose
    private String rootAuthenticationCertificate;
    @SerializedName("root_certificate")
    @Expose
    private String rootCertificate;
    @SerializedName("root_non_repudiation_certificate")
    @Expose
    private String rootNonRepudiationCertificate;

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

    public GclPtIdAllCertificates withAuthenticationCertificate(String authenticationCertificate) {
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

    public GclPtIdAllCertificates withNonRepudiationCertificate(String nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * @return The rootAuthenticationCertificate
     */
    public String getRootAuthenticationCertificate() {
        return rootAuthenticationCertificate;
    }

    /**
     * @param rootAuthenticationCertificate The root_authentication_certificate
     */
    public void setRootAuthenticationCertificate(String rootAuthenticationCertificate) {
        this.rootAuthenticationCertificate = rootAuthenticationCertificate;
    }

    public GclPtIdAllCertificates withRootAuthenticationCertificate(String rootAuthenticationCertificate) {
        this.rootAuthenticationCertificate = rootAuthenticationCertificate;
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

    public GclPtIdAllCertificates withRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
        return this;
    }

    /**
     * @return The rootNonRepudiationCertificate
     */
    public String getRootNonRepudiationCertificate() {
        return rootNonRepudiationCertificate;
    }

    /**
     * @param rootNonRepudiationCertificate The root_non_repudiation_certificate
     */
    public void setRootNonRepudiationCertificate(String rootNonRepudiationCertificate) {
        this.rootNonRepudiationCertificate = rootNonRepudiationCertificate;
    }

    public GclPtIdAllCertificates withRootNonRepudiationCertificate(String rootNonRepudiationCertificate) {
        this.rootNonRepudiationCertificate = rootNonRepudiationCertificate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationCertificate).append(nonRepudiationCertificate).append(rootAuthenticationCertificate).append(rootCertificate).append(rootNonRepudiationCertificate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclPtIdAllCertificates) == false) {
            return false;
        }
        GclPtIdAllCertificates rhs = ((GclPtIdAllCertificates) other);
        return new EqualsBuilder().append(authenticationCertificate, rhs.authenticationCertificate).append(nonRepudiationCertificate, rhs.nonRepudiationCertificate).append(rootAuthenticationCertificate, rhs.rootAuthenticationCertificate).append(rootCertificate, rhs.rootCertificate).append(rootNonRepudiationCertificate, rhs.rootNonRepudiationCertificate).isEquals();
    }

}
