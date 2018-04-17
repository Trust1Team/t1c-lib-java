package com.t1t.t1c.exceptions;

/**
 * A set of error codes used by the client when returning errors.
 */
public final class ErrorCodes {

    //
    // HTTP status codes
    //
    public static final int HTTP_STATUS_CODE_INVALID_INPUT              = 400;
    public static final int HTTP_STATUS_CODE_SYSTEM_ERROR               = 500;

    //
    // Config related
    //

    public static final int ERROR_INITIALIZATION                        = 1000;

    public static final String INFO_INITIALIZATION_ERROR                = "initializationError";

    //
    // REST layer related
    //

    public static final int ERROR_REST                                  = 2000;
    public static final int ERROR_GCL_REST                              = 2001;
    public static final int ERROR_GCL_ADMIN_REST                        = 2002;
    public static final int ERROR_DS_REST_ERROR                         = 2003;
    public static final int ERROR_GENERIC_CONTAINER_EXCEPTION           = 2004;
    public static final int ERROR_OCV_REST                              = 2005;
    public static final int ERROR_JSON_CONVERSION                       = 2006;


    public static final String INFO_REST_ERROR                          = "restError";
    public static final String INFO_JSON_CONVERSION_ERROR               = "jsonError";

    //
    // Container related
    //

    public static final int ERROR_VERIFY_PIN                            = 3000;
    public static final int ERROR_CONTAINER_NOT_AVAILABLE               = 3001;
    public static final int ERROR_LUXID_CONTAINER_REST                  = 3002;

    public static final String INFO_VERIFY_PIN_ERROR                    = "verifyPinError";
    public static final String INFO_CONTAINER_NOT_AVAILABLE_ERROR       = "containerNotAvailable";

    //
    // Authentication related
    //

    public static final int ERROR_AUTHENTICATE                          = 4000;

    public static final String INFO_AUTHENTICATE_ERROR                  = "authenticateError";

    //
    // Signing related
    //

    public static final int ERROR_SIGNING                               = 5000;

    public static final String INFO_SIGNING_ERROR                       = "signingError";

    //
    // Core related
    //

    public static final int ERROR_CORE                                  = 6000;
    public static final int ERROR_INCOMPATIBLE_VERSION                  = 6001;

    public static final String INFO_CORE_ERROR                          = "coreError";
    public static final String INFO_INCOMPATIBLE_VERSION                = "incompatibleVersion";

    //
    // Certificate related
    //

    public static final int ERROR_CERTIFICATE_ORDERING                  = 7000;

    public static final String INFO_CERTIFICATE_ORDERING_ERROR          = "certificateOrderingError";

    //
    // Consent related
    //

    public static final int ERROR_NO_CONSENT                            = 8000;

    public static final String INFO_NO_CONSENT_ERROR                    = "noConsent";

    //
    // Token related
    //

    public static final int ERROR_INVALID_TOKEN                         = 9000;

    public static final String INFO_INVALID_TOKEN                       = "invalidToken";
}