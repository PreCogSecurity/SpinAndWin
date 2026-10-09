package com.precogsecurity.spinandwin;

/**
 * Represents a player in the slot machine game.
 * Encapsulates the player's points and betting logic.
 */
public final class Player {

    private static final int INITIAL_POINTS = 10;
    private static final int MIN_BET = 1;

    private int points;

    /**
     * Creates a new player with the initial points.
     */
    public Player() {
        this.points = INITIAL_POINTS;
    }

    /**
     * Creates a new player with a specific starting points value.
     * Primarily for testing purposes.
     *
     * @param initialPoints the starting points
     */
    Player(int initialPoints) {
        if (initialPoints < 0) {
            throw new IllegalArgumentException("Initial points cannot be negative");
        }
        this.points = initialPoints;
    }

    /**
     * Gets the current points balance.
     *
     * @return the current points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Checks if the player can place a bet of the given amount.
     *
     * @param betAmount the bet amount to check
     * @return true if the player can afford the bet, false otherwise
     */
    public boolean canAffordBet(int betAmount) {
        return betAmount >= MIN_BET && betAmount <= points;
    }

    /**
     * Places a bet and returns the bet amount if valid.
     *
     * @param betAmount the amount to bet
     * @return the bet amount
     * @throws IllegalArgumentException if the bet is invalid
     */
    public int placeBet(int betAmount) {
        if (!canAffordBet(betAmount)) {
            throw new IllegalArgumentException(
                String.format("Invalid bet: %d. Must be between %d and %d", betAmount, MIN_BET, points));
        }
        return betAmount;
    }

    /**
     * Updates the player's points based on the spin outcome.
     *
     * @param betAmount the amount that was bet
     * @param outcome the outcome of the spin
     */
    public void applyOutcome(int betAmount, SlotMachine.SpinOutcome outcome) {
        switch (outcome) {
            case JACKPOT -> points += betAmount;
            case PARTIAL_MATCH -> points -= betAmount / 2;
            case NO_MATCH -> points -= betAmount;
        }
        // Ensure points never go below zero
        if (points < 0) {
            points = 0;
        }
    }

    /**
     * Checks if the player has lost all points (game over).
     *
     * @return true if points are 0 or less, false otherwise
     */
    public boolean isGameOver() {
        return points <= 0;
    }

    /**
     * Resets the player to initial points.
     */
    public void reset() {
        this.points = INITIAL_POINTS;
    }
}