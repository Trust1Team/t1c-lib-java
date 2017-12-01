package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Portuguese ID Container
 */
public interface GclPtIdRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/id")
    Call<T1cResponse<GclPtIdData>> getPtIdData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("photo") boolean includePhoto);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/photo")
    Call<T1cResponse<String>> getPtIdPhoto(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root-authentication")
    Call<T1cResponse<String>> getRootAuthenticationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root-non-repudiation")
    Call<T1cResponse<String>> getRootNonRepudiationCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter);
}
