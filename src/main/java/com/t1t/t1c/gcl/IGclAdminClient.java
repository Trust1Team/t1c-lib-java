package com.t1t.t1c.gcl;

import com.t1t.t1c.exceptions.GclAdminClientException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGclAdminClient {

    String getUrl();

    boolean activate() throws GclAdminClientException;

    String getPublicKey() throws GclAdminClientException;

    boolean setPublicKey(String publicKey) throws GclAdminClientException;
}
