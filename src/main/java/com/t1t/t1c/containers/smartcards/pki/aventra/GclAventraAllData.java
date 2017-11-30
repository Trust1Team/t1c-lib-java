
package com.t1t.t1c.containers.smartcards.pki.aventra;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclAventraAllData implements AllData
{

    @SerializedName("applet_info")
    @Expose
    private GclAventraAppletInfo appletInfo;
    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("encryption_certificate")
    @Expose
    private String encryptionCertificate;
    @SerializedName("issuer_certificate")
    @Expose
    private String issuerCertificate;
    @SerializedName("root_certificate")
    @Expose
    private String rootCertificate;
    @SerializedName("signing_certificate")
    @Expose
    private String signingCertificate;

    /**
     * 
     * @return
     *     The appletInfo
     */
    public GclAventraAppletInfo getAppletInfo() {
        return appletInfo;
    }

    /**
     * 
     * @param appletInfo
     *     The applet_info
     */
    public void setAppletInfo(GclAventraAppletInfo appletInfo) {
        this.appletInfo = appletInfo;
    }

    public GclAventraAllData withAppletInfo(GclAventraAppletInfo appletInfo) {
        this.appletInfo = appletInfo;
        return this;
    }

    /**
     * 
     * @return
     *     The authenticationCertificate
     */
    public String getAuthenticationCertificate() {
        return authenticationCertificate;
    }

    /**
     * 
     * @param authenticationCertificate
     *     The authentication_certificate
     */
    public void setAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
    }

    public GclAventraAllData withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The encryptionCertificate
     */
    public String getEncryptionCertificate() {
        return encryptionCertificate;
    }

    /**
     * 
     * @param encryptionCertificate
     *     The encryption_certificate
     */
    public void setEncryptionCertificate(String encryptionCertificate) {
        this.encryptionCertificate = encryptionCertificate;
    }

    public GclAventraAllData withEncryptionCertificate(String encryptionCertificate) {
        this.encryptionCertificate = encryptionCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The issuerCertificate
     */
    public String getIssuerCertificate() {
        return issuerCertificate;
    }

    /**
     * 
     * @param issuerCertificate
     *     The issuer_certificate
     */
    public void setIssuerCertificate(String issuerCertificate) {
        this.issuerCertificate = issuerCertificate;
    }

    public GclAventraAllData withIssuerCertificate(String issuerCertificate) {
        this.issuerCertificate = issuerCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The rootCertificate
     */
    public String getRootCertificate() {
        return rootCertificate;
    }

    /**
     * 
     * @param rootCertificate
     *     The root_certificate
     */
    public void setRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
    }

    public GclAventraAllData withRootCertificate(String rootCertificate) {
        this.rootCertificate = rootCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The signingCertificate
     */
    public String getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * 
     * @param signingCertificate
     *     The signing_certificate
     */
    public void setSigningCertificate(String signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public GclAventraAllData withSigningCertificate(String signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(appletInfo).append(authenticationCertificate).append(encryptionCertificate).append(issuerCertificate).append(rootCertificate).append(signingCertificate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclAventraAllData) == false) {
            return false;
        }
        GclAventraAllData rhs = ((GclAventraAllData) other);
        return new EqualsBuilder().append(appletInfo, rhs.appletInfo).append(authenticationCertificate, rhs.authenticationCertificate).append(encryptionCertificate, rhs.encryptionCertificate).append(issuerCertificate, rhs.issuerCertificate).append(rootCertificate, rhs.rootCertificate).append(signingCertificate, rhs.signingCertificate).isEquals();
    }

}
