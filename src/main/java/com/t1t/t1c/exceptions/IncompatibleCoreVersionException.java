package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class IncompatibleCoreVersionException extends AbstractConsumerException {

    private String downloadUrl;

    public IncompatibleCoreVersionException(final String message, final String downloadUrl) {
        super(message);
        this.downloadUrl = downloadUrl;
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_INVALID_INPUT;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_INCOMPATIBLE_VERSION;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_INCOMPATIBLE_VERSION;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }
}