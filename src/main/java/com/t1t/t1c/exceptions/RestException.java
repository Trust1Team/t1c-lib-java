package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class RestException extends AbstractException {

    private int httpCode = 400;

    public RestException(String message, Integer httpCode) {
        super(message);
    }

    public RestException(Throwable cause) {
        super(cause);

    }

    @Override
    public final int getHttpCode() {
        return this.httpCode;
    }

    private void setHttpCode(Integer httpCode) {
        if (httpCode != null) {
            this.httpCode = httpCode;
        }
    }

    @Override
    public int getErrorCode() {
        return ErrorCodes.REST_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.REST_ERROR_INFO;
    }
}