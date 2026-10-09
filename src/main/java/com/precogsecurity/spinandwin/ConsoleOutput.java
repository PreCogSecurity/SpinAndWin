package com.precogsecurity.spinandwin;

/**
 * Console implementation of GameOutput.
 * Handles all user-facing text output.
 */
public final class ConsoleOutput implements GameOutput {

    @Override
    public void printWelcome() {
        System.out.println("========================================");
        System.out.println("       Welcome to Spin and Win!         ");
        System.out.println("========================================");
        System.out.println("Get three matching numbers to win big!");
        System.out.println("Two matching numbers loses half your bet.");
        System.out.println("No matches loses your full bet.");
        System.out.println();
    }

    @Override
    public void printStartingPoints(int points) {
        System.out.println("You start with " + points + " points. Use them wisely!");
        System.out.println();
    }

    @Override
    public void printSpinning() {
        System.out.println("...Spinning...");
    }

    @Override
    public void printReels(int[] reels) {
        for (int i = 0; i < reels.length; i++) {
            System.out.println("Reel " + (i + 1) + ": " + reels[i]);
        }
    }

    @Override
    public void printOutcome(SlotMachine.SpinOutcome outcome, int betAmount, int currentPoints) {
        switch (outcome) {
            case JACKPOT -> {
                System.out.println("🎉 JACKPOT! All three match! You won " + betAmount + " points!");
            }
            case PARTIAL_MATCH -> {
                int loss = betAmount / 2;
                System.out.println("So close! Two out of three match. You lost " + loss + " points.");
            }
            case NO_MATCH -> {
                System.out.println("No matches this time. You lost " + betAmount + " points.");
            }
        }
        System.out.println("Current points: " + currentPoints);
        System.out.println();
    }

    @Override
    public void printCurrentPoints(int points) {
        System.out.println("You have " + points + " points!");
        System.out.println();
    }

    @Override
    public void printGameOver() {
        System.out.println("========================================");
        System.out.println("       GAME OVER                        ");
        System.out.println("========================================");
        System.out.println("You lost all your points. Better luck next time!");
        System.out.println();
    }

    @Override
    public void printFinalScore(int points) {
        System.out.println("========================================");
        System.out.println("       Thanks for playing!              ");
        System.out.println("========================================");
        System.out.println("You ended the game with " + points + " points!");
        System.out.println();
    }

    @Override
    public void printGoodbye() {
        System.out.println("Goodbye! Come back soon!");
    }
}