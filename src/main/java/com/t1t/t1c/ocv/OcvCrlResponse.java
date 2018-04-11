
package com.t1t.t1c.ocv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class OcvCrlResponse {

    @SerializedName("productionDate")
    @Expose
    private String productionDate;
    @SerializedName("issuerCertificate")
    @Expose
    private String issuerCertificate;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("crlLocations")
    @Expose
    private List<String> crlLocations = new ArrayList<String>();
    @SerializedName("signatureAlgorithm")
    @Expose
    private String signatureAlgorithm;
    @SerializedName("status")
    @Expose
    private Boolean status;

    /**
     * 
     * @return
     *     The productionDate
     */
    public String getProductionDate() {
        return productionDate;
    }

    /**
     * 
     * @param productionDate
     *     The productionDate
     */
    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public OcvCrlResponse withProductionDate(String productionDate) {
        this.productionDate = productionDate;
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
     *     The issuerCertificate
     */
    public void setIssuerCertificate(String issuerCertificate) {
        this.issuerCertificate = issuerCertificate;
    }

    public OcvCrlResponse withIssuerCertificate(String issuerCertificate) {
        this.issuerCertificate = issuerCertificate;
        return this;
    }

    /**
     * 
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public OcvCrlResponse withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * @return
     *     The crlLocations
     */
    public List<String> getCrlLocations() {
        return crlLocations;
    }

    /**
     * 
     * @param crlLocations
     *     The crlLocations
     */
    public void setCrlLocations(List<String> crlLocations) {
        this.crlLocations = crlLocations;
    }

    public OcvCrlResponse withCrlLocations(List<String> crlLocations) {
        this.crlLocations = crlLocations;
        return this;
    }

    /**
     * 
     * @return
     *     The signatureAlgorithm
     */
    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    /**
     * 
     * @param signatureAlgorithm
     *     The signatureAlgorithm
     */
    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public OcvCrlResponse withSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
        return this;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public OcvCrlResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productionDate).append(issuerCertificate).append(version).append(crlLocations).append(signatureAlgorithm).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OcvCrlResponse) == false) {
            return false;
        }
        OcvCrlResponse rhs = ((OcvCrlResponse) other);
        return new EqualsBuilder().append(productionDate, rhs.productionDate).append(issuerCertificate, rhs.issuerCertificate).append(version, rhs.version).append(crlLocations, rhs.crlLocations).append(signatureAlgorithm, rhs.signatureAlgorithm).append(status, rhs.status).isEquals();
    }

}
