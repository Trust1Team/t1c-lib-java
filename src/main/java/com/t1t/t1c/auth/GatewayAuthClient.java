package com.t1t.t1c.auth;

import com.t1t.t1c.exceptions.InvalidTokenException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.JwtUtil;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class GatewayAuthClient implements IGatewayAuthClient {

    private GatewayAuthRestClient client;
    private String token;

    public GatewayAuthClient(final GatewayAuthRestClient client) {
        this.client = client;
        this.token = obtainToken();
    }

    @Override
    public String getToken() {
        try {
            if (JwtUtil.isTokenAlmostExpired(token)) {
                token = refreshToken(token);
            }
        } catch (final InvalidTokenException ex) {
            token = obtainToken();
        }
        return token;
    }

    private String obtainToken() {
        try {
            return RestExecutor.executeCall(this.client.getToken(), false).getToken();
        } catch (final RestException ex) {
            // Fail safe, return null
            return null;
        }
    }

    private String refreshToken(final String token) {
        try {
            return RestExecutor.executeCall(client.refreshToken(new GwJwt().withToken(token)), false).getToken();
        } catch (final RestException ex) {
            return null;
        }
    }
}