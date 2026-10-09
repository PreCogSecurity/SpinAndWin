package com.precogsecurity.spinandwin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for Game class.
 */
class GameTest {

    private SlotMachine mockSlotMachine;
    private Player mockPlayer;
    private InputValidator mockInputValidator;
    private GameOutput mockOutput;
    private Game game;

    @BeforeEach
    void setUp() {
        mockSlotMachine = mock(SlotMachine.class);
        mockPlayer = mock(Player.class);
        mockInputValidator = mock(InputValidator.class);
        mockOutput = mock(GameOutput.class);
        game = new Game(mockSlotMachine, mockPlayer, mockInputValidator, mockOutput);
    }

    @Test
    void runExitsImmediatelyIfUserDeclines() {
        when(mockInputValidator.readYesNo(anyString())).thenReturn(false);

        game.run();

        verify(mockOutput).printWelcome();
        verify(mockOutput).printGoodbye();
        verify(mockInputValidator).readYesNo("Would you like to play?");
        verifyNoMoreInteractions(mockSlotMachine, mockPlayer);
    }

    @Test
    void runPlaysRoundWhenUserAccepts() {
        when(mockInputValidator.readYesNo("Would you like to play?")).thenReturn(true);
        when(mockPlayer.getPoints()).thenReturn(10);
        when(mockInputValidator.readBetAmount(anyString(), eq(1), eq(10))).thenReturn(5);
        when(mockSlotMachine.spin()).thenReturn(new int[]{1, 1, 1});
        when(mockSlotMachine.evaluate(any())).thenReturn(SlotMachine.SpinOutcome.JACKPOT);
        when(mockPlayer.isGameOver()).thenReturn(false);
        when(mockInputValidator.readYesNo("Would you like to play again?")).thenReturn(false);

        game.run();

        verify(mockOutput).printWelcome();
        verify(mockOutput).printStartingPoints(10);
        verify(mockOutput).printSpinning();
        verify(mockSlotMachine).spin();
        verify(mockSlotMachine).evaluate(new int[]{1, 1, 1});
        verify(mockPlayer).placeBet(5);
        verify(mockPlayer).applyOutcome(5, SlotMachine.SpinOutcome.JACKPOT);
        verify(mockOutput).printReels(new int[]{1, 1, 1});
        verify(mockOutput).printOutcome(SlotMachine.SpinOutcome.JACKPOT, 5, 10);
        verify(mockOutput).printCurrentPoints(10);
        verify(mockOutput).printFinalScore(10);
        verify(mockInputValidator).close();
    }

    @Test
    void runHandlesQuitSignal() {
        when(mockInputValidator.readYesNo("Would you like to play?")).thenReturn(true);
        when(mockPlayer.getPoints()).thenReturn(10);
        when(mockInputValidator.readBetAmount(anyString(), eq(1), eq(10))).thenReturn(-1); // Quit signal

        game.run();

        verify(mockOutput).printFinalScore(10);
        verify(mockInputValidator).close();
        verify(mockSlotMachine, never()).spin();
    }

    @Test
    void runHandlesGameOver() {
        when(mockInputValidator.readYesNo("Would you like to play?")).thenReturn(true);
        when(mockPlayer.getPoints()).thenReturn(10);
        when(mockInputValidator.readBetAmount(anyString(), eq(1), eq(10))).thenReturn(10);
        when(mockSlotMachine.spin()).thenReturn(new int[]{1, 2, 3});
        when(mockSlotMachine.evaluate(any())).thenReturn(SlotMachine.SpinOutcome.NO_MATCH);
        when(mockPlayer.isGameOver()).thenReturn(true);

        game.run();

        verify(mockOutput).printGameOver();
        verify(mockInputValidator).close();
    }

    @Test
    void runContinuesGameWhenUserWantsToPlayAgain() {
        when(mockInputValidator.readYesNo("Would you like to play?")).thenReturn(true);
        when(mockPlayer.getPoints()).thenReturn(10, 15);
        when(mockInputValidator.readBetAmount(anyString(), eq(1), eq(10))).thenReturn(5);
        when(mockInputValidator.readBetAmount(anyString(), eq(1), eq(15))).thenReturn(5);
        when(mockSlotMachine.spin()).thenReturn(new int[]{1, 1, 1}, new int[]{2, 2, 2});
        when(mockSlotMachine.evaluate(any())).thenReturn(SlotMachine.SpinOutcome.JACKPOT);
        when(mockPlayer.isGameOver()).thenReturn(false, false);
        when(mockInputValidator.readYesNo("Would you like to play again?")).thenReturn(true, false);

        game.run();

        verify(mockSlotMachine, times(2)).spin();
        verify(mockPlayer, times(2)).placeBet(5);
        verify(mockPlayer, times(2)).applyOutcome(5, SlotMachine.SpinOutcome.JACKPOT);
        verify(mockOutput, times(2)).printSpinning();
        verify(mockOutput, times(2)).printReels(any());
    }

    @Test
    void constructorThrowsOnNullSlotMachine() {
        assertThrows(NullPointerException.class, () ->
            new Game(null, mockPlayer, mockInputValidator, mockOutput));
    }

    @Test
    void constructorThrowsOnNullPlayer() {
        assertThrows(NullPointerException.class, () ->
            new Game(mockSlotMachine, null, mockInputValidator, mockOutput));
    }

    @Test
    void constructorThrowsOnNullInputValidator() {
        assertThrows(NullPointerException.class, () ->
            new Game(mockSlotMachine, mockPlayer, null, mockOutput));
    }

    @Test
    void constructorThrowsOnNullOutput() {
        assertThrows(NullPointerException.class, () ->
            new Game(mockSlotMachine, mockPlayer, mockInputValidator, null));
    }
}