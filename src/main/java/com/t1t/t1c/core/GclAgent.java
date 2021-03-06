
package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

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
    @SerializedName("port")
    @Expose
    private Long port;
    @SerializedName("type")
    @Expose
    private String type;
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
     *     The port
     */
    public Long getPort() {
        return port;
    }

    /**
     * 
     * @param port
     *     The port
     */
    public void setPort(Long port) {
        this.port = port;
    }

    public GclAgent withPort(Long port) {
        this.port = port;
        return this;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public GclAgent withType(String type) {
        this.type = type;
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
        return new HashCodeBuilder().append(challenge).append(hostname).append(lastUpdate).append(port).append(type).append(username).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclAgent)) {
            return false;
        }
        GclAgent rhs = ((GclAgent) other);
        return new EqualsBuilder().append(challenge, rhs.challenge).append(hostname, rhs.hostname).append(lastUpdate, rhs.lastUpdate).append(port, rhs.port).append(type, rhs.type).append(username, rhs.username).isEquals();
    }

}
