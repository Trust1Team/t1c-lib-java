package com.t1t.t1c.auth;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.InvalidTokenException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class GatewayAuthClient implements IGatewayAuthClient {

    private GatewayAuthRestClient client;
    private String token;

    public GatewayAuthClient(GatewayAuthRestClient client) {
        this.client = client;
        this.token = obtainToken();
    }

    @Override
    public String getToken() {
        try {
            if (JwtUtil.isTokenAlmostExpired(token)) {
                token = refreshToken(token);
            }
        } catch (InvalidTokenException ex) {
            token = obtainToken();
        } if (JwtUtil.isTokenAlmostExpired(token)) {
            token = refreshToken(token);
        }
        return token;
    }

    private String obtainToken() {
        try {
            return RestExecutor.executeCall(this.client.getToken(), false).getToken();
        } catch (RestException ex) {
            throw ExceptionFactory.authenticateException(ex.getMessage());
        }
    }

    private String refreshToken(String token) {
        try {
            return RestExecutor.executeCall(client.refreshToken(new GwJwt().withToken(token)), false).getToken();
        } catch (RestException ex) {
            throw ExceptionFactory.authenticateException(ex.getMessage());
        }
    }
}