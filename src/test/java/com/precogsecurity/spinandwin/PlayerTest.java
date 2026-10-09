package com.precogsecurity.spinandwin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Player class.
 */
class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void initialPointsAreTen() {
        assertEquals(10, player.getPoints());
    }

    @Test
    void canAffordBetReturnsTrueForValidBet() {
        assertTrue(player.canAffordBet(1));
        assertTrue(player.canAffordBet(5));
        assertTrue(player.canAffordBet(10));
    }

    @Test
    void canAffordBetReturnsFalseForInvalidBet() {
        assertFalse(player.canAffordBet(0));
        assertFalse(player.canAffordBet(-1));
        assertFalse(player.canAffordBet(11));
        assertFalse(player.canAffordBet(100));
    }

    @Test
    void placeBetReturnsBetAmountForValidBet() {
        assertEquals(5, player.placeBet(5));
        assertEquals(10, player.placeBet(10));
    }

    @Test
    void placeBetThrowsForInvalidBet() {
        assertThrows(IllegalArgumentException.class, () -> player.placeBet(0));
        assertThrows(IllegalArgumentException.class, () -> player.placeBet(-1));
        assertThrows(IllegalArgumentException.class, () -> player.placeBet(11));
        assertThrows(IllegalArgumentException.class, () -> player.placeBet(100));
    }

    @Test
    void applyOutcomeJackpotIncreasesPoints() {
        player.placeBet(5);
        player.applyOutcome(5, SlotMachine.SpinOutcome.JACKPOT);
        assertEquals(15, player.getPoints()); // 10 + 5
    }

    @Test
    void applyOutcomePartialMatchDecreasesPointsByHalf() {
        player.placeBet(6);
        player.applyOutcome(6, SlotMachine.SpinOutcome.PARTIAL_MATCH);
        assertEquals(7, player.getPoints()); // 10 - 3
    }

    @Test
    void applyOutcomeNoMatchDecreasesPointsByFullBet() {
        player.placeBet(4);
        player.applyOutcome(4, SlotMachine.SpinOutcome.NO_MATCH);
        assertEquals(6, player.getPoints()); // 10 - 4
    }

    @Test
    void applyOutcomePartialMatchRoundsDown() {
        player.placeBet(5);
        player.applyOutcome(5, SlotMachine.SpinOutcome.PARTIAL_MATCH);
        assertEquals(8, player.getPoints()); // 10 - 2 (5/2 = 2.5 -> 2)
    }

    @Test
    void pointsNeverGoBelowZero() {
        Player lowPointsPlayer = new Player(3);
        lowPointsPlayer.placeBet(3);
        lowPointsPlayer.applyOutcome(3, SlotMachine.SpinOutcome.NO_MATCH);
        assertEquals(0, lowPointsPlayer.getPoints());
    }

    @Test
    void isGameOverReturnsFalseInitially() {
        assertFalse(player.isGameOver());
    }

    @Test
    void isGameOverReturnsTrueWhenPointsZero() {
        player.applyOutcome(10, SlotMachine.SpinOutcome.NO_MATCH);
        assertTrue(player.isGameOver());
    }

    @Test
    void resetRestoresInitialPoints() {
        player.applyOutcome(5, SlotMachine.SpinOutcome.NO_MATCH);
        player.reset();
        assertEquals(10, player.getPoints());
        assertFalse(player.isGameOver());
    }

    @Test
    void constructorWithCustomPoints() {
        Player customPlayer = new Player(25);
        assertEquals(25, customPlayer.getPoints());
    }

    @Test
    void constructorThrowsOnNegativePoints() {
        assertThrows(IllegalArgumentException.class, () -> new Player(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10})
    void multipleRoundsMaintainCorrectPoints(int betAmount) {
        int initialPoints = player.getPoints();
        player.placeBet(betAmount);
        player.applyOutcome(betAmount, SlotMachine.SpinOutcome.JACKPOT);
        assertEquals(initialPoints + betAmount, player.getPoints());
    }
}