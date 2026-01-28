package com.builderportfolio.service;

import com.builderportfolio.dao.BuilderDAO;
import com.builderportfolio.dao.ManagerDAO;
import com.builderportfolio.dao.UserDAO;
import com.builderportfolio.exception.InvalidCredentialsException;
import com.builderportfolio.exception.UserAlreadyExistsException;
import com.builderportfolio.exception.UserNotFoundException;
import com.builderportfolio.model.User;
import java.util.logging.Logger;


/**
 * Service layer class responsible for handling all user-related business logic.
 * <p>
 * This includes:
 * - User registration
 * - Login authentication
 * - Fetching user details
 * <p>
 * It coordinates between DAO classes and applies validation rules.
 */
public class UserService {

    private static final Logger logger =
            Logger.getLogger(UserService.class.getName());

    /**
     * Registers a new user in the system.
     * <p>
     * This method:
     * - Checks if the email is already registered
     * - Saves the user in the database
     * - Creates role-specific records (Builder or Project Manager)
     *
     * @param user the User object containing registration details
     * @param role role selected by the user (1 = Builder, 2 = Project Manager)
     * @return true if registration is successful
     * @throws UserAlreadyExistsException if the email is already registered
     * @throws IllegalArgumentException if the role is invalid
     */
    public boolean registrationService(User user, int role) {

        if (UserDAO.emailExists(user.getUserEmail())) {
            logger.warning("User already exists");
            throw new UserAlreadyExistsException(
                    "User already exists with email: " + user.getUserEmail()
            );
        }

        UserDAO.saveUser(user);

        if (role == 1) {
            BuilderDAO.createBuilder(user.getUserId());
        } else if (role == 2) {
            ManagerDAO.createProjectManager(user.getUserId());
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
        return true;
    }


    /**
     * Authenticates user login credentials.
     *
     * @param enteredUserId user ID entered during login
     * @param enteredPassword password entered during login
     * @return the logged-in User object if credentials are valid
     * @throws UserNotFoundException if the user ID does not exist
     * @throws InvalidCredentialsException if the password is incorrect
     */
    public User loginService(String enteredUserId, String enteredPassword) {

        if (!UserDAO.idExists(enteredUserId)) {
            logger.warning("User not found");
            throw new UserNotFoundException("User not found: " + enteredUserId);
        }

        User loggedInUser = UserDAO.getUserById(enteredUserId);

        if (!loggedInUser.getPassword().equals(enteredPassword)) {
            logger.warning("Invalid password");
            throw new InvalidCredentialsException("Incorrect password");
        }

        logger.info("Login successful");
        return loggedInUser;
    }

    /**
     * Retrieves user details using user ID.
     *
     * @param userId unique ID of the user
     * @return User object containing user information
     * @throws UserNotFoundException if no user exists with the given ID
     */
    public User fetchUserDetails(String userId) {
        User user = UserDAO.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found: " + userId);
        }
        return user;
    }
}
