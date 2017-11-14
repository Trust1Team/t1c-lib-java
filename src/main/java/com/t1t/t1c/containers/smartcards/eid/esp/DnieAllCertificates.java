package com.t1t.t1c.containers.smartcards.eid.esp;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.rest.GclDnieAllCertificates;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;

public class DnieAllCertificates implements AllCertificates {

    private T1cCertificate authenticationCertificate;
    private T1cCertificate intermediateCertificate;
    private T1cCertificate signingCertificate;

    public DnieAllCertificates(GclDnieAllCertificates certificates, boolean... parseCertificate) {

        this.authenticationCertificate = CertificateUtil.createT1cCertificate(certificates.getAuthenticationCertificate(), parseCertificate);
        this.intermediateCertificate = CertificateUtil.createT1cCertificate(certificates.getIntermediateCertificate(), parseCertificate);
        this.signingCertificate = CertificateUtil.createT1cCertificate(certificates.getSigningCertificate(), parseCertificate);
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

    public DnieAllCertificates withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The intermediateCertificate
     */
    public T1cCertificate getIntermediateCertificate() {
        return intermediateCertificate;
    }

    /**
     * @param intermediateCertificate The intermediate_certificate
     */
    public void setIntermediateCertificate(T1cCertificate intermediateCertificate) {
        this.intermediateCertificate = intermediateCertificate;
    }

    public DnieAllCertificates withIntermediateCertificate(T1cCertificate intermediateCertificate) {
        this.intermediateCertificate = intermediateCertificate;
        return this;
    }

    /**
     * @return The signingCertificate
     */
    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * @param signingCertificate The signing_certificate
     */
    public void setSigningCertificate(T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public DnieAllCertificates withSigningCertificate(T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

}
