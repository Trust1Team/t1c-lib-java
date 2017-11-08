package com.t1t.t1c.exceptions;

import org.apache.commons.lang3.StringUtils;

/**
 * Simple factory for creating REST exceptions.
 */
public final class ExceptionFactory {
    /**
     * Creates an exception
     *
     * @param throwable
     * @return
     */
    public static final SystemErrorException systemErrorException(Throwable throwable) {
        return new SystemErrorException(throwable);
    }

    /**
     * Creates an exception
     *
     * @param message
     * @return
     */
    public static final SystemErrorException systemErrorException(String message) {
        return new SystemErrorException(message);
    }

    /**
     * GCL Client exception
     *
     * @param message
     * @return
     */
    public static GclClientException gclClientException(String message) {
        return new GclClientException(message);
    }

    /**
     * GCL Client exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static GclClientException gclClientException(String message, RestException cause) {
        String errorMessage = "Communication error with GCL";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new GclClientException(errorMessage, cause);
    }

    /**
     * GCL Admin Client exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static GclAdminClientException gclAdminClientException(String message, RestException cause) {
        String errorMessage = "Communication error with GCL Admin interface";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new GclAdminClientException(errorMessage, cause);
    }

    /**
     * DS Client exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static DsClientException dsClientException(String message, RestException cause) {
        String errorMessage = "Communication error with Distribution Service";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new DsClientException(errorMessage, cause);
    }

    /**
     * Client configuration exception.
     *
     * @param message
     * @return
     */
    public static ConfigException configException(String message) {
        return new ConfigException(message);
    }

    /**
     * Client initialization exception
     *
     * @param message
     * @return
     */
    public static InitializationException initializationException(String message) {
        return new InitializationException(message);
    }

    /**
     * Creates a REST exception
     *
     * @param message
     * @param httpCode
     * @return
     */
    public static RestException restException(String message, Integer httpCode) {
        return new RestException(message, httpCode);
    }

    /**
     * Creates a REST exception
     *
     * @param cause
     * @return
     */
    public static RestException restException(Throwable cause) {
        return new RestException(cause);
    }
}