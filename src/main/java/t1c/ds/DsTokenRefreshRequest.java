
package t1c.ds;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DsTokenRefreshRequest {

    @SerializedName("originalJWT")
    @Expose
    private String originalJWT;

    /**
     * 
     * @return
     *     The originalJWT
     */
    public String getOriginalJWT() {
        return originalJWT;
    }

    /**
     * 
     * @param originalJWT
     *     The originalJWT
     */
    public void setOriginalJWT(String originalJWT) {
        this.originalJWT = originalJWT;
    }

    public DsTokenRefreshRequest withOriginalJWT(String originalJWT) {
        this.originalJWT = originalJWT;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(originalJWT).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsTokenRefreshRequest) == false) {
            return false;
        }
        DsTokenRefreshRequest rhs = ((DsTokenRefreshRequest) other);
        return new EqualsBuilder().append(originalJWT, rhs.originalJWT).isEquals();
    }

}
