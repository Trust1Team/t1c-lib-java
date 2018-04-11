package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class GclContainerInfo {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("status")
    @Expose
    private Status status;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public GclContainerInfo withName(String name) {
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

    public GclContainerInfo withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * @return The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    public GclContainerInfo withStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(version).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclContainerInfo) == false) {
            return false;
        }
        GclContainerInfo rhs = ((GclContainerInfo) other);
        return new EqualsBuilder().append(name, rhs.name).append(version, rhs.version).append(status, rhs.status).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum Status {

        @SerializedName("DOWNLOAD_ERROR")
        DOWNLOAD_ERROR("DOWNLOAD_ERROR"),
        @SerializedName("INIT")
        INIT("INIT"),
        @SerializedName("DOWNLOADING")
        DOWNLOADING("DOWNLOADING"),
        @SerializedName("INSTALLED")
        INSTALLED("INSTALLED"),
        @SerializedName("ERROR")
        ERROR("ERROR");
        private static Map<String, Status> constants = new HashMap<String, Status>();

        static {
            for (Status c : values()) {
                constants.put(c.value, c);
            }
        }

        private final String value;

        private Status(String value) {
            this.value = value;
        }

        public static Status fromValue(String value) {
            Status constant = constants.get(value);
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
