package de.seism0saurus.hectoc.generator;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * The generator creates new, valid and solvable {@link HectocChallenge Hectoc challenges}.
 *
 * @author seism0saurus
 */
public class HectocGenerator {

    /**
     * <code>List</code> of unsolved {@link HectocChallenge Hectoc challenges}.
     * These hectocs have no solution so far.
     * That does not mean, that it is proven, that they are unsolvable.
     * This list is used to exclude unsolved hectocs from the generated ones,
     * so users don't have to crack those and get frustrated.
     * @see <a href="https://wir-rechnen.de/hectoc/hectocs-ungeloest-unsolved">Unsolved Hectocs</a>
      */
    public static final List<HectocChallenge> UNSOLVABLE_HECTOCS = List.of(
            new HectocChallenge(1, 1, 2, 1, 1, 7),
            new HectocChallenge(1, 1, 4, 1, 2, 3),
            new HectocChallenge(1, 1, 5, 5, 6, 7),
            new HectocChallenge(1, 1, 5, 8, 2, 7),
            new HectocChallenge(1, 1, 6, 5, 6, 7),
            new HectocChallenge(1, 2, 1, 1, 4, 3),
            new HectocChallenge(1, 2, 1, 5, 8, 1),
            new HectocChallenge(1, 3, 1, 1, 1, 6),
            new HectocChallenge(1, 4, 1, 1, 7, 1),
            new HectocChallenge(1, 5, 6, 5, 6, 7),
            new HectocChallenge(1, 6, 7, 1, 8, 1),
            new HectocChallenge(1, 6, 7, 4, 5, 1),
            new HectocChallenge(1, 7, 1, 7, 1, 7),
            new HectocChallenge(1, 7, 5, 1, 1, 7),
            new HectocChallenge(1, 7, 6, 6, 1, 1),
            new HectocChallenge(1, 7, 8, 1, 8, 1),
            new HectocChallenge(1, 7, 8, 1, 8, 8),
            new HectocChallenge(1, 7, 8, 8, 8, 1),
            new HectocChallenge(1, 7, 8, 8, 8, 8),
            new HectocChallenge(1, 7, 8, 9, 8, 8),
            new HectocChallenge(1, 8, 4, 1, 5, 6),
            new HectocChallenge(1, 8, 5, 5, 7, 1),
            new HectocChallenge(1, 8, 8, 7, 8, 8),
            new HectocChallenge(1, 8, 8, 8, 8, 7),
            new HectocChallenge(2, 1, 1, 1, 4, 3),
            new HectocChallenge(2, 1, 1, 5, 3, 9),
            new HectocChallenge(3, 5, 1, 1, 1, 7),
            new HectocChallenge(3, 6, 1, 8, 6, 9),
            new HectocChallenge(3, 6, 3, 3, 6, 9),
            new HectocChallenge(3, 6, 6, 3, 6, 9),
            new HectocChallenge(3, 8, 3, 8, 8, 8),
            new HectocChallenge(3, 8, 8, 8, 3, 8),
            new HectocChallenge(5, 9, 8, 9, 9, 9),
            new HectocChallenge(6, 1, 1, 1, 7, 1),
            new HectocChallenge(6, 1, 1, 1, 7, 7),
            new HectocChallenge(6, 1, 7, 6, 6, 7),
            new HectocChallenge(6, 1, 7, 6, 7, 6),
            new HectocChallenge(6, 1, 7, 7, 6, 6),
            new HectocChallenge(6, 3, 3, 6, 3, 9),
            new HectocChallenge(6, 3, 9, 6, 6, 9),
            new HectocChallenge(6, 6, 1, 6, 6, 7),
            new HectocChallenge(6, 6, 4, 1, 4, 9),
            new HectocChallenge(6, 6, 4, 9, 8, 9),
            new HectocChallenge(6, 6, 6, 1, 1, 7),
            new HectocChallenge(6, 6, 6, 1, 6, 1),
            new HectocChallenge(6, 6, 6, 1, 6, 6),
            new HectocChallenge(6, 6, 6, 6, 1, 5),
            new HectocChallenge(6, 6, 6, 6, 5, 1),
            new HectocChallenge(6, 6, 6, 6, 6, 1),
            new HectocChallenge(6, 6, 6, 6, 6, 7),
            new HectocChallenge(6, 6, 6, 7, 6, 1),
            new HectocChallenge(6, 6, 7, 6, 6, 1),
            new HectocChallenge(6, 7, 5, 1, 5, 1),
            new HectocChallenge(6, 7, 6, 1, 1, 1),
            new HectocChallenge(6, 7, 6, 1, 6, 7),
            new HectocChallenge(6, 7, 6, 1, 7, 6),
            new HectocChallenge(6, 7, 6, 6, 6, 7),
            new HectocChallenge(6, 7, 6, 7, 6, 1),
            new HectocChallenge(6, 7, 7, 7, 6, 1),
            new HectocChallenge(6, 8, 1, 1, 8, 1),
            new HectocChallenge(6, 8, 1, 6, 6, 7),
            new HectocChallenge(7, 1, 1, 1, 6, 1),
            new HectocChallenge(7, 1, 1, 7, 8, 1),
            new HectocChallenge(7, 1, 7, 7, 6, 7),
            new HectocChallenge(7, 1, 8, 1, 7, 8),
            new HectocChallenge(7, 1, 8, 8, 8, 7),
            new HectocChallenge(7, 1, 8, 8, 8, 8),
            new HectocChallenge(7, 1, 9, 1, 7, 1),
            new HectocChallenge(7, 1, 9, 8, 7, 8),
            new HectocChallenge(7, 4, 5, 1, 7, 1),
            new HectocChallenge(7, 4, 7, 7, 7, 8),
            new HectocChallenge(7, 4, 7, 7, 8, 7),
            new HectocChallenge(7, 4, 7, 8, 7, 7),
            new HectocChallenge(7, 4, 8, 7, 7, 7),
            new HectocChallenge(7, 6, 1, 1, 1, 7),
            new HectocChallenge(7, 6, 1, 1, 6, 1),
            new HectocChallenge(7, 6, 1, 7, 6, 7),
            new HectocChallenge(7, 6, 6, 1, 1, 1),
            new HectocChallenge(7, 6, 6, 8, 6, 1),
            new HectocChallenge(7, 6, 7, 7, 1, 7),
            new HectocChallenge(7, 6, 7, 7, 6, 1),
            new HectocChallenge(7, 7, 1, 8, 1, 8),
            new HectocChallenge(7, 7, 3, 1, 6, 7),
            new HectocChallenge(7, 7, 3, 7, 8, 1),
            new HectocChallenge(7, 7, 6, 7, 6, 1),
            new HectocChallenge(7, 7, 8, 1, 8, 1),
            new HectocChallenge(7, 7, 8, 4, 5, 1),
            new HectocChallenge(7, 7, 8, 5, 5, 1),
            new HectocChallenge(7, 7, 8, 9, 7, 8),
            new HectocChallenge(7, 8, 1, 1, 1, 7),
            new HectocChallenge(7, 8, 1, 1, 7, 1),
            new HectocChallenge(7, 8, 1, 2, 8, 1),
            new HectocChallenge(7, 8, 1, 6, 7, 6),
            new HectocChallenge(7, 8, 1, 7, 1, 8),
            new HectocChallenge(7, 8, 1, 8, 8, 8),
            new HectocChallenge(7, 8, 1, 9, 7, 8),
            new HectocChallenge(7, 8, 1, 9, 8, 7),
            new HectocChallenge(7, 8, 7, 8, 8, 8),
            new HectocChallenge(7, 8, 7, 8, 8, 9),
            new HectocChallenge(7, 8, 8, 1, 8, 9),
            new HectocChallenge(7, 8, 8, 7, 8, 9),
            new HectocChallenge(7, 8, 8, 8, 1, 8),
            new HectocChallenge(7, 8, 8, 8, 7, 8),
            new HectocChallenge(7, 8, 8, 8, 8, 1),
            new HectocChallenge(7, 8, 8, 9, 7, 1),
            new HectocChallenge(7, 8, 9, 7, 8, 8),
            new HectocChallenge(7, 9, 1, 8, 8, 7),
            new HectocChallenge(7, 9, 7, 8, 8, 1),
            new HectocChallenge(7, 9, 9, 9, 7, 1),
            new HectocChallenge(8, 1, 7, 7, 8, 1),
            new HectocChallenge(8, 1, 7, 7, 8, 9),
            new HectocChallenge(8, 1, 7, 8, 8, 1),
            new HectocChallenge(8, 1, 7, 8, 8, 8),
            new HectocChallenge(8, 1, 8, 8, 7, 8),
            new HectocChallenge(8, 1, 9, 7, 8, 7),
            new HectocChallenge(8, 1, 9, 8, 7, 7),
            new HectocChallenge(8, 1, 9, 8, 7, 8),
            new HectocChallenge(8, 1, 9, 8, 8, 7),
            new HectocChallenge(8, 3, 8, 3, 8, 3),
            new HectocChallenge(8, 3, 8, 5, 8, 8),
            new HectocChallenge(8, 3, 8, 8, 5, 8),
            new HectocChallenge(8, 3, 8, 8, 8, 3),
            new HectocChallenge(8, 5, 3, 8, 7, 8),
            new HectocChallenge(8, 5, 8, 8, 3, 8),
            new HectocChallenge(8, 7, 1, 8, 8, 8),
            new HectocChallenge(8, 7, 7, 8, 8, 9),
            new HectocChallenge(8, 7, 8, 1, 8, 1),
            new HectocChallenge(8, 7, 8, 1, 8, 8),
            new HectocChallenge(8, 7, 8, 5, 3, 8),
            new HectocChallenge(8, 7, 8, 7, 8, 7),
            new HectocChallenge(8, 7, 8, 7, 8, 9),
            new HectocChallenge(8, 7, 8, 8, 8, 1),
            new HectocChallenge(8, 7, 8, 8, 8, 7),
            new HectocChallenge(8, 7, 8, 9, 8, 8),
            new HectocChallenge(8, 8, 1, 7, 8, 8),
            new HectocChallenge(8, 8, 1, 8, 7, 8),
            new HectocChallenge(8, 8, 1, 8, 8, 7),
            new HectocChallenge(8, 8, 1, 9, 8, 7),
            new HectocChallenge(8, 8, 5, 8, 3, 8),
            new HectocChallenge(8, 8, 7, 7, 7, 8),
            new HectocChallenge(8, 8, 7, 8, 1, 8),
            new HectocChallenge(8, 8, 7, 8, 8, 1),
            new HectocChallenge(8, 8, 7, 8, 8, 8),
            new HectocChallenge(8, 8, 8, 1, 7, 8),
            new HectocChallenge(8, 8, 8, 1, 8, 7),
            new HectocChallenge(8, 8, 8, 3, 8, 3),
            new HectocChallenge(8, 8, 8, 7, 1, 7),
            new HectocChallenge(8, 8, 8, 7, 8, 1),
            new HectocChallenge(8, 8, 8, 7, 8, 7),
            new HectocChallenge(8, 8, 8, 7, 8, 9),
            new HectocChallenge(8, 8, 8, 8, 1, 7),
            new HectocChallenge(8, 8, 8, 8, 6, 1),
            new HectocChallenge(8, 8, 8, 8, 7, 1),
            new HectocChallenge(8, 8, 8, 8, 7, 7),
            new HectocChallenge(8, 9, 7, 8, 7, 8),
            new HectocChallenge(8, 9, 7, 8, 8, 7),
            new HectocChallenge(8, 9, 8, 7, 7, 1),
            new HectocChallenge(8, 9, 8, 7, 8, 1),
            new HectocChallenge(8, 9, 8, 8, 7, 8),
            new HectocChallenge(9, 5, 1, 9, 9, 9),
            new HectocChallenge(9, 5, 8, 9, 9, 9),
            new HectocChallenge(9, 6, 1, 9, 9, 9),
            new HectocChallenge(9, 6, 9, 1, 9, 9),
            new HectocChallenge(9, 6, 9, 6, 5, 9),
            new HectocChallenge(9, 7, 8, 7, 8, 8),
            new HectocChallenge(9, 7, 8, 8, 8, 7)
    );

    /**
     * The pseudo random number generator is used to make the
     * generation of new hectocs as random as possible.
     */
    private static final Random prg = new SecureRandom();

    /**
     * Generates a random digit between {@link HectocChallenge#MIN MIN} and {@link HectocChallenge#MAX MAX}.
     * This method is used to generate each of the six digits of a hectoc.
     * @return A random number between {@link HectocChallenge#MIN MIN} and {@link HectocChallenge#MAX MAX}
     */
    private static int generateDigit() {
        return prg.nextInt(HectocChallenge.MAX - HectocChallenge.MIN + 1) + HectocChallenge.MIN;
    }

    /**
     * Generate a valid, solvable {@link HectocChallenge Hectoc challenges}.
     * A challenge is generated with six random digits.
     * As long as the challenge is in the list of unsolved hectocs, a new challenge is created.
     * This is repeated until a solvable one was generated.
     *
     * @return A solvable, valid hectoc.
     *
     * @see #UNSOLVABLE_HECTOCS
     */
    public static HectocChallenge generate() {
        HectocChallenge challenge = null;
        while (challenge == null || UNSOLVABLE_HECTOCS.contains(challenge)) {
            challenge = HectocChallenge.builder()
                    .firstDigit(generateDigit())
                    .secondDigit(generateDigit())
                    .thirdDigit(generateDigit())
                    .fourthDigit(generateDigit())
                    .fifthDigit(generateDigit())
                    .sixthDigit(generateDigit())
                    .build();
        }
        return challenge;
    }
}
