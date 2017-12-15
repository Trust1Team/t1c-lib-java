package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class InitializationException extends AbstractSystemException {

    public InitializationException(String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_SYSTEM_ERROR;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.INITIALIZATION_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INITIALIZATION_ERROR_INFO;
    }
}