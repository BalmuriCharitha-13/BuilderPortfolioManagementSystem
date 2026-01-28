package com.builderportfolio.view;

import com.builderportfolio.exception.UserNotFoundException;
import com.builderportfolio.model.*;
import com.builderportfolio.view.util.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


/**
 * View layer class responsible for handling all project-related
 * user interactions from the console.
 * <p>
 * This includes project creation, status updates, and deletion.
 * It collects validated input from users and delegates business
 * operations to the service layer.
 */
public class ProjectView {

    /**
     * Displays menu flow for creating a new project.
     * <p>
     * The method gathers project details such as name, description,
     * dates, client details, status, and builder assignment.
     * Input is validated before the project is created via ProjectService.
     */
    public void addProjectMenu() {
        String managerId = Session.getUser().getUserId();

        System.out.println("Enter Project Name:");
        String name = InputUtil.nextLine();

        System.out.println("Enter Description:");
        String desc = InputUtil.nextLine();

        LocalDate startDate = readDate("Start Date");
        LocalDate endDate;

        while (true) {
            endDate = readDate("End Date");

            if (endDate.isEqual(startDate)) {
                System.out.println("Start date and End date cannot be the same. Please re-enter End Date.");
            } else if (endDate.isBefore(startDate)) {
                System.out.println("End date cannot be before Start date. Please re-enter End Date.");
            } else {
                break;
            }
        }


        System.out.println("Enter Client Name:");
        String clientName = InputUtil.nextLine();

        String email;
        while (true) {
            System.out.println("Enter Client Email:");
            email = InputUtil.nextLine();
            if (ValidationUtil.isValidEmail(email)) break;
            else{
                System.out.println("Invalid Email, Try Again!!");
            }
        }

        String phone;
        while (true) {
            System.out.println("Enter Client Phone:");
            phone = InputUtil.nextLine();
            if (ValidationUtil.isValidPhone(phone)) break;
            else{
                System.out.println("Invalid Phone Number, Try Again!!");
            }
        }

        Client client = new Client(clientName, email, phone);

        Status status;
        while (true) {
            System.out.println("Enter Status (UPCOMING / IN_PROGRESS / COMPLETED):");
            try {
                status = Status.valueOf(InputUtil.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status! Please enter again.");
            }
        }


        String builderId;

        while (true) {
            System.out.println("Enter Builder ID:");
            builderId = InputUtil.nextLine();

            try {
                ServiceFactory.userService.fetchUserDetails(builderId);
                break;
            } catch (UserNotFoundException e) {
                System.out.println("No builder exists with this ID. Please try again.");
            }
        }


        ServiceFactory.projectService.createProject(name, desc, startDate, endDate, client, status, builderId, managerId);
        System.out.println("Project created successfully!");
    }

    /**
     * Allows a builder to update the status of one of their assigned projects.
     * <p>
     * Displays the builder's projects, accepts a project ID and new status,
     * and updates the project if authorization and ID validation succeed.
     */
    public void updateProjectStatusMenu() {
        String builderId = Session.getUser().getUserId();
        List<Project> projects = ServiceFactory.projectService.getBuilderProjects(builderId);

        projects.forEach(p -> System.out.println("ID: " + p.getProjectId() + " | Status: " + p.getStatus()));

        System.out.println("Enter Project ID:");
        long id = InputUtil.nextLong();

        System.out.println("Enter New Status:");
        Status status = Status.valueOf(InputUtil.nextLine().toUpperCase());

        if (ServiceFactory.projectService.updateProjectStatus(builderId, id, status))
            System.out.println("Updated successfully!");
        else
            System.out.println("Invalid project ID.");
    }

    /**
     * Allows a project manager to delete a project they manage.
     * <p>
     * Accepts a project ID and removes the project if the manager is authorized.
     */
    public void deleteProjectMenu() {
        String managerId = Session.getUser().getUserId();
        System.out.println("Enter Project ID:");
        long id = InputUtil.nextLong();

        if (ServiceFactory.projectService.deleteProject(managerId, id))
            System.out.println("Deleted successfully!");
        else
            System.out.println("Deletion failed.");
    }

    /**
     * Utility method to safely read and parse a date from user input.
     * <p>
     * Continues prompting until a valid date in YYYY-MM-DD format is entered.
     *
     * @param label the label indicating whether it is a start or end date
     * @return parsed LocalDate value
     */
    private LocalDate readDate(String label) {
        while (true) {
            try {
                System.out.println("Enter " + label + " (YYYY-MM-DD):");
                return LocalDate.parse(InputUtil.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            }
        }
    }
}
