package com.builderportfolio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DAO class for managing Builder data.
 * <p>
 * This class maintains an in-memory mapping between builder IDs and their assigned project IDs.
 * Supports operations for creating builders, adding/removing projects, and querying builder data.
 * Thread-safe collections are used for concurrent access.
 * </p>
 */
public class BuilderDAO {

    /**
     * In-memory storage for builder data.
     * Key   → Builder ID (ex: B1)
     * Value → List of project IDs assigned to that builder
     */
    private static Map<String, List<Long>> builderDatabase = new ConcurrentHashMap<>();

    /**
     * Creates a new builder entry when a builder registers.
     * Initially, the builder has no projects assigned.
     *
     * @param builderId ID of the builder to create
     */
    public static void createBuilder(String builderId) {
        builderDatabase.put(builderId, new ArrayList<>());
    }


    /**
     * Adds a project ID to the builder's project list.
     * If the builder does not exist, a new entry is created automatically.
     *
     * @param builderId ID of the builder
     * @param projectId ID of the project to assign
     * @throws NullPointerException if builderId or projectId is null
     */
    public static void addProjectToBuilder(String builderId, Long projectId) {
        if (builderId == null) throw new NullPointerException("Builder ID cannot be null");
        if (projectId == null) throw new NullPointerException("Project ID cannot be null");

        builderDatabase
                .computeIfAbsent(builderId, k -> new ArrayList<>())
                .add(projectId);
    }

    /**
     * Returns all project IDs assigned to a builder.
     *
     * @param builderId ID of the builder
     * @return List of project IDs assigned to the builder; empty list if none exist
     */
    public static List<Long> getProjectIds(String builderId) {
        return builderDatabase.getOrDefault(builderId, new CopyOnWriteArrayList<>());
    }

    /**
     * Removes a project from a builder's project list when the project is deleted.
     *
     * @param builderId ID of the builder
     * @param projectId ID of the project to remove
     */
    public static void removeProjectFromBuilder(String builderId, long projectId) {
        List<Long> projects = builderDatabase.get(builderId);
        if (projects != null) {
            projects.remove(projectId);
        }
    }


    /**
     * Checks whether a builder exists in the system.
     *
     * @param builderId ID of the builder to check
     * @return true if the builder exists, false otherwise
     */
    public static boolean builderExists(String builderId) {
        return builderDatabase.containsKey(builderId);
    }

    /**
     * Clears all builder data from the system.
     * Useful for resetting in-memory storage during tests.
     */
    public static void clear() {
        builderDatabase.clear();
    }


}
