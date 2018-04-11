package com.t1t.t1c.core;

import com.t1t.t1c.ds.DsAtrList;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.List;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public interface GclAdminRestClient {

    @GET("admin/certificate")
    Call<T1cResponse<GclAdminCertificates>> getCertificates() throws RestException;

    @GET("admin/certificate/ds")
    Call<T1cResponse<String>> getDsCertificate() throws RestException;

    @GET("admin/certificate/device")
    Call<T1cResponse<String>> getDeviceCertificate() throws RestException;

    @GET("admin/certificate/ssl")
    Call<T1cResponse<String>> getSslCertificate() throws RestException;

    @POST("admin/activate")
    Call<T1cResponse<Object>> activate() throws RestException;

    @PUT("admin/certificate")
    Call<T1cResponse<Object>> setDsPublicKey(@Body GclUpdatePublicKeyRequest request) throws RestException;

    @POST("admin/containers")
    Call<T1cResponse<Object>> loadContainers(@Body GclLoadContainersRequest gclLoadContainersRequest) throws RestException;

    @POST("admin/atr")
    Call<T1cResponse<Object>> loadAtrList(@Body DsAtrList atrList) throws RestException;
}
