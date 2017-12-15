package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class SigningException extends AbstractRuntimeException {

    public SigningException(String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_INVALID_INPUT;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.SIGNING_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.SIGNING_ERROR_INFO;
    }
}