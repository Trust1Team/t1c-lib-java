package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclMobibAllData;
import com.t1t.t1c.model.rest.GclMobibCardIssuing;
import com.t1t.t1c.model.rest.GclMobibContract;
import com.t1t.t1c.containers.CommonContainerRestClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import t1c.containers.smartcards.mobib.GclMobibAllData;
import t1c.containers.smartcards.mobib.GclMobibCardIssuing;
import t1c.containers.smartcards.mobib.GclMobibContract;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Mobib (Belgian Mobility Card) Container
 */
public interface GclMobibRestClientCommon extends CommonContainerRestClient {
    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/status")
    Call<T1cResponse<Boolean>> getMobibStatus(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<String>> getMobibPicture(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/card-issuing")
    Call<T1cResponse<GclMobibCardIssuing>> getMobibCardIssuing(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/contracts")
    Call<T1cResponse<List<GclMobibContract>>> getMobibContracts(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclMobibAllData>> getMobibAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclMobibAllData>> getMobibAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);
}
