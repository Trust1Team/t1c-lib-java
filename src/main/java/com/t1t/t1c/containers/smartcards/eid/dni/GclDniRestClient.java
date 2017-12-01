package com.t1t.t1c.containers.smartcards.eid.dni;

import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Spanish DNIe Container
 */
public interface GclDniRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/info")
    Call<T1cResponse<GclDnieInfo>> getDnieInfo(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclDnieAllData>> getDnieAllData(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclDnieAllData>> getDnieAllData(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/intermediate")
    Call<T1cResponse<String>> getIntermediateCertificate(@Path("containerId") String containerId, @Path("reader") String readerId);

}
