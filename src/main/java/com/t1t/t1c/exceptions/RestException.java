package com.t1t.t1c.exceptions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.t1t.t1c.core.GclError;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class RestException extends AbstractRuntimeException {

    private static final Logger log = LoggerFactory.getLogger(RestException.class);

    private Integer httpCode = 400;
    private String uri;
    private GclError gclError;

    public RestException(String message, Integer httpCode, String uri, String jsonError) {
        super(message);
        this.httpCode = httpCode;
        this.uri = uri;
        if (StringUtils.isNotEmpty(jsonError)) {
            try {
                this.gclError = new Gson().fromJson(jsonError, GclError.class);
            } catch (JsonSyntaxException e) {
                log.error("Couldn't decode error message: ", e);
            }
        }
    }

    public RestException(Throwable cause) {
        super(cause);

    }

    @Override
    public final Integer getHttpCode() {
        return this.httpCode;
    }

    public String getUri() {
        return uri;
    }

    public GclError getGclError() {
        return gclError;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.REST_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.REST_ERROR_INFO;
    }
}