package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.utils.CertificateUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class LuxIdAllData implements AllData {

    private T1cCertificate authenticationCertificate;
    private T1cCertificate nonRepudiationCertificate;
    private List<T1cCertificate> rootCertificates;
    private GclLuxIdBiometric biometric;
    private GclLuxIdPicture picture;
    private GclLuxIdSignatureImage signatureImage;
    private String signatureObject;

    public LuxIdAllData(GclLuxIdAllData data, boolean... parseCertificates) {
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificates);
        this.nonRepudiationCertificate = CertificateUtil.createT1cCertificate(data.getNonRepudiationCertificate(), parseCertificates);
        List<T1cCertificate> rootCerts = new ArrayList<>();
        for (String cert : data.getRootCertificates()) {
            rootCerts.add(CertificateUtil.createT1cCertificate(cert, parseCertificates));
        }
        this.rootCertificates = rootCerts;
        this.biometric = data.getBiometric();
        this.picture = data.getPicture();
        this.signatureImage = data.getSignatureImage();
        this.signatureObject = data.getSignatureObject();
    }

    /**
     * @return The authenticationCertificate
     */
    public T1cCertificate getAuthenticationCertificate() {
        return authenticationCertificate;
    }

    /**
     * @param authenticationCertificate The authentication_certificate
     */
    public void setAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public LuxIdAllData withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The nonRepudiationCertificate
     */
    public T1cCertificate getNonRepudiationCertificate() {
        return nonRepudiationCertificate;
    }

    /**
     * @param nonRepudiationCertificate The non_repudiation_certificate
     */
    public void setNonRepudiationCertificate(T1cCertificate nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
    }

    public LuxIdAllData withNonRepudiationCertificate(T1cCertificate nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * @return The rootCertificates
     */
    public List<T1cCertificate> getRootCertificates() {
        return rootCertificates;
    }

    /**
     * @param rootCertificates The root_certificates
     */
    public void setRootCertificates(List<T1cCertificate> rootCertificates) {
        this.rootCertificates = rootCertificates;
    }

    public LuxIdAllData withRootCertificates(List<T1cCertificate> rootCertificates) {
        this.rootCertificates = rootCertificates;
        return this;
    }

    /**
     * @return The biometric
     */
    public GclLuxIdBiometric getBiometric() {
        return biometric;
    }

    /**
     * @param biometric The biometric
     */
    public void setBiometric(GclLuxIdBiometric biometric) {
        this.biometric = biometric;
    }

    public LuxIdAllData withBiometric(GclLuxIdBiometric biometric) {
        this.biometric = biometric;
        return this;
    }

    /**
     * @return The picture
     */
    public GclLuxIdPicture getPicture() {
        return picture;
    }

    /**
     * @param picture The picture
     */
    public void setPicture(GclLuxIdPicture picture) {
        this.picture = picture;
    }

    public LuxIdAllData withPicture(GclLuxIdPicture picture) {
        this.picture = picture;
        return this;
    }

    /**
     * @return The signatureImage
     */
    public GclLuxIdSignatureImage getSignatureImage() {
        return signatureImage;
    }

    /**
     * @param signatureImage The signature_image
     */
    public void setSignatureImage(GclLuxIdSignatureImage signatureImage) {
        this.signatureImage = signatureImage;
    }

    public LuxIdAllData withSignatureImage(GclLuxIdSignatureImage signatureImage) {
        this.signatureImage = signatureImage;
        return this;
    }

    /**
     * @return The signatureObject
     */
    public String getSignatureObject() {
        return signatureObject;
    }

    /**
     * @param signatureObject The signature_object
     */
    public void setSignatureObject(String signatureObject) {
        this.signatureObject = signatureObject;
    }

    public LuxIdAllData withSignatureObject(String signatureObject) {
        this.signatureObject = signatureObject;
        return this;
    }

}
