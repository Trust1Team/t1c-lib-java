
package t1c.core;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclConsent {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("durationInDays")
    @Expose
    private Integer durationInDays;

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public GclConsent withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    public GclConsent withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * 
     * @return
     *     The durationInDays
     */
    public Integer getDurationInDays() {
        return durationInDays;
    }

    /**
     * 
     * @param durationInDays
     *     The durationInDays
     */
    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public GclConsent withDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(text).append(durationInDays).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclConsent) == false) {
            return false;
        }
        GclConsent rhs = ((GclConsent) other);
        return new EqualsBuilder().append(title, rhs.title).append(text, rhs.text).append(durationInDays, rhs.durationInDays).isEquals();
    }

}
