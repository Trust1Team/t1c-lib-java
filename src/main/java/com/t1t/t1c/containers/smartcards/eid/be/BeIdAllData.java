package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;

public class BeIdAllData implements AllData {

    private GclBeIdAddress address;
    private T1cCertificate authenticationCertificate;
    private T1cCertificate citizenCertificate;
    private T1cCertificate nonRepudiationCertificate;
    private String picture;
    private GclBeIdRn rn;
    private T1cCertificate rootCertificate;
    private T1cCertificate rrnCertificate;

    public BeIdAllData(GclBeIdAllData data, Boolean... parseCertificate) {
        this.address = data.getAddress();
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificate);
        this.citizenCertificate = CertificateUtil.createT1cCertificate(data.getCitizenCertificate(), parseCertificate);
        this.nonRepudiationCertificate = CertificateUtil.createT1cCertificate(data.getNonRepudiationCertificate(), parseCertificate);
        this.picture = data.getPicture();
        this.rn = data.getRn();
        this.rootCertificate = CertificateUtil.createT1cCertificate(data.getRootCertificate(), parseCertificate);
        this.rrnCertificate = CertificateUtil.createT1cCertificate(data.getRrnCertificate(), parseCertificate);
    }

    /**
     * @return The address
     */
    public GclBeIdAddress getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(GclBeIdAddress address) {
        this.address = address;
    }

    public BeIdAllData withAddress(GclBeIdAddress address) {
        this.address = address;
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

    public BeIdAllData withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
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

    public BeIdAllData withCitizenCertificate(T1cCertificate citizenCertificate) {
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

    public BeIdAllData withNonRepudiationCertificate(T1cCertificate nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
        return this;
    }

    /**
     * @return The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BeIdAllData withPicture(String picture) {
        this.picture = picture;
        return this;
    }

    /**
     * @return The rn
     */
    public GclBeIdRn getRn() {
        return rn;
    }

    /**
     * @param rn The rn
     */
    public void setRn(GclBeIdRn rn) {
        this.rn = rn;
    }

    public BeIdAllData withRn(GclBeIdRn rn) {
        this.rn = rn;
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

    public BeIdAllData withRootCertificate(T1cCertificate rootCertificate) {
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

    public BeIdAllData withRrnCertificate(T1cCertificate rrnCertificate) {
        this.rrnCertificate = rrnCertificate;
        return this;
    }

    @Override
    public String toString() {
        return "BeIdAllData{" +
                "address=" + address +
                ", authenticationCertificate=" + authenticationCertificate +
                ", citizenCertificate=" + citizenCertificate +
                ", nonRepudiationCertificate=" + nonRepudiationCertificate +
                ", picture='" + picture + '\'' +
                ", rn=" + rn +
                ", rootCertificate=" + rootCertificate +
                ", rrnCertificate=" + rrnCertificate +
                '}';
    }
}
