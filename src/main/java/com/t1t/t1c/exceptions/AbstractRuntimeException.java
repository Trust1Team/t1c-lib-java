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
    public AbstractRuntimeException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause the exception cause
     */
    public AbstractRuntimeException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the exception cause
     */
    public AbstractRuntimeException(final String message, final Throwable cause) {
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
    public void setServerStack(final String stacktrace) {
        this.serverStack = stacktrace;
    }

    /**
     * @return the httpCode
     */
    public abstract Integer getHttpCode();

    /**
     * @return the errorCode
     */
    public abstract Integer getErrorCode();

    /**
     * @return the moreInfo
     */
    public abstract String getMoreInfoUrl();

}
