package com.precogsecurity.spinandwin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InputValidator class.
 */
class InputValidatorTest {

    private InputValidator inputValidator;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        if (inputValidator != null) {
            inputValidator.close();
        }
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        inputValidator = new InputValidator(new Scanner(System.in));
    }

    @Test
    void readYesNoReturnsTrueForYes() {
        provideInput("yes\n");
        assertTrue(inputValidator.readYesNo("Play?"));
    }

    @Test
    void readYesNoReturnsTrueForY() {
        provideInput("y\n");
        assertTrue(inputValidator.readYesNo("Play?"));
    }

    @Test
    void readYesNoReturnsTrueForCaseInsensitiveYes() {
        provideInput("YES\n");
        assertTrue(inputValidator.readYesNo("Play?"));
        provideInput("Yes\n");
        assertTrue(inputValidator.readYesNo("Play?"));
    }

    @Test
    void readYesNoReturnsFalseForNo() {
        provideInput("no\n");
        assertFalse(inputValidator.readYesNo("Play?"));
    }

    @Test
    void readYesNoReturnsFalseForN() {
        provideInput("n\n");
        assertFalse(inputValidator.readYesNo("Play?"));
    }

    @Test
    void readYesNoRepromptsOnInvalidInput() {
        provideInput("maybe\nno\n");
        assertFalse(inputValidator.readYesNo("Play?"));
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter 'yes' or 'no'"));
    }

    @Test
    void readBetAmountReturnsValidBet() {
        provideInput("5\n");
        int bet = inputValidator.readBetAmount("Bet:", 1, 10);
        assertEquals(5, bet);
    }

    @Test
    void readBetAmountReturnsMinusOneForQuit() {
        provideInput("0\n");
        int bet = inputValidator.readBetAmount("Bet:", 1, 10);
        assertEquals(-1, bet);
    }

    @Test
    void readBetAmountRepromptsOnOutOfRange() {
        provideInput("15\n5\n");
        int bet = inputValidator.readBetAmount("Bet:", 1, 10);
        assertEquals(5, bet);
        String output = outputStream.toString();
        assertTrue(output.contains("must be between"));
    }

    @Test
    void readBetAmountRepromptsOnNonNumeric() {
        provideInput("abc\n5\n");
        int bet = inputValidator.readBetAmount("Bet:", 1, 10);
        assertEquals(5, bet);
        String output = outputStream.toString();
        assertTrue(output.contains("valid number"));
    }

    @Test
    void readBetAmountRepromptsOnNegativeNumber() {
        provideInput("-5\n5\n");
        int bet = inputValidator.readBetAmount("Bet:", 1, 10);
        assertEquals(5, bet);
    }

    @Test
    void readBetAmountHandlesWhitespace() {
        provideInput("  5  \n");
        int bet = inputValidator.readBetAmount("Bet:", 1, 10);
        assertEquals(5, bet);
    }

    @Test
    void readBetAmountThrowsOnInvalidMinBet() {
        provideInput("5\n");
        assertThrows(IllegalArgumentException.class, () ->
            inputValidator.readBetAmount("Bet:", 0, 10));
    }

    @Test
    void readBetAmountThrowsOnMaxLessThanMin() {
        provideInput("5\n");
        assertThrows(IllegalArgumentException.class, () ->
            inputValidator.readBetAmount("Bet:", 10, 5));
    }

    @Test
    void constructorThrowsOnNullScanner() {
        assertThrows(NullPointerException.class, () -> new InputValidator(null));
    }

    @Test
    void closeDoesNotThrow() {
        provideInput("yes\n");
        inputValidator.readYesNo("Test?");
        assertDoesNotThrow(() -> inputValidator.close());
    }
}