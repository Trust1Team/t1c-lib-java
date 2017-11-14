package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.rest.GclLuxTrustAllCertificates;
import com.t1t.t1c.model.rest.GclLuxTrustAllData;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class LuxTrustAllCertificates implements AllCertificates {
    
    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private List<T1cCertificate> rootCertificates;

    public LuxTrustAllCertificates(GclLuxTrustAllCertificates certificates, boolean... parseCertificates) {
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(certificates.getAuthenticationCertificate(), parseCertificates);
        this.signingCertificate = CertificateUtil.createT1cCertificate(certificates.getSigningCertificate(), parseCertificates);
        List<T1cCertificate> rootCerts = new ArrayList<>();
        for (String cert : certificates.getRootCertificates()) {
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

    public LuxTrustAllCertificates withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The nonRepudiationCertificate
     */
    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * @param nonRepudiationCertificate The non_repudiation_certificate
     */
    public void setSigningCertificate(T1cCertificate nonRepudiationCertificate) {
        this.signingCertificate = nonRepudiationCertificate;
    }

    public LuxTrustAllCertificates withSigningCertificate(T1cCertificate nonRepudiationCertificate) {
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

    public LuxTrustAllCertificates withRootCertificates(List<T1cCertificate> rootCertificates) {
        this.rootCertificates = rootCertificates;
        return this;
    }

}
