package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclCoreException extends AbstractRuntimeException {

    private Integer httpCode;

    public GclCoreException(String message) {
        super(message);
    }

    public GclCoreException(Throwable cause) {
        super(cause);
    }

    public GclCoreException(String message, Throwable cause) {
        super(message, cause);
        if (cause instanceof RestException) {
            httpCode = ((RestException) cause).getHttpCode();
        }
    }

    @Override
    public Integer getHttpCode() {
        return httpCode;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_CORE;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_CORE_ERROR;
    }
}