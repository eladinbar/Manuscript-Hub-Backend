package com.manuscript.core.exceptions;

public class UnauthorizedException extends RuntimeException {
    public static final String message = "This user does not have authorization for the requested resource/s";

    public UnauthorizedException() {
        super(message);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
