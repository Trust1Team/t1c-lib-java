package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclOcraAllData;
import com.t1t.t1c.model.rest.GclOcraChallengeData;
import com.t1t.t1c.model.rest.GclVerifyPinRequest;
import com.t1t.t1c.rest.ContainerRestClient;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Ocra (OAuth Challenge Response Algorithm) Container
 */
public interface GclOcraRestClient extends ContainerRestClient{
    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclOcraAllData>> getOcraAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> ocraChallenge(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclOcraChallengeData request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/challenge")
    Call<T1cResponse<String>> getOcraReadCounter(@Path("containerId") String containerId, @Path("readerId") String readerId, @Body GclVerifyPinRequest request);
}
