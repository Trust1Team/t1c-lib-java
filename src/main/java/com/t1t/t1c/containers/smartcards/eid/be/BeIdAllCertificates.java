package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class BeIdAllCertificates implements AllCertificates {

    private T1cCertificate authenticationCertificate;
    private T1cCertificate citizenCertificate;
    private T1cCertificate nonRepudiationCertificate;
    private T1cCertificate rootCertificate;
    private T1cCertificate rrnCertificate;

    public BeIdAllCertificates(GclBeIdAllCertificates certs, Boolean... parseCertificate) {
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(certs.getAuthenticationCertificate(), parseCertificate);
        this.citizenCertificate = CertificateUtil.createT1cCertificate(certs.getCitizenCertificate(), parseCertificate);
        this.nonRepudiationCertificate = CertificateUtil.createT1cCertificate(certs.getNonRepudiationCertificate(), parseCertificate);
        this.rootCertificate = CertificateUtil.createT1cCertificate(certs.getRootCertificate(), parseCertificate);
        this.rrnCertificate = CertificateUtil.createT1cCertificate(certs.getRrnCertificate(), parseCertificate);
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

    public BeIdAllCertificates withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The citizenCertificate
     */
    public T1cCertificate getCitizenCertificate() {
        return citizenCertificate;
    }

    /**
     * @param citizenCertificate The citizen_certificate
     */
    public void setCitizenCertificate(T1cCertificate citizenCertificate) {
        this.citizenCertificate = citizenCertificate;
    }

    public BeIdAllCertificates withCitizenCertificate(T1cCertificate citizenCertificate) {
        this.citizenCertificate = citizenCertificate;
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

    public BeIdAllCertificates withNonRepudiationCertificate(T1cCertificate nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
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

    public BeIdAllCertificates withRootCertificate(T1cCertificate rootCertificate) {
        this.rootCertificate = rootCertificate;
        return this;
    }

    /**
     * @return The rrnCertificate
     */
    public T1cCertificate getRrnCertificate() {
        return rrnCertificate;
    }

    /**
     * @param rrnCertificate The rrn_certificate
     */
    public void setRrnCertificate(T1cCertificate rrnCertificate) {
        this.rrnCertificate = rrnCertificate;
    }

    public BeIdAllCertificates withRrnCertificate(T1cCertificate rrnCertificate) {
        this.rrnCertificate = rrnCertificate;
        return this;
    }

    @Override
    public String toString() {
        return "BeIdAllCertificates{" +
                "authenticationCertificate=" + authenticationCertificate +
                ", citizenCertificate=" + citizenCertificate +
                ", nonRepudiationCertificate=" + nonRepudiationCertificate +
                ", rootCertificate=" + rootCertificate +
                ", rrnCertificate=" + rrnCertificate +
                '}';
    }
}