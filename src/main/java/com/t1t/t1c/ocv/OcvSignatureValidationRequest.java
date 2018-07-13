package com.t1t.t1c.ocv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OcvSignatureValidationRequest {

    @SerializedName("rawData")
    @Expose
    private String rawData;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("certificate")
    @Expose
    private String certificate;
    @SerializedName("digest")
    @Expose
    private String digest;

    /**
     * @return The rawData
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * @param rawData The rawData
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public OcvSignatureValidationRequest withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    /**
     * @return The signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature The signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public OcvSignatureValidationRequest withSignature(String signature) {
        this.signature = signature;
        return this;
    }

    /**
     * @return The certificate
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * @param certificate The certificate
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public OcvSignatureValidationRequest withCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    /**
     * @return The digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * @param digest The digest
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    public OcvSignatureValidationRequest withDigest(String digest) {
        this.digest = digest;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(rawData).append(signature).append(certificate).append(digest).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof OcvSignatureValidationRequest)) {
            return false;
        }
        OcvSignatureValidationRequest rhs = ((OcvSignatureValidationRequest) other);
        return new EqualsBuilder().append(rawData, rhs.rawData).append(signature, rhs.signature).append(certificate, rhs.certificate).append(digest, rhs.digest).isEquals();
    }

}
