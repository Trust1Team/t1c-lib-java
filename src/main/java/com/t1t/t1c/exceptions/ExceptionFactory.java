package com.t1t.t1c.exceptions;

/**
 * Simple factory for creating REST exceptions.
 */
public final class ExceptionFactory {
    /**
     * Creates an exception
     * @param throwable
     * @return
     */
    public static final SystemErrorException systemErrorException(Throwable throwable) {
        return new SystemErrorException(throwable);
    }

    /**
     * Creates an exception
     * @param message
     * @return
     */
    public static final SystemErrorException systemErrorException(String message) {
        return new SystemErrorException(message);
    }
}