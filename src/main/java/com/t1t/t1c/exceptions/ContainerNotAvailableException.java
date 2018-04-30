package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class ContainerNotAvailableException extends AbstractRuntimeException {

    public ContainerNotAvailableException(String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_INVALID_INPUT;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_CONTAINER_NOT_AVAILABLE;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_CONTAINER_NOT_AVAILABLE_ERROR;
    }
}