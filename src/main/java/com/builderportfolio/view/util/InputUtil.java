package com.builderportfolio.view.util;

import java.util.Scanner;

/**
 * Utility class for handling user input from the console.
 * <p>
 * This class wraps the Scanner object and provides methods to safely
 * read different data types while handling newline issues.
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads a full line of text input from the console.
     *
     * @return the string entered by the user
     */
    public static String nextLine() {
        return scanner.nextLine();
    }

    /**
            * Reads an integer value from the console.
     * Also consumes the leftover newline to prevent input skipping issues.
            *
            * @return the integer entered by the user
     */
    public static int nextInt() {
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    /**
     * Reads a long value from the console.
     * Also consumes the leftover newline to prevent input skipping issues.
     *
     * @return the long value entered by the user
     */
    public static long nextLong() {
        long val = scanner.nextLong();
        scanner.nextLine();
        return val;
    }
}

