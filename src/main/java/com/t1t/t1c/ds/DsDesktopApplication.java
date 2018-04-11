package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class DsDesktopApplication {

    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("version")
    @Expose
    private String version;

    /**
     * @return The name
     */
    public Name getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(Name name) {
        this.name = name;
    }

    public DsDesktopApplication withName(Name name) {
        this.name = name;
        return this;
    }

    /**
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public DsDesktopApplication withVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DsDesktopApplication)) {
            return false;
        }
        DsDesktopApplication rhs = ((DsDesktopApplication) other);
        return new EqualsBuilder().append(name, rhs.name).append(version, rhs.version).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Name {

        @SerializedName("JAVA")
        JAVA("JAVA"),
        @SerializedName("DOTNET")
        DOTNET("DOTNET");
        private static Map<String, Name> constants = new HashMap<String, Name>();

        static {
            for (Name c : values()) {
                constants.put(c.value, c);
            }
        }

        private final String value;

        private Name(String value) {
            this.value = value;
        }

        public static Name fromValue(String value) {
            Name constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        @Override
        public String toString() {
            return this.value;
        }

    }

}
