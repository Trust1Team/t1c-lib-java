package com.t1t.t1c.containers.smartcards.piv;

import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;

public class PivAllData implements AllData {


    private T1cCertificate authenticationCertificate;
    private T1cCertificate signingCertificate;
    private GclPivFacialImage facialImage;
    private GclPivPrintedInformation printedInformation;

    public PivAllData(GclPivAllData data) {
        this(data, null);
    }

    public PivAllData(GclPivAllData data, Boolean parseCertificate) {
        this.authenticationCertificate = PkiUtil.createT1cCertificate(data.getAuthenticationCertificate(), parseCertificate);
        this.signingCertificate = PkiUtil.createT1cCertificate(data.getSigningCertificate(), parseCertificate);
        this.facialImage = data.getFacialImage();
        this.printedInformation = data.getPrintedInformation();
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

    public PivAllData withAuthenticationCertificate(T1cCertificate authenticationCertificate) {
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
     * @param signingCertificate The signing_certificate
     */
    public void setSigningCertificate(T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public PivAllData withSigningCertificate(T1cCertificate signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }


    /**
     * @return The facialImage
     */
    public GclPivFacialImage getFacialImage() {
        return facialImage;
    }

    /**
     * @param facialImage The facialImage
     */
    public void setFacialImage(GclPivFacialImage facialImage) {
        this.facialImage = facialImage;
    }

    public PivAllData withFacialImage(GclPivFacialImage facialImage) {
        this.facialImage = facialImage;
        return this;
    }

    /**
     * @return The rn
     */
    public GclPivPrintedInformation getPrintedInformation() {
        return printedInformation;
    }

    /**
     * @param printedInformation The printed information
     */
    public void setPrintedInformation(GclPivPrintedInformation printedInformation) {
        this.printedInformation = printedInformation;
    }

    public PivAllData withPrintedInformation(GclPivPrintedInformation printedInformation) {
        this.printedInformation = printedInformation;
        return this;
    }

    @Override
    public String toString() {
        return "PivAllData{" +
                "authenticationCertificate=" + authenticationCertificate +
                ", signingCertificate=" + signingCertificate +
                ", facialImage=" + facialImage +
                ", printedInformation=" + printedInformation +
                '}';
    }
}
