package com.t1t.t1c.core;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public enum GclAlertPosition {

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
    private static Map<String, GclAlertPosition> constants = new HashMap<String, GclAlertPosition>();

    static {
        for (GclAlertPosition c : values()) {
            constants.put(c.value, c);
        }
    }

    private final String value;

    private GclAlertPosition(String value) {
        this.value = value;
    }

    public static GclAlertPosition fromValue(String value) {
        GclAlertPosition constant = constants.get(value);
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
