package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Safenet token Container
 */
public interface GclSafeNetRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<List<String>>> getSafeNetCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclSafeNetRequest request) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/info")
    Call<T1cResponse<GclSafeNetInfo>> getSafeNetInfo(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclSafeNetRequest request) throws RestException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/slots")
    Call<T1cResponse<List<GclSafeNetSlot>>> getSafeNetSlots(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclSafeNetRequest request, @Query("token-present") Boolean tokenPresent) throws RestException;
}
