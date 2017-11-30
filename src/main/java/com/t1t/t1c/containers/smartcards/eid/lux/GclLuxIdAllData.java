
package com.t1t.t1c.containers.smartcards.eid.lux;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclLuxIdAllData implements AllData
{

    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("non_repudiation_certificate")
    @Expose
    private String nonRepudiationCertificate;
    @SerializedName("root_certificates")
    @Expose
    private List<String> rootCertificates = new ArrayList<String>();
    @SerializedName("biometric")
    @Expose
    private GclLuxIdBiometric biometric;
    @SerializedName("picture")
    @Expose
    private GclLuxIdPicture picture;
    @SerializedName("signature_image")
    @Expose
    private GclLuxIdSignatureImage signatureImage;
    @SerializedName("signature_object")
    @Expose
    private String signatureObject;

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

    public GclLuxIdAllData withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
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

    public GclLuxIdAllData withNonRepudiationCertificate(String nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The rootCertificates
     */
    public List<String> getRootCertificates() {
        return rootCertificates;
    }

    /**
     * 
     * @param rootCertificates
     *     The root_certificates
     */
    public void setRootCertificates(List<String> rootCertificates) {
        this.rootCertificates = rootCertificates;
    }

    public GclLuxIdAllData withRootCertificates(List<String> rootCertificates) {
        this.rootCertificates = rootCertificates;
        return this;
    }

    /**
     * 
     * @return
     *     The biometric
     */
    public GclLuxIdBiometric getBiometric() {
        return biometric;
    }

    /**
     * 
     * @param biometric
     *     The biometric
     */
    public void setBiometric(GclLuxIdBiometric biometric) {
        this.biometric = biometric;
    }

    public GclLuxIdAllData withBiometric(GclLuxIdBiometric biometric) {
        this.biometric = biometric;
        return this;
    }

    /**
     * 
     * @return
     *     The picture
     */
    public GclLuxIdPicture getPicture() {
        return picture;
    }

    /**
     * 
     * @param picture
     *     The picture
     */
    public void setPicture(GclLuxIdPicture picture) {
        this.picture = picture;
    }

    public GclLuxIdAllData withPicture(GclLuxIdPicture picture) {
        this.picture = picture;
        return this;
    }

    /**
     * 
     * @return
     *     The signatureImage
     */
    public GclLuxIdSignatureImage getSignatureImage() {
        return signatureImage;
    }

    /**
     * 
     * @param signatureImage
     *     The signature_image
     */
    public void setSignatureImage(GclLuxIdSignatureImage signatureImage) {
        this.signatureImage = signatureImage;
    }

    public GclLuxIdAllData withSignatureImage(GclLuxIdSignatureImage signatureImage) {
        this.signatureImage = signatureImage;
        return this;
    }

    /**
     * 
     * @return
     *     The signatureObject
     */
    public String getSignatureObject() {
        return signatureObject;
    }

    /**
     * 
     * @param signatureObject
     *     The signature_object
     */
    public void setSignatureObject(String signatureObject) {
        this.signatureObject = signatureObject;
    }

    public GclLuxIdAllData withSignatureObject(String signatureObject) {
        this.signatureObject = signatureObject;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationCertificate).append(nonRepudiationCertificate).append(rootCertificates).append(biometric).append(picture).append(signatureImage).append(signatureObject).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclLuxIdAllData) == false) {
            return false;
        }
        GclLuxIdAllData rhs = ((GclLuxIdAllData) other);
        return new EqualsBuilder().append(authenticationCertificate, rhs.authenticationCertificate).append(nonRepudiationCertificate, rhs.nonRepudiationCertificate).append(rootCertificates, rhs.rootCertificates).append(biometric, rhs.biometric).append(picture, rhs.picture).append(signatureImage, rhs.signatureImage).append(signatureObject, rhs.signatureObject).isEquals();
    }

}
