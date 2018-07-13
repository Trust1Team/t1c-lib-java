
package com.t1t.t1c.core;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public enum GclPinReference {

    @SerializedName("user")
    USER("user"),
    @SerializedName("admin")
    ADMIN("admin");
    private final String value;
    private static Map<String, GclPinReference> constants = new HashMap<String, GclPinReference>();

    static {
        for (GclPinReference c: values()) {
            constants.put(c.value, c);
        }
    }

    private GclPinReference(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static GclPinReference fromValue(String value) {
        GclPinReference constant = constants.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
