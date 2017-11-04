package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GCLClientException extends RuntimeException {
    public GCLClientException() {
        super();
    }

    public GCLClientException(String message) {
        super(message);
    }

    public GCLClientException(String message, Throwable cause) {super(message, cause);}

    public GCLClientException(Throwable cause) {
        super(cause);
    }
}