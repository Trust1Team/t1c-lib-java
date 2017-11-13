package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclAdminClientException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.DsPublicKeyEncoding;
import com.t1t.t1c.model.rest.GclUpdatePublicKeyRequest;
import com.t1t.t1c.rest.AbstractRestClient;
import com.t1t.t1c.rest.GclAdminRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclAdminClient extends AbstractRestClient<GclAdminRestClient> implements IGclAdminClient {

    private static final Logger log = LoggerFactory.getLogger(GclAdminClient.class);

    private LibConfig config;

    public GclAdminClient(LibConfig config, GclAdminRestClient httpClient) {
        super(httpClient);
        this.config = config;
    }

    @Override
    public String getUrl() {
        return config.getDsUri();
    }

    @Override
    public boolean activate() throws GclAdminClientException {
        try {
            return isCallSuccessful(executeCall(getHttpClient().activate()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not activate GCL", ex);
        }
    }

    @Override
    public String getPublicKey() throws GclAdminClientException {
        try {
            return returnData(getHttpClient().getPublicKey());
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not retrieve GCL public key", ex);
        }
    }

    @Override
    public String getPublicKey(DsPublicKeyEncoding encoding) throws GclAdminClientException {
        if (encoding == null) return getPublicKey();
        try {
            return returnData(getHttpClient().getPublicKey(encoding.getQueryParamValue()));
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not retrieve GCL public key", ex);
        }
    }

    @Override
    public boolean setPublicKey(String publicKey) throws GclAdminClientException {
        GclUpdatePublicKeyRequest request = new GclUpdatePublicKeyRequest().withCertificate(publicKey);
        try {
            return isCallSuccessful(executeCall(getHttpClient().setPublicKey(request)));
        } catch (RestException ex) {
            throw ExceptionFactory.gclAdminClientException("Could not set GCL public key", ex);
        }
    }
}