package com.builderportfolio;

import com.builderportfolio.view.LoginView;
import com.builderportfolio.view.MainMenuView;
import com.builderportfolio.view.RegisterView;
import com.builderportfolio.view.util.InputUtil;


/**
 * Entry point of the Builder Portfolio Management application.
 * <p>
 * This class starts the program and continuously displays the
 * main menu until the user chooses to exit.
 * It allows users to:
 * <ul>
 *     <li>Register as a new user</li>
 *     <li>Login with existing credentials</li>
 *     <li>Exit the application</li>
 * </ul>
 */
public class Main {

    /**
     * The main method where program execution begins.
     * <p>
     * This method:
     * <ul>
     *     <li>Displays the main menu</li>
     *     <li>Accepts and validates user input</li>
     *     <li>Redirects users to registration or login views</li>
     *     <li>Handles invalid numeric and non-numeric inputs</li>
     * </ul>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        MainMenuView mainMenu = new MainMenuView();

        while (true) {
            mainMenu.show();
            int option;
            while (true) {
                System.out.print("Enter your choice: ");
                try {
                    option = Integer.parseInt(InputUtil.nextLine());
                    if (option < 1 || option > 3) {
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
                }
            }

            switch (option) {
                case 1 -> new RegisterView().registerMenu();
                case 2 -> new LoginView().loginMenu();
                case 3 -> {
                    System.out.println("Exiting... Come Back Soon!!");
                    return;
                }
            }
        }
    }
}
