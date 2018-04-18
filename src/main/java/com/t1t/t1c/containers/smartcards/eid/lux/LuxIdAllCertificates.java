package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class LuxIdAllCertificates implements AllCertificates {

    private T1cCertificate authenticationCertificate;
    private T1cCertificate nonRepudiationCertificate;
    private List<T1cCertificate> rootCertificates;

    public LuxIdAllCertificates(GclLuxIdAllCertificates certificates, Boolean parseCertificates) {
        this.authenticationCertificate = PkiUtil.createT1cCertificate(certificates.getAuthenticationCertificate(), parseCertificates);
        this.nonRepudiationCertificate = PkiUtil.createT1cCertificate(certificates.getNonRepudiationCertificate(), parseCertificates);
        if (CollectionUtils.isNotEmpty(certificates.getRootCertificates())) {
            List<T1cCertificate> rootCerts = new ArrayList<>();
            for (String cert : certificates.getRootCertificates()) {
                rootCerts.add(PkiUtil.createT1cCertificate(cert, parseCertificates));
            }
            this.rootCertificates = rootCerts;
        }
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

    public LuxIdAllCertificates withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
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

    public LuxIdAllCertificates withNonRepudiationCertificate(T1cCertificate nonRepudiationCertificate) {
        this.nonRepudiationCertificate = nonRepudiationCertificate;
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

    public LuxIdAllCertificates withRootCertificates(List<T1cCertificate> rootCertificates) {
        this.rootCertificates = rootCertificates;
        return this;
    }

    @Override
    public String toString() {
        return "LuxIdAllCertificates{" +
                "authenticationCertificate=" + authenticationCertificate +
                ", nonRepudiationCertificate=" + nonRepudiationCertificate +
                ", rootCertificates=" + rootCertificates +
                '}';
    }
}
