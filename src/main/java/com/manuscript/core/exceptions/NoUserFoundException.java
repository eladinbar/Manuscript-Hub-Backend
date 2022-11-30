package com.manuscript.core.exceptions;


public class NoUserFoundException extends RuntimeException {
    public static final String message = "No user found in DB";

    public NoUserFoundException() {
        super(message);
    }

    public NoUserFoundException(String message) {
        super(message);
    }

    public NoUserFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
