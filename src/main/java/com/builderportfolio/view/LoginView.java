package com.builderportfolio.view;

import com.builderportfolio.exception.InvalidCredentialsException;
import com.builderportfolio.exception.UserNotFoundException;
import com.builderportfolio.model.User;
import com.builderportfolio.view.util.InputUtil;
import com.builderportfolio.view.util.ServiceFactory;
import com.builderportfolio.view.util.Session;

/**
 * Handles user authentication through the console interface.
 * <p>
 * Collects login credentials, validates them using the service layer,
 * and redirects the user to the appropriate menu based on role.
 */
public class LoginView {

    /**
     * Displays the login prompt, reads user credentials,
     * and performs authentication.
     * <p>
     * On successful login:
     * <ul>
     *     <li>Stores the logged-in user in Session</li>
     *     <li>Redirects Builder users to BuilderMenuView</li>
     *     <li>Redirects Manager users to ManagerMenuView</li>
     * </ul>
     * <p>
     * Handles common login errors such as:
     * <ul>
     *     <li>User not found</li>
     *     <li>Invalid password</li>
     * </ul>
     */
    public void loginMenu() {
        System.out.println("Enter your Details:");
        System.out.println("Enter Your UserId:");
        String userId = InputUtil.nextLine();

        System.out.println("Enter your Password:");
        String password = InputUtil.nextLine();

        try {
            User user = ServiceFactory.userService.loginService(userId, password);
            Session.setUser(user);

            char role = user.getUserId().charAt(0);
            if (role == 'B') new BuilderMenuView().builderMenu();
            else if (role == 'P') new ManagerMenuView().managerMenu();

        } catch (UserNotFoundException e) {
            System.out.println("Warning: User not found with ID " + userId);
        } catch (InvalidCredentialsException e) {
            System.out.println("Warning: Incorrect password. Try again.");
        }
    }
}
