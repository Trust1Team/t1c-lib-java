package com.t1t.t1c.exceptions;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainerException;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainerException;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainerException;
import com.t1t.t1c.containers.smartcards.eid.pt.PtIdContainerException;
import com.t1t.t1c.containers.smartcards.emv.EmvContainerException;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainerException;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainerException;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerException;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainerException;
import com.t1t.t1c.core.GclError;
import com.t1t.t1c.utils.ContainerUtil;
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
    public static RestException restException(String message, Integer httpCode, String uri, String jsonError) {
        return new RestException(message, httpCode, uri, jsonError);
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
     * Creates a verify PIN exception
     *
     * @param error
     * @return
     */
    public static VerifyPinException verifyPinException(GclError error) {
        if (error != null) {
            return new VerifyPinException(error.getDescription(), ContainerUtil.getPinVerificationRetriesLeftFor(error.getCode()));
        }
        else {
            return verifyPinException("No error message present, cannot determine cause");
        }
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
     * Creates a lux ID container exception
     *
     * @return
     */
    public static LuxIdContainerException luxIdContainerException(String message) {
        String errorMessage = "Communication error with Lux ID container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new LuxIdContainerException(errorMessage);
    }

    /**
     * Creates a luxTrust container exception
     *
     * @param message the message
     * @param cause the cause
     * @return LuxTrustContainerException
     */
    public static LuxTrustContainerException luxTrustContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with LuxTrust container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new LuxTrustContainerException(errorMessage, cause);
    }

    /**
     * Creates a luxTrust container exception
     *
     * @param message the message
     * @return LuxTrustContainerException
     */
    public static LuxTrustContainerException luxTrustContainerException(String message) {
        String errorMessage = "Communication error with LuxTrust container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new LuxTrustContainerException(errorMessage);
    }

    /**
     * Creates a DNIE container exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static DnieContainerException dnieContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with DNIE container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new DnieContainerException(errorMessage, cause);
    }

    /**
     * Creates a PT ID container exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static PtIdContainerException ptIdContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with PT ID container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new PtIdContainerException(errorMessage, cause);
    }

    /**
     * Creates an EMV container exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static EmvContainerException emvContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with EMV container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new EmvContainerException(errorMessage, cause);
    }

    /**
     * Creates a Mobib container exception
     * @param message
     * @param cause
     * @return
     */
    public static MobibContainerException mobibContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with MOBIB container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new MobibContainerException(errorMessage, cause);
    }

    /**
     * Creates an OCRA container exception
     * @param message
     * @param cause
     * @return
     */
    public static OcraContainerException ocraContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with MOBIB container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new OcraContainerException(errorMessage, cause);
    }

    public static UnsupportedOperationException unsupportedOperationException(String message) {
        return new UnsupportedOperationException(message);
    }

    /**
     * Creates an OCV exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static OcvClientException ocvException(String message, RestException cause) {
        String errorMessage = "Communication error with OCV";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new OcvClientException(errorMessage, cause);
    }

    /**
     * Creates a SafeNet container exception
     * @param message
     * @return
     */
    public static SafeNetContainerException safeNetContainerException(String message) {
        return new SafeNetContainerException(message);
    }

    /**
     * Creates a SafeNet container exception
     * @param message
     * @param cause
     * @return
     */
    public static SafeNetContainerException safeNetContainerException(String message, RestException cause) {
        String errorMessage = "Communication error with SafeNet Container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new SafeNetContainerException(errorMessage, cause);
    }

    /**
     * Creates a GCL Core exception
     * @param message
     * @param cause
     * @return
     */
    public static GclCoreException gclCoreException(String message, Throwable cause) {
        String errorMessage = "Communication error with GCL core";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new GclCoreException(errorMessage, cause);
    }
}