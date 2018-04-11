
package com.t1t.t1c.containers.readerapi;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclReaderApiCommandRequest {

    @SerializedName("tx")
    @Expose
    private String tx;

    /**
     * 
     * @return
     *     The tx
     */
    public String getTx() {
        return tx;
    }

    /**
     * 
     * @param tx
     *     The tx
     */
    public void setTx(String tx) {
        this.tx = tx;
    }

    public GclReaderApiCommandRequest withTx(String tx) {
        this.tx = tx;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tx).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclReaderApiCommandRequest) == false) {
            return false;
        }
        GclReaderApiCommandRequest rhs = ((GclReaderApiCommandRequest) other);
        return new EqualsBuilder().append(tx, rhs.tx).isEquals();
    }

}
