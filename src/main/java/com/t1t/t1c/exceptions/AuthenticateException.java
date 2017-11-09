package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class AuthenticateException extends AbstractRuntimeException {

    public AuthenticateException(String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_INVALID_INPUT;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.AUTHENTICATE_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.AUTHENTICATE_ERROR_INFO;
    }
}