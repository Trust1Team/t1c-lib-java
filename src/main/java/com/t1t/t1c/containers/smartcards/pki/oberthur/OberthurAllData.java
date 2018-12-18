package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;

public class OberthurAllData implements AllData, AllCertificates {

    private T1cCertificate rootCertificate;
    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private T1cCertificate issuerCertificate;
    private T1cCertificate encryptionCertificate;

    public OberthurAllData(final GclOberthurAllData data) {
        this(data, null);
    }

    public OberthurAllData(final GclOberthurAllData data, final Boolean parseCertificates) {
        this(data.getAuthenticationCertificate(),
                data.getRootCertificate(),
                data.getSigningCertificate(),
                data.getIssuerCertificate(),
                data.getEncryptionCertificate(), parseCertificates);
    }

    public OberthurAllData(final String authenticationCertificate, final String rootCertificate, final String signingCertificate, final String issuerCertificate, final String encryptionCertificate, final Boolean parseCertificates) {
        this.authenticationCertificate = PkiUtil.createT1cCertificate(authenticationCertificate, parseCertificates);
        this.rootCertificate = PkiUtil.createT1cCertificate(rootCertificate, parseCertificates);
        this.signingCertificate = PkiUtil.createT1cCertificate(signingCertificate, parseCertificates);
        this.issuerCertificate = PkiUtil.createT1cCertificate(issuerCertificate, parseCertificates);
        this.encryptionCertificate = PkiUtil.createT1cCertificate(encryptionCertificate, parseCertificates);
    }

    public T1cCertificate getRootCertificate() {
        return rootCertificate;
    }

    public void setRootCertificate(final T1cCertificate rootCertificate) {
        this.rootCertificate = rootCertificate;
    }

    public T1cCertificate getAuthenticationCertificate() {
        return authenticationCertificate;
    }

    public void setAuthenticationCertificate(final T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    public void setSigningCertificate(final T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public T1cCertificate getIssuerCertificate() {
        return issuerCertificate;
    }

    public void setIssuerCertificate(final T1cCertificate issuerCertificate) {
        this.issuerCertificate = issuerCertificate;
    }

    public T1cCertificate getEncryptionCertificate() {
        return encryptionCertificate;
    }

    public void setEncryptionCertificate(final T1cCertificate encryptionCertificate) {
        this.encryptionCertificate = encryptionCertificate;
    }

    @Override
    public String toString() {
        return "OberthurAllData{" +
                "rootCertificate=" + rootCertificate +
                ", authenticationCertificate=" + authenticationCertificate +
                ", signingCertificate=" + signingCertificate +
                ", issuerCertificate=" + issuerCertificate +
                ", encryptionCertificate=" + encryptionCertificate +
                '}';
    }
}
