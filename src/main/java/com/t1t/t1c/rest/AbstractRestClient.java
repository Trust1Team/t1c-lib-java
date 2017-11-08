package com.t1t.t1c.rest;

import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.T1cResponse;
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
public abstract class AbstractRestClient<R> {

    private static final Logger log = LoggerFactory.getLogger(AbstractRestClient.class);

    private R httpClient;

    protected AbstractRestClient(R httpClient) {
        this.httpClient = httpClient;
    }

    protected R getHttpClient() {
        return httpClient;
    }

    protected <T> T executeCall(Call<T> call) throws RestException {
        try {
            Response<T> response = call.execute();
            if (call.isExecuted() && response.isSuccessful()) {
                log.debug("Response data: {}", response.body());
                return response.body();
            } else {
                Integer httpCode = null;
                StringBuilder message = new StringBuilder();
                if (response.errorBody() != null && StringUtils.isNotBlank(response.errorBody().string())) {
                    log.error("Something went wrong: {}", response.errorBody().string());
                    message.append(response.errorBody().string());
                }
                if (response.raw() != null) {
                    log.error("Something went wrong, code: {}, message: {}", response.raw().code(), response.raw().message());
                    httpCode = response.raw().code();
                    if (StringUtils.isNotBlank(message.toString())) {
                        message.append(" - ");
                    }
                    message.append(response.raw().message());
                }
                throw ExceptionFactory.restException(message.toString(), httpCode);
            }
        } catch (IOException ex) {
            log.error("Error executing request: ", ex);
            throw ExceptionFactory.restException(ex);
        }
    }

    protected <T> T returnData(Call<T1cResponse<T>> call) throws RestException {
        if (call != null) {
            T1cResponse<T> response = executeCall(call);
            if (isCallSuccessful(response)) {
                return response.getData();
            }
        }
        return null;
    }

    protected boolean isCallSuccessful(T1cResponse response) {
        if (response != null && response.getSuccess() != null) {
            return response.getSuccess();
        }
        return false;
    }
}