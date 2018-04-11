
package com.t1t.t1c.containers.readerapi;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class GclReaderApiApdu {

    @SerializedName("cla")
    @Expose
    private String cla;
    @SerializedName("ins")
    @Expose
    private String ins;
    @SerializedName("p1")
    @Expose
    private String p1;
    @SerializedName("p2")
    @Expose
    private String p2;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("le")
    @Expose
    private String le;

    /**
     * 
     * @return
     *     The cla
     */
    public String getCla() {
        return cla;
    }

    /**
     * 
     * @param cla
     *     The cla
     */
    public void setCla(String cla) {
        this.cla = cla;
    }

    public GclReaderApiApdu withCla(String cla) {
        this.cla = cla;
        return this;
    }

    /**
     * 
     * @return
     *     The ins
     */
    public String getIns() {
        return ins;
    }

    /**
     * 
     * @param ins
     *     The ins
     */
    public void setIns(String ins) {
        this.ins = ins;
    }

    public GclReaderApiApdu withIns(String ins) {
        this.ins = ins;
        return this;
    }

    /**
     * 
     * @return
     *     The p1
     */
    public String getP1() {
        return p1;
    }

    /**
     * 
     * @param p1
     *     The p1
     */
    public void setP1(String p1) {
        this.p1 = p1;
    }

    public GclReaderApiApdu withP1(String p1) {
        this.p1 = p1;
        return this;
    }

    /**
     * 
     * @return
     *     The p2
     */
    public String getP2() {
        return p2;
    }

    /**
     * 
     * @param p2
     *     The p2
     */
    public void setP2(String p2) {
        this.p2 = p2;
    }

    public GclReaderApiApdu withP2(String p2) {
        this.p2 = p2;
        return this;
    }

    /**
     * 
     * @return
     *     The data
     */
    public String getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(String data) {
        this.data = data;
    }

    public GclReaderApiApdu withData(String data) {
        this.data = data;
        return this;
    }

    /**
     * 
     * @return
     *     The le
     */
    public String getLe() {
        return le;
    }

    /**
     * 
     * @param le
     *     The le
     */
    public void setLe(String le) {
        this.le = le;
    }

    public GclReaderApiApdu withLe(String le) {
        this.le = le;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cla).append(ins).append(p1).append(p2).append(data).append(le).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclReaderApiApdu) == false) {
            return false;
        }
        GclReaderApiApdu rhs = ((GclReaderApiApdu) other);
        return new EqualsBuilder().append(cla, rhs.cla).append(ins, rhs.ins).append(p1, rhs.p1).append(p2, rhs.p2).append(data, rhs.data).append(le, rhs.le).isEquals();
    }

}
