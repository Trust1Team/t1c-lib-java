package com.t1t.t1c.containers.smartcards.mobib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclMobibSpatial {

    @SerializedName("route_destination")
    @Expose
    private Boolean routeDestination;
    @SerializedName("route_origin")
    @Expose
    private Long routeOrigin;
    @SerializedName("type")
    @Expose
    private Long type;

    /**
     * @return The routeDestination
     */
    public Boolean getRouteDestination() {
        return routeDestination;
    }

    /**
     * @param routeDestination The route_destination
     */
    public void setRouteDestination(Boolean routeDestination) {
        this.routeDestination = routeDestination;
    }

    public GclMobibSpatial withRouteDestination(Boolean routeDestination) {
        this.routeDestination = routeDestination;
        return this;
    }

    /**
     * @return The routeOrigin
     */
    public Long getRouteOrigin() {
        return routeOrigin;
    }

    /**
     * @param routeOrigin The route_origin
     */
    public void setRouteOrigin(Long routeOrigin) {
        this.routeOrigin = routeOrigin;
    }

    public GclMobibSpatial withRouteOrigin(Long routeOrigin) {
        this.routeOrigin = routeOrigin;
        return this;
    }

    /**
     * @return The type
     */
    public Long getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(Long type) {
        this.type = type;
    }

    public GclMobibSpatial withType(Long type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(routeDestination).append(routeOrigin).append(type).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclMobibSpatial)) {
            return false;
        }
        GclMobibSpatial rhs = ((GclMobibSpatial) other);
        return new EqualsBuilder().append(routeDestination, rhs.routeDestination).append(routeOrigin, rhs.routeOrigin).append(type, rhs.type).isEquals();
    }

}
