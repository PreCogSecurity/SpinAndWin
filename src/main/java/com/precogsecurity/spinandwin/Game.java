package com.precogsecurity.spinandwin;

/**
 * Main game controller that orchestrates the slot machine game flow.
 * Separates game logic from I/O for testability.
 */
public final class Game {

    private final SlotMachine slotMachine;
    private final Player player;
    private final InputValidator inputValidator;
    private final GameOutput output;

    /**
     * Creates a new Game with default components.
     */
    public Game() {
        this(new SlotMachine(), new Player(), new InputValidator(), new ConsoleOutput());
    }

    /**
     * Creates a new Game with injected dependencies.
     * Primarily for testing purposes.
     *
     * @param slotMachine the slot machine to use
     * @param player the player instance
     * @param inputValidator the input validator
     * @param output the output handler
     */
    Game(SlotMachine slotMachine, Player player, InputValidator inputValidator, GameOutput output) {
        this.slotMachine = Objects.requireNonNull(slotMachine, "SlotMachine must not be null");
        this.player = Objects.requireNonNull(player, "Player must not be null");
        this.inputValidator = Objects.requireNonNull(inputValidator, "InputValidator must not be null");
        this.output = Objects.requireNonNull(output, "GameOutput must not be null");
    }

    /**
     * Runs the main game loop.
     */
    public void run() {
        output.printWelcome();

        if (!inputValidator.readYesNo("Would you like to play?")) {
            output.printGoodbye();
            return;
        }

        output.printStartingPoints(player.getPoints());

        while (!player.isGameOver()) {
            int betAmount = inputValidator.readBetAmount(
                "Place your bet",
                1,
                player.getPoints()
            );

            if (betAmount == -1) { // User chose to quit
                output.printFinalScore(player.getPoints());
                return;
            }

            playRound(betAmount);

            if (player.isGameOver()) {
                output.printGameOver();
                break;
            }

            output.printCurrentPoints(player.getPoints());

            if (!inputValidator.readYesNo("Would you like to play again?")) {
                output.printFinalScore(player.getPoints());
                return;
            }
        }

        inputValidator.close();
    }

    /**
     * Plays a single round of the game.
     *
     * @param betAmount the amount the player bets
     */
    private void playRound(int betAmount) {
        output.printSpinning();

        int[] reels = slotMachine.spin();
        output.printReels(reels);

        SlotMachine.SpinOutcome outcome = slotMachine.evaluate(reels);
        player.applyOutcome(betAmount, outcome);

        output.printOutcome(outcome, betAmount, player.getPoints());
    }
}