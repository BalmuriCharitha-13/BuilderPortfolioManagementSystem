package com.builderportfolio.view.util;

import com.builderportfolio.service.ProjectService;
import com.builderportfolio.service.UserService;

/**
 * Factory class that provides shared service instances for the application.
 * <p>
 * This ensures that a single instance of each service is used across
 * the entire system, avoiding repeated object creation.
 */
public class ServiceFactory {
    /**
     * Shared instance of {@link UserService} used for all user-related operations.
     */
    public static final UserService userService = new UserService();

    /**
     * Shared instance of {@link ProjectService} used for all project-related operations.
     */
    public static final ProjectService projectService = new ProjectService();
}
