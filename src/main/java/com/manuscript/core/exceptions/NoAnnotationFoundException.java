package com.manuscript.core.exceptions;

public class NoAnnotationFoundException extends RuntimeException {
    public static final String message = "No annotation found in the database.";

    public NoAnnotationFoundException() {
        super(message);
    }

    public NoAnnotationFoundException(String message) {
        super(message);
    }

    public NoAnnotationFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
