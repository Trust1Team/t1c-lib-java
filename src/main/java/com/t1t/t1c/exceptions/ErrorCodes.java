package com.t1t.t1c.exceptions;


/**
 * A set of error codes used by the client when returning errors.
 */
public final class ErrorCodes {

    //
    // HTTP status codes
    //
    public static final int HTTP_STATUS_CODE_INVALID_INPUT              = 400;
    public static final int HTTP_STATUS_CODE_UNAUTHORIZED               = 401;
    public static final int HTTP_STATUS_CODE_FORBIDDEN                  = 403;
    public static final int HTTP_STATUS_CODE_NOT_FOUND                  = 404;
    public static final int HTTP_STATUS_CODE_ALREADY_EXISTS             = 409;
    public static final int HTTP_STATUS_CODE_INVALID_STATE              = 409;
    public static final int HTTP_STATUS_CODE_SYSTEM_ERROR               = 500;
    public static final int HTTP_STATUS_CODE_UNAVAILABLE                = 503;

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
    public static final int BEID_CONTAINER_REST_ERROR                   = 2005;
    public static final int LUXID_CONTAINER_REST_ERROR                  = 2006;
    public static final int LUXTRUST_CONTAINER_REST_ERROR               = 2007;
    public static final int DNIE_CONTAINER_REST_ERROR                   = 2008;
    public static final int PT_CONTAINER_REST_ERROR                     = 2009;
    public static final int EMV_CONTAINER_REST_ERROR                    = 2010;
    public static final int MOBIB_CONTAINER_REST_ERROR                  = 2011;
    public static final int OCRA_CONTAINER_REST_ERROR                   = 2012;
    public static final int SAFENET_CONTAINER_REST_ERROR                = 2013;
    public static final int OCV_REST_ERROR                              = 2014;

    public static final String REST_ERROR_INFO                          = "restError";

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
}
