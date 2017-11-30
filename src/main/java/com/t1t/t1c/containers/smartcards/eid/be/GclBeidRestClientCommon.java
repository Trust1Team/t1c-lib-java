package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.containers.CommonContainerRestClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Belgian eID Container
 */
public interface GclBeidRestClientCommon extends CommonContainerRestClient {
    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/rn")
    Call<T1cResponse<GclBeIdRn>> getRnData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/address")
    Call<T1cResponse<GclBeIdAddress>> getBeIdAddress(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<String>> getBeIdPicture(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/rrn")
    Call<T1cResponse<String>> getRrnCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);
}
