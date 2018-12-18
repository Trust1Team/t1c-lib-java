package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GenericContainerException extends AbstractRuntimeException {

    private Integer httpCode = 400;
    private String uri;

    public GenericContainerException(final String message) {
        super(message);
        this.httpCode = null;
        this.uri = null;
    }

    public GenericContainerException(final String message, final RestException cause) {
        super(message, cause);
        this.httpCode = cause.getHttpCode();
        this.uri = cause.getUri();
    }

    @Override
    public Integer getHttpCode() {
        return httpCode;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_GENERIC_CONTAINER_EXCEPTION;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_REST_ERROR;
    }

}