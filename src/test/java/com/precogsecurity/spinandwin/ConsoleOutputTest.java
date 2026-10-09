package com.precogsecurity.spinandwin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ConsoleOutput class.
 */
class ConsoleOutputTest {

    private ConsoleOutput consoleOutput;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        consoleOutput = new ConsoleOutput();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void printWelcomeContainsGameTitle() {
        consoleOutput.printWelcome();
        String output = outputStream.toString();
        assertTrue(output.contains("Spin and Win"));
        assertTrue(output.contains("Welcome"));
    }

    @Test
    void printStartingPointsShowsPoints() {
        consoleOutput.printStartingPoints(10);
        assertTrue(outputStream.toString().contains("10"));
    }

    @Test
    void printSpinningShowsMessage() {
        consoleOutput.printSpinning();
        assertTrue(outputStream.toString().contains("Spinning"));
    }

    @Test
    void printReelsShowsAllThreeReels() {
        consoleOutput.printReels(new int[]{1, 2, 3});
        String output = outputStream.toString();
        assertTrue(output.contains("Reel 1: 1"));
        assertTrue(output.contains("Reel 2: 2"));
        assertTrue(output.contains("Reel 3: 3"));
    }

    @Test
    void printOutcomeJackpotShowsWinMessage() {
        consoleOutput.printOutcome(SlotMachine.SpinOutcome.JACKPOT, 5, 15);
        String output = outputStream.toString();
        assertTrue(output.contains("JACKPOT"));
        assertTrue(output.contains("5"));
        assertTrue(output.contains("15"));
    }

    @Test
    void printOutcomePartialMatchShowsLossMessage() {
        consoleOutput.printOutcome(SlotMachine.SpinOutcome.PARTIAL_MATCH, 6, 7);
        String output = outputStream.toString();
        assertTrue(output.contains("close"));
        assertTrue(output.contains("3")); // half of 6
    }

    @Test
    void printOutcomeNoMatchShowsLossMessage() {
        consoleOutput.printOutcome(SlotMachine.SpinOutcome.NO_MATCH, 4, 6);
        String output = outputStream.toString();
        assertTrue(output.contains("No matches"));
        assertTrue(output.contains("4"));
    }

    @Test
    void printCurrentPointsShowsPoints() {
        consoleOutput.printCurrentPoints(8);
        assertTrue(outputStream.toString().contains("8"));
    }

    @Test
    void printGameOverShowsMessage() {
        consoleOutput.printGameOver();
        String output = outputStream.toString();
        assertTrue(output.contains("GAME OVER"));
        assertTrue(output.contains("Better luck"));
    }

    @Test
    void printFinalScoreShowsPoints() {
        consoleOutput.printFinalScore(12);
        String output = outputStream.toString();
        assertTrue(output.contains("Thanks for playing"));
        assertTrue(output.contains("12"));
    }

    @Test
    void printGoodbyeShowsMessage() {
        consoleOutput.printGoodbye();
        assertTrue(outputStream.toString().contains("Goodbye"));
    }
}