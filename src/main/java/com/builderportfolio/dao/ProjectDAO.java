package com.builderportfolio.dao;

import com.builderportfolio.model.Project;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DAO class for managing Project data.
 * <p>
 * Maintains an in-memory mapping of Project IDs to Project objects.
 * Provides methods to save, fetch, remove, and clear projects.
 * Thread-safe collections are used to allow concurrent access.
 * </p>
 */
public class ProjectDAO {

    /**
     * In-memory storage for project data.
     * Key   → Project ID
     * Value → Project object
     */
    private static Map<Long, Project> projectDatabase = new ConcurrentHashMap<>();

    /**
     * Saves a new project into the database.
     * If the project ID already exists, it will overwrite the existing project.
     *
     * @param project Project object to save
     */
    public static void saveProject(Project project) {
        projectDatabase.put(project.getProjectId(), project);
    }

    /**
     * Fetches a project from the database using its ID.
     *
     * @param projectId ID of the project to fetch
     * @return Project object if found, otherwise null
     */
    public static Project getProjectById(Long projectId) {
        return projectDatabase.get(projectId);
    }

    /**
     * Removes a project from the database using its ID.
     * Called when a project is deleted.
     *
     * @param projectId ID of the project to remove
     */
    public static void removeProject(Long projectId) {
        projectDatabase.remove(projectId);
    }

    /**
     * Clears all project records from the database.
     * Useful for resetting in-memory storage during tests.
     */
    public static void clearDatabase() {
        projectDatabase.clear();
    }

}

