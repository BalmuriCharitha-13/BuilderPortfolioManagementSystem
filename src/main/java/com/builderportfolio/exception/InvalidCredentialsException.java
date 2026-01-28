package com.builderportfolio.exception;

/**
 * Exception thrown when user login fails due to invalid credentials,
 * such as incorrect password or user ID.
 */
public class InvalidCredentialsException extends RuntimeException {
    /**
     * Constructs a new InvalidCredentialsException with the specified detail message.
     *
     * @param message Explanation of why the exception occurred
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
