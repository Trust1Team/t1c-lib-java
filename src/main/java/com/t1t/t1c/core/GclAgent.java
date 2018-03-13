
package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class GclAgent {

    @SerializedName("challenge")
    @Expose
    private String challenge;
    @SerializedName("hostname")
    @Expose
    private String hostname;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("metadata")
    @Expose
    private Map<String, String> metadata;
    @SerializedName("port")
    @Expose
    private Integer port;
    @SerializedName("username")
    @Expose
    private String username;

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

    public GclAgent withChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    /**
     * 
     * @return
     *     The hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * 
     * @param hostname
     *     The hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public GclAgent withHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    /**
     * 
     * @return
     *     The lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * 
     * @param lastUpdate
     *     The last_update
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public GclAgent withLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    /**
     * 
     * @return
     *     The metadata
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * 
     * @param metadata
     *     The metadata
     */
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public GclAgent withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * 
     * @return
     *     The port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 
     * @param port
     *     The port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    public GclAgent withPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public GclAgent withUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(challenge).append(hostname).append(lastUpdate).append(metadata).append(port).append(username).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclAgent) == false) {
            return false;
        }
        GclAgent rhs = ((GclAgent) other);
        return new EqualsBuilder().append(challenge, rhs.challenge).append(hostname, rhs.hostname).append(lastUpdate, rhs.lastUpdate).append(metadata, rhs.metadata).append(port, rhs.port).append(username, rhs.username).isEquals();
    }

}
