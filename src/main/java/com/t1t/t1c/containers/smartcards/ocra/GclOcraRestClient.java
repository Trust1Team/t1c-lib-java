package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.NoConsentException;
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
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<Long>> ocraChallenge(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclOcraChallengeData request) throws RestException, NoConsentException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/counter")
    Call<T1cResponse<String>> readCounter(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/verify-pin")
    Call<T1cResponse<Object>> verifyPin(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException, NoConsentException;
}
