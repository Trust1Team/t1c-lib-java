package com.t1t.t1c.gcl;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGclAdminClient {

    boolean activate();

    String getPublicKey();

    boolean setPublicKey(String publicKey);
}
