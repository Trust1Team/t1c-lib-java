package com.t1t.t1c.gcl;

import com.t1t.t1c.exceptions.GclAdminClientException;
import com.t1t.t1c.model.DsPublicKeyEncoding;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGclAdminClient {

    String getUrl();

    boolean activate() throws GclAdminClientException;

    String getPublicKey() throws GclAdminClientException;

    String getPublicKey(DsPublicKeyEncoding encoding) throws GclAdminClientException;

    boolean setPublicKey(String publicKey) throws GclAdminClientException;
}
