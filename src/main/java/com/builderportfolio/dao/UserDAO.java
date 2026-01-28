package com.builderportfolio.dao;

import com.builderportfolio.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DAO class for managing User data.
 * <p>
 * Maintains an in-memory mapping of User IDs to User objects.
 * Provides methods to save, fetch, and check users.
 * Thread-safe collection is used for concurrent access.
 * </p>
 */
public class UserDAO {

    /**
     * In-memory storage for registered users.
     * Key   → User ID (e.g., B1, P2)
     * Value → User object containing full user details
     */
    private static Map<String, User> registeredUsers = new ConcurrentHashMap<>();


    /**
     * Saves a new user into the database.
     * If the user ID already exists, it will overwrite the existing user.
     *
     * @param user User object to save
     */
    public static void saveUser(User user) {
        registeredUsers.put(user.getUserId(), user);
    }

    /**
     * Checks whether a user ID already exists in the system.
     *
     * @param userId ID to check
     * @return true if the user ID exists, false otherwise
     */
    public static boolean idExists(String userId) {
        return registeredUsers.containsKey(userId);
    }

    /**
     * Checks whether an email is already registered.
     *
     * @param userEmail Email to check
     * @return true if the email exists, false otherwise
     */
    public static boolean emailExists(String userEmail) {
        for (User user : registeredUsers.values()) {
            if (user.getUserEmail().equals(userEmail)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Retrieves a user's details using their user ID.
     *
     * @param userId ID of the user to fetch
     * @return User object if found, otherwise null
     */
    public static User getUserById(String userId) {
        return registeredUsers.get(userId);
    }

    /**
     * Clears all registered users from memory.
     * Useful for resetting during tests.
     */
    public static void clear() {
        registeredUsers.clear();
    }


}
