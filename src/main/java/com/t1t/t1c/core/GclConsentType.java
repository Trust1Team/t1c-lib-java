package com.t1t.t1c.core;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public enum GclConsentType {

    @SerializedName("reader")
    READER("reader"),
    @SerializedName("file_exchange")
    FILE_EXCHANGE("file_exchange");
    private static Map<String, GclConsentType> constants = new HashMap<String, GclConsentType>();

    static {
        for (GclConsentType c : values()) {
            constants.put(c.value, c);
        }
    }

    private final String value;

    private GclConsentType(String value) {
        this.value = value;
    }

    public static GclConsentType fromValue(String value) {
        GclConsentType constant = constants.get(value);
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
