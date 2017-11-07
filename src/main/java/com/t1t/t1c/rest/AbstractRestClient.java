package com.t1t.t1c.rest;

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
public abstract class AbstractRestClient {

    private static final Logger log = LoggerFactory.getLogger(AbstractRestClient.class);

    protected  <T> T executeCall(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (call.isExecuted() && response.isSuccessful()) {
                return response.body();
            }
            else {
                if (!response.isSuccessful()) {
                    if (response.errorBody() != null && StringUtils.isNotBlank(response.errorBody().string())) {
                        log.error("Something went wrong: {}", response.errorBody().string());
                    }
                    else if (response.raw() != null){
                        log.error("Something went wrong, code: {}, message: {}", response.raw().code(), response.raw().message());
                    }
                }
                return null;
            }
        }
        catch (IOException ex) {
            log.error("Error executing request: ", ex);
            return null;
        }
    }

    protected  <T> T returnData(Call<T1cResponse<T>> call) {
        if (call != null) {
            T1cResponse<T> response = executeCall(call);
            if (response != null && response.getSuccess() == null ? false : response.getSuccess()) {
                return response.getData();
            }
        }
        return null;
    }

}