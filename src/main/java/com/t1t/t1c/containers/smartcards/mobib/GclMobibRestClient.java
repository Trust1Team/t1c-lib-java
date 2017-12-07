package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Mobib (Belgian Mobility Card) Container
 */
public interface GclMobibRestClient {

    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/status")
    Call<T1cResponse<Boolean>> getMobibStatus(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<String>> getMobibPicture(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/card-issuing")
    Call<T1cResponse<GclMobibCardIssuing>> getMobibCardIssuing(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/contracts")
    Call<T1cResponse<List<GclMobibContract>>> getMobibContracts(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclMobibAllData>> getMobibAllData(@Path("containerId") String containerId, @Path("reader") String readerId) throws RestException;

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclMobibAllData>> getMobibAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter) throws RestException;
}
