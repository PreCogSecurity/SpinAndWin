package com.precogsecurity.spinandwin;

import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Handles secure input validation for the game.
 * Provides methods to safely read and validate user input.
 */
public final class InputValidator {

    private static final int MAX_INPUT_LENGTH = 100;
    private static final String YES_PATTERN = "^(yes|y)$";
    private static final String NO_PATTERN = "^(no|n)$";

    private final Scanner scanner;

    /**
     * Creates a new InputValidator with a scanner reading from System.in.
     */
    public InputValidator() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Creates a new InputValidator with a provided scanner.
     * Primarily for testing purposes.
     *
     * @param scanner the scanner to use
     */
    InputValidator(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "Scanner must not be null");
    }

    /**
     * Reads a yes/no answer from the user.
     *
     * @param prompt the prompt to display
     * @return true for yes, false for no
     */
    public boolean readYesNo(String prompt) {
        Objects.requireNonNull(prompt, "Prompt must not be null");

        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String input = readLine();
            if (input == null) {
                continue;
            }
            String trimmed = input.trim().toLowerCase();
            if (trimmed.matches(YES_PATTERN)) {
                return true;
            } else if (trimmed.matches(NO_PATTERN)) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }

    /**
     * Reads an integer bet amount from the user with validation.
     *
     * @param prompt the prompt to display
     * @param minBet the minimum allowed bet
     * @param maxBet the maximum allowed bet
     * @return the validated bet amount, or -1 if user wants to quit (enters 0)
     */
    public int readBetAmount(String prompt, int minBet, int maxBet) {
        Objects.requireNonNull(prompt, "Prompt must not be null");
        if (minBet < 1) {
            throw new IllegalArgumentException("Minimum bet must be at least 1");
        }
        if (maxBet < minBet) {
            throw new IllegalArgumentException("Maximum bet must be >= minimum bet");
        }

        while (true) {
            System.out.print(prompt + " (" + minBet + "-" + maxBet + ", or 0 to quit): ");
            String input = readLine();
            if (input == null) {
                continue;
            }

            String trimmed = input.trim();
            if (trimmed.equals("0")) {
                return -1; // Signal to quit
            }

            try {
                int bet = Integer.parseInt(trimmed);
                if (bet >= minBet && bet <= maxBet) {
                    return bet;
                } else {
                    System.out.println("Bet must be between " + minBet + " and " + maxBet + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a line of input with length limitation for security.
     *
     * @return the input line, or null if input is invalid
     */
    private String readLine() {
        if (!scanner.hasNextLine()) {
            return null;
        }
        String line = scanner.nextLine();
        if (line.length() > MAX_INPUT_LENGTH) {
            System.out.println("Input too long. Maximum " + MAX_INPUT_LENGTH + " characters.");
            return null;
        }
        return line;
    }

    /**
     * Closes the underlying scanner.
     * Should be called when the game ends.
     */
    public void close() {
        scanner.close();
    }
}