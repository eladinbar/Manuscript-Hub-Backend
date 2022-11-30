package com.manuscript.core.exceptions;


public class NoImageFoundException extends RuntimeException {
    public static final String message = "No images found in storage";

    public NoImageFoundException() {
        super(message);
    }

    public NoImageFoundException(String message) {
        super(message);
    }

    public NoImageFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
