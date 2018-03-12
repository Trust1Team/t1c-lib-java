package com.t1t.t1c.core;

import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public interface GclRestClient {

    @GET("/v1")
    Call<T1cResponse<GclStatus>> getV1Status();

    @GET("admin/certificate")
    Call<T1cResponse<String>> getPublicKey();

    @GET("card-readers")
    Call<T1cResponse<List<GclReader>>> getCardReaders();

    @GET("card-readers")
    Call<T1cResponse<List<GclReader>>> getCardInsertedReaders(@Query("card-inserted") boolean cardInserted);

    @GET("card-readers/{reader}")
    Call<T1cResponse<GclReader>> getCardReader(@Path("reader") String readerId);

    @GET("plugins")
    Call<T1cResponse<List<GclContainer>>> getContainers();

    @GET("agent")
    Call<T1cResponse<List<GclAgent>>> getAgents(@QueryMap Map<String,String> filters);

}