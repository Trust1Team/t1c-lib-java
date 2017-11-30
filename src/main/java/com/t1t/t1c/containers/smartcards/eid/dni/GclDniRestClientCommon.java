package com.t1t.t1c.containers.smartcards.eid.dni;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclDnieAllCertificates;
import com.t1t.t1c.model.rest.GclDnieAllData;
import com.t1t.t1c.model.rest.GclDnieInfo;
import com.t1t.t1c.containers.CommonContainerRestClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import t1c.containers.smartcards.eid.dni.GclDnieAllCertificates;
import t1c.containers.smartcards.eid.dni.GclDnieAllData;
import t1c.containers.smartcards.eid.dni.GclDnieInfo;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Spanish DNIe Container
 */
public interface GclDniRestClientCommon extends CommonContainerRestClient {
    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/info")
    Call<T1cResponse<GclDnieInfo>> getDnieInfo(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclDnieAllData>> getDnieAllData(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclDnieAllData>> getDnieAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclDnieAllCertificates>> getDnieAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH + "/intermediate")
    Call<T1cResponse<String>> getIntermediateCertificate(@Path("containerId") String containerId, @Path("readerId") String readerId);

}
