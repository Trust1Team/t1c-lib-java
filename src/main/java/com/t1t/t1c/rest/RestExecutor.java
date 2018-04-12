package com.t1t.t1c.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import okhttp3.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class RestExecutor {

    private static final Logger log = LoggerFactory.getLogger(RestExecutor.class);

    private static final String ACCESS_TOKEN_HEADER_NAME = "access_token";

    public static final synchronized <T> T executeCall(Call<T> call) throws RestException, NoConsentException {
        return executeCall(call, true);
    }

    public static final synchronized <T> Pair<T, String> executeCallAndReturnAccessTokenHeader(Call<T> call, boolean consentRequired) {
        return executeCall(call, true, consentRequired);
    }

    public static final synchronized <T> Pair<T, String> executeCall(Call<T> call, boolean extractTokenFromHeader, boolean consentRequired) {
        try {
            Response<T> response = call.execute();
            if (call.isExecuted() && response.isSuccessful()) {
                log.debug("Response data: {}", new Gson().toJson(response.body()));
                if (extractTokenFromHeader) {
                    return Pair.of(response.body(), response.headers().get(ACCESS_TOKEN_HEADER_NAME));
                } else {
                    return Pair.of(response.body(), "");
                }
            } else {
                Integer httpCode = null;
                String url = null;
                StringBuilder message = new StringBuilder();
                String jsonError = null;
                if (response.errorBody() != null) {
                    boolean isJson = MediaType.parse("application/json").equals(response.errorBody().contentType());
                    if (isJson) {
                        jsonError = response.errorBody().source().readUtf8();
                    } else {
                        if (StringUtils.isNotBlank(response.errorBody().string())) {
                            log.debug("Something went wrong: {}", response.errorBody().string());
                            message.append(response.errorBody().string());
                        }
                    }
                }
                if (response.raw() != null) {
                    log.debug("Something went wrong, code: {}, message: {}", response.raw().code(), response.raw().message());
                    httpCode = response.raw().code();
                    if (StringUtils.isNotBlank(message.toString())) {
                        message.append(" - ");
                    }
                    message.append(response.raw().message());
                    if (response.raw().request() != null && response.raw().request().url() != null) {
                        url = response.raw().request().url().toString();
                    }
                }
                if (consentRequired && httpCode != null && httpCode == 401) {
                    throw ExceptionFactory.noConsentException(message.toString(), httpCode, url);
                }
                throw ExceptionFactory.restException(message.toString(), httpCode, url, jsonError);
            }
        } catch (IOException ex) {
            log.error("Error executing request: ", ex);
            throw ExceptionFactory.restException(ex);
        } catch (JsonSyntaxException ex) {
            log.error("Failed to deserialize response: ", ex.getMessage());
            throw ExceptionFactory.jsonConversionException(ex.getMessage());
        }
    }

    public static final synchronized <T> T executeCall(Call<T> call, boolean consentRequired) throws RestException, NoConsentException {
        return executeCall(call, false, consentRequired).getLeft();
    }

    public static synchronized <T> T returnData(Call<T1cResponse<T>> call, boolean consentRequired) throws RestException, NoConsentException {
        if (call != null) {
            T1cResponse<T> response = executeCall(call, consentRequired);
            if (isCallSuccessful(response)) {
                return response.getData();
            } else {
                log.warn("Request was unsuccessfull: ", response);
            }
        }
        return null;
    }

    public static boolean isCallSuccessful(T1cResponse response) {
        if (response != null && response.getSuccess() != null) {
            return response.getSuccess();
        }
        return false;
    }
}