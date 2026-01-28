package com.builderportfolio.model;

import java.time.LocalDate;

/**
 * Represents a construction project in the Builder Portfolio Management System.
 * Each project has a unique ID, assigned client, builder, project manager, status,
 * start and end dates, and a description.
 */
public class Project {
    private static long lastProjectId = 0L;
    private long projectId;
    private String projectName;
    private String projectDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private Client assignedClient;
    private String builderId;
    private String projectManagerId;


    /**
     * Creates a new Project with all required details.
     *
     * @param projectName Name of the project (cannot be null or empty)
     * @param projectDescription Description of the project
     * @param startDate Start date of the project (cannot be null)
     * @param endDate End date of the project (cannot be null, cannot be before start date)
     * @param assignedClient Client assigned to this project (cannot be null)
     * @param status Status of the project
     * @param builderId Builder assigned to this project (cannot be null or empty)
     * @param projectManagerId Project Manager assigned (cannot be null or empty)
     * @throws IllegalArgumentException if any mandatory field is invalid
     */
    public Project(String projectName, String projectDescription, LocalDate startDate, LocalDate endDate, Client assignedClient, Status status, String builderId, String projectManagerId) {

        if (projectName == null || projectName.isEmpty())
            throw new IllegalArgumentException("Project name cannot be null or empty");
        if (builderId == null || builderId.isEmpty())
            throw new IllegalArgumentException("Builder ID cannot be null or empty");
        if (projectManagerId == null || projectManagerId.isEmpty())
            throw new IllegalArgumentException("Project Manager ID cannot be null or empty");
        if (assignedClient == null)
            throw new IllegalArgumentException("Assigned client cannot be null");
        if (startDate == null || endDate == null)
            throw new IllegalArgumentException("Start date and end date cannot be null");
        if (endDate.isBefore(startDate))
            throw new IllegalArgumentException("End date cannot be before start date");

        this.projectId = ++lastProjectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignedClient = assignedClient;
        this.status = status;
        this.builderId = builderId;
        this.projectManagerId = projectManagerId;
    }

    /** @return the unique project ID */
    public long getProjectId() {
        return projectId;
    }

    /** @return the project name */
    public String getProjectName() {
        return projectName;
    }

    /** @param projectName new name for the project */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    /** @return the project description */
    public String getProjectDescription() {
        return projectDescription;
    }

    /** @param projectDescription new description for the project */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }


    /** @return the project start date */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** @param startDate new start date for the project */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /** @return the project end date */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the project end date.
     *
     * @param endDate new end date (cannot be before start date)
     * @throws IllegalArgumentException if endDate is before startDate
     */
    public void setEndDate(LocalDate endDate) {
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("End date cannot be before start date");
        this.endDate = endDate;
    }

    /** @return the client assigned to this project */
    public Client getAssignedClient() {
        return assignedClient;
    }


    /** @param assignedClient new client for the project */
    public void setAssignedClient(Client assignedClient) {
        this.assignedClient = assignedClient;
    }

    /** @return the builder ID responsible for this project */
    public String getBuilderId() {
        return builderId;
    }

    /** @return the project manager ID responsible for this project */
    public String getProjectManagerId() {
        return projectManagerId;
    }

    /** @return the current status of the project */
    public Status getStatus() {
        return status;
    }

    /** @param status new status for the project */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the project including all details.
     *
     * @return formatted string containing project ID, name, description, dates, client, status, builder, and manager
     */
    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", assignedClient=" + assignedClient +
                ", builderId='" + builderId + '\'' +
                ", projectManagerId='" + projectManagerId +

                '}';
    }
}
