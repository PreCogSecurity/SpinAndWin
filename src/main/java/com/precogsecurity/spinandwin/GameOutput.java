package com.precogsecurity.spinandwin;

/**
 * Interface for game output handling.
 * Allows for different output implementations (console, GUI, testing).
 */
public interface GameOutput {

    /**
     * Prints the welcome message.
     */
    void printWelcome();

    /**
     * Prints the starting points.
     *
     * @param points the initial points
     */
    void printStartingPoints(int points);

    /**
     * Prints the spinning message.
     */
    void printSpinning();

    /**
     * Prints the reel values.
     *
     * @param reels the three reel values
     */
    void printReels(int[] reels);

    /**
     * Prints the outcome of a spin.
     *
     * @param outcome the spin outcome
     * @param betAmount the bet amount
     * @param currentPoints the player's current points
     */
    void printOutcome(SlotMachine.SpinOutcome outcome, int betAmount, int currentPoints);

    /**
     * Prints the current points.
     *
     * @param points the current points
     */
    void printCurrentPoints(int points);

    /**
     * Prints the game over message.
     */
    void printGameOver();

    /**
     * Prints the final score when the player quits.
     *
     * @param points the final points
     */
    void printFinalScore(int points);

    /**
     * Prints the goodbye message.
     */
    void printGoodbye();
}