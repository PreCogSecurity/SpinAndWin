package com.precogsecurity.spinandwin;

import java.security.SecureRandom;
import java.util.Objects;

/**
 * Represents a slot machine with three reels.
 * Uses cryptographically secure random number generation for fair gameplay.
 */
public final class SlotMachine {

    private static final int MIN_REEL_VALUE = 1;
    private static final int MAX_REEL_VALUE = 3;
    private static final int REEL_COUNT = 3;

    private final SecureRandom secureRandom;

    /**
     * Creates a new SlotMachine with a secure random number generator.
     */
    public SlotMachine() {
        this.secureRandom = new SecureRandom();
    }

    /**
     * Creates a new SlotMachine with a provided SecureRandom instance.
     * Primarily for testing purposes.
     *
     * @param secureRandom the secure random instance to use
     */
    SlotMachine(SecureRandom secureRandom) {
        this.secureRandom = Objects.requireNonNull(secureRandom, "SecureRandom must not be null");
    }

    /**
     * Spins the slot machine and returns the three reel values.
     *
     * @return an array of three integers representing the reel values (1-3)
     */
    public int[] spin() {
        int[] reels = new int[REEL_COUNT];
        for (int i = 0; i < REEL_COUNT; i++) {
            reels[i] = secureRandom.nextInt(MAX_REEL_VALUE - MIN_REEL_VALUE + 1) + MIN_REEL_VALUE;
        }
        return reels;
    }

    /**
     * Evaluates the spin result and returns the outcome.
     *
     * @param reels the three reel values
     * @return the spin outcome
     */
    public SpinOutcome evaluate(int[] reels) {
        Objects.requireNonNull(reels, "Reels array must not be null");
        if (reels.length != REEL_COUNT) {
            throw new IllegalArgumentException("Expected " + REEL_COUNT + " reels, got " + reels.length);
        }

        boolean firstEqualsSecond = reels[0] == reels[1];
        boolean secondEqualsThird = reels[1] == reels[2];
        boolean firstEqualsThird = reels[0] == reels[2];

        if (firstEqualsSecond && secondEqualsThird) {
            return SpinOutcome.JACKPOT;
        } else if (firstEqualsSecond || secondEqualsThird || firstEqualsThird) {
            return SpinOutcome.PARTIAL_MATCH;
        } else {
            return SpinOutcome.NO_MATCH;
        }
    }

    /**
     * Enum representing the possible outcomes of a spin.
     */
    public enum SpinOutcome {
        /** All three reels match - player wins the bet amount */
        JACKPOT,
        /** Two out of three reels match - player loses half the bet */
        PARTIAL_MATCH,
        /** No reels match - player loses the full bet */
        NO_MATCH
    }
}