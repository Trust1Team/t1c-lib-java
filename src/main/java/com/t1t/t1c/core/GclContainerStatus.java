package com.t1t.t1c.core;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public enum GclContainerStatus {

    @SerializedName("DOWNLOAD_ERROR")
    DOWNLOAD_ERROR("DOWNLOAD_ERROR"),
    @SerializedName("INIT")
    INIT("INIT"),
    @SerializedName("DOWNLOADING")
    DOWNLOADING("DOWNLOADING"),
    @SerializedName("INSTALLED")
    INSTALLED("INSTALLED"),
    @SerializedName("ERROR")
    ERROR("ERROR");
    private static Map<String, GclContainerStatus> constants = new HashMap<String, GclContainerStatus>();

    static {
        for (GclContainerStatus c : values()) {
            constants.put(c.value, c);
        }
    }

    private final String value;

    private GclContainerStatus(String value) {
        this.value = value;
    }

    public static GclContainerStatus fromValue(String value) {
        GclContainerStatus constant = constants.get(value);
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
