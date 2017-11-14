package com.t1t.t1c.rest;

import com.google.gson.Gson;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
import okhttp3.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public abstract class AbstractRestClient<U> {

    private static final Logger log = LoggerFactory.getLogger(AbstractRestClient.class);

    private U httpClient;

    protected AbstractRestClient(U httpClient) {
        this.httpClient = httpClient;
    }

    protected final U getHttpClient() {
        return httpClient;
    }

    protected final void setHttpClient(U httpClient) {
        this.httpClient = httpClient;
    }

    protected final <T> T executeCall(Call<T> call) throws RestException {
        try {
            Response<T> response = call.execute();
            if (call.isExecuted() && response.isSuccessful()) {
                log.debug("Response data: {}", new Gson().toJson(response.body()));
                return response.body();
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
                            log.error("Something went wrong: {}", response.errorBody().string());
                            message.append(response.errorBody().string());
                        }
                    }
                } else if (response.raw() != null) {
                    log.error("Something went wrong, code: {}, message: {}", response.raw().code(), response.raw().message());
                    httpCode = response.raw().code();
                    if (StringUtils.isNotBlank(message.toString())) {
                        message.append(" - ");
                    }
                    message.append(response.raw().message());
                    if (response.raw().request() != null && response.raw().request().url() != null) {
                        url = response.raw().request().url().toString();
                    }
                }
                throw ExceptionFactory.restException(message.toString(), httpCode, url, jsonError);
            }
        } catch (IOException ex) {
            log.error("Error executing request: ", ex);
            throw ExceptionFactory.restException(ex);
        }
    }

    protected final <T> T returnData(Call<T1cResponse<T>> call) throws RestException {
        if (call != null) {
            T1cResponse<T> response = executeCall(call);
            if (isCallSuccessful(response)) {
                return response.getData();
            }
        }
        return null;
    }

    protected final boolean isCallSuccessful(T1cResponse response) {
        if (response != null && response.getSuccess() != null) {
            return response.getSuccess();
        }
        return false;
    }
}