package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class NoConsentException extends AbstractRuntimeException {

    private Integer httpCode = 400;
    private String uri;

    public NoConsentException(final String message, final Integer httpCode, final String uri) {
        super(message);
        this.httpCode = httpCode;
        this.uri = uri;
    }

    @Override
    public final Integer getHttpCode() {
        return this.httpCode;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_NO_CONSENT;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_NO_CONSENT_ERROR;
    }

}