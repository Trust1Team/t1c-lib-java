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
public interface GclCitrixRestClient {

    @GET("/v1")
    Call<T1cResponse<GclInfo>> getV1Status() throws RestException;

    @GET("/v2")
    Call<T1cResponse<GclInfo>> getV2Status() throws RestException;

    @GET("admin/certificate")
    Call<T1cResponse<String>> getPublicKey() throws RestException;

    @GET("agent/{agentPort}/card-readers")
    Call<T1cResponse<List<GclReader>>> getCardReaders(@Path("agentPort") Integer agentPort) throws RestException, NoConsentException;

    @GET("agent/{agentPort}/card-readers")
    Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(@Path("agentPort") Integer agentPort, @Query("card-inserted") boolean cardInserted) throws RestException, NoConsentException;

    @GET("agent/{agentPort}/card-readers/{reader}")
    Call<T1cResponse<GclReader>> getCardReader(@Path("agentPort") Integer agentPort, @Path("reader") String readerId) throws RestException, NoConsentException;

    @GET("plugins")
    Call<T1cResponse<List<GclContainer>>> getV1Containers() throws RestException;

    @GET("agent")
    Call<T1cResponse<List<GclAgent>>> getAgents(@QueryMap Map<String, String> filters) throws RestException;

    @GET("agent")
    Call<T1cResponse<GclAgent>> getAgent(@QueryMap Map<String, String> filters) throws RestException;

    @POST("agent/{agentPort}/consent")
    Call<T1cResponse<Boolean>> getConsent(@Path("agentPort") Integer agentPort, @Body GclConsent request);

}