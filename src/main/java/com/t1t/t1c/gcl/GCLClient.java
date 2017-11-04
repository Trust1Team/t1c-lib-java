package com.t1t.t1c.gcl;

import com.google.gson.Gson;
import retrofit2.http.GET;

import java.io.Serializable;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface GCLClient extends GenericRestClient {

    @GET("/v1")
    Gson getStatus();

}