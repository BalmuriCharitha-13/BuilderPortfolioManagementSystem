package com.builderportfolio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DAO class for managing Project Manager data.
 * <p>
 * Keeps an in-memory mapping of Project Manager IDs to the list of project IDs they manage.
 * Supports creating managers, adding/removing projects, and querying manager data.
 * Thread-safe collections are used for concurrent access.
 * </p>
 */
public class ManagerDAO {

    /**
     * In-memory storage for manager data.
     * Key   → Manager ID (ex: P1)
     * Value → List of project IDs managed by the manager
     */
    private static Map<String, List<Long>> projectManagerDatabase = new ConcurrentHashMap<>();

    /**
     * Creates a new Project Manager entry when a manager registers.
     * Initially, the manager has no projects assigned.
     *
     * @param projectManagerId ID of the manager to create
     */
    public static void createProjectManager(String projectManagerId) {
        projectManagerDatabase.put(projectManagerId, new ArrayList<>());
    }

    /**
     * Adds a project ID to a Project Manager's project list.
     * If the manager does not exist, a new entry is created automatically.
     *
     * @param managerId ID of the manager
     * @param projectId ID of the project to assign
     * @throws NullPointerException if managerId or projectId is null
     */
    public static void addProjectToManager(String managerId, Long projectId) {
        if (managerId == null) throw new NullPointerException("Manager ID cannot be null");
        if (projectId == null) throw new NullPointerException("Project ID cannot be null");

        projectManagerDatabase
                .computeIfAbsent(managerId, k -> new CopyOnWriteArrayList<>())
                .add(projectId);
    }

    /**
     * Returns all project IDs assigned to a manager.
     *
     * @param managerId ID of the manager
     * @return List of project IDs; empty list if manager has no projects
     */
    public static List<Long> getProjectIds(String managerId) {
        return projectManagerDatabase.getOrDefault(managerId, new ArrayList<>());
    }


    /**
     * Removes a project from a manager's project list.
     * Called when a project is deleted.
     *
     * @param managerId ID of the manager
     * @param projectId ID of the project to remove
     */
    public static void removeProjectFromManager(String managerId, long projectId) {
        List<Long> projects = projectManagerDatabase.get(managerId);
        if (projects != null) {
            projects.remove(projectId);

        }
    }

    /**
     * Checks whether a Project Manager exists in the system.
     *
     * @param managerId ID of the manager
     * @return true if the manager exists, false otherwise
     */
    public static boolean projectManagerExists(String managerId) {
        return projectManagerDatabase.containsKey(managerId);
    }

    /**
     * Clears all Project Manager data.
     * Useful for resetting in-memory storage during tests.
     */
    public static void clearDatabase() {
        projectManagerDatabase.clear();
    }

}
