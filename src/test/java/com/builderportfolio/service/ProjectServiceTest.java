package com.builderportfolio.service;

import com.builderportfolio.model.Client;
import com.builderportfolio.model.Project;
import com.builderportfolio.model.Status;
import com.builderportfolio.dao.BuilderDAO;
import com.builderportfolio.dao.ManagerDAO;
import com.builderportfolio.dao.ProjectDAO;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ProjectService}.
 * <p>
 * This test class verifies the main functionalities of ProjectService:
 * <ul>
 *     <li>Creating projects</li>
 *     <li>Fetching projects for managers and builders</li>
 *     <li>Updating project status</li>
 *     <li>Deleting projects</li>
 *     <li>Validating input constraints (null, empty, invalid dates)</li>
 * </ul>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceTest {

    private static ProjectService projectService;
    private static Client client;

    private static String managerId = "M1";
    private static String builderId = "B1";

    /**
     * Initializes ProjectService, Client, and sets up manager/builder entries before all tests.
     */
    @BeforeAll
    static void setup() {
        projectService = new ProjectService();
        client = new Client("Charitha", "charitha@gmail.com", "1234567890");

        ManagerDAO.createProjectManager(managerId);
        BuilderDAO.createBuilder(builderId);
    }

    /**
     * Tests creation of a new project and verifies it is correctly assigned to the manager.
     */
    @Test
    @Order(1)
    void testCreateProject() {
        projectService.createProject(
                "Bridge Project",
                "River bridge",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                client,
                Status.UPCOMING,
                builderId,
                managerId
        );

        List<Project> managerProjects = projectService.getManagerProjects(managerId);
        assertEquals(1, managerProjects.size());
        assertEquals("Bridge Project", managerProjects.get(0).getProjectName());
    }

    /**
     * Tests fetching all projects assigned to a manager.
     */
    @Test
    @Order(2)
    void testGetManagerProjects() {
        List<Project> projects = projectService.getManagerProjects(managerId);
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
    }

    /**
     * Tests fetching all projects assigned to a builder.
     */
    @Test
    @Order(3)
    void testGetBuilderProjects() {
        List<Project> projects = projectService.getBuilderProjects(builderId);
        assertNotNull(projects);
        assertEquals(1, projects.size());
    }

    /**
     * Tests updating project status successfully by the correct builder.
     */
    @Test
    @Order(4)
    void testUpdateProjectStatus_Success() {
        Project project = projectService.getBuilderProjects(builderId).get(0);

        boolean updated = projectService.updateProjectStatus(builderId, project.getProjectId(), Status.IN_PROGRESS);

        assertTrue(updated);
        assertEquals(Status.IN_PROGRESS, project.getStatus());
    }

    /**
     * Tests that updating project status fails if an incorrect builder tries to update.
     */
    @Test
    @Order(5)
    void testUpdateProjectStatus_Failure_WrongBuilder() {
        Project project = projectService.getBuilderProjects(builderId).get(0);

        boolean updated = projectService.updateProjectStatus("B999", project.getProjectId(), Status.COMPLETED);

        assertFalse(updated);
    }

    /**
     * Tests deleting a project successfully by the correct manager.
     */
    @Test
    @Order(6)
    void testDeleteProject_Success() {
        Project project = projectService.getManagerProjects(managerId).get(0);

        boolean deleted = projectService.deleteProject(managerId, project.getProjectId());

        assertTrue(deleted);
        assertNull(ProjectDAO.getProjectById(project.getProjectId()));
    }

    /**
     * Tests that deleting a project fails if an incorrect manager tries to delete.
     */
    @Test
    @Order(7)
    void testDeleteProject_Failure_WrongManager() {
        projectService.createProject(
                "Mall Project",
                "Commercial mall",
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                client,
                Status.UPCOMING,
                builderId,
                managerId
        );

        Project project = projectService.getManagerProjects(managerId).get(0);

        boolean deleted = projectService.deleteProject("M999", project.getProjectId());
        assertFalse(deleted);
    }

    /**
     * Tests creating a project with null name throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(8)
    void testCreateProject_nullName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                projectService.createProject(null, "desc", LocalDate.now(), LocalDate.now().plusDays(1),
                        client, Status.UPCOMING, builderId, managerId)
        );
    }

    /**
     * Tests creating a project with empty name throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(9)
    void testCreateProject_emptyName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                projectService.createProject("", "desc", LocalDate.now(), LocalDate.now().plusDays(1),
                        client, Status.UPCOMING, builderId, managerId)
        );
    }

    /**
     * Tests creating a project with null client throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(10)
    void testCreateProject_nullClient_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                projectService.createProject("Test", "desc", LocalDate.now(), LocalDate.now().plusDays(1),
                        null, Status.UPCOMING, builderId, managerId)
        );
    }

    /**
     * Tests creating a project with end date before start date throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(11)
    void testCreateProject_endDateBeforeStart_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                projectService.createProject("Test", "desc", LocalDate.now().plusDays(2), LocalDate.now(),
                        client, Status.UPCOMING, builderId, managerId)
        );
    }

    /**
     * Tests creating a project with null builder ID throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(12)
    void testCreateProject_nullBuilderId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                projectService.createProject("Test", "desc", LocalDate.now(), LocalDate.now().plusDays(1),
                        client, Status.UPCOMING, null, managerId)
        );
    }

    /**
     * Tests creating a project with null manager ID throws {@link IllegalArgumentException}.
     */
    @Test
    @Order(13)
    void testCreateProject_nullManagerId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                projectService.createProject("Test", "desc", LocalDate.now(), LocalDate.now().plusDays(1),
                        client, Status.UPCOMING, builderId, null)
        );
    }
}
