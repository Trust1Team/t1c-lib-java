package com.t1t.t1c.containers.smartcards.pki.aventra;

import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;

public class AventraAllData implements AllData {

    private GclAventraAppletInfo appletInfo;
    private T1cCertificate rootCertificate;
    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private T1cCertificate issuerCertificate;
    private T1cCertificate encryptionCertificate;

    public AventraAllData(GclAventraAllData data, Boolean... parseCertificates) {
        this.appletInfo = data.getAppletInfo();
        this.authenticationCertificate = PkiUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificates);
        this.rootCertificate = PkiUtil.createT1cCertificate(data.getRootCertificate(), parseCertificates);
        this.signingCertificate = PkiUtil.createT1cCertificate(data.getSigningCertificate(), parseCertificates);
        this.issuerCertificate = PkiUtil.createT1cCertificate(data.getIssuerCertificate(), parseCertificates);
        this.encryptionCertificate = PkiUtil.createT1cCertificate(data.getEncryptionCertificate(), parseCertificates);
    }

    public GclAventraAppletInfo getAppletInfo() {
        return appletInfo;
    }

    public void setAppletInfo(GclAventraAppletInfo appletInfo) {
        this.appletInfo = appletInfo;
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
    public String toString() {
        return "AventraAllData{" +
                "appletInfo=" + appletInfo +
                ", rootCertificate=" + rootCertificate +
                ", authenticationCertificate=" + authenticationCertificate +
                ", signingCertificate=" + signingCertificate +
                ", issuerCertificate=" + issuerCertificate +
                ", encryptionCertificate=" + encryptionCertificate +
                '}';
    }
}
