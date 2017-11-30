package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.*;
import com.t1t.t1c.containers.ContainerRestClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import t1c.containers.smartcards.eid.lux.*;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Luxembourg ID Container
 */
public interface GclLuxIdRestClient extends ContainerRestClient{
    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH)
    Call<T1cResponse<GclLuxIdAllData>> getLuxIdAllData(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<GclLuxIdAllCertificates>> getLuxIdAllCertificates(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin, @Query("filter") String filter);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/biometric")
    Call<T1cResponse<GclLuxIdBiometric>> getLuxIdBiometric(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/picture")
    Call<T1cResponse<GclLuxIdPicture>> getLuxIdPicture(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/signature-image")
    Call<T1cResponse<GclLuxIdSignatureImage>> getLuxIdSignatureImage(@Path("containerId") String containerId, @Path("readerId") String readerId, @Query("pin") String pin);
}
