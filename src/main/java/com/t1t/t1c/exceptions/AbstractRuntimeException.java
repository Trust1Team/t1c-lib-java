package com.t1t.t1c.exceptions;


/**
 * Created by michallispashidis on 31/10/2017.
 * <p>
 * Base class for all T1C errors coming out of the REST layer.
 */
public abstract class AbstractRuntimeException extends RuntimeException {

    private transient String serverStack;

    /**
     * Constructor.
     */
    public AbstractRuntimeException() {
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public AbstractRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause the exception cause
     */
    public AbstractRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the exception cause
     */
    public AbstractRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return the stacktrace
     */
    public String getServerStack() {
        return serverStack;
    }

    /**
     * @param stacktrace the stacktrace to set
     */
    public void setServerStack(String stacktrace) {
        this.serverStack = stacktrace;
    }

    /**
     * @return the httpCode
     */
    public abstract int getHttpCode();

    /**
     * @return the errorCode
     */
    public abstract int getErrorCode();

    /**
     * @return the moreInfo
     */
    public abstract String getMoreInfoUrl();

}
