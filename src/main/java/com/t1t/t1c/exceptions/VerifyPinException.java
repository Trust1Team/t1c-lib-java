package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class VerifyPinException extends AbstractRuntimeException {

    public VerifyPinException(String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_INVALID_INPUT;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.VERIFY_PIN_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.VERIFY_PIN_ERROR_INFO;
    }
}