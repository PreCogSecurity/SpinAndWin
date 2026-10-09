package com.precogsecurity.spinandwin;

/**
 * Main entry point for the Spin and Win game.
 */
public final class SpinAndWin {

    /**
     * Private constructor to prevent instantiation.
     */
    private SpinAndWin() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Main method to start the game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}