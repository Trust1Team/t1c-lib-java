package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclPtIdAllCertificates;
import com.t1t.t1c.model.rest.GclPtIdAllData;
import com.t1t.t1c.model.rest.GclPtIdData;
import com.t1t.t1c.containers.ContainerRestClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import t1c.containers.smartcards.eid.pt.GclPtIdAllCertificates;
import t1c.containers.smartcards.eid.pt.GclPtIdAllData;
import t1c.containers.smartcards.eid.pt.GclPtIdData;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Portuguese ID Container
 */
public interface GclPtRestClient extends ContainerRestClient {
    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/id")
    Call<T1cResponse<GclPtIdData>> getPtIdData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("photo") boolean includePhoto);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/photo")
    Call<T1cResponse<String>> getPtIdPhoto(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root-authentication")
    Call<T1cResponse<String>> getRootAuthenticationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/root-non-repudiation")
    Call<T1cResponse<String>> getRootNonRepudiationCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclPtIdAllData>> getPtIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclPtIdAllCertificates>> getPtIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);
}
