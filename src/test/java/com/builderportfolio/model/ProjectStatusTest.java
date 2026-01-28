package com.builderportfolio.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Status} enum.
 * <p>
 * This class verifies the integrity of the Status enum, including:
 * <ul>
 *     <li>Existence and order of enum values</li>
 *     <li>Correct behavior of {@link Status#valueOf(String)}</li>
 *     <li>Correct behavior of {@link Status#name()}</li>
 *     <li>Handling of invalid enum string values</li>
 * </ul>
 */
public class ProjectStatusTest {

    /**
     * Tests that all enum values exist and are in the expected order.
     */
    @Test
    void testEnumValuesExist() {
        Status[] statuses = Status.values();
        assertEquals(3, statuses.length);
        assertEquals(Status.UPCOMING, statuses[0]);
        assertEquals(Status.IN_PROGRESS, statuses[1]);
        assertEquals(Status.COMPLETED, statuses[2]);
    }

    /**
     * Tests that {@link Status#valueOf(String)} returns the correct enum
     * for a valid string.
     */
    @Test
    void testValueOfSuccess() {
        Status status = Status.valueOf("UPCOMING");
        assertEquals(Status.UPCOMING, status);
    }

    /**
     * Tests that {@link Status#valueOf(String)} throws
     * {@link IllegalArgumentException} for an invalid string.
     */
    @Test
    void testValueOfFailure() {
        assertThrows(IllegalArgumentException.class, () -> {
            Status.valueOf("NOT_STARTED");
        });
    }

    /**
     * Tests that {@link Status#name()} returns the correct string
     * representation of the enum value.
     */
    @Test
    void testEnumName() {
        assertEquals("COMPLETED", Status.COMPLETED.name());
    }
}
