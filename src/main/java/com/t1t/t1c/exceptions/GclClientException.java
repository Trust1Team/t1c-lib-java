package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclClientException extends AbstractRuntimeException {

    private int httpCode = 400;

    public GclClientException(String message) {
        super(message);
    }

    public GclClientException(String message, RestException cause) {
        super(message, cause);
        this.httpCode = cause.getHttpCode();
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public int getErrorCode() {
        return ErrorCodes.GCL_ADMIN_REST_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.REST_ERROR_INFO;
    }
}