package com.t1t.t1c.rest;

import com.t1t.t1c.model.rest.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public interface DsRestClient {

    @GET("system/status")
    Call<DsInfoResponse> getInfo();

    @GET("devices/{deviceId}")
    Call<DsDeviceResponse> getDevice(@Path("deviceId") String deviceId);

    @GET("security/jwt/issue")
    Call<DsToken> getJWT();

    @POST("security/jwt/refresh")
    Call<DsToken> refreshJWT(@Body DsToken token);

    @GET("security/keys/public")
    Call<DsPublicKeyResponse> getPubKey();

    @POST("download/gcl")
    Call<DsDownloadResponse> getDownloadLink(@Body DsDownloadRequest request);

    @PUT("devices/{deviceId}")
    Call<DsToken> register(@Path("deviceId") String deviceId, @Body DsDeviceRegistrationRequest request);

    @POST("devices/{deviceId}")
    Call<DsToken> sync(@Path("deviceId") String deviceId, @Body DsDeviceRegistrationRequest request);
}
