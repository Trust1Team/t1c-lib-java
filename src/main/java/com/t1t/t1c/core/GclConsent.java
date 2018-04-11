
package com.t1t.t1c.core;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    private Integer days;
    @SerializedName("alert_level")
    @Expose
    private AlertLevel alertLevel;
    @SerializedName("alert_position")
    @Expose
    private AlertPosition alertPosition;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("timeout")
    @Expose
    private Integer timeout;

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public GclConsent withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    public GclConsent withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * 
     * @return
     *     The days
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 
     * @param days
     *     The days
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    public GclConsent withDays(Integer days) {
        this.days = days;
        return this;
    }

    /**
     * 
     * @return
     *     The alertLevel
     */
    public AlertLevel getAlertLevel() {
        return alertLevel;
    }

    /**
     * 
     * @param alertLevel
     *     The alert_level
     */
    public void setAlertLevel(AlertLevel alertLevel) {
        this.alertLevel = alertLevel;
    }

    public GclConsent withAlertLevel(AlertLevel alertLevel) {
        this.alertLevel = alertLevel;
        return this;
    }

    /**
     * 
     * @return
     *     The alertPosition
     */
    public AlertPosition getAlertPosition() {
        return alertPosition;
    }

    /**
     * 
     * @param alertPosition
     *     The alert_position
     */
    public void setAlertPosition(AlertPosition alertPosition) {
        this.alertPosition = alertPosition;
    }

    public GclConsent withAlertPosition(AlertPosition alertPosition) {
        this.alertPosition = alertPosition;
        return this;
    }

    /**
     * 
     * @return
     *     The type
     */
    public Type getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(Type type) {
        this.type = type;
    }

    public GclConsent withType(Type type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * @return
     *     The timeout
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * 
     * @param timeout
     *     The timeout
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public GclConsent withTimeout(Integer timeout) {
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

    @Generated("org.jsonschema2pojo")
    public static enum AlertLevel {

        @SerializedName("information")
        INFORMATION("information"),
        @SerializedName("question")
        QUESTION("question"),
        @SerializedName("warning")
        WARNING("warning"),
        @SerializedName("error")
        ERROR("error");
        private final String value;
        private static Map<String, AlertLevel> constants = new HashMap<String, AlertLevel>();

        static {
            for (AlertLevel c: values()) {
                constants.put(c.value, c);
            }
        }

        private AlertLevel(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static AlertLevel fromValue(String value) {
            AlertLevel constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public static enum AlertPosition {

        @SerializedName("standard")
        STANDARD("standard"),
        @SerializedName("center")
        CENTER("center"),
        @SerializedName("left")
        LEFT("left"),
        @SerializedName("right")
        RIGHT("right"),
        @SerializedName("top")
        TOP("top"),
        @SerializedName("top_left")
        TOP_LEFT("top_left"),
        @SerializedName("top_right")
        TOP_RIGHT("top_right"),
        @SerializedName("bottom")
        BOTTOM("bottom"),
        @SerializedName("bottom_left")
        BOTTOM_LEFT("bottom_left"),
        @SerializedName("bottom_right")
        BOTTOM_RIGHT("bottom_right");
        private final String value;
        private static Map<String, AlertPosition> constants = new HashMap<String, AlertPosition>();

        static {
            for (AlertPosition c: values()) {
                constants.put(c.value, c);
            }
        }

        private AlertPosition(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static AlertPosition fromValue(String value) {
            AlertPosition constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public static enum Type {

        @SerializedName("reader")
        READER("reader"),
        @SerializedName("file_exchange")
        FILE_EXCHANGE("file_exchange");
        private final String value;
        private static Map<String, Type> constants = new HashMap<String, Type>();

        static {
            for (Type c: values()) {
                constants.put(c.value, c);
            }
        }

        private Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static Type fromValue(String value) {
            Type constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
