package com.builderportfolio.view;

import com.builderportfolio.model.Project;
import com.builderportfolio.model.User;
import com.builderportfolio.view.util.*;
import java.util.List;


/**
 * Represents the menu interface for users with the Project Manager role.
 * <p>
 * This view allows project managers to create and delete projects,
 * view all projects assigned to them, see their profile details,
 * and log out of the system.
 */
public class ManagerMenuView {

    /**
     * Displays the project manager menu and handles user choices.
     * <p>
     * The menu runs in a loop until the manager chooses to log out.
     * Based on the selected option, appropriate project operations
     * or profile actions are triggered.
     * <ul>
     *     <li>Add a new project</li>
     *     <li>Delete an existing project</li>
     *     <li>View all managed projects</li>
     *     <li>View manager details</li>
     *     <li>Log out</li>
     * </ul>
     */
    public void managerMenu() {
        User user = Session.getUser();

        int choice;
        do {
            System.out.println("\nProject Manager Menu:");
            System.out.println("1. Add Project");
            System.out.println("2. Delete Project");
            System.out.println("3. View All Projects");
            System.out.println("4. View My Details");
            System.out.println("5. Log Out");

            choice = InputUtil.nextInt();

            switch (choice) {
                case 1 -> new ProjectView().addProjectMenu();
                case 2 -> {
                    List<Project> projects = ServiceFactory.projectService.getManagerProjects(user.getUserId());

                    if (projects.isEmpty()) {
                        System.out.println("There are no projects to delete.");
                    } else {
                        System.out.print("Enter Project ID: ");
                        int id = InputUtil.nextInt();

                        boolean removed = false;
                        for (Project p : projects) {
                            if (p.getProjectId() == id) {
                                projects.remove(p);
                                System.out.println("Project deleted successfully!");
                                removed = true;
                                break;
                            }
                        }
                        if (!removed) {
                            System.out.println("Deletion failed. Project ID not found.");
                        }
                    }
                }
                case 3 -> {
                    List<Project> projects =
                            ServiceFactory.projectService.getManagerProjects(user.getUserId());

                    if (projects.isEmpty()) {
                        System.out.println("No projects assigned.");
                    } else {
                        projects.forEach(System.out::println);
                    }
                }
                case 4 -> System.out.println(user);
                case 5 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }
}
