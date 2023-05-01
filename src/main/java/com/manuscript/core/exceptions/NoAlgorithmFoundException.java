package com.manuscript.core.exceptions;

public class NoAlgorithmFoundException extends RuntimeException {
    public static final String message = "No algorithm found in the database.";

    public NoAlgorithmFoundException() {
        super(message);
    }

    public NoAlgorithmFoundException(String message) {
        super(message);
    }

    public NoAlgorithmFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
