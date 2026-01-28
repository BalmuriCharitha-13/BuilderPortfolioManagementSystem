package com.builderportfolio.service;

import com.builderportfolio.dao.BuilderDAO;
import com.builderportfolio.dao.ManagerDAO;
import com.builderportfolio.dao.UserDAO;
import com.builderportfolio.exception.InvalidCredentialsException;
import com.builderportfolio.exception.UserAlreadyExistsException;
import com.builderportfolio.exception.UserNotFoundException;
import com.builderportfolio.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserService}.
 * <p>
 * This test class verifies the main functionalities of UserService:
 * <ul>
 *     <li>User registration for builders and managers</li>
 *     <li>User login with correct and incorrect credentials</li>
 *     <li>Fetching user details</li>
 *     <li>Input validation for creating and registering users</li>
 *     <li>Handling exceptions such as duplicate users and invalid credentials</li>
 * </ul>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    private static UserService userService;

    /**
     * Initializes UserService before running all tests.
     */
    @BeforeAll
    static void setup() {
        userService = new UserService();
    }

    /**
     * Tests successful registration of a project manager and verifies
     * both UserDAO and ManagerDAO entries.
     */
    @Test
    @Order(1)
    void testRegister_Manager_Success() throws Exception {
        User manager = new User("Charitha", "charitha@gmail.com", "1234567890", 5, "pass123", 2);

        userService.registrationService(manager, manager.getRole());

        assertNotNull(UserDAO.getUserById(manager.getUserId()));
        assertTrue(ManagerDAO.projectManagerExists(manager.getUserId()));
    }

    /**
     * Tests successful registration of a builder and verifies
     * both UserDAO and BuilderDAO entries.
     */
    @Test
    @Order(2)
    void testRegister_Builder_Success() throws Exception {
        User builder = new User("Ravi", "ravi@gmail.com", "9876543210", 3, "builder123", 1);

        userService.registrationService(builder, builder.getRole());

        assertNotNull(UserDAO.getUserById(builder.getUserId()));
        assertTrue(BuilderDAO.builderExists(builder.getUserId()));
    }

    /**
     * Tests that registration fails if a user with the same email already exists.
     */
    @Test
    @Order(3)
    void testRegister_UserAlreadyExists() {
        User duplicateUser = new User("Charitha", "charitha@gmail.com", "9999999999", 6, "newpass", 2);

        assertThrows(UserAlreadyExistsException.class, () ->
                userService.registrationService(duplicateUser, duplicateUser.getRole()));
    }

    /**
     * Tests successful login with correct credentials.
     */
    @Test
    @Order(4)
    void testLogin_Success() throws Exception {
        User loggedInUser = userService.loginService("P1", "pass123");

        assertNotNull(loggedInUser);
        assertEquals("Charitha", loggedInUser.getUserName());
    }

    /**
     * Tests that login fails with incorrect password.
     */
    @Test
    @Order(5)
    void testLogin_WrongPassword() {
        assertThrows(InvalidCredentialsException.class, () ->
                userService.loginService("P1", "wrongpassword"));
    }

    /**
     * Tests that login fails if the user ID does not exist.
     */
    @Test
    @Order(6)
    void testLogin_UserNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                userService.loginService("P999", "pass123"));
    }

    /**
     * Tests fetching user details successfully.
     */
    @Test
    @Order(7)
    void testFetchDetails_Success() throws Exception {
        User user = userService.fetchUserDetails("P1");

        assertNotNull(user);
        assertEquals("charitha@gmail.com", user.getUserEmail());
    }

    /**
     * Tests fetching user details fails for non-existent user.
     */
    @Test
    @Order(8)
    void testFetchDetails_UserNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                userService.fetchUserDetails("P999"));
    }

    /**
     * Tests that creating a user with null name throws IllegalArgumentException.
     */
    @Test
    @Order(9)
    void testUserCreation_nullName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(null, "email@gmail.com", "123456", 5, "pass", 1));
    }

    /**
     * Tests that creating a user with empty name throws IllegalArgumentException.
     */
    @Test
    @Order(10)
    void testUserCreation_emptyName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("", "email@gmail.com", "123456", 5, "pass", 1));
    }

    /**
     * Tests that creating a user with null email throws IllegalArgumentException.
     */
    @Test
    @Order(11)
    void testUserCreation_nullEmail_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("Name", null, "123456", 5, "pass", 1));
    }

    /**
     * Tests that creating a user with empty email throws IllegalArgumentException.
     */
    @Test
    @Order(12)
    void testUserCreation_emptyEmail_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("Name", "", "123456", 5, "pass", 1));
    }

    /**
     * Tests that creating a user with null password throws IllegalArgumentException.
     */
    @Test
    @Order(13)
    void testUserCreation_nullPassword_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("Name", "a@gmail.com", "123456", 5, null, 1));
    }

    /**
     * Tests that creating a user with empty password throws IllegalArgumentException.
     */
    @Test
    @Order(14)
    void testUserCreation_emptyPassword_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("Name", "a@gmail.com", "123456", 5, "", 1));
    }

    /**
     * Tests that registering a user with a duplicate email throws {@link UserAlreadyExistsException}.
     */
    @Test
    @Order(15)
    void testRegister_duplicateEmail_shouldThrow() {
        User duplicateUser = new User("Charitha", "charitha@gmail.com", "9999999999", 5, "pass123", 2);

        assertThrows(UserAlreadyExistsException.class, () ->
                userService.registrationService(duplicateUser, duplicateUser.getRole()));
    }
}
