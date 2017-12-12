package com.t1t.t1c.core;

import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public interface GclAdminRestClient {

    @POST("admin/activate")
    Call<T1cResponse<Object>> activate();

    @PUT("admin/certificate")
    Call<T1cResponse<Object>> setPublicKey(@Body GclUpdatePublicKeyRequest request);
}
