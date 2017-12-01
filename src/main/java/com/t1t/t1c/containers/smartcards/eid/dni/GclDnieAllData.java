
package com.t1t.t1c.containers.smartcards.eid.dni;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclDnieAllData implements AllData
{

    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("info")
    @Expose
    private GclDnieInfo info;
    @SerializedName("intermediate_certificate")
    @Expose
    private String intermediateCertificate;
    @SerializedName("signing_certificate")
    @Expose
    private String signingCertificate;

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

    public GclDnieAllData withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The info
     */
    public GclDnieInfo getInfo() {
        return info;
    }

    /**
     * 
     * @param info
     *     The info
     */
    public void setInfo(GclDnieInfo info) {
        this.info = info;
    }

    public GclDnieAllData withInfo(GclDnieInfo info) {
        this.info = info;
        return this;
    }

    /**
     * 
     * @return
     *     The intermediateCertificate
     */
    public String getIntermediateCertificate() {
        return intermediateCertificate;
    }

    /**
     * 
     * @param intermediateCertificate
     *     The intermediate_certificate
     */
    public void setIntermediateCertificate(String intermediateCertificate) {
        this.intermediateCertificate = intermediateCertificate;
    }

    public GclDnieAllData withIntermediateCertificate(String intermediateCertificate) {
        this.intermediateCertificate = intermediateCertificate;
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

    public GclDnieAllData withSigningCertificate(String signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationCertificate).append(info).append(intermediateCertificate).append(signingCertificate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclDnieAllData) == false) {
            return false;
        }
        GclDnieAllData rhs = ((GclDnieAllData) other);
        return new EqualsBuilder().append(authenticationCertificate, rhs.authenticationCertificate).append(info, rhs.info).append(intermediateCertificate, rhs.intermediateCertificate).append(signingCertificate, rhs.signingCertificate).isEquals();
    }

}
