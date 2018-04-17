package com.t1t.t1c.core;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public enum GclPrivateKeyReference {

    @SerializedName("sign")
    SIGN("sign"),
    @SerializedName("authenticate")
    AUTHENTICATE("authenticate"),
    @SerializedName("non-repudiation")
    NON_REPUDIATION("non-repudiation"),
    @SerializedName("key")
    KEY("key"),
    @SerializedName("private_key")
    PRIVATE_KEY("private_key");
    private static Map<String, GclPrivateKeyReference> constants = new HashMap<String, GclPrivateKeyReference>();

    static {
        for (GclPrivateKeyReference c : values()) {
            constants.put(c.value, c);
        }
    }

    private final String value;

    private GclPrivateKeyReference(String value) {
        this.value = value;
    }

    public static GclPrivateKeyReference fromValue(String value) {
        GclPrivateKeyReference constant = constants.get(value);
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
