package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclAdminClientException extends AbstractRuntimeException {

    private Integer httpCode = 400;
    private String uri;

    public GclAdminClientException(String message, RestException cause) {
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
        return ErrorCodes.GCL_ADMIN_REST_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.REST_ERROR_INFO;
    }
}