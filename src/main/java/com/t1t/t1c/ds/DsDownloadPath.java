package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsDownloadPath {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("fileName")
    @Expose
    private String fileName;

    /**
     * @return The path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path The path
     */
    public void setPath(String path) {
        this.path = path;
    }

    public DsDownloadPath withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * @return The fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName The fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DsDownloadPath withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(path).append(fileName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DsDownloadPath) == false) {
            return false;
        }
        DsDownloadPath rhs = ((DsDownloadPath) other);
        return new EqualsBuilder().append(path, rhs.path).append(fileName, rhs.fileName).isEquals();
    }

}
