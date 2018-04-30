package com.t1t.t1c.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public interface GatewayAuthRestClient {

    @GET("login/application/token")
    Call<GwJwt> getToken();

    @POST("login/token/refresh")
    Call<GwJwt> refreshToken(@Body GwJwt request);
}
