package com.t1t.t1c.containers.smartcards.pkcs11;

import com.t1t.t1c.exceptions.NoConsentException;
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
public interface GclPkcs11RestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<List<String>>> getPkcs11Certificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclPkcs11Request request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/info")
    Call<T1cResponse<GclPkcs11Info>> getPkcs11Info(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclPkcs11Request request) throws RestException, NoConsentException;

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/slots")
    Call<T1cResponse<List<GclPkcs11Slot>>> getPkcs11Slots(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclPkcs11Request request, @Query("token-present") Boolean tokenPresent) throws RestException, NoConsentException;
}
