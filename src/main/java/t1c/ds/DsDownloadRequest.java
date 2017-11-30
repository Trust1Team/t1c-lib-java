
package t1c.ds;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DsDownloadRequest {

    @SerializedName("os")
    @Expose
    private DsOs os;
    @SerializedName("browser")
    @Expose
    private DsBrowser browser;
    @SerializedName("ua")
    @Expose
    private String ua;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;

    /**
     * 
     * @return
     *     The os
     */
    public DsOs getOs() {
        return os;
    }

    /**
     * 
     * @param os
     *     The os
     */
    public void setOs(DsOs os) {
        this.os = os;
    }

    public DsDownloadRequest withOs(DsOs os) {
        this.os = os;
        return this;
    }

    /**
     * 
     * @return
     *     The browser
     */
    public DsBrowser getBrowser() {
        return browser;
    }

    /**
     * 
     * @param browser
     *     The browser
     */
    public void setBrowser(DsBrowser browser) {
        this.browser = browser;
    }

    public DsDownloadRequest withBrowser(DsBrowser browser) {
        this.browser = browser;
        return this;
    }

    /**
     * 
     * @return
     *     The ua
     */
    public String getUa() {
        return ua;
    }

    /**
     * 
     * @param ua
     *     The ua
     */
    public void setUa(String ua) {
        this.ua = ua;
    }

    public DsDownloadRequest withUa(String ua) {
        this.ua = ua;
        return this;
    }

    /**
     * 
     * @return
     *     The manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 
     * @param manufacturer
     *     The manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public DsDownloadRequest withManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(os).append(browser).append(ua).append(manufacturer).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsDownloadRequest) == false) {
            return false;
        }
        DsDownloadRequest rhs = ((DsDownloadRequest) other);
        return new EqualsBuilder().append(os, rhs.os).append(browser, rhs.browser).append(ua, rhs.ua).append(manufacturer, rhs.manufacturer).isEquals();
    }

}
