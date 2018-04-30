package com.t1t.t1c.exceptions;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class JsonConversionException extends AbstractRuntimeException {

    private static final String EXPECTED_ARRAY = "BEGIN_ARRAY but was BEGIN_OBJECT";

    private boolean objectInsteadOfArray = false;

    public JsonConversionException(String message) {
        super(message);
        if (StringUtils.isNotEmpty(message) && message.contains(EXPECTED_ARRAY)) {
            this.objectInsteadOfArray = true;
        }
    }

    public boolean isObjectInsteadOfArray() {
        return objectInsteadOfArray;
    }

    @Override
    public Integer getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_SYSTEM_ERROR;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.ERROR_JSON_CONVERSION;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.INFO_JSON_CONVERSION_ERROR;
    }
}