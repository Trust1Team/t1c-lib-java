package com.t1t.t1c.ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DsError {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("moreInfoUrl")
    @Expose
    private String moreInfoUrl;
    @SerializedName("stackTrace")
    @Expose
    private String stackTrace;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public DsError withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The errorCode
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public DsError withErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public DsError withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * @return The moreInfoUrl
     */
    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    /**
     * @param moreInfoUrl The moreInfoUrl
     */
    public void setMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
    }

    public DsError withMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
        return this;
    }

    /**
     * @return The stackTrace
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * @param stackTrace The stackTrace
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public DsError withStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(errorCode).append(message).append(moreInfoUrl).append(stackTrace).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DsError)) {
            return false;
        }
        DsError rhs = ((DsError) other);
        return new EqualsBuilder().append(type, rhs.type).append(errorCode, rhs.errorCode).append(message, rhs.message).append(moreInfoUrl, rhs.moreInfoUrl).append(stackTrace, rhs.stackTrace).isEquals();
    }

}
