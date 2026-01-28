package com.builderportfolio.view.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating user input fields such as
 * email address, phone number, and password.
 * <p>
 * All methods are static and can be used without creating an object.
 */
public class ValidationUtil {

    /**
     * Validates whether the given email address follows
     * a standard email format.
     *
     * @param email the email string to validate
     * @return true if email format is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", email);
    }

    /**
     * Validates whether the phone number contains exactly 10 digits.
     *
     * @param phone the phone number string
     * @return true if phone number is valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    /**
     * Validates password strength based on rules:
     * <ul>
     *     <li>Minimum 8 characters</li>
     *     <li>At least one uppercase letter</li>
     *     <li>At least one digit</li>
     *     <li>Only letters and numbers allowed</li>
     * </ul>
     *
     * @param password the password string to validate
     * @return true if password meets all conditions, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        if (!Pattern.compile("[A-Z]").matcher(password).find()) return false;
        if (!Pattern.compile("[0-9]").matcher(password).find()) return false;
        return Pattern.compile("[a-zA-Z0-9]*").matcher(password).matches();
    }
}

