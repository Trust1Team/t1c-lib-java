package com.t1t.t1c.model;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public enum DigestAlgorithm {
    SHA1, SHA256, SHA512, MD5;

    public String getStringValue() {
        return this.name().toLowerCase();
    }

    public static DigestAlgorithm getAlgoForRef(String ref) {
        String parsed = ref.replace("-", "").toUpperCase();
        return DigestAlgorithm.valueOf(parsed);
    }
}
