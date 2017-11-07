package com.t1t.t1c.rest;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclContainerResponse;
import com.t1t.t1c.model.rest.GclReaderResponse;
import com.t1t.t1c.model.rest.GclStatusResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface GclRestClient {

    @GET("/v1")
    Call<T1cResponse<GclStatusResponse>> getV1Status();

    @GET("/v2")
    Call<T1cResponse<GclStatusResponse>> getV2Status();

    @GET("card-readers")
    Call<T1cResponse<List<GclReaderResponse>>> getCardReaders();

    @GET("card-readers")
    Call<T1cResponse<List<GclReaderResponse>>> getCardInsertedReaders(@Query("card-inserted") boolean cardInserted);

    @GET("card-readers/{readerId}")
    Call<T1cResponse<GclReaderResponse>> getCardReader(@Path("readerId") String readerId);

    @GET("plugins")
    Call<T1cResponse<List<GclContainerResponse>>> getCointainers();

}