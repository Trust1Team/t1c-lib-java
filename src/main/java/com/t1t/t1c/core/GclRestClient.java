package com.t1t.t1c.core;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import retrofit2.Call;
import retrofit2.http.*;
import t1c.core.GclContainer;
import t1c.core.GclReader;
import t1c.core.GclStatus;

import java.util.List;

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

    @GET("card-readers/{readerId}")
    Call<T1cResponse<GclReader>> getCardReader(@Path("readerId") String readerId);

    @GET("plugins")
    Call<T1cResponse<List<GclContainer>>> getContainers();

}