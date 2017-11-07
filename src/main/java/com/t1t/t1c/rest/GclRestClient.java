package com.t1t.t1c.rest;

import com.google.gson.Gson;
import retrofit2.http.GET;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface GclRestClient {

    @GET("/v1")
    Gson getStatus();

}