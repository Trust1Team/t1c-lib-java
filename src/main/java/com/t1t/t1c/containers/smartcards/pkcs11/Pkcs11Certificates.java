package com.t1t.t1c.containers.smartcards.pkcs11;

import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.T1cCertificate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class Pkcs11Certificates implements AllCertificates {

    private List<T1cCertificate> certificates;

    public Pkcs11Certificates(List<T1cCertificate> certificates) {
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
        if (!(o instanceof Pkcs11Certificates)) return false;

        Pkcs11Certificates that = (Pkcs11Certificates) o;

        return certificates != null ? certificates.equals(that.certificates) : that.certificates == null;
    }

    @Override
    public int hashCode() {
        return certificates != null ? certificates.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Pkcs11Certificates{" +
                "certificates=" + certificates +
                '}';
    }
}