package com.t1t.t1c.rest;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclUpdatePublicKeyRequest;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public interface GclAdminRestClient {

    @POST("admin/activate")
    Call<T1cResponse<Object>> activate();

    @GET("admin/certificate")
    Call<T1cResponse<String>> getPublicKey();

    @PUT("admin/certificate")
    Call<T1cResponse<Object>> setPublicKey(@Body GclUpdatePublicKeyRequest request);
}
