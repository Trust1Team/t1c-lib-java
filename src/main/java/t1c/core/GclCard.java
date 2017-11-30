
package t1c.core;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclCard {

    @SerializedName("atr")
    @Expose
    private String atr;
    @SerializedName("description")
    @Expose
    private List<String> description = new ArrayList<String>();

    /**
     * 
     * @return
     *     The atr
     */
    public String getAtr() {
        return atr;
    }

    /**
     * 
     * @param atr
     *     The atr
     */
    public void setAtr(String atr) {
        this.atr = atr;
    }

    public GclCard withAtr(String atr) {
        this.atr = atr;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    public List<String> getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(List<String> description) {
        this.description = description;
    }

    public GclCard withDescription(List<String> description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(atr).append(description).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclCard) == false) {
            return false;
        }
        GclCard rhs = ((GclCard) other);
        return new EqualsBuilder().append(atr, rhs.atr).append(description, rhs.description).isEquals();
    }

}
