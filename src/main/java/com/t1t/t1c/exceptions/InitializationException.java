package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class InitializationException extends AbstractSystemException {

    public InitializationException(final String message) {
        super(message);
    }

    public InitializationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_SYSTEM_ERROR;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_INITIALIZATION;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_INITIALIZATION_ERROR;
    }
}