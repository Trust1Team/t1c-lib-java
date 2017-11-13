package com.t1t.t1c.exceptions;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.be.exceptions.BeIdContainerException;
import com.t1t.t1c.containers.smartcards.eid.lux.exceptions.LuxIdContainerException;
import com.t1t.t1c.containers.smartcards.eid.pt.exceptions.DnieContainerException;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.exceptions.LuxTrustContainerException;
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
     * BE ID container exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static BeIdContainerException beIdContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with BE ID container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new BeIdContainerException(errorMessage, cause);
    }

    /**
     * BE ID container exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static BeIdContainerException beIdContainerException(String message, GenericContainerException cause) {
        String errorMessage = "Communication error with BE ID container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return beIdContainerException(errorMessage, (RestException) cause.getCause());
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
    public static RestException restException(String message, Integer httpCode, String uri) {
        return new RestException(message, httpCode, uri);
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

    /**
     * Creates generic container exception
     *
     * @return
     */
    public static GenericContainerException genericContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with generic container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new GenericContainerException(errorMessage, cause);
    }

    /**
     * Creates generic container exception
     *
     * @return
     */
    public static GenericContainerException genericContainerException(String message) {
        return new GenericContainerException(message);
    }

    /**
     * Creates container not available exception
     *
     * @param type
     * @return
     */
    public static ContainerNotAvailableException containerNotAvailableException(ContainerType type) {
        return new ContainerNotAvailableException("Container \"" + type.getId() + "\" not available in local installation");
    }


    /**
     * Creates a verify PIN exception
     *
     * @param message
     * @return
     */
    public static VerifyPinException verifyPinException(String message) {
        return new VerifyPinException(message);
    }

    /**
     * Creates an authentication exception
     *
     * @param message
     * @return
     */
    public static AuthenticateException authenticateException(String message) {
        return new AuthenticateException(message);
    }

    /**
     * Creates an authentication exception
     *
     * @param message
     * @return
     */
    public static SigningException signingException(String message) {
        return new SigningException(message);
    }

    /**
     * Creates a lux ID container exception
     *
     * @param message
     * @return
     */
    public static LuxIdContainerException luxIdContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with Lux ID container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new LuxIdContainerException(errorMessage, cause);
    }

    /**
     * Creates a luxTrust container exception
     *
     * @param message
     * @return
     */
    public static LuxTrustContainerException luxTrustContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with LuxTrust container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new LuxTrustContainerException(errorMessage, cause);
    }

    /**
     * Creates a DNIE container exception
     * @param message
     * @param cause
     * @return
     */
    public static DnieContainerException dnieContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with LuxTrust container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new DnieContainerException(errorMessage, cause);
    }
}