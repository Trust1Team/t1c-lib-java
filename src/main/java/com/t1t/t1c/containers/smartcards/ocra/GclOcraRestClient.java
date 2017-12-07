package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Ocra (OAuth Challenge Response Algorithm) Container
 */
public interface GclOcraRestClient {

    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> ocraChallenge(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclOcraChallengeData request) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> getOcraReadCounter(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException;
}
