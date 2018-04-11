package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class DsContainerResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("osStorage")
    @Expose
    private List<DsContainerStorage> osStorage = new ArrayList<DsContainerStorage>();
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("dependsOn")
    @Expose
    private List<String> dependsOn = new ArrayList<String>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("allowedOrigins")
    @Expose
    private List<String> allowedOrigins = new ArrayList<String>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public DsContainerResponse withId(String id) {
        this.id = id;
        return this;
    }

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

    public DsContainerResponse withName(String name) {
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

    public DsContainerResponse withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * @return The osStorage
     */
    public List<DsContainerStorage> getOsStorage() {
        return osStorage;
    }

    /**
     * @param osStorage The osStorage
     */
    public void setOsStorage(List<DsContainerStorage> osStorage) {
        this.osStorage = osStorage;
    }

    public DsContainerResponse withOsStorage(List<DsContainerStorage> osStorage) {
        this.osStorage = osStorage;
        return this;
    }

    /**
     * @return The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    public DsContainerResponse withLanguage(String language) {
        this.language = language;
        return this;
    }

    /**
     * @return The availability
     */
    public String getAvailability() {
        return availability;
    }

    /**
     * @param availability The availability
     */
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public DsContainerResponse withAvailability(String availability) {
        this.availability = availability;
        return this;
    }

    /**
     * @return The dependsOn
     */
    public List<String> getDependsOn() {
        return dependsOn;
    }

    /**
     * @param dependsOn The dependsOn
     */
    public void setDependsOn(List<String> dependsOn) {
        this.dependsOn = dependsOn;
    }

    public DsContainerResponse withDependsOn(List<String> dependsOn) {
        this.dependsOn = dependsOn;
        return this;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public DsContainerResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return The allowedOrigins
     */
    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    /**
     * @param allowedOrigins The allowedOrigins
     */
    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public DsContainerResponse withAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(version).append(osStorage).append(language).append(availability).append(dependsOn).append(status).append(allowedOrigins).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsContainerResponse) == false) {
            return false;
        }
        DsContainerResponse rhs = ((DsContainerResponse) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(version, rhs.version).append(osStorage, rhs.osStorage).append(language, rhs.language).append(availability, rhs.availability).append(dependsOn, rhs.dependsOn).append(status, rhs.status).append(allowedOrigins, rhs.allowedOrigins).isEquals();
    }

}
