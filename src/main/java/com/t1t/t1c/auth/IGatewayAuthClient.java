package com.t1t.t1c.auth;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public interface IGatewayAuthClient {

    String getToken();
    String refreshToken(String token);

}
