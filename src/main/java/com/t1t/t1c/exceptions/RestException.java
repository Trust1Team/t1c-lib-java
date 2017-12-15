package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class RestException extends AbstractRuntimeException {

    private Integer httpCode = 400;
    private String uri;
    private String jsonError;

    public RestException(String message, Integer httpCode, String uri, String jsonError) {
        super(message);
        this.httpCode = httpCode;
        this.uri = uri;
        this.jsonError = jsonError;
    }

    public RestException(Throwable cause) {
        super(cause);

    }

    @Override
    public final Integer getHttpCode() {
        return this.httpCode;
    }

    private void setHttpCode(Integer httpCode) {
        if (httpCode != null) {
            this.httpCode = httpCode;
        }
    }

    public String getUri() {
        return uri;
    }

    public String getJsonError() {
        return jsonError;
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