package com.manuscript.core.exceptions;


public class FailedUploadException extends RuntimeException {
    public FailedUploadException() {
        super();
    }

    public FailedUploadException(String message) {
        super(message);
    }

    public FailedUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
