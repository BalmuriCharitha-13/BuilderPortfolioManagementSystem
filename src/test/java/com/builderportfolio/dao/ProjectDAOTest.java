package com.builderportfolio.dao;

import com.builderportfolio.model.Client;
import com.builderportfolio.model.Project;
import com.builderportfolio.model.Status;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ProjectDAO}.
 * <p>
 * This test class validates core operations of ProjectDAO including:
 * <ul>
 *     <li>Saving and retrieving projects</li>
 *     <li>Removing projects</li>
 *     <li>Handling multiple projects</li>
 *     <li>Edge cases like null inputs or non-existent projects</li>
 * </ul>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectDAOTest {

    private Client client;

    /**
     * Runs before each test.
     * Clears the project database to ensure tests are independent.
     * Initializes a sample client object for project creation.
     */
    @BeforeEach
    void setUp() {
        ProjectDAO.clearDatabase();

        client = new Client(
                "Charitha",
                "charitha@gmail.com",
                "1234567890"
        );
    }

    /**
     * Tests saving a project and retrieving it by its ID.
     * Ensures the saved project matches the expected values.
     */
    @Test
    @Order(1)
    void testSaveAndGetProject() {
        Project project = new Project(
                "Bridge",
                "Bridge construction",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                client,
                Status.UPCOMING,
                "B1",
                "P1"
        );

        ProjectDAO.saveProject(project);

        Project stored = ProjectDAO.getProjectById(project.getProjectId());

        assertNotNull(stored);
        assertEquals(project.getProjectId(), stored.getProjectId());
        assertEquals("Bridge", stored.getProjectName());
        assertEquals(Status.UPCOMING, stored.getStatus());
    }

    /**
     * Tests removing a project from the database.
     * Ensures that after removal, the project cannot be retrieved.
     */
    @Test
    @Order(2)
    void testRemoveProject() {
        Project project = new Project(
                "Mall",
                "Mall project",
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                client,
                Status.IN_PROGRESS,
                "B2",
                "P2"
        );

        ProjectDAO.saveProject(project);
        Long id = project.getProjectId();

        ProjectDAO.removeProject(id);

        assertNull(ProjectDAO.getProjectById(id));
    }

    /**
     * Tests fetching a project that does not exist in the database.
     * Should return null.
     */
    @Test
    @Order(3)
    void testGetProject_notFound() {
        Project project = ProjectDAO.getProjectById(999L);
        assertNull(project);
    }

    /**
     * Tests saving multiple projects independently.
     * Ensures that each project is saved separately and has unique IDs.
     */
    @Test
    @Order(4)
    void testMultipleProjects_savedIndependently() {
        Project p1 = new Project(
                "P1",
                "Desc1",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                client,
                Status.UPCOMING,
                "B1",
                "P1"
        );

        Project p2 = new Project(
                "P2",
                "Desc2",
                LocalDate.now(),
                LocalDate.now().plusDays(20),
                client,
                Status.COMPLETED,
                "B2",
                "P2"
        );

        ProjectDAO.saveProject(p1);
        ProjectDAO.saveProject(p2);

        assertNotNull(ProjectDAO.getProjectById(p1.getProjectId()));
        assertNotNull(ProjectDAO.getProjectById(p2.getProjectId()));
        assertNotEquals(p1.getProjectId(), p2.getProjectId());
    }

    /**
     * Tests saving a null project.
     * Should throw NullPointerException.
     */
    @Test
    void testSaveProject_withNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            ProjectDAO.saveProject(null);
        });
    }

    /**
     * Tests adding a project to a builder with a null project ID.
     * Should throw NullPointerException.
     */
    @Test
    void testAddProjectToBuilder_withNullProjectId() {
        BuilderDAO.createBuilder("B1");
        assertThrows(NullPointerException.class, () -> {
            BuilderDAO.addProjectToBuilder("B1", null);
        });
    }
}
