package com.builderportfolio.exception;

/**
 * Exception thrown when an attempt is made to register a user
 * with an email or ID that already exists in the system.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message Explanation of why the exception occurred
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
