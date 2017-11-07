package com.t1t.t1c.exceptions;


/**
 * A set of error codes used by the client when returning errors.
 */
public final class ErrorCodes {

    //
    // HTTP status codes
    //
    public static final int HTTP_STATUS_CODE_INVALID_INPUT = 400;
    public static final int HTTP_STATUS_CODE_UNAUTHORIZED = 401;
    public static final int HTTP_STATUS_CODE_FORBIDDEN = 403;
    public static final int HTTP_STATUS_CODE_NOT_FOUND = 404;
    public static final int HTTP_STATUS_CODE_ALREADY_EXISTS = 409;
    public static final int HTTP_STATUS_CODE_INVALID_STATE = 409;
    public static final int HTTP_STATUS_CODE_SYSTEM_ERROR = 500;
    public static final int HTTP_STATUS_CODE_UNAVAILABLE = 503;

}
