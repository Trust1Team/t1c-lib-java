package com.t1t.t1c.containers.smartcards.eid.dni;

import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;

public class DnieAllData implements AllData {

    private T1cCertificate authenticationCertificate;
    private T1cCertificate intermediateCertificate;
    private T1cCertificate signingCertificate;
    private GclDnieInfo info;

    public DnieAllData(final GclDnieAllData data, final Boolean parseCertificate) {
        this.authenticationCertificate = PkiUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificate);
        this.intermediateCertificate = PkiUtil.createT1cCertificate(data.getIntermediateCertificate(), parseCertificate);
        this.signingCertificate = PkiUtil.createT1cCertificate(data.getSigningCertificate(), parseCertificate);
        this.info = data.getInfo();
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
    public void setAuthenticationCertificate(final T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public DnieAllData withAuthenticationCertificate(final T1cCertificate authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * @return The intermediateCertificate
     */
    public T1cCertificate getIntermediateCertificate() {
        return intermediateCertificate;
    }

    /**
     * @param intermediateCertificate The intermediate_certificate
     */
    public void setIntermediateCertificate(final T1cCertificate intermediateCertificate) {
        this.intermediateCertificate = intermediateCertificate;
    }

    public DnieAllData withIntermediateCertificate(final T1cCertificate intermediateCertificate) {
        this.intermediateCertificate = intermediateCertificate;
        return this;
    }

    /**
     * @return The signingCertificate
     */
    public T1cCertificate getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * @param signingCertificate The signing_certificate
     */
    public void setSigningCertificate(final T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public DnieAllData withSigningCertificate(final T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    /**
     * @return The info
     */
    public GclDnieInfo getInfo() {
        return info;
    }

    /**
     * @param info The info
     */
    public void setInfo(final GclDnieInfo info) {
        this.info = info;
    }

    public DnieAllData withInfo(final GclDnieInfo info) {
        this.info = info;
        return this;
    }

    @Override
    public String toString() {
        return "DnieAllData{" +
                "authenticationCertificate=" + authenticationCertificate +
                ", intermediateCertificate=" + intermediateCertificate +
                ", signingCertificate=" + signingCertificate +
                ", info=" + info +
                '}';
    }
}
