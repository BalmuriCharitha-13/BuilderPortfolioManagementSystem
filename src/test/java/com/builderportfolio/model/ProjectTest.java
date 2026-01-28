package com.builderportfolio.model;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Project} class.
 * <p>
 * This test class verifies the proper functioning of Project's:
 * <ul>
 *     <li>Constructor and field initialization</li>
 *     <li>Getters and setters for all fields</li>
 *     <li>Date assignments for start and end dates</li>
 *     <li>Client assignment and updates</li>
 *     <li>Auto-increment behavior of project IDs</li>
 *     <li>Validation rules for null or invalid inputs</li>
 *     <li>{@link Project#toString()} method</li>
 * </ul>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectTest {

    private static Client client =
            new Client("Charitha", "charitha@gmail.com", "1234567890");

    private static Project project;

    /**
     * Tests project creation and verifies all initial values.
     */
    @Test
    @Order(1)
    public void testProjectCreation() {
        project = new Project(
                "Bridge Construction",
                "River bridge project",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                client,
                Status.UPCOMING,
                "B1",
                "P1"
        );

        assertNotNull(project);
        assertEquals(1, project.getProjectId());
        assertEquals("Bridge Construction", project.getProjectName());
        assertEquals("River bridge project", project.getProjectDescription());
        assertEquals(Status.UPCOMING, project.getStatus());
        assertEquals("B1", project.getBuilderId());
        assertEquals("P1", project.getProjectManagerId());
    }

    /**
     * Tests all setters and getters for project fields.
     */
    @Test
    @Order(2)
    public void testSettersAndGetters() {
        project.setProjectName("Highway Construction");
        project.setProjectDescription("National highway project");
        project.setStatus(Status.IN_PROGRESS);

        assertEquals("Highway Construction", project.getProjectName());
        assertEquals("National highway project", project.getProjectDescription());
        assertEquals(Status.IN_PROGRESS, project.getStatus());
    }

    /**
     * Tests setting and retrieving project start and end dates.
     */
    @Test
    @Order(3)
    public void testDateSetters() {
        LocalDate newStart = LocalDate.of(2025, 2, 1);
        LocalDate newEnd = LocalDate.of(2025, 11, 30);

        project.setStartDate(newStart);
        project.setEndDate(newEnd);

        assertEquals(newStart, project.getStartDate());
        assertEquals(newEnd, project.getEndDate());
    }

    /**
     * Tests assigning a new client to the project.
     */
    @Test
    @Order(4)
    public void testClientAssignment() {
        Client newClient = new Client("Ravi", "ravi@gmail.com", "9876543210");

        project.setAssignedClient(newClient);

        assertNotNull(project.getAssignedClient());
        assertEquals("Ravi", project.getAssignedClient().getClientName());
    }

    /**
     * Tests the {@link Project#toString()} method for expected output.
     */
    @Test
    @Order(5)
    public void testToString() {
        String projectString = project.toString();

        assertTrue(projectString.contains("projectId=1"));
        assertTrue(projectString.contains("Highway Construction"));
        assertTrue(projectString.contains("IN_PROGRESS"));
        assertTrue(projectString.contains("P1"));
        assertTrue(projectString.contains("B1"));
    }

    /**
     * Tests auto-increment behavior of project IDs.
     */
    @Test
    @Order(6)
    public void testAutoIncrementProjectId() {
        Project secondProject = new Project(
                "Mall Construction",
                "Commercial mall",
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                client,
                Status.UPCOMING,
                "B2",
                "P2"
        );
        assertEquals(2, secondProject.getProjectId());
    }

    /**
     * Tests that null project name throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(7)
    void testProjectCreation_nullName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Project(null, "desc", LocalDate.now(), LocalDate.now().plusDays(10),
                        client, Status.UPCOMING, "B1", "P1")
        );
    }

    /**
     * Tests that null builder ID throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(8)
    void testProjectCreation_nullBuilderId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Project("Proj", "desc", LocalDate.now(), LocalDate.now().plusDays(10),
                        client, Status.UPCOMING, null, "P1")
        );
    }

    /**
     * Tests that null project manager ID throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(9)
    void testProjectCreation_nullManagerId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Project("Proj", "desc", LocalDate.now(), LocalDate.now().plusDays(10),
                        client, Status.UPCOMING, "B1", null)
        );
    }

    /**
     * Tests that null assigned client throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(10)
    void testProjectCreation_nullClient_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Project("Proj", "desc", LocalDate.now(), LocalDate.now().plusDays(10),
                        null, Status.UPCOMING, "B1", "P1")
        );
    }

    /**
     * Tests that end date before start date throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(11)
    void testProjectCreation_endDateBeforeStart_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Project("Proj", "desc", LocalDate.now().plusDays(10), LocalDate.now(),
                        client, Status.UPCOMING, "B1", "P1")
        );
    }
}
