package com.t1t.t1c.containers.smartcards.piv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclPivAllData implements AllData {

    @SerializedName("printed_information")
    @Expose
    private GclPivPrintedInformation printedInformation;
    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("signing_certificate")
    @Expose
    private String signingCertificate;
    @SerializedName("facial_image")
    @Expose
    private GclPivFacialImage facialImage;

    /**
     * @return The printedInformation
     */
    public GclPivPrintedInformation getPrintedInformation() {
        return printedInformation;
    }

    /**
     * @param printedInformation The printed_information
     */
    public void setPrintedInformation(GclPivPrintedInformation printedInformation) {
        this.printedInformation = printedInformation;
    }

    public GclPivAllData withPrintedInformation(GclPivPrintedInformation printedInformation) {
        this.printedInformation = printedInformation;
        return this;
    }

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

    public GclPivAllData withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The signingCertificate
     */
    public String getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * @param signingCertificate The signing_certificate
     */
    public void setSigningCertificate(String signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public GclPivAllData withSigningCertificate(String signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    /**
     * @return The facialImage
     */
    public GclPivFacialImage getFacialImage() {
        return facialImage;
    }

    /**
     * @param facialImage The facial_image
     */
    public void setFacialImage(GclPivFacialImage facialImage) {
        this.facialImage = facialImage;
    }

    public GclPivAllData withFacialImage(GclPivFacialImage facialImage) {
        this.facialImage = facialImage;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(printedInformation).append(authenticationCertificate).append(signingCertificate).append(facialImage).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclPivAllData)) {
            return false;
        }
        GclPivAllData rhs = ((GclPivAllData) other);
        return new EqualsBuilder().append(printedInformation, rhs.printedInformation).append(authenticationCertificate, rhs.authenticationCertificate).append(signingCertificate, rhs.signingCertificate).append(facialImage, rhs.facialImage).isEquals();
    }

}
