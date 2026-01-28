package com.builderportfolio.model;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Client}.
 * <p>
 * This test class validates core functionality of the Client model including:
 * <ul>
 *     <li>Client creation and constructor validations</li>
 *     <li>Getter and setter methods</li>
 *     <li>toString method output</li>
 *     <li>Auto-increment behavior of client IDs</li>
 *     <li>Handling of invalid input such as null or empty fields</li>
 * </ul>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientTest {

    private static Client client;

    /**
     * Tests creation of a client with valid details.
     * Verifies initial values and auto-generated client ID.
     */
    @Test
    @Order(1)
    void testClientCreation() {
        client = new Client(
                "Charitha",
                "charitha@gmail.com",
                "1234567890"
        );

        assertNotNull(client);
        assertEquals(1L, client.getClientId());
        assertEquals("Charitha", client.getClientName());
        assertEquals("charitha@gmail.com", client.getClientEmail());
        assertEquals("1234567890", client.getClientPhoneNo());
    }

    /**
     * Tests the setter and getter methods for client fields.
     * Ensures proper assignment and retrieval of values.
     */
    @Test
    @Order(2)
    void testSettersAndGetters() {
        client.setClientName("Charitha B");
        client.setClientEmail("charitha.b@gmail.com");
        client.setClientPhoneNo("9999999999");

        assertEquals("Charitha B", client.getClientName());
        assertEquals("charitha.b@gmail.com", client.getClientEmail());
        assertEquals("9999999999", client.getClientPhoneNo());
    }

    /**
     * Tests the {@link Client#toString()} method.
     * Ensures all key fields are included in the string representation.
     */
    @Test
    @Order(3)
    void testToString() {
        String clientString = client.toString();

        assertTrue(clientString.contains("clientId=1"));
        assertTrue(clientString.contains("Charitha B"));
        assertTrue(clientString.contains("charitha.b@gmail.com"));
        assertTrue(clientString.contains("9999999999"));
    }

    /**
     * Tests the auto-increment behavior of client IDs.
     * Ensures each new client gets a unique sequential ID.
     */
    @Test
    @Order(4)
    void testAutoIncrementClientId() {
        Client secondClient = new Client(
                "Ravi",
                "ravi@gmail.com",
                "8888888888"
        );

        assertEquals(2L, secondClient.getClientId());
    }

    /**
     * Tests creation of a client with a null name.
     * Should throw {@link IllegalArgumentException}.
     */
    @Test
    @Order(5)
    void testClientCreation_nullName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Client(null, "a@gmail.com", "1234567890")
        );
    }

    /**
     * Tests creation of a client with an empty email.
     * Should throw {@link IllegalArgumentException}.
     */
    @Test
    @Order(6)
    void testClientCreation_emptyEmail_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Client("Name", "", "1234567890")
        );
    }

    /**
     * Tests creation of a client with a null phone number.
     * Should throw {@link IllegalArgumentException}.
     */
    @Test
    @Order(7)
    void testClientCreation_nullPhone_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Client("Name", "a@gmail.com", null)
        );
    }

}
