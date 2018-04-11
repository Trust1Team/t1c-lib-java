package com.t1t.t1c.auth;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.rest.RestExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class GatewayAuthClient implements IGatewayAuthClient {

    private static final Logger log = LoggerFactory.getLogger(GatewayAuthClient.class);

    private GatewayAuthRestClient client;
    private LibConfig config;
    private String token;

    private GatewayAuthClient(GatewayAuthRestClient client, LibConfig config) {
        this.client = client;
        this.config = config;
        this.token = RestExecutor.executeCall(this.client.getToken(), false);
    }

    @Override
    public String getToken() {
        if (token)
            try {
                return RestExecutor.executeCall(client.getToken(), false).getToken();
            } catch (RestException ex) {
                throw ExceptionFactory.authenticateException(ex.getMessage());
            }
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }
}