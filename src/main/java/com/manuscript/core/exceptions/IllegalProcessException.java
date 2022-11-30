package com.manuscript.core.exceptions;


public class IllegalProcessException extends RuntimeException {
    public IllegalProcessException() {
        super();
    }

    public IllegalProcessException(String message) {
        super(message);
    }

    public IllegalProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
