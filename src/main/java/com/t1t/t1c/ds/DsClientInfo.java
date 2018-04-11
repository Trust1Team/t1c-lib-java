
package com.t1t.t1c.ds;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DsClientInfo {

    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("version")
    @Expose
    private String version;

    /**
     * 
     * @return
     *     The type
     */
    public Type getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(Type type) {
        this.type = type;
    }

    public DsClientInfo withType(Type type) {
        this.type = type;
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

    public DsClientInfo withVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsClientInfo) == false) {
            return false;
        }
        DsClientInfo rhs = ((DsClientInfo) other);
        return new EqualsBuilder().append(type, rhs.type).append(version, rhs.version).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Type {

        @SerializedName("JAVA")
        JAVA("JAVA"),
        @SerializedName("JAVASCRIPT")
        JAVASCRIPT("JAVASCRIPT");
        private final String value;
        private static Map<String, Type> constants = new HashMap<String, Type>();

        static {
            for (Type c: values()) {
                constants.put(c.value, c);
            }
        }

        private Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static Type fromValue(String value) {
            Type constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
