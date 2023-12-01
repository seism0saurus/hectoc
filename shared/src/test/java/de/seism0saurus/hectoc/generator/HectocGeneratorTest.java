package de.seism0saurus.hectoc.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests for the {@link  HectocGenerator}
 *
 * @author seism0saurus
 */
public class HectocGeneratorTest {

    @RepeatedTest(10)
    public void generatedHectocIsSolvable() {
        final HectocChallenge generatedHectoc = HectocGenerator.generate();

        assertFalse(HectocGenerator.UNSOLVABLE_HECTOCS.contains(generatedHectoc));
    }

    @RepeatedTest(10)
    public void generatedHectocContainsValidNumbers() {
        final HectocChallenge generatedHectoc = HectocGenerator.generate();

        Assertions.assertTrue(generatedHectoc.getFirstDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        Assertions.assertTrue(generatedHectoc.getSecondDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        Assertions.assertTrue(generatedHectoc.getThirdDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        Assertions.assertTrue(generatedHectoc.getFourthDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        Assertions.assertTrue(generatedHectoc.getFifthDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        Assertions.assertTrue(generatedHectoc.getSixthDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
    }
}
