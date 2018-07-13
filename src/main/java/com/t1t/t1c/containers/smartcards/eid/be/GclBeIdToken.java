
package com.t1t.t1c.containers.smartcards.eid.be;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclBeIdToken {

    @SerializedName("eid_compliant")
    @Expose
    private Long eidCompliant;
    @SerializedName("electrical_perso_interface_version")
    @Expose
    private Long electricalPersoInterfaceVersion;
    @SerializedName("electrical_perso_version")
    @Expose
    private Long electricalPersoVersion;
    @SerializedName("graphical_perso_version")
    @Expose
    private Long graphicalPersoVersion;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("prn_generation")
    @Expose
    private Long prnGeneration;
    @SerializedName("raw_data")
    @Expose
    private String rawData;
    @SerializedName("serial_number")
    @Expose
    private String serialNumber;
    @SerializedName("version")
    @Expose
    private Long version;
    @SerializedName("version_rfu")
    @Expose
    private Long versionRfu;

    /**
     * 
     * @return
     *     The eidCompliant
     */
    public Long getEidCompliant() {
        return eidCompliant;
    }

    /**
     * 
     * @param eidCompliant
     *     The eid_compliant
     */
    public void setEidCompliant(Long eidCompliant) {
        this.eidCompliant = eidCompliant;
    }

    public GclBeIdToken withEidCompliant(Long eidCompliant) {
        this.eidCompliant = eidCompliant;
        return this;
    }

    /**
     * 
     * @return
     *     The electricalPersoInterfaceVersion
     */
    public Long getElectricalPersoInterfaceVersion() {
        return electricalPersoInterfaceVersion;
    }

    /**
     * 
     * @param electricalPersoInterfaceVersion
     *     The electrical_perso_interface_version
     */
    public void setElectricalPersoInterfaceVersion(Long electricalPersoInterfaceVersion) {
        this.electricalPersoInterfaceVersion = electricalPersoInterfaceVersion;
    }

    public GclBeIdToken withElectricalPersoInterfaceVersion(Long electricalPersoInterfaceVersion) {
        this.electricalPersoInterfaceVersion = electricalPersoInterfaceVersion;
        return this;
    }

    /**
     * 
     * @return
     *     The electricalPersoVersion
     */
    public Long getElectricalPersoVersion() {
        return electricalPersoVersion;
    }

    /**
     * 
     * @param electricalPersoVersion
     *     The electrical_perso_version
     */
    public void setElectricalPersoVersion(Long electricalPersoVersion) {
        this.electricalPersoVersion = electricalPersoVersion;
    }

    public GclBeIdToken withElectricalPersoVersion(Long electricalPersoVersion) {
        this.electricalPersoVersion = electricalPersoVersion;
        return this;
    }

    /**
     * 
     * @return
     *     The graphicalPersoVersion
     */
    public Long getGraphicalPersoVersion() {
        return graphicalPersoVersion;
    }

    /**
     * 
     * @param graphicalPersoVersion
     *     The graphical_perso_version
     */
    public void setGraphicalPersoVersion(Long graphicalPersoVersion) {
        this.graphicalPersoVersion = graphicalPersoVersion;
    }

    public GclBeIdToken withGraphicalPersoVersion(Long graphicalPersoVersion) {
        this.graphicalPersoVersion = graphicalPersoVersion;
        return this;
    }

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public GclBeIdToken withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * 
     * @return
     *     The prnGeneration
     */
    public Long getPrnGeneration() {
        return prnGeneration;
    }

    /**
     * 
     * @param prnGeneration
     *     The prn_generation
     */
    public void setPrnGeneration(Long prnGeneration) {
        this.prnGeneration = prnGeneration;
    }

    public GclBeIdToken withPrnGeneration(Long prnGeneration) {
        this.prnGeneration = prnGeneration;
        return this;
    }

    /**
     * 
     * @return
     *     The rawData
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * 
     * @param rawData
     *     The raw_data
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public GclBeIdToken withRawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    /**
     * 
     * @return
     *     The serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 
     * @param serialNumber
     *     The serial_number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public GclBeIdToken withSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    /**
     * 
     * @return
     *     The version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public GclBeIdToken withVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * @return
     *     The versionRfu
     */
    public Long getVersionRfu() {
        return versionRfu;
    }

    /**
     * 
     * @param versionRfu
     *     The version_rfu
     */
    public void setVersionRfu(Long versionRfu) {
        this.versionRfu = versionRfu;
    }

    public GclBeIdToken withVersionRfu(Long versionRfu) {
        this.versionRfu = versionRfu;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(eidCompliant).append(electricalPersoInterfaceVersion).append(electricalPersoVersion).append(graphicalPersoVersion).append(label).append(prnGeneration).append(rawData).append(serialNumber).append(version).append(versionRfu).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclBeIdToken)) {
            return false;
        }
        GclBeIdToken rhs = ((GclBeIdToken) other);
        return new EqualsBuilder().append(eidCompliant, rhs.eidCompliant).append(electricalPersoInterfaceVersion, rhs.electricalPersoInterfaceVersion).append(electricalPersoVersion, rhs.electricalPersoVersion).append(graphicalPersoVersion, rhs.graphicalPersoVersion).append(label, rhs.label).append(prnGeneration, rhs.prnGeneration).append(rawData, rhs.rawData).append(serialNumber, rhs.serialNumber).append(version, rhs.version).append(versionRfu, rhs.versionRfu).isEquals();
    }

}
