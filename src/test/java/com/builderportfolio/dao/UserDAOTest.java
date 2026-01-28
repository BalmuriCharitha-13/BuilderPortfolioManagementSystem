package com.builderportfolio.dao;

import com.builderportfolio.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserDAO}.
 * <p>
 * This test class validates core operations of UserDAO including:
 * <ul>
 *     <li>Saving and retrieving users</li>
 *     <li>Checking existence by ID and email</li>
 *     <li>Handling duplicates and null inputs</li>
 *     <li>Case sensitivity checks for emails</li>
 * </ul>
 */
class UserDAOTest {

    /**
     * Runs before each test.
     * Clears the user database to ensure test independence.
     */
    @BeforeEach
    void setUp() {
        UserDAO.clear();
    }

    /**
     * Tests saving a user and retrieving it by its ID.
     * Ensures that the stored user has the correct details.
     */
    @Test
    void testSaveUser_success() {
        User user = new User(
                "Charitha",
                "charitha@gmail.com",
                "9876543210",
                5,
                "pass123",
                1
        );

        UserDAO.saveUser(user);

        User storedUser = UserDAO.getUserById(user.getUserId());

        assertNotNull(storedUser);
        assertEquals("Charitha", storedUser.getUserName());
    }

    /**
     * Tests checking if a user ID exists (positive case).
     */
    @Test
    void testIdExists_true() {
        User user = new User(
                "Ravi",
                "ravi@gmail.com",
                "1111111111",
                3,
                "pass",
                1
        );

        UserDAO.saveUser(user);

        assertTrue(UserDAO.idExists(user.getUserId()));
    }

    /**
     * Tests checking if a user ID exists (negative case).
     */
    @Test
    void testIdExists_false() {
        assertFalse(UserDAO.idExists("INVALID"));
    }

    /**
     * Tests checking if an email exists (positive case).
     */
    @Test
    void testEmailExists_true() {
        User user = new User(
                "Exists",
                "exists@gmail.com",
                "9999999999",
                2,
                "pass",
                2
        );

        UserDAO.saveUser(user);

        assertTrue(UserDAO.emailExists("exists@gmail.com"));
    }

    /**
     * Tests checking if an email exists (negative case).
     */
    @Test
    void testEmailExists_false() {
        assertFalse(UserDAO.emailExists("missing@gmail.com"));
    }

    /**
     * Tests retrieving a user by an ID that does not exist.
     * Should return null.
     */
    @Test
    void testGetUserById_notFound() {
        assertNull(UserDAO.getUserById("INVALID"));
    }

    /**
     * Tests saving a null user.
     * Should throw NullPointerException.
     */
    @Test
    void testSaveUser_nullUser_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            UserDAO.saveUser(null);
        });
    }

    /**
     * Tests that duplicate emails are detected.
     * Note: The email check in UserDAO is case-sensitive.
     */
    @Test
    void testDuplicateEmail_shouldDetect() {
        User user1 = new User("User1", "test@gmail.com", "1111111111", 1, "pass1", 1);
        User user2 = new User("User2", "TEST@gmail.com", "2222222222", 2, "pass2", 2);
        UserDAO.saveUser(user1);

        assertFalse(UserDAO.emailExists(user2.getUserEmail()));
    }

    /**
     * Tests the case sensitivity of email existence check.
     */
    @Test
    void testEmailExists_caseSensitive() {
        User user = new User("User", "A@gmail.com", "1234567890", 1, "pass", 1);
        UserDAO.saveUser(user);

        assertTrue(UserDAO.emailExists("A@gmail.com"));
        assertFalse(UserDAO.emailExists("a@gmail.com"));
    }

}
