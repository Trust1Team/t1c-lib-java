package com.t1t.t1c.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclCoreException extends AbstractRuntimeException {

    public GclCoreException(String message) {
        super(message);
    }

    public GclCoreException(Throwable cause) {
        super(cause);
    }

    public GclCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Integer getHttpCode() {
        return null;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.CORE_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.CORE_ERROR_INFO;
    }
}