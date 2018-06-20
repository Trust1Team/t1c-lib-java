
package com.t1t.t1c.containers.smartcards.eid.be;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclBeIdAllData implements AllData
{

    @SerializedName("address")
    @Expose
    private GclBeIdAddress address;
    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("citizen_certificate")
    @Expose
    private String citizenCertificate;
    @SerializedName("non_repudiation_certificate")
    @Expose
    private String nonRepudiationCertificate;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("rn")
    @Expose
    private GclBeIdRn rn;
    @SerializedName("root_certificate")
    @Expose
    private String rootCertificate;
    @SerializedName("rrn_certificate")
    @Expose
    private String rrnCertificate;
    @SerializedName("token")
    @Expose
    private GclBeIdToken token;

    /**
     * 
     * @return
     *     The address
     */
    public GclBeIdAddress getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(GclBeIdAddress address) {
        this.address = address;
    }

    public GclBeIdAllData withAddress(GclBeIdAddress address) {
        this.address = address;
        return this;
    }

    /**
     * 
     * @return
     *     The authenticationCertificate
     */
    public String getAuthenticationCertificate() {
        return authenticationCertificate;
    }

    /**
     * 
     * @param authenticationCertificate
     *     The authentication_certificate
     */
    public void setAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public GclBeIdAllData withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The citizenCertificate
     */
    public String getCitizenCertificate() {
        return citizenCertificate;
    }

    /**
     * 
     * @param citizenCertificate
     *     The citizen_certificate
     */
    public void setCitizenCertificate(String citizenCertificate) {
        this.citizenCertificate = citizenCertificate;
    }

    public GclBeIdAllData withCitizenCertificate(String citizenCertificate) {
        this.citizenCertificate = citizenCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The nonRepudiationCertificate
     */
    public String getNonRepudiationCertificate() {
        return nonRepudiationCertificate;
    }

    /**
     * 
     * @param nonRepudiationCertificate
     *     The non_repudiation_certificate
     */
    public void setNonRepudiationCertificate(String nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
    }

    public GclBeIdAllData withNonRepudiationCertificate(String nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 
     * @param picture
     *     The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public GclBeIdAllData withPicture(String picture) {
        this.picture = picture;
        return this;
    }

    /**
     * 
     * @return
     *     The rn
     */
    public GclBeIdRn getRn() {
        return rn;
    }

    /**
     * 
     * @param rn
     *     The rn
     */
    public void setRn(GclBeIdRn rn) {
        this.rn = rn;
    }

    public GclBeIdAllData withRn(GclBeIdRn rn) {
        this.rn = rn;
        return this;
    }

    /**
     * 
     * @return
     *     The rootCertificate
     */
    public String getRootCertificate() {
        return rootCertificate;
    }

    /**
     * 
     * @param rootCertificate
     *     The root_certificate
     */
    public void setRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
    }

    public GclBeIdAllData withRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The rrnCertificate
     */
    public String getRrnCertificate() {
        return rrnCertificate;
    }

    /**
     * 
     * @param rrnCertificate
     *     The rrn_certificate
     */
    public void setRrnCertificate(String rrnCertificate) {
        this.rrnCertificate = rrnCertificate;
    }

    public GclBeIdAllData withRrnCertificate(String rrnCertificate) {
        this.rrnCertificate = rrnCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The token
     */
    public GclBeIdToken getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    public void setToken(GclBeIdToken token) {
        this.token = token;
    }

    public GclBeIdAllData withToken(GclBeIdToken token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(address).append(authenticationCertificate).append(citizenCertificate).append(nonRepudiationCertificate).append(picture).append(rn).append(rootCertificate).append(rrnCertificate).append(token).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclBeIdAllData)) {
            return false;
        }
        GclBeIdAllData rhs = ((GclBeIdAllData) other);
        return new EqualsBuilder().append(address, rhs.address).append(authenticationCertificate, rhs.authenticationCertificate).append(citizenCertificate, rhs.citizenCertificate).append(nonRepudiationCertificate, rhs.nonRepudiationCertificate).append(picture, rhs.picture).append(rn, rhs.rn).append(rootCertificate, rhs.rootCertificate).append(rrnCertificate, rhs.rrnCertificate).append(token, rhs.token).isEquals();
    }

}
