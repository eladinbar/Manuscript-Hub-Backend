package com.manuscript.core.exceptions;


public class FailedDownloadException extends RuntimeException {
    public FailedDownloadException() {
        super();
    }

    public FailedDownloadException(String message) {
        super(message);
    }

    public FailedDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
