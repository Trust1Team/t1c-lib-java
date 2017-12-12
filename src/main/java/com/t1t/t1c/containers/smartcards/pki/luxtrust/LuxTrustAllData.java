package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.CertificateUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class LuxTrustAllData implements AllData {

    private Boolean activated;
    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private List<T1cCertificate> rootCertificates;

    public LuxTrustAllData(GclLuxTrustAllData data, Boolean... parseCertificates) {
        this.activated = data.getActivated();
        this.authenticationCertificate = CertificateUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificates);
        this.signingCertificate = CertificateUtil.createT1cCertificate(data.getSigningCertificate(), parseCertificates);
        if (CollectionUtils.isNotEmpty(data.getRootCertificates())) {
            List<T1cCertificate> rootCerts = new ArrayList<>();
            for (String cert : data.getRootCertificates()) {
                rootCerts.add(CertificateUtil.createT1cCertificate(cert, parseCertificates));
            }
            this.rootCertificates = rootCerts;
        }
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LuxTrustAllData)) return false;

        LuxTrustAllData that = (LuxTrustAllData) o;

        if (activated != null ? !activated.equals(that.activated) : that.activated != null) return false;
        if (authenticationCertificate != null ? !authenticationCertificate.equals(that.authenticationCertificate) : that.authenticationCertificate != null)
            return false;
        if (signingCertificate != null ? !signingCertificate.equals(that.signingCertificate) : that.signingCertificate != null)
            return false;
        return rootCertificates != null ? rootCertificates.equals(that.rootCertificates) : that.rootCertificates == null;
    }

    @Override
    public int hashCode() {
        int result = activated != null ? activated.hashCode() : 0;
        result = 31 * result + (authenticationCertificate != null ? authenticationCertificate.hashCode() : 0);
        result = 31 * result + (signingCertificate != null ? signingCertificate.hashCode() : 0);
        result = 31 * result + (rootCertificates != null ? rootCertificates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LuxTrustAllData{" +
                "activated=" + activated +
                ", authenticationCertificate=" + authenticationCertificate +
                ", signingCertificate=" + signingCertificate +
                ", rootCertificates=" + rootCertificates +
                '}';
    }
}