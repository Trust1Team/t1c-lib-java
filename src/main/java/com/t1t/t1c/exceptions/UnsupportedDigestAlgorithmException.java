package com.t1t.t1c.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class UnsupportedDigestAlgorithmException extends AbstractConsumerException {

    public UnsupportedDigestAlgorithmException(String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_INVALID_INPUT;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_UNSUPPORTED_DIGEST_ALGO;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_UNSUPPORTED_DIGEST_ALGO;
    }
}