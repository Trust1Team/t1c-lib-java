package com.t1t.t1c.exceptions;

import com.t1t.t1c.containers.ContainerVersion;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainerException;
import com.t1t.t1c.core.GclContainerInfo;
import com.t1t.t1c.core.GclError;
import com.t1t.t1c.model.DigestAlgorithm;
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
    public static final SystemErrorException systemErrorException(final Throwable throwable) {
        return new SystemErrorException(throwable);
    }

    /**
     * Creates an exception
     *
     * @param message
     * @return
     */
    public static final SystemErrorException systemErrorException(final String message) {
        return new SystemErrorException(message);
    }

    /**
     * GCL Client exception
     *
     * @param message
     * @return
     */
    public static GclClientException gclClientException(final String message) {
        return new GclClientException(message);
    }

    /**
     * GCL Client exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static GclClientException gclClientException(final String message, final RestException cause) {
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
    public static GclAdminClientException gclAdminClientException(final String message, final RestException cause) {
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
    public static DsClientException dsClientException(final String message, final RestException cause) {
        String errorMessage = "Communication error with Distribution Service";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new DsClientException(errorMessage, cause);
    }

    /**
     * Client initialization exception
     *
     * @param message
     * @return
     */
    public static InitializationException initializationException(final String message) {
        return new InitializationException(message);
    }

    /**
     * Core version incompatibility exception
     *
     * @param actualVersion the incompatible version
     * @param downloadUrl   download url for compatible package
     * @return the exception
     */
    public static IncompatibleCoreVersionException incompatibleCoreVersionException(final String actualVersion, final String downloadUrl) {
        final String message = "GCL version \"" + actualVersion + "\" not compatible with library. Download and install new version: " + downloadUrl;
        return new IncompatibleCoreVersionException(message, downloadUrl);
    }

    /**
     * Client initialization exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static InitializationException initializationException(final String message, final Throwable cause) {
        return new InitializationException(message, cause);
    }

    /**
     * Creates a REST exception
     *
     * @param message
     * @param httpCode
     * @return
     */
    public static RestException restException(final String message, final Integer httpCode, final String uri, final String jsonError) {
        return new RestException(message, httpCode, uri, jsonError);
    }

    /**
     * Creates a REST exception
     *
     * @param cause
     * @return
     */
    public static RestException restException(final Throwable cause) {
        return new RestException(cause);
    }

    /**
     * Creates a JsonConversionException
     *
     * @param message
     * @return
     */
    public static JsonConversionException jsonConversionException(final String message) {
        return new JsonConversionException(message);
    }

    /**
     * Creates generic container exception
     *
     * @return
     */
    public static GenericContainerException genericContainerException(final String message, final RestException cause) {
        String errorMessage = "Communication error with generic container";
        if (StringUtils.isNotBlank(message)) errorMessage = message + " - " + errorMessage;
        return new GenericContainerException(errorMessage, cause);
    }

    /**
     * Creates generic container exception
     *
     * @return
     */
    public static GenericContainerException genericContainerException(final String message) {
        return new GenericContainerException(message);
    }

    /**
     * Creates container not available exception
     *
     * @param version
     * @return
     */
    public static ContainerNotAvailableException containerVersionNotAvailableException(final ContainerVersion version) {
        return new ContainerNotAvailableException("Container \"" + version.getId() + "\" not available in local installation");
    }


    /**
     * Creates a verify PIN exception
     *
     * @param message
     * @return
     */
    public static VerifyPinException verifyPinException(final String message) {
        return new VerifyPinException(message);
    }

    /**
     * Creates a verify PIN exception
     *
     * @param error
     * @return
     */
    public static VerifyPinException verifyPinException(final GclError error) {
        if (error != null) {
            return new VerifyPinException(error.getDescription(), ContainerUtil.getPinVerificationRetriesLeftFor(error.getCode().intValue()));
        } else {
            return verifyPinException("No error message present, cannot determine cause");
        }
    }

    /**
     * Creates an authz exception
     *
     * @param message
     * @return
     */
    public static AuthException authenticateException(final String message) {
        return new AuthException(message);
    }

    /**
     * Creates an unsupported operation exception
     *
     * @param message the message
     * @return an UnsupportedOperationException
     */
    public static UnsupportedOperationException unsupportedOperationException(final String message) {
        return new UnsupportedOperationException(message);
    }

    /**
     * Creates an OCV exception
     *
     * @param message
     * @param cause
     * @return
     */
    public static OcvClientException ocvException(final String message, final RestException cause) {
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
    public static GclCoreException gclCoreException(final String message, final Throwable cause) {
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
    public static GclCoreException containerLoadingFailed(final List<GclContainerInfo> containerInfo) {
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
    public static LuxIdContainerException luxIdContainerException(final String message) {
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
    public static CertificateOrderingException certificateOrderingException(final String message) {
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
    public static NoConsentException noConsentException(final String message, final Integer httpCode, final String url) {
        return new NoConsentException(message, httpCode, url);
    }

    /**
     * Creates an InvalidTokenException
     *
     * @param cause the cause
     * @return the exception
     */
    public static InvalidTokenException invalidTokenException(final Throwable cause) {
        throw new InvalidTokenException(cause);
    }

    /**
     * Creates an UnsupportedDigestAlgorithmException
     *
     * @param selectedAlgorithm the unsupported, selected algorithm
     * @param supported         the supported algorithm(s)
     * @return the exception
     */
    public static UnsupportedDigestAlgorithmException unsupportedDigestAlgorithm(final DigestAlgorithm selectedAlgorithm, final List<DigestAlgorithm> supported) {
        return new UnsupportedDigestAlgorithmException("Container does not support \"" + selectedAlgorithm.toString() + "\", must be one of: " + supported.toString());
    }

    /**
     * Creates an illegal argument exception for constructors
     * @param argumentName the name of the argument
     * @return the exception
     */
    public static IllegalArgumentException nullOrEmptyConstructorArgument(final String argumentName) {
        return new IllegalArgumentException(String.format("\"%s\" is null or empty", argumentName));
    }
}