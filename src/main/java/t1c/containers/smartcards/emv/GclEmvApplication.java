
package t1c.containers.smartcards.emv;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclEmvApplication {

    @SerializedName("aid")
    @Expose
    private String aid;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("priority")
    @Expose
    private Integer priority;

    /**
     * 
     * @return
     *     The aid
     */
    public String getAid() {
        return aid;
    }

    /**
     * 
     * @param aid
     *     The aid
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    public GclEmvApplication withAid(String aid) {
        this.aid = aid;
        return this;
    }

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public GclEmvApplication withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * 
     * @return
     *     The priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 
     * @param priority
     *     The priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public GclEmvApplication withPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aid).append(label).append(priority).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclEmvApplication) == false) {
            return false;
        }
        GclEmvApplication rhs = ((GclEmvApplication) other);
        return new EqualsBuilder().append(aid, rhs.aid).append(label, rhs.label).append(priority, rhs.priority).isEquals();
    }

}
