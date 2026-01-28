package com.builderportfolio.view;

import com.builderportfolio.exception.UserAlreadyExistsException;
import com.builderportfolio.model.User;
import com.builderportfolio.view.util.*;

/**
 * View layer class responsible for handling new user registration.
 * <p>
 * This class interacts with the user through the console, collects
 * validated registration details, creates a User object, and
 * delegates the registration process to the service layer.
 */
public class RegisterView {

    /**
     * Displays the registration flow for new users.
     * <p>
     * The method collects user details such as name, email, phone,
     * experience, role, and password. Inputs are validated using
     * ValidationUtil before creating a User object.
     * <p>
     * If registration succeeds:
     * <ul>
     *   <li>User ID is displayed</li>
     *   <li>User session is initialized</li>
     *   <li>User is redirected to role-based menu</li>
     * </ul>
     * <p>
     * If a user with the same email already exists,
     * a UserAlreadyExistsException is handled.
     */
    public void registerMenu() {
        System.out.println("Enter Name:");
        String name = InputUtil.nextLine();

        String email;
        while (true) {
            System.out.println("Enter your E-Mail:");
            email = InputUtil.nextLine();
            if (ValidationUtil.isValidEmail(email)) break;
            System.out.println("Invalid email! Try again.");
        }

        String phone;
        while (true) {
            System.out.println("Enter Phone Number:");
            phone = InputUtil.nextLine();
            if (ValidationUtil.isValidPhone(phone)) break;
            System.out.println("Invalid phone number.");
        }

        System.out.println("Enter Experience:");
        int exp = Integer.parseInt(InputUtil.nextLine());

        System.out.println("Enter Role (1.Builder 2.Project_Manager):");
        int role = Integer.parseInt(InputUtil.nextLine());

        String password;
        while (true) {
            System.out.println("Enter password:");
            password = InputUtil.nextLine();
            if (ValidationUtil.isValidPassword(password)) break;
            System.out.println("Password must be at least 8 characters, contain 1 uppercase letter, 1 number, and only letters & digits.");
        }

        User user = new User(name, email, phone, exp, password, role);

        try {
            if (ServiceFactory.userService.registrationService(user, role)) {
                System.out.println("\nRegistration Successful!");
                System.out.println("Your User ID is: " + user.getUserId());
                System.out.println("Please remember this ID for future login.\n");

                Session.setUser(user);

                if (role == 1) new BuilderMenuView().builderMenu();
                else new ManagerMenuView().managerMenu();
            }
        } catch (UserAlreadyExistsException e) {
            System.out.println("User already exists with email " + email);
        }
    }
}
