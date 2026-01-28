package com.builderportfolio.exception;

/**
 * Exception thrown when a requested user cannot be found in the system.
 * This can occur during login, fetching user details, or other operations
 * that require a valid existing user.
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message Explanation of why the exception occurred
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
