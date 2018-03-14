package com.t1t.t1c.containers.smartcards.emv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.t1t.t1c.model.AllData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class GclEmvAllData implements AllData {

    @SerializedName("applications")
    @Expose
    private List<GclEmvApplication> applications = new ArrayList<GclEmvApplication>();
    @SerializedName("application_data")
    @Expose
    private GclEmvApplicationData applicationData;

    /**
     * @return The applications
     */
    public List<GclEmvApplication> getApplications() {
        return applications;
    }

    /**
     * @param applications The applications
     */
    public void setApplications(List<GclEmvApplication> applications) {
        this.applications = applications;
    }

    public GclEmvAllData withApplications(List<GclEmvApplication> applications) {
        this.applications = applications;
        return this;
    }

    /**
     * @return The applicationData
     */
    public GclEmvApplicationData getApplicationData() {
        return applicationData;
    }

    /**
     * @param applicationData The application_data
     */
    public void setApplicationData(GclEmvApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public GclEmvAllData withApplicationData(GclEmvApplicationData applicationData) {
        this.applicationData = applicationData;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(applications).append(applicationData).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclEmvAllData)) {
            return false;
        }
        GclEmvAllData rhs = ((GclEmvAllData) other);
        return new EqualsBuilder().append(applications, rhs.applications).append(applicationData, rhs.applicationData).isEquals();
    }

}
