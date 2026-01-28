package com.builderportfolio.model;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link User} class.
 * <p>
 * This test class verifies the proper functioning of User's:
 * <ul>
 *     <li>Constructor and field initialization for both Builder and Manager roles</li>
 *     <li>Getters and setters for all fields</li>
 *     <li>Auto-increment behavior for user IDs based on role</li>
 *     <li>{@link User#toString()} method</li>
 *     <li>Validation rules for null or empty inputs</li>
 * </ul>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    private static User managerUser;
    private static User builderUser;

    /**
     * Tests creation of a Project Manager user and verifies all field values.
     */
    @Test
    @Order(1)
    void testManagerUserCreation() {
        managerUser = new User(
                "Ramesh",
                "ramesh@gmail.com",
                "9999999999",
                10,
                "manager123",
                2   // Manager
        );

        assertNotNull(managerUser);
        assertEquals("P1", managerUser.getUserId());
        assertEquals("Ramesh", managerUser.getUserName());
        assertEquals("ramesh@gmail.com", managerUser.getUserEmail());
        assertEquals("9999999999", managerUser.getUserPhoneNo());
        assertEquals(10, managerUser.getUserExperience());
        assertEquals("manager123", managerUser.getPassword());
    }

    /**
     * Tests creation of a Builder user and verifies all field values.
     */
    @Test
    @Order(2)
    void testBuilderUserCreation() {
        builderUser = new User(
                "Suresh",
                "suresh@gmail.com",
                "8888888888",
                5,
                "builder123",
                1   // Builder
        );

        assertNotNull(builderUser);
        assertEquals("B1", builderUser.getUserId());
        assertEquals("Suresh", builderUser.getUserName());
        assertEquals("suresh@gmail.com", builderUser.getUserEmail());
        assertEquals("8888888888", builderUser.getUserPhoneNo());
        assertEquals(5, builderUser.getUserExperience());
        assertEquals("builder123", builderUser.getPassword());
    }

    /**
     * Tests updating user details via setters and verifies via getters.
     */
    @Test
    @Order(3)
    void testSettersAndGetters() {
        managerUser.setUserName("Ramesh Kumar");
        managerUser.setUserEmail("ramesh.kumar@gmail.com");
        managerUser.setUserPhoneNo("7777777777");
        managerUser.setUserExperience(12);
        managerUser.setPassword("newpass123");

        assertEquals("Ramesh Kumar", managerUser.getUserName());
        assertEquals("ramesh.kumar@gmail.com", managerUser.getUserEmail());
        assertEquals("7777777777", managerUser.getUserPhoneNo());
        assertEquals(12, managerUser.getUserExperience());
        assertEquals("newpass123", managerUser.getPassword());
    }

    /**
     * Tests auto-increment functionality for Manager IDs.
     */
    @Test
    @Order(4)
    void testAutoIncrementManagerId() {
        User secondManager = new User(
                "Mahesh",
                "mahesh@gmail.com",
                "6666666666",
                8,
                "manager456",
                2
        );

        assertEquals("P2", secondManager.getUserId());
    }

    /**
     * Tests auto-increment functionality for Builder IDs.
     */
    @Test
    @Order(5)
    void testAutoIncrementBuilderId() {
        User secondBuilder = new User(
                "Ravi",
                "ravi@gmail.com",
                "5555555555",
                6,
                "builder456",
                1
        );

        assertEquals("B2", secondBuilder.getUserId());
    }

    /**
     * Tests the {@link User#toString()} method for expected output.
     */
    @Test
    @Order(6)
    void testToString() {
        String userString = managerUser.toString();

        assertTrue(userString.contains("P1"));
        assertTrue(userString.contains("Ramesh Kumar"));
        assertTrue(userString.contains("ramesh.kumar@gmail.com"));
        assertTrue(userString.contains("7777777777"));
        assertTrue(userString.contains("12"));
        assertTrue(userString.contains("newpass123"));
    }

    /**
     * Tests that null userName throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(7)
    void testUserCreation_nullUserName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(null, "a@gmail.com", "1234567890", 5, "pass", 1)
        );
    }

    /**
     * Tests that empty userEmail throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(8)
    void testUserCreation_emptyUserEmail_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("Name", "", "1234567890", 5, "pass", 1)
        );
    }

    /**
     * Tests that null password throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(9)
    void testUserCreation_nullPassword_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("Name", "a@gmail.com", "1234567890", 5, null, 2)
        );
    }
}
