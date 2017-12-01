package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;

public class AventraAllCertificates implements AllCertificates {

    private T1cCertificate authenticationCertificate;
    private T1cCertificate encryptionCertificate;
    private T1cCertificate issuerCertificate;
    private T1cCertificate rootCertificate;
    private T1cCertificate signingCertificate;

    public AventraAllCertificates(GclAventraAllCertificates certificates, Boolean... parseCertificates) {
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(certificates.getAuthenticationCertificate(), parseCertificates);
        this.encryptionCertificate = CertificateUtil.createT1cCertificate(certificates.getEncryptionCertificate(), parseCertificates);
        ;
        this.issuerCertificate = CertificateUtil.createT1cCertificate(certificates.getIssuerCertificate(), parseCertificates);
        ;
        this.rootCertificate = CertificateUtil.createT1cCertificate(certificates.getRootCertificate(), parseCertificates);
        ;
        this.signingCertificate = CertificateUtil.createT1cCertificate(certificates.getSigningCertificate(), parseCertificates);
        ;
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

    public AventraAllCertificates withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The encryptionCertificate
     */
    public T1cCertificate getEncryptionCertificate() {
        return encryptionCertificate;
    }

    /**
     * @param encryptionCertificate The non_repudiation_certificate
     */
    public void setEncryptionCertificate(T1cCertificate encryptionCertificate) {
        this.encryptionCertificate = encryptionCertificate;
    }

    public AventraAllCertificates withNonRepudiationCertificate(T1cCertificate nonRepudiationCertificate) {
        this.encryptionCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * @return The issuerCertificate
     */
    public T1cCertificate getIssuerCertificate() {
        return issuerCertificate;
    }

    /**
     * @param issuerCertificate The root_authentication_certificate
     */
    public void setIssuerCertificate(T1cCertificate issuerCertificate) {
        this.issuerCertificate = issuerCertificate;
    }

    public AventraAllCertificates withRootAuthenticationCertificate(T1cCertificate rootAuthenticationCertificate) {
        this.issuerCertificate = rootAuthenticationCertificate;
        return this;
    }

    /**
     * @return The rootCertificate
     */
    public T1cCertificate getRootCertificate() {
        return rootCertificate;
    }

    /**
     * @param rootCertificate The root_certificate
     */
    public void setRootCertificate(T1cCertificate rootCertificate) {
        this.rootCertificate = rootCertificate;
    }

    public AventraAllCertificates withRootCertificate(T1cCertificate rootCertificate) {
        this.rootCertificate = rootCertificate;
        return this;
    }

    /**
     * @return The signingCertificate
     */
    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * @param signingCertificate The root_non_repudiation_certificate
     */
    public void setSigningCertificate(T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public AventraAllCertificates withRootNonRepudiationCertificate(T1cCertificate rootNonRepudiationCertificate) {
        this.signingCertificate = rootNonRepudiationCertificate;
        return this;
    }

    @Override
    public String toString() {
        return "PtIdAllCertificates{" +
                "authenticationCertificate=" + authenticationCertificate +
                ", encryptionCertificate=" + encryptionCertificate +
                ", issuerCertificate=" + issuerCertificate +
                ", rootCertificate=" + rootCertificate +
                ", signingCertificate=" + signingCertificate +
                '}';
    }
}
