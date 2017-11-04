package com.t1t.t1c.exceptions;

/**
 * Thrown when something unexpected happens.
 */
public class SystemErrorException extends AbstractSystemException {
    public SystemErrorException() {}
    public SystemErrorException(String message) {
        super(message);
    }
    
    /**
     * Constructor.
     * @param t the cause t
     */
    public SystemErrorException(Throwable t) {
        super(t);
    }

    @Override
    public int getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_SYSTEM_ERROR;
    }

    @Override
    public int getErrorCode() {
        return -1;
    }

    @Override
    public String getMoreInfoUrl() {
        return null;
    }

}
