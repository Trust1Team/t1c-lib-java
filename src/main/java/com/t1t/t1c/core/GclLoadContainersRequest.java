package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.ds.DsContainerResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class GclLoadContainersRequest {

    @SerializedName("containerResponses")
    @Expose
    private List<DsContainerResponse> containerResponses = new ArrayList<DsContainerResponse>();

    /**
     * @return The containerResponses
     */
    public List<DsContainerResponse> getContainerResponses() {
        return containerResponses;
    }

    /**
     * @param containerResponses The containerResponses
     */
    public void setContainerResponses(List<DsContainerResponse> containerResponses) {
        this.containerResponses = containerResponses;
    }

    public GclLoadContainersRequest withContainerResponses(List<DsContainerResponse> containerResponses) {
        this.containerResponses = containerResponses;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(containerResponses).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclLoadContainersRequest) == false) {
            return false;
        }
        GclLoadContainersRequest rhs = ((GclLoadContainersRequest) other);
        return new EqualsBuilder().append(containerResponses, rhs.containerResponses).isEquals();
    }

}
