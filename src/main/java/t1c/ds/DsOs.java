
package t1c.ds;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DsOs {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("architecture")
    @Expose
    private String architecture;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public DsOs withName(String name) {
        this.name = name;
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

    public DsOs withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * @return
     *     The architecture
     */
    public String getArchitecture() {
        return architecture;
    }

    /**
     * 
     * @param architecture
     *     The architecture
     */
    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public DsOs withArchitecture(String architecture) {
        this.architecture = architecture;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(version).append(architecture).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsOs) == false) {
            return false;
        }
        DsOs rhs = ((DsOs) other);
        return new EqualsBuilder().append(name, rhs.name).append(version, rhs.version).append(architecture, rhs.architecture).isEquals();
    }

}
