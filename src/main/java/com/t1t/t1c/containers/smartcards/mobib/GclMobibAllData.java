package com.t1t.t1c.containers.smartcards.mobib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class GclMobibAllData implements AllData {

    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("card-issuing")
    @Expose
    private GclMobibCardIssuing cardIssuing;
    @SerializedName("contracts")
    @Expose
    private List<GclMobibContract> contracts = new ArrayList<GclMobibContract>();
    @SerializedName("picture")
    @Expose
    private String picture;

    /**
     * @return The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    public GclMobibAllData withActive(Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * @return The cardIssuing
     */
    public GclMobibCardIssuing getCardIssuing() {
        return cardIssuing;
    }

    /**
     * @param cardIssuing The card-issuing
     */
    public void setCardIssuing(GclMobibCardIssuing cardIssuing) {
        this.cardIssuing = cardIssuing;
    }

    public GclMobibAllData withCardIssuing(GclMobibCardIssuing cardIssuing) {
        this.cardIssuing = cardIssuing;
        return this;
    }

    /**
     * @return The contracts
     */
    public List<GclMobibContract> getContracts() {
        return contracts;
    }

    /**
     * @param contracts The contracts
     */
    public void setContracts(List<GclMobibContract> contracts) {
        this.contracts = contracts;
    }

    public GclMobibAllData withContracts(List<GclMobibContract> contracts) {
        this.contracts = contracts;
        return this;
    }

    /**
     * @return The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public GclMobibAllData withPicture(String picture) {
        this.picture = picture;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(active).append(cardIssuing).append(contracts).append(picture).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclMobibAllData)) {
            return false;
        }
        GclMobibAllData rhs = ((GclMobibAllData) other);
        return new EqualsBuilder().append(active, rhs.active).append(cardIssuing, rhs.cardIssuing).append(contracts, rhs.contracts).append(picture, rhs.picture).isEquals();
    }

}
