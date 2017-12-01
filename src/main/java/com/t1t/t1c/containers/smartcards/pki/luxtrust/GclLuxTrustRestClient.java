package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for LuxTrust Token Container
 */
public interface GclLuxTrustRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxTrustAllData>> getLuxTrustAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxTrustAllCertificates>> getLuxTrustAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/activate")
    Call<T1cResponse<Object>> isLuxTrustActivated(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("pin") String pin);
}
