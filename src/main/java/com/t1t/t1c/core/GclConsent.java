package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclConsent {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("days")
    @Expose
    private Long days;
    @SerializedName("alert_level")
    @Expose
    private GclAlertLevel alertLevel;
    @SerializedName("alert_position")
    @Expose
    private GclAlertPosition alertPosition;
    @SerializedName("type")
    @Expose
    private GclConsentType type;
    @SerializedName("timeout")
    @Expose
    private Long timeout;

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public GclConsent withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    public GclConsent withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * @return The days
     */
    public Long getDays() {
        return days;
    }

    /**
     * @param days The days
     */
    public void setDays(Long days) {
        this.days = days;
    }

    public GclConsent withDays(Long days) {
        this.days = days;
        return this;
    }

    /**
     * @return The alertLevel
     */
    public GclAlertLevel getAlertLevel() {
        return alertLevel;
    }

    /**
     * @param alertLevel The alert_level
     */
    public void setAlertLevel(GclAlertLevel alertLevel) {
        this.alertLevel = alertLevel;
    }

    public GclConsent withAlertLevel(GclAlertLevel alertLevel) {
        this.alertLevel = alertLevel;
        return this;
    }

    /**
     * @return The alertPosition
     */
    public GclAlertPosition getAlertPosition() {
        return alertPosition;
    }

    /**
     * @param alertPosition The alert_position
     */
    public void setAlertPosition(GclAlertPosition alertPosition) {
        this.alertPosition = alertPosition;
    }

    public GclConsent withAlertPosition(GclAlertPosition alertPosition) {
        this.alertPosition = alertPosition;
        return this;
    }

    /**
     * @return The type
     */
    public GclConsentType getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(GclConsentType type) {
        this.type = type;
    }

    public GclConsent withType(GclConsentType type) {
        this.type = type;
        return this;
    }

    /**
     * @return The timeout
     */
    public Long getTimeout() {
        return timeout;
    }

    /**
     * @param timeout The timeout
     */
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public GclConsent withTimeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(text).append(days).append(alertLevel).append(alertPosition).append(type).append(timeout).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GclConsent) == false) {
            return false;
        }
        GclConsent rhs = ((GclConsent) other);
        return new EqualsBuilder().append(title, rhs.title).append(text, rhs.text).append(days, rhs.days).append(alertLevel, rhs.alertLevel).append(alertPosition, rhs.alertPosition).append(type, rhs.type).append(timeout, rhs.timeout).isEquals();
    }

}
