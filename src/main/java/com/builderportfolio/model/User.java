package com.builderportfolio.model;

/**
 * Represents a user in the Builder Portfolio Management System.
 * A user can either be a Builder or a Project Manager based on role.
 * Each user is assigned a unique ID automatically during registration.
 */
public class User {
    private static long builderLastId = 0L;
    private static long projectManagerLastId = 0L;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhoneNo;
    private int userExperience;
    private int role;
    private String password;

    /**
     * Creates a new user during registration.
     * Automatically assigns a unique ID based on the selected role.
     *
     * @param userName Name of the user (cannot be null or empty)
     * @param userEmail Email of the user (cannot be null or empty)
     * @param userPhoneNo Phone number of the user
     * @param userExperience Experience in years
     * @param password Account password (cannot be null or empty)
     * @param selectedRole Role of user (1 = Builder, 2 = Project Manager)
     * @throws IllegalArgumentException if mandatory fields are invalid
     */
    public User(String userName, String userEmail, String userPhoneNo, int userExperience, String password, int selectedRole) {
        if (userName == null || userName.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        if (userEmail == null || userEmail.isEmpty())
            throw new IllegalArgumentException("Email cannot be null or empty");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be null or empty"); // For User

        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNo = userPhoneNo;
        this.userExperience = userExperience;
        this.password = password;
        this.role = selectedRole;
        if (selectedRole == 1) {
            String id = "B";
            this.userId = id + (++builderLastId);
        } else {
            String id = "P";
            this.userId = id + (++projectManagerLastId);
        }
    }

    /** @return the unique user ID */
    public String getUserId() {
        return userId;
    }

    /** @return role of the user */
    public int getRole() {
        return role;
    }

    /** @return account password */
    public String getPassword() {
        return password;
    }

    /** @param password new password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return user name */
    public String getUserName() {
        return userName;
    }

    /** @param userName new name */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** @return user email */
    public String getUserEmail() {
        return userEmail;
    }

    /** @param userEmail new email */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /** @return user phone number */
    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    /** @param userPhoneNo new phone number */
    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    /** @return years of experience */
    public int getUserExperience() {
        return userExperience;
    }

    /** @param userExperience updated experience */
    public void setUserExperience(int userExperience) {
        this.userExperience = userExperience;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return formatted user details
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", userExperience=" + userExperience +
                ", password='" + password + '\'' +
                '}';
    }
}
