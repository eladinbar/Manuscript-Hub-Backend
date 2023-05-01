package com.manuscript.core.exceptions;


public class UserAlreadyExistException extends RuntimeException {
    public static final String message = "User email is already saved on DB";

    public UserAlreadyExistException() {
        super(message);
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
