package com.t1t.t1c.exceptions;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainerException;
import com.t1t.t1c.core.GclContainerInfo;
import com.t1t.t1c.core.GclError;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
     * Core version incompatibility exception
     *
     * @param actualVersion the incompatible version
     * @param downloadUrl   download url for compatible package
     * @return the exception
     */
    public static IncompatibleCoreVersionException incompatibleCoreVersionException(String actualVersion, String downloadUrl) {
        String message = "GCL version \"" + actualVersion + "\" not compatible with library. Download and install new version: " + downloadUrl;
        return new IncompatibleCoreVersionException(message, downloadUrl);
    }

    /**
     * Client initialization exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static InitializationException initializationException(String message, Throwable cause) {
        return new InitializationException(message, cause);
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
     * Creates a JsonConversionException
     *
     * @param message
     * @return
     */
    public static JsonConversionException jsonConversionException(String message) {
        return new JsonConversionException(message);
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
            return new VerifyPinException(error.getDescription(), ContainerUtil.getPinVerificationRetriesLeftFor(error.getCode().intValue()));
        } else {
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
     * Creates an unsupported operation exception
     *
     * @param message the message
     * @return an UnsupportedOperationException
     */
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
        if (StringUtils.isNotEmpty(cause.getMessage())) errorMessage = errorMessage + " - " + cause.getMessage();
        return new OcvClientException(errorMessage, cause);
    }

    /**
     * Creates a GCL Core exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static GclCoreException gclCoreException(String message, Throwable cause) {
        String errorMessage = "Communication error with GCL core";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        if (StringUtils.isNotEmpty(cause.getMessage())) errorMessage = errorMessage + " - " + cause.getMessage();
        return new GclCoreException(errorMessage, cause);
    }

    /**
     * Creates a GCL Core exception
     *
     * @param containerInfo
     * @return
     */
    public static GclCoreException containerLoadingFailed(List<GclContainerInfo> containerInfo) {
        return new GclCoreException("Container download failed: " + containerInfo.toString());
    }

    /**
     * Creates a GCL Core exception
     *
     * @return
     */
    public static GclCoreException containerLoadingTimeoutExceeded() {
        return new GclCoreException("Download timeout period for container download exceeded");
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
     * Creates a CertificateOrderingException
     *
     * @param message the message
     * @return an exception
     */
    public static CertificateOrderingException certificateOrderingException(String message) {
        return new CertificateOrderingException(message);
    }

    /**
     * Creates a NoConsentException
     *
     * @param message  the message
     * @param httpCode the http code
     * @param url      the url for which consent is required
     * @return an exception
     */
    public static NoConsentException noConsentException(String message, Integer httpCode, String url) {
        return new NoConsentException(message, httpCode, url);
    }

    /**
     * Creates an InvalidTokenException
     *
     * @param cause the cause
     * @return the exception
     */
    public static InvalidTokenException invalidTokenException(Throwable cause) {
        throw new InvalidTokenException(cause);
    }
}