
package t1c.containers.smartcards.pki.oberthur;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclOberthurAllData implements AllData
{

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

    public GclOberthurAllData withAuthenticationCertificate(String authenticationCertificate) {
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

    public GclOberthurAllData withEncryptionCertificate(String encryptionCertificate) {
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

    public GclOberthurAllData withIssuerCertificate(String issuerCertificate) {
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

    public GclOberthurAllData withRootCertificate(String rootCertificate) {
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

    public GclOberthurAllData withSigningCertificate(String signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationCertificate).append(encryptionCertificate).append(issuerCertificate).append(rootCertificate).append(signingCertificate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclOberthurAllData) == false) {
            return false;
        }
        GclOberthurAllData rhs = ((GclOberthurAllData) other);
        return new EqualsBuilder().append(authenticationCertificate, rhs.authenticationCertificate).append(encryptionCertificate, rhs.encryptionCertificate).append(issuerCertificate, rhs.issuerCertificate).append(rootCertificate, rhs.rootCertificate).append(signingCertificate, rhs.signingCertificate).isEquals();
    }

}
