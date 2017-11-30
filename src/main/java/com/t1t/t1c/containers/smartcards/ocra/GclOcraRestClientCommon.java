package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclOcraAllData;
import com.t1t.t1c.model.rest.GclOcraChallengeData;
import com.t1t.t1c.model.rest.GclVerifyPinRequest;
import com.t1t.t1c.containers.ContainerRestClient;
import retrofit2.Call;
import retrofit2.http.*;
import t1c.containers.smartcards.ocra.GclOcraAllData;
import t1c.containers.smartcards.ocra.GclOcraChallengeData;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Ocra (OAuth Challenge Response Algorithm) Container
 */
public interface GclOcraRestClientCommon extends ContainerRestClient {
    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> ocraChallenge(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclOcraChallengeData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> getOcraReadCounter(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclVerifyPinRequest request);
}
