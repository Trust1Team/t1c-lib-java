package com.t1t.t1c.model;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public enum DsPublicKeyEncoding {
    PEM, DER;
    public String getQueryParamValue() {
        return this.name().toLowerCase();
    }
}
