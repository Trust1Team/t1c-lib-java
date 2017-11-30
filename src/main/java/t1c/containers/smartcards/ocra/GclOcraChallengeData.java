
package t1c.containers.smartcards.ocra;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclOcraChallengeData {

    @SerializedName("challenge")
    @Expose
    private String challenge;
    @SerializedName("pin")
    @Expose
    private String pin;

    /**
     * 
     * @return
     *     The challenge
     */
    public String getChallenge() {
        return challenge;
    }

    /**
     * 
     * @param challenge
     *     The challenge
     */
    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public GclOcraChallengeData withChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    /**
     * 
     * @return
     *     The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * 
     * @param pin
     *     The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    public GclOcraChallengeData withPin(String pin) {
        this.pin = pin;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(challenge).append(pin).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclOcraChallengeData) == false) {
            return false;
        }
        GclOcraChallengeData rhs = ((GclOcraChallengeData) other);
        return new EqualsBuilder().append(challenge, rhs.challenge).append(pin, rhs.pin).isEquals();
    }

}
