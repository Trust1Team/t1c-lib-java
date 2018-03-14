package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;

public class PtIdAllData implements AllData {

    private GclPtIdData id;
    private T1cCertificate authenticationCertificate;
    private T1cCertificate nonRepudiationCertificate;
    private T1cCertificate rootAuthenticationCertificate;
    private T1cCertificate rootCertificate;
    private T1cCertificate rootNonRepudiationCertificate;

    public PtIdAllData(GclPtIdAllData data, Boolean... parseCertificates) {
        this.id = data.getId();
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificates);
        this.nonRepudiationCertificate = CertificateUtil.createT1cCertificate(data.getNonRepudiationCertificate(), parseCertificates);
        this.rootAuthenticationCertificate = CertificateUtil.createT1cCertificate(data.getRootAuthenticationCertificate(), parseCertificates);
        this.rootCertificate = CertificateUtil.createT1cCertificate(data.getRootCertificate(), parseCertificates);
        this.rootNonRepudiationCertificate = CertificateUtil.createT1cCertificate(data.getRootNonRepudiationCertificate(), parseCertificates);
    }

    /**
     * @return The id
     */
    public GclPtIdData getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(GclPtIdData id) {
        this.id = id;
    }

    public PtIdAllData withId(GclPtIdData id) {
        this.id = id;
        return this;
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

    public PtIdAllData withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
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

    public PtIdAllData withNonRepudiationCertificate(T1cCertificate nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * @return The rootAuthenticationCertificate
     */
    public T1cCertificate getRootAuthenticationCertificate() {
        return rootAuthenticationCertificate;
    }

    /**
     * @param rootAuthenticationCertificate The root_authentication_certificate
     */
    public void setRootAuthenticationCertificate(T1cCertificate rootAuthenticationCertificate) {
        this.rootAuthenticationCertificate = rootAuthenticationCertificate;
    }

    public PtIdAllData withRootAuthenticationCertificate(T1cCertificate rootAuthenticationCertificate) {
        this.rootAuthenticationCertificate = rootAuthenticationCertificate;
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

    public PtIdAllData withRootCertificate(T1cCertificate rootCertificate) {
        this.rootCertificate = rootCertificate;
        return this;
    }

    /**
     * @return The rootNonRepudiationCertificate
     */
    public T1cCertificate getRootNonRepudiationCertificate() {
        return rootNonRepudiationCertificate;
    }

    /**
     * @param rootNonRepudiationCertificate The root_non_repudiation_certificate
     */
    public void setRootNonRepudiationCertificate(T1cCertificate rootNonRepudiationCertificate) {
        this.rootNonRepudiationCertificate = rootNonRepudiationCertificate;
    }

    public PtIdAllData withRootNonRepudiationCertificate(T1cCertificate rootNonRepudiationCertificate) {
        this.rootNonRepudiationCertificate = rootNonRepudiationCertificate;
        return this;
    }
}
