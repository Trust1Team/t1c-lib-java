package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;

public class AventraAllCertificates implements AllCertificates {

    private T1cCertificate rootCertificate;
    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private T1cCertificate issuerCertificate;
    private T1cCertificate encryptionCertificate;

    public AventraAllCertificates(GclAventraAllCertificates certs, Boolean... parseCertificates) {
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(certs.getAuthenticationCertificate(), parseCertificates);
        this.rootCertificate = CertificateUtil.createT1cCertificate(certs.getRootCertificate(), parseCertificates);
        this.signingCertificate = CertificateUtil.createT1cCertificate(certs.getSigningCertificate(), parseCertificates);
        this.issuerCertificate = CertificateUtil.createT1cCertificate(certs.getIssuerCertificate(), parseCertificates);
        this.encryptionCertificate = CertificateUtil.createT1cCertificate(certs.getEncryptionCertificate(), parseCertificates);
    }

    public T1cCertificate getRootCertificate() {
        return rootCertificate;
    }

    public void setRootCertificate(T1cCertificate rootCertificate) {
        this.rootCertificate = rootCertificate;
    }

    public T1cCertificate getAuthenticationCertificate() {
        return authenticationCertificate;
    }

    public void setAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    public void setSigningCertificate(T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public T1cCertificate getIssuerCertificate() {
        return issuerCertificate;
    }

    public void setIssuerCertificate(T1cCertificate issuerCertificate) {
        this.issuerCertificate = issuerCertificate;
    }

    public T1cCertificate getEncryptionCertificate() {
        return encryptionCertificate;
    }

    public void setEncryptionCertificate(T1cCertificate encryptionCertificate) {
        this.encryptionCertificate = encryptionCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AventraAllCertificates)) return false;

        AventraAllCertificates that = (AventraAllCertificates) o;

        if (rootCertificate != null ? !rootCertificate.equals(that.rootCertificate) : that.rootCertificate != null)
            return false;
        if (authenticationCertificate != null ? !authenticationCertificate.equals(that.authenticationCertificate) : that.authenticationCertificate != null)
            return false;
        if (signingCertificate != null ? !signingCertificate.equals(that.signingCertificate) : that.signingCertificate != null)
            return false;
        if (issuerCertificate != null ? !issuerCertificate.equals(that.issuerCertificate) : that.issuerCertificate != null)
            return false;
        return encryptionCertificate != null ? encryptionCertificate.equals(that.encryptionCertificate) : that.encryptionCertificate == null;
    }

    @Override
    public int hashCode() {
        int result = rootCertificate != null ? rootCertificate.hashCode() : 0;
        result = 31 * result + (authenticationCertificate != null ? authenticationCertificate.hashCode() : 0);
        result = 31 * result + (signingCertificate != null ? signingCertificate.hashCode() : 0);
        result = 31 * result + (issuerCertificate != null ? issuerCertificate.hashCode() : 0);
        result = 31 * result + (encryptionCertificate != null ? encryptionCertificate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AventraAllCertificates{" +
                "rootCertificate=" + rootCertificate +
                ", authenticationCertificate=" + authenticationCertificate +
                ", signingCertificate=" + signingCertificate +
                ", issuerCertificate=" + issuerCertificate +
                ", encryptionCertificate=" + encryptionCertificate +
                '}';
    }
}
