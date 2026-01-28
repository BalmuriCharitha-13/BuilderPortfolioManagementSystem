package com.builderportfolio.view;

import com.builderportfolio.model.Project;
import com.builderportfolio.model.User;
import com.builderportfolio.view.util.*;
import java.util.List;

/**
 * Console menu view for users with the Builder role.
 * <p>
 * Provides options for builders to:
 * <ul>
 *     <li>Update project status</li>
 *     <li>View assigned projects</li>
 *     <li>View their profile details</li>
 *     <li>Log out</li>
 * </ul>
 */
public class BuilderMenuView {

    /**
     * Displays the Builder menu and handles user actions
     * in a loop until the user chooses to log out.
     * <p>
     * Uses Session to get logged-in user details and
     * ServiceFactory to interact with business logic.
     */
    public void builderMenu() {
        User user = Session.getUser();

        int choice;
        do {
            System.out.println("\nBuilder Menu:");
            System.out.println("1. Update Project");
            System.out.println("2. View All Projects");
            System.out.println("3. View My Details");
            System.out.println("4. Log Out");

            choice = InputUtil.nextInt();

            switch (choice) {
                case 1 -> new ProjectView().updateProjectStatusMenu();
                case 2 -> {
                    List<Project> projects =
                            ServiceFactory.projectService.getBuilderProjects(user.getUserId());

                    if (projects.isEmpty()) {
                        System.out.println("No projects assigned.");
                    } else {
                        projects.forEach(System.out::println);
                    }
                }
                case 3 -> System.out.println(user);
                case 4 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }
}


