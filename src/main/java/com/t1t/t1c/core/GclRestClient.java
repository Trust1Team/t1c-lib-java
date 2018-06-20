package com.t1t.t1c.core;

import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public interface GclRestClient {

    @GET("/v2")
    Call<T1cResponse<GclInfo>> getStatus() throws RestException;

    @GET("card-readers")
    Call<T1cResponse<List<GclReader>>> getCardReaders() throws RestException, NoConsentException;

    @GET("card-readers")
    Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(@Query("card-inserted") boolean cardInserted) throws RestException, NoConsentException;

    @GET("card-readers/{reader}")
    Call<T1cResponse<GclReader>> getCardReader(@Path("reader") String readerId) throws RestException, NoConsentException;

    @POST("agent")
    Call<T1cResponse<List<GclAgent>>> getAgents(@Body GclAgentRequestFilter request) throws RestException;

    @POST("agent/resolve")
    Call<T1cResponse<List<GclAgent>>> resolveAgent(@Body GclAgentResolutionRequest request) throws RestException;

    @POST("consent")
    Call<T1cResponse<Boolean>> getConsent(@Body GclConsent request);

}