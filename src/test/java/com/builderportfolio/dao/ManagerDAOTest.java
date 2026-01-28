package com.builderportfolio.dao;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ManagerDAO}.
 * <p>
 * This test class validates core operations of ManagerDAO including:
 * <ul>
 *     <li>Creating a project manager</li>
 *     <li>Adding projects to a manager</li>
 *     <li>Removing projects</li>
 *     <li>Handling edge cases like null IDs, duplicate projects, or non-existent managers</li>
 * </ul>
 */
class ManagerDAOTest {

    /**
     * Runs before each test.
     * Clears the ManagerDAO database to ensure tests are isolated.
     */
    @BeforeEach
    void setUp() {
        ManagerDAO.clearDatabase();
    }

    /**
     * Runs after each test.
     * Clears the ManagerDAO database to maintain a clean state.
     */
    @AfterEach
    void tearDown() {
        ManagerDAO.clearDatabase();
    }

    /**
     * Tests whether creating a project manager correctly initializes an empty project list.
     */
    @Test
    void testCreateProjectManager_success() {
        ManagerDAO.createProjectManager("P1");
        assertTrue(ManagerDAO.projectManagerExists("P1"));
        List<Long> projects = ManagerDAO.getProjectIds("P1");
        assertNotNull(projects);
        assertTrue(projects.isEmpty());
    }

    /**
     * Tests adding multiple projects to an existing project manager.
     */
    @Test
    void testAddProjectToManager_success() {
        ManagerDAO.createProjectManager("P1");

        ManagerDAO.addProjectToManager("P1", 1L);
        ManagerDAO.addProjectToManager("P1", 2L);

        List<Long> projects = ManagerDAO.getProjectIds("P1");

        assertEquals(2, projects.size());
        assertTrue(projects.contains(1L));
        assertTrue(projects.contains(2L));
    }

    /**
     * Tests adding a project to a manager that doesn't exist yet.
     * Manager should be automatically created.
     */
    @Test
    void testAddProjectToManager_withoutCreate_shouldAutoCreateManager() {
        ManagerDAO.addProjectToManager("P2", 10L);

        List<Long> projects = ManagerDAO.getProjectIds("P2");

        assertEquals(1, projects.size());
        assertEquals(10L, projects.get(0));
        assertTrue(ManagerDAO.projectManagerExists("P2"));
    }

    /**
     * Tests fetching project IDs for a non-existent manager.
     * Should return an empty list.
     */
    @Test
    void testGetProjectIds_forNonExistingManager() {
        List<Long> projects = ManagerDAO.getProjectIds("UNKNOWN");

        assertNotNull(projects);
        assertTrue(projects.isEmpty());
    }

    /**
     * Tests removing a project from an existing manager.
     */
    @Test
    void testRemoveProjectFromManager_success() {
        ManagerDAO.createProjectManager("P3");
        ManagerDAO.addProjectToManager("P3", 100L);
        ManagerDAO.addProjectToManager("P3", 200L);

        ManagerDAO.removeProjectFromManager("P3", 100L);

        List<Long> projects = ManagerDAO.getProjectIds("P3");

        assertEquals(1, projects.size());
        assertFalse(projects.contains(100L));
        assertTrue(projects.contains(200L));
    }

    /**
     * Tests removing a project that doesn't exist in the manager's project list.
     */
    @Test
    void testRemoveProjectFromManager_nonExistingProject() {
        ManagerDAO.createProjectManager("P4");
        ManagerDAO.addProjectToManager("P4", 300L);

        ManagerDAO.removeProjectFromManager("P4", 999L);

        List<Long> projects = ManagerDAO.getProjectIds("P4");

        assertEquals(1, projects.size());
        assertTrue(projects.contains(300L));
    }

    /**
     * Tests removing a project from a manager that doesn't exist.
     * Should not throw any exception.
     */
    @Test
    void testRemoveProjectFromNonExistingManager_shouldNotThrowException() {
        assertDoesNotThrow(() ->
                ManagerDAO.removeProjectFromManager("NO_MANAGER", 1L)
        );
    }

    /**
     * Tests adding duplicate projects to a manager.
     * Both entries should be stored.
     */
    @Test
    void testAddDuplicateProject() {
        ManagerDAO.createProjectManager("B1");
        ManagerDAO.addProjectToManager("B1", 1L);
        ManagerDAO.addProjectToManager("B1", 1L);

        assertEquals(2, ManagerDAO.getProjectIds("B1").size());
    }

    /**
     * Tests creating a project manager with null ID.
     * Should throw NullPointerException.
     */
    @Test
    void testCreateProjectManager_withNullId_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            ManagerDAO.createProjectManager(null);
        });
    }

    /**
     * Tests creating a project manager with empty string ID.
     */
    @Test
    void testCreateProjectManager_withEmptyId() {
        ManagerDAO.createProjectManager("");
        assertTrue(ManagerDAO.projectManagerExists(""));
    }

    /**
     * Tests adding a project with null project ID.
     * Should throw NullPointerException.
     */
    @Test
    void testAddProjectToManager_withNullProjectId() {
        ManagerDAO.createProjectManager("M1");
        assertThrows(NullPointerException.class, () -> {
            ManagerDAO.addProjectToManager("M1", null);
        });
    }

    /**
     * Tests adding a project with null manager ID.
     * Should throw NullPointerException.
     */
    @Test
    void testAddProjectToManager_withNullManagerId() {
        assertThrows(NullPointerException.class, () -> {
            ManagerDAO.addProjectToManager(null, 1L);
        });
    }
}
