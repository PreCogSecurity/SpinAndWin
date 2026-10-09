package com.precogsecurity.spinandwin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SlotMachine class.
 */
class SlotMachineTest {

    private SlotMachine slotMachine;
    private SecureRandom fixedRandom;

    @BeforeEach
    void setUp() {
        // Use a fixed seed for deterministic testing
        fixedRandom = new SecureRandom(new byte[]{1, 2, 3, 4, 5, 6, 7, 8});
        slotMachine = new SlotMachine(fixedRandom);
    }

    @Test
    void spinReturnsThreeReels() {
        int[] reels = slotMachine.spin();
        assertNotNull(reels);
        assertEquals(3, reels.length);
    }

    @Test
    void spinReelsAreInValidRange() {
        for (int i = 0; i < 100; i++) {
            int[] reels = slotMachine.spin();
            for (int reel : reels) {
                assertTrue(reel >= 1 && reel <= 3, "Reel value out of range: " + reel);
            }
        }
    }

    @Test
    void evaluateJackpotWhenAllThreeMatch() {
        int[] reels = {2, 2, 2};
        assertEquals(SlotMachine.SpinOutcome.JACKPOT, slotMachine.evaluate(reels));
    }

    @Test
    void evaluatePartialMatchWhenFirstTwoMatch() {
        int[] reels = {1, 1, 3};
        assertEquals(SlotMachine.SpinOutcome.PARTIAL_MATCH, slotMachine.evaluate(reels));
    }

    @Test
    void evaluatePartialMatchWhenLastTwoMatch() {
        int[] reels = {2, 3, 3};
        assertEquals(SlotMachine.SpinOutcome.PARTIAL_MATCH, slotMachine.evaluate(reels));
    }

    @Test
    void evaluatePartialMatchWhenFirstAndLastMatch() {
        int[] reels = {1, 2, 1};
        assertEquals(SlotMachine.SpinOutcome.PARTIAL_MATCH, slotMachine.evaluate(reels));
    }

    @Test
    void evaluateNoMatchWhenAllDifferent() {
        int[] reels = {1, 2, 3};
        assertEquals(SlotMachine.SpinOutcome.NO_MATCH, slotMachine.evaluate(reels));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void evaluateJackpotForAllValues(int value) {
        int[] reels = {value, value, value};
        assertEquals(SlotMachine.SpinOutcome.JACKPOT, slotMachine.evaluate(reels));
    }

    @Test
    void evaluateThrowsOnNullReels() {
        assertThrows(IllegalArgumentException.class, () -> slotMachine.evaluate(null));
    }

    @Test
    void evaluateThrowsOnWrongReelCount() {
        assertThrows(IllegalArgumentException.class, () -> slotMachine.evaluate(new int[]{1, 2}));
        assertThrows(IllegalArgumentException.class, () -> slotMachine.evaluate(new int[]{1, 2, 3, 4}));
    }

    @Test
    void constructorThrowsOnNullRandom() {
        assertThrows(NullPointerException.class, () -> new SlotMachine(null));
    }
}