package com.t1t.t1c.core;

import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public interface GclRestClient {

    @GET("/v1")
    Call<T1cResponse<GclInfo>> getV1Status() throws RestException;

    @GET("/v2")
    Call<T1cResponse<GclInfo>> getV2Status() throws RestException;

    @GET("admin/certificate")
    Call<T1cResponse<String>> getPublicKey() throws RestException;

    @GET("card-readers")
    Call<T1cResponse<List<GclReader>>> getCardReaders() throws RestException, NoConsentException;

    @GET("card-readers")
    Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(@Query("card-inserted") boolean cardInserted) throws RestException, NoConsentException;

    @GET("card-readers/{reader}")
    Call<T1cResponse<GclReader>> getCardReader(@Path("reader") String readerId) throws RestException, NoConsentException;

    @GET("plugins")
    Call<T1cResponse<List<GclContainer>>> getV1Containers() throws RestException;

    @GET("containers")
    Call<T1cResponse<List<GclContainer>>> getV2Containers() throws RestException;

    @GET("agent")
    Call<T1cResponse<List<GclAgent>>> getAgents(@QueryMap Map<String,String> filters) throws RestException;

    @POST("consent")
    Call<T1cResponse<Boolean>> getConsent(@Body GclConsent request);

}