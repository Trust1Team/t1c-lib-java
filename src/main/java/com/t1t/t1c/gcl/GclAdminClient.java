package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.LibConfig;
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
    public boolean activate() {
        return isCallSuccessful(executeCall(getHttpClient().activate()));
    }

    @Override
    public String getPublicKey() {
        return returnData(getHttpClient().getPublicKey());
    }

    @Override
    public boolean setPublicKey(String publicKey) {
        GclUpdatePublicKeyRequest request = new GclUpdatePublicKeyRequest().withCertificate(publicKey);
        return isCallSuccessful(executeCall(getHttpClient().setPublicKey(request)));
    }
}