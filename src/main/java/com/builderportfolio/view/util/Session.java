package com.builderportfolio.view.util;

import com.builderportfolio.model.User;


/**
 * Manages the current logged-in user session.
 * <p>
 * This class stores the user who is currently authenticated
 * and provides global access to that user during runtime.
 */
public class Session {
    /**
     * Holds the currently logged-in user.
     */
    private static User loggedInUser;

    /**
     * Sets the active user session after successful login or registration.
     *
     * @param user the user to be stored as the current session user
     */
    public static void setUser(User user) {
        loggedInUser = user;
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the active session user, or null if no user is logged in
     */
    public static User getUser() {
        return loggedInUser;
    }
}
