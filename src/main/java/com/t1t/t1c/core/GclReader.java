
package com.t1t.t1c.core;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclReader {

    @SerializedName("card")
    @Expose
    private GclCard card;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pinpad")
    @Expose
    private Boolean pinpad;

    /**
     * 
     * @return
     *     The card
     */
    public GclCard getCard() {
        return card;
    }

    /**
     * 
     * @param card
     *     The card
     */
    public void setCard(GclCard card) {
        this.card = card;
    }

    public GclReader withCard(GclCard card) {
        this.card = card;
        return this;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public GclReader withId(String id) {
        this.id = id;
        return this;
    }

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

    public GclReader withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The pinpad
     */
    public Boolean getPinpad() {
        return pinpad;
    }

    /**
     * 
     * @param pinpad
     *     The pinpad
     */
    public void setPinpad(Boolean pinpad) {
        this.pinpad = pinpad;
    }

    public GclReader withPinpad(Boolean pinpad) {
        this.pinpad = pinpad;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(card).append(id).append(name).append(pinpad).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclReader) == false) {
            return false;
        }
        GclReader rhs = ((GclReader) other);
        return new EqualsBuilder().append(card, rhs.card).append(id, rhs.id).append(name, rhs.name).append(pinpad, rhs.pinpad).isEquals();
    }

}
