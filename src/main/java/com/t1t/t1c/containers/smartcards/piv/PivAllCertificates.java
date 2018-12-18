package com.t1t.t1c.containers.smartcards.piv;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PivAllCertificates implements AllCertificates {

    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;

    public PivAllCertificates(final GclPivAllCertificates certs) {
        this(certs, null);
    }

    public PivAllCertificates(final GclPivAllCertificates certs, final Boolean parseCertificate) {
        this.authenticationCertificate = PkiUtil.createT1cCertificate(certs.getAuthenticationCertificate(), parseCertificate);
        this.signingCertificate = PkiUtil.createT1cCertificate(certs.getSigningCertificate(), parseCertificate);
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
    public void setAuthenticationCertificate(final T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public PivAllCertificates withAuthenticationCertificate(final T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The signing certificate
     */
    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * @param signingCertificate The signing certificate
     */
    public void setSigningCertificate(final T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public PivAllCertificates withSigningCertificate(final T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    @Override
    public String toString() {
        return "PivAllCertificates{" +
                "authenticationCertificate=" + authenticationCertificate +
                ", signingCertificate=" + signingCertificate +
                '}';
    }
}