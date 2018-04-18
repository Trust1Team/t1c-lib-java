package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxTrustAllCertificates implements AllCertificates {
    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private List<T1cCertificate> rootCertificates;

    public LuxTrustAllCertificates(GclLuxTrustAllCertificates certs) {
        this(certs, null);
    }

    public LuxTrustAllCertificates(GclLuxTrustAllCertificates certs, Boolean parseCertificates) {
        this.authenticationCertificate = PkiUtil.createT1cCertificate(certs.getAuthenticationCertificate(), parseCertificates);
        this.signingCertificate = PkiUtil.createT1cCertificate(certs.getSigningCertificate(), parseCertificates);
        if (CollectionUtils.isNotEmpty(certs.getRootCertificates())) {
            List<T1cCertificate> rootCerts = new ArrayList<>();
            for (String cert : certs.getRootCertificates()) {
                rootCerts.add(PkiUtil.createT1cCertificate(cert, parseCertificates));
            }
            this.rootCertificates = rootCerts;
        }
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

    public List<T1cCertificate> getRootCertificates() {
        return rootCertificates;
    }

    public void setRootCertificates(List<T1cCertificate> rootCertificates) {
        this.rootCertificates = rootCertificates;
    }

    @Override
    public String toString() {
        return "LuxTrustAllCertificates{" +
                "authenticationCertificate=" + authenticationCertificate +
                ", signingCertificate=" + signingCertificate +
                ", rootCertificates=" + rootCertificates +
                '}';
    }
}