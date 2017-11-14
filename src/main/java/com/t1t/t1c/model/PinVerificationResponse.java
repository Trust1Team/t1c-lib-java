package com.t1t.t1c.model;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PinVerificationResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PinVerificationResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PinVerificationResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "PinVerificationResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}