package com.builderportfolio.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for {@link BuilderDAO}.
 * <p>
 * This test class validates all core operations of BuilderDAO including:
 * <ul>
 *     <li>Creating a builder</li>
 *     <li>Adding projects</li>
 *     <li>Removing projects</li>
 *     <li>Edge cases like null IDs or non-existent builders</li>
 * </ul>
 */
class BuilderDAOTest {

    /**
     * Runs before each test.
     * Clears the BuilderDAO database to ensure a clean test environment.
     */
    @BeforeEach
    void setUp() {
        BuilderDAO.clear();
    }

    /**
     * Tests whether creating a builder correctly initializes an empty project list.
     */
    @Test
    void testCreateBuilder_success() {
        BuilderDAO.createBuilder("B1");

        assertTrue(BuilderDAO.builderExists("B1"));
        assertNotNull(BuilderDAO.getProjectIds("B1"));
        assertTrue(BuilderDAO.getProjectIds("B1").isEmpty());
    }

    /**
     * Tests adding projects to an existing builder and validates the project list.
     */
    @Test
    void testAddProjectToBuilder_success() {
        BuilderDAO.createBuilder("B1");

        BuilderDAO.addProjectToBuilder("B1", 1L);
        BuilderDAO.addProjectToBuilder("B1", 2L);

        List<Long> projects = BuilderDAO.getProjectIds("B1");

        assertEquals(2, projects.size());
        assertTrue(projects.contains(1L));
        assertTrue(projects.contains(2L));
    }

    /**
     * Tests adding a project to a builder that does not exist yet.
     * Builder should be auto-created.
     */
    @Test
    void testAddProjectToBuilder_withoutCreate_shouldAutoCreate() {
        BuilderDAO.addProjectToBuilder("B2", 10L);

        List<Long> projects = BuilderDAO.getProjectIds("B2");

        assertEquals(1, projects.size());
        assertEquals(10L, projects.get(0));
        assertTrue(BuilderDAO.builderExists("B2"));
    }

    /**
     * Tests getting project IDs for a builder that does not exist.
     * Should return an empty list.
     */
    @Test
    void testGetProjectIds_forNonExistingBuilder() {
        List<Long> projects = BuilderDAO.getProjectIds("UNKNOWN");

        assertNotNull(projects);
        assertTrue(projects.isEmpty());
    }

    /**
     * Tests removing a project from an existing builder.
     */
    @Test
    void testRemoveProjectFromBuilder_success() {
        BuilderDAO.createBuilder("B3");
        BuilderDAO.addProjectToBuilder("B3", 100L);
        BuilderDAO.addProjectToBuilder("B3", 200L);

        BuilderDAO.removeProjectFromBuilder("B3", 100L);

        List<Long> projects = BuilderDAO.getProjectIds("B3");

        assertEquals(1, projects.size());
        assertFalse(projects.contains(100L));
        assertTrue(projects.contains(200L));
    }

    /**
     * Tests removing a project from a builder that does not exist.
     * Should not throw any exception.
     */
    @Test
    void testRemoveProjectFromNonExistingBuilder_shouldNotThrowException() {
        assertDoesNotThrow(() ->
                BuilderDAO.removeProjectFromBuilder("NO_BUILDER", 1L)
        );
    }

    /**
     * Tests adding duplicate project IDs to a builder.
     * Both entries should be stored.
     */
    @Test
    void testAddDuplicateProject() {
        BuilderDAO.createBuilder("B1");
        BuilderDAO.addProjectToBuilder("B1", 1L);
        BuilderDAO.addProjectToBuilder("B1", 1L);

        assertEquals(2, BuilderDAO.getProjectIds("B1").size());
    }

    /**
     * Tests creating a builder with null ID.
     * Should throw NullPointerException.
     */
    @Test
    void testCreateBuilder_withNullId_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            BuilderDAO.createBuilder(null);
        });
    }

    /**
     * Tests creating a builder with an empty string as ID.
     */
    @Test
    void testCreateBuilder_withEmptyId() {
        BuilderDAO.createBuilder("");
        assertTrue(BuilderDAO.builderExists(""));
    }

    /**
     * Tests adding a project to a builder with null project ID.
     * Should throw NullPointerException.
     */
    @Test
    void testAddProjectToBuilder_withNullProjectId() {
        BuilderDAO.createBuilder("B1");
        assertThrows(NullPointerException.class, () -> {
            BuilderDAO.addProjectToBuilder("B1", null);
        });
    }

    /**
     * Tests adding a project with null builder ID.
     * Should throw NullPointerException.
     */
    @Test
    void testAddProjectToBuilder_withNullBuilderId() {
        assertThrows(NullPointerException.class, () -> {
            BuilderDAO.addProjectToBuilder(null, 1L);
        });
    }
}