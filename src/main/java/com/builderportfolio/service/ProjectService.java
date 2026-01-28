package com.builderportfolio.service;

import com.builderportfolio.dao.BuilderDAO;
import com.builderportfolio.dao.ManagerDAO;
import com.builderportfolio.dao.ProjectDAO;
import com.builderportfolio.model.Project;
import com.builderportfolio.model.Client;
import com.builderportfolio.model.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer class that handles all project-related operations.
 * Acts as a bridge between the View layer and DAO layer.
 */
public class ProjectService {

    /**
     * Creates a new project and assigns it to both a Builder and a Project Manager.
     *
     * @param projectName Name of the project
     * @param description Project description
     * @param startDate Project start date
     * @param endDate Project end date
     * @param client Client associated with the project
     * @param status Initial project status
     * @param builderId ID of the builder responsible for the project
     * @param managerId ID of the project manager responsible for the project
     */
    public void createProject(String projectName, String description, LocalDate startDate, LocalDate endDate, Client client, Status status, String builderId, String managerId) {
        Project project = new Project(projectName, description, startDate, endDate, client, status, builderId, managerId);

        ProjectDAO.saveProject(project);

        ManagerDAO.addProjectToManager(managerId, project.getProjectId());
        BuilderDAO.addProjectToBuilder(builderId, project.getProjectId());
    }

    /**
     * Retrieves all projects managed by a specific Project Manager.
     *
     * @param managerId ID of the manager
     * @return list of projects assigned to the manager
     */
    public List<Project> getManagerProjects(String managerId) {
        List<Long> projectIds = ManagerDAO.getProjectIds(managerId);
        List<Project> projects = new ArrayList<>();

        for (Long id : projectIds) {
            Project p = ProjectDAO.getProjectById(id);
            if (p != null) {
                projects.add(p);
            }
        }
        return projects;
    }

    /**
     * Retrieves all projects assigned to a specific Builder.
     *
     * @param builderId ID of the builder
     * @return list of projects assigned to the builder
     */
    public List<Project> getBuilderProjects(String builderId) {
        List<Long> projectIds = BuilderDAO.getProjectIds(builderId);
        List<Project> projects = new ArrayList<>();

        for (Long id : projectIds) {
            Project p = ProjectDAO.getProjectById(id);
            if (p != null) {
                projects.add(p);
            }
        }
        return projects;
    }


    /**
     * Updates the status of a project by the assigned Builder.
     *
     * @param builderId ID of the builder attempting the update
     * @param projectId ID of the project
     * @param newStatus New project status
     * @return true if update is successful, false otherwise
     */
    public boolean updateProjectStatus(String builderId, long projectId, Status newStatus) {
        Project project = ProjectDAO.getProjectById(projectId);

        if (project == null) {
            return false;
        }

        // Authorization check
        if (!builderId.equals(project.getBuilderId())) {
            return false;
        }

        project.setStatus(newStatus);
        return true;

    }

    /**
     * Deletes a project if requested by the correct Project Manager.
     * Also removes project references from Builder and Manager records.
     *
     * @param managerId ID of the manager attempting deletion
     * @param projectId ID of the project
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteProject(String managerId, long projectId) {
        Project project = ProjectDAO.getProjectById(projectId);
        if (project == null) return false;

        // Check manager authorization
        if (!managerId.equals(project.getProjectManagerId())) return false;

        ProjectDAO.removeProject(projectId);

        ManagerDAO.removeProjectFromManager(managerId, projectId);

        String builderId = project.getBuilderId();
        if (builderId != null) {
            BuilderDAO.removeProjectFromBuilder(builderId, projectId);
        }

        return true;
    }
}
