package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclLuxTrustAllData;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;

import java.util.ArrayList;
import java.util.List;

public class LuxTrustAllData implements AllData {
    
    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private List<T1cCertificate> rootCertificates;

    public LuxTrustAllData(GclLuxTrustAllData data, boolean... parseCertificates) {
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificates);
        this.signingCertificate = CertificateUtil.createT1cCertificate(data.getSigningCertificate(), parseCertificates);
        List<T1cCertificate> rootCerts = new ArrayList<>();
        for (String cert : data.getRootCertificates()) {
            rootCerts.add(CertificateUtil.createT1cCertificate(cert, parseCertificates));
        }
        this.rootCertificates = rootCerts;
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

    public LuxTrustAllData withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The signingCertificate
     */
    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * @param signingCertificate The signing certificate
     */
    public void setSigningCertificate(T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public LuxTrustAllData withSigningCertificate(T1cCertificate nonRepudiationCertificate) {
        this.signingCertificate = nonRepudiationCertificate;
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

    public LuxTrustAllData withRootCertificates(List<T1cCertificate> rootCertificates) {
        this.rootCertificates = rootCertificates;
        return this;
    }

}
