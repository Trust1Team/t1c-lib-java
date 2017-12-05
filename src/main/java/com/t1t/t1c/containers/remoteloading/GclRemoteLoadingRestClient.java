package com.t1t.t1c.containers.remoteloading;

import com.t1t.t1c.model.T1cResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface GclRemoteLoadingRestClient {

    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/atr")
    Call<T1cResponse<String>> getAtr(@Path("containerId") String containerId,
                                     @Path("reader") String readerId,
                                     @Query("session-id") String sessionId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/apdu")
    Call<T1cResponse<GclRemoteLoadingCommand>> executeApduCall(@Path("containerId") String containerId,
                                                               @Path("reader") String readerId,
                                                               @Body GclRemoteLoadingApdu apdu,
                                                               @Query("session-id") String sessionId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/apdu")
    Call<T1cResponse<List<GclRemoteLoadingCommand>>> executeApduCalls(@Path("containerId") String containerId,
                                                                      @Path("reader") String readerId,
                                                                      @Body List<GclRemoteLoadingApdu> apdu,
                                                                      @Query("session-id") String sessionId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/ccid")
    Call<T1cResponse<GclRemoteLoadingCommand>> getCcid(@Path("containerId") String containerId,
                                                       @Path("reader") String readerId,
                                                       @Body GclRemoteLoadingCcidRequest request,
                                                       @Query("session-id") String sessionId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/ccid-features")
    Call<T1cResponse<List<GclRemoteLoadingCcidFeature>>> getCcidFeatures(@Path("containerId") String containerId,
                                                                         @Path("reader") String readerId,
                                                                         @Query("session-id") String sessionId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/command")
    Call<T1cResponse<GclRemoteLoadingCommand>> executeCommand(@Path("containerId") String containerId,
                                                              @Path("reader") String readerId,
                                                              @Body GclRemoteLoadingCommandRequest request,
                                                              @Query("session-id") String sessionId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/commands")
    Call<T1cResponse<List<GclRemoteLoadingCommand>>> executeCommands(@Path("containerId") String containerId,
                                                                     @Path("reader") String readerId,
                                                                     @Body List<GclRemoteLoadingCommandRequest> request,
                                                                     @Query("session-id") String sessionId);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/is-present")
    Call<T1cResponse<Boolean>> isPresent(@Path("containerId") String containerId,
                                         @Path("reader") String readerId,
                                         @Query("session-id") String sessionId);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/open-session")
    Call<T1cResponse<String>> openSession(@Path("containerId") String containerId,
                                          @Path("reader") String readerId,
                                          @Body GclRemoteLoadingOpenSessionRequest request);

    @GET(CONTAINER_AND_READER_CONTEXT_PATH + "/close-session")
    Call<T1cResponse<String>> closeSession(@Path("containerId") String containerId,
                                           @Path("reader") String readerId,
                                           @Query("session-id") String sessionId);
}
