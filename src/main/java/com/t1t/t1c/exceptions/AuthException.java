package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class AuthException extends AbstractRuntimeException {

    public AuthException(final String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_UNAUTHORIZED;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_AUTH;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_AUTH_ERROR;
    }
}