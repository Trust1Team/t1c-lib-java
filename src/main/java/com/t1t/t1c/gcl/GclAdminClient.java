package com.t1t.t1c.gcl;

import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclUpdatePublicKeyRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclAdminClientException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.rest.RestExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public class GclAdminClient implements IGclAdminClient {

    private static final Logger log = LoggerFactory.getLogger(GclAdminClient.class);

    private GclAdminRestClient gclAdminRestClient;

    public GclAdminClient(GclAdminRestClient gclAdminRestClient) {
        this.gclAdminRestClient = gclAdminRestClient;
    }

    @Override
    public Boolean activate() throws GclAdminClientException {
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.activate()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not activate GCL", ex);
        }
    }

    @Override
    public Boolean setPublicKey(String publicKey) throws GclAdminClientException {
        GclUpdatePublicKeyRequest request = new GclUpdatePublicKeyRequest().withCertificate(publicKey);
        try {
            return RestExecutor.isCallSuccessful(RestExecutor.executeCall(gclAdminRestClient.setPublicKey(request)));
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not set GCL public key", ex);
        }
    }

}