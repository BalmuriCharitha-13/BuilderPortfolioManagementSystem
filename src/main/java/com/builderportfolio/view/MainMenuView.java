package com.builderportfolio.view;

/**
 * Displays the main menu of the application.
 * <p>
 * This menu is shown when the application starts and provides
 * navigation options for users to register, log in, or exit.
 */
public class MainMenuView {

    /**
     * Prints the main menu options to the console.
     * <ul>
     *     <li>Register a new user</li>
     *     <li>Login as an existing user</li>
     *     <li>Exit the application</li>
     * </ul>
     */
    public void show() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }
}
