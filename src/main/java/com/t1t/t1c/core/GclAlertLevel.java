package com.t1t.t1c.core;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public enum GclAlertLevel {

    @SerializedName("information")
    INFORMATION("information"),
    @SerializedName("question")
    QUESTION("question"),
    @SerializedName("warning")
    WARNING("warning"),
    @SerializedName("error")
    ERROR("error");
    private static Map<String, GclAlertLevel> constants = new HashMap<String, GclAlertLevel>();

    static {
        for (GclAlertLevel c : values()) {
            constants.put(c.value, c);
        }
    }

    private final String value;

    private GclAlertLevel(String value) {
        this.value = value;
    }

    public static GclAlertLevel fromValue(String value) {
        GclAlertLevel constant = constants.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

    @Override
    public String toString() {
        return this.value;
    }

}
