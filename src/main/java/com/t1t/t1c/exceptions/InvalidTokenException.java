package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class InvalidTokenException extends AbstractSystemException {

    public InvalidTokenException(Throwable cause) {
        super(cause);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_SYSTEM_ERROR;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_INVALID_TOKEN;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_INVALID_TOKEN;
    }
}