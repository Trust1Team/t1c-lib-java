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

    public static final int INITIALIZATION_ERROR                        = 1000;

    public static final String INITIALIZATION_ERROR_INFO                = "initializationError";

    //
    // REST layer related
    //

    public static final int REST_ERROR                                  = 2000;
    public static final int GCL_REST_ERROR                              = 2001;
    public static final int GCL_ADMIN_REST_ERROR                        = 2002;
    public static final int DS_REST_ERROR                               = 2003;
    public static final int GENERIC_CONTAINER_EXCEPTION                 = 2004;
    public static final int LUXID_CONTAINER_REST_ERROR                  = 2006;
    public static final int OCV_REST_ERROR                              = 2014;
    public static final int JSON_CONVERSION_ERROR                       = 2015;


    public static final String REST_ERROR_INFO                          = "restError";
    public static final String JSON_CONVERSION_ERROR_INFO               = "jsonError";

    //
    // Container related
    //

    public static final int VERIFY_PIN_ERROR                            = 3000;
    public static final int CONTAINER_NOT_AVAILABLE_ERROR               = 3001;

    public static final String VERIFY_PIN_ERROR_INFO                    = "verifyPinError";
    public static final String CONTAINER_NOT_AVAILABLE_ERROR_INFO       = "containerNotAvailable";

    //
    // Authentication related
    //

    public static final int AUTHENTICATE_ERROR                          = 4000;

    public static final String AUTHENTICATE_ERROR_INFO                  = "authenticateError";

    //
    // Signing related
    //

    public static final int SIGNING_ERROR                               = 5000;

    public static final String SIGNING_ERROR_INFO                       = "signingError";

    //
    // Core related
    //

    public static final int CORE_ERROR                                  = 6000;

    public static final String CORE_ERROR_INFO                          = "coreError";

    //
    // Certificate related
    //

    public static final int CERTIFICATE_ORDERING_ERROR                  = 7000;

    public static final String CERTIFICATE_ORDERING_ERROR_INFO          = "certificateOrderingError";

    //
    // Consent related
    //

    public static final int NO_CONSENT_ERROR                            = 8000;

    public static final String NO_CONSENT_ERROR_INFO                    = "noConsent";

    //
    // Token related
    //

    public static final int ERROR_INVALID_TOKEN                         = 9000;

    public static final String INFO_INVALID_TOKEN                       = "invalidToken";
}