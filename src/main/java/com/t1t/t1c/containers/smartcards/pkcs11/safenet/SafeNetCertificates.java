package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class SafeNetCertificates implements AllCertificates {

    private List<T1cCertificate> certificates;

    public SafeNetCertificates(List<T1cCertificate> certificates) {
        this.certificates = certificates;
    }

    public List<T1cCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<T1cCertificate> certificates) {
        this.certificates = certificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SafeNetCertificates)) return false;

        SafeNetCertificates that = (SafeNetCertificates) o;

        return certificates != null ? certificates.equals(that.certificates) : that.certificates == null;
    }

    @Override
    public int hashCode() {
        return certificates != null ? certificates.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SafeNetCertificates{" +
                "certificates=" + certificates +
                '}';
    }
}