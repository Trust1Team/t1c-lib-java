
package com.t1t.t1c.core;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

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
    private final String value;
    private static Map<String, GclPrivateKeyReference> constants = new HashMap<String, GclPrivateKeyReference>();

    static {
        for (GclPrivateKeyReference c: values()) {
            constants.put(c.value, c);
        }
    }

    private GclPrivateKeyReference(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static GclPrivateKeyReference fromValue(String value) {
        GclPrivateKeyReference constant = constants.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
