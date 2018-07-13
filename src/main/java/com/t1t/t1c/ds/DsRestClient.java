package com.t1t.t1c.ds;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public interface DsRestClient {
    @GET("system/status")
    Call<DsSystemStatus> getInfo();

    @GET("devices/{deviceId}")
    Call<DsDevice> getDevice(@Path("deviceId") String deviceId);

    @PUT("devices/{deviceId}")
    Call<DsRegistrationSyncResponse> register(@Path("deviceId") String deviceId,
                                              @Body DsDeviceRegistrationRequest request);

    @POST("devices/{deviceId}")
    Call<DsRegistrationSyncResponse> sync(@Path("deviceId") String deviceId,
                                          @Body DsDeviceRegistrationRequest request);

    @POST("security/jwt/refresh")
    Call<DsToken> refreshJWT(@Body DsTokenRefreshRequest request);

    @GET("security/keys/public/{deviceId}")
    Call<DsPublicKey> getPubKey(@Path("deviceId") String deviceId,
                                @Query("namespace") String namespace);

    @POST("download/gcl")
    Call<DsDownloadLink> getDownloadLink(@Body DsDownloadRequest request);
}
