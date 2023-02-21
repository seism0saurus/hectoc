package de.seism0saurus.hectoc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;

public class HectocGeneratorTest {

    @RepeatedTest(10)
    public void generatedHectocIsSolvable(){
        final HectocChallenge generatedHectoc = HectocGenerator.generate();

        assertFalse(HectocGenerator.UNSOLVABLE_HECTOCS.contains(generatedHectoc));
    }

    @RepeatedTest(10)
    public void generatedHectocContainsValidNumbers(){
        final HectocChallenge generatedHectoc = HectocGenerator.generate();

        assertTrue(generatedHectoc.getFirstDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        assertTrue(generatedHectoc.getSecondDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        assertTrue(generatedHectoc.getThirdDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        assertTrue(generatedHectoc.getFourthDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        assertTrue(generatedHectoc.getFifthDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
        assertTrue(generatedHectoc.getSixtDigit() >= HectocChallenge.MIN && generatedHectoc.getFirstDigit() <= HectocChallenge.MAX);
    }
}
