
package t1c.containers.smartcards.pki.luxtrust;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllCertificates;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclLuxTrustAllCertificates implements AllCertificates
{

    @SerializedName("authentication_certificate")
    @Expose
    private String authenticationCertificate;
    @SerializedName("signing_certificate")
    @Expose
    private String signingCertificate;
    @SerializedName("root_certificates")
    @Expose
    private List<String> rootCertificates = new ArrayList<String>();

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

    public GclLuxTrustAllCertificates withAuthenticationCertificate(String authenticationCertificate) {
        this.authenticationCertificate = authenticationCertificate;
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

    public GclLuxTrustAllCertificates withSigningCertificate(String signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The rootCertificates
     */
    public List<String> getRootCertificates() {
        return rootCertificates;
    }

    /**
     * 
     * @param rootCertificates
     *     The root_certificates
     */
    public void setRootCertificates(List<String> rootCertificates) {
        this.rootCertificates = rootCertificates;
    }

    public GclLuxTrustAllCertificates withRootCertificates(List<String> rootCertificates) {
        this.rootCertificates = rootCertificates;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authenticationCertificate).append(signingCertificate).append(rootCertificates).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclLuxTrustAllCertificates) == false) {
            return false;
        }
        GclLuxTrustAllCertificates rhs = ((GclLuxTrustAllCertificates) other);
        return new EqualsBuilder().append(authenticationCertificate, rhs.authenticationCertificate).append(signingCertificate, rhs.signingCertificate).append(rootCertificates, rhs.rootCertificates).isEquals();
    }

}
