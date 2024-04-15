package de.seism0saurus.hectoc.shuntingyardalgorithm;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the {@link  HectocSolution}
 *
 * @author seism0saurus
 */
public class HectocSolutionTest {

    private final HectocChallenge challange = HectocChallenge.builder()
            .firstDigit(1)
            .secondDigit(2)
            .thirdDigit(3)
            .fourthDigit(4)
            .fifthDigit(5)
            .sixthDigit(6)
            .build();
    private final HectocSolution solution = new HectocSolution(challange);

    @Test
    public void detectChangedOrderOfNumbers() {
        assertThrows(IllegalArgumentException.class,
                () -> solution.checkSolution("132456"));
    }

    @Test
    public void detectMissingNumbers() {
        assertThrows(IllegalArgumentException.class,
                () -> solution.checkSolution("12345"));
    }

    @Test
    public void detectAdditionalNumbers() {
        assertThrows(IllegalArgumentException.class,
                () -> solution.checkSolution("1234567"));
    }

    @Test
    public void detectAdditionalZero() {
        assertThrows(IllegalArgumentException.class,
                () -> solution.checkSolution("1234560"));
    }

    @Test
    public void acceptCorrectNumberAndOrderOfNumbers() {
        assertDoesNotThrow(() -> solution.checkSolution("123456"));
    }


    @Test
    public void acceptSyntacticalCorrectEquotations() {
        assertDoesNotThrow(() -> solution.checkSolution("((1))^2/3*4-5+(6)"));
    }

    @Test
    public void acceptSyntacticalCorrectButSenslesEquotatons() {
        assertDoesNotThrow(() -> solution.checkSolution("123456"));
    }

    @Test
    public void testUnaryOperatorFollowedByPlusAtFirstPosition() {
        final HectocChallenge challange = HectocChallenge.builder()
                .firstDigit(5)
                .secondDigit(5)
                .thirdDigit(9)
                .fourthDigit(9)
                .fifthDigit(1)
                .sixthDigit(1)
                .build();
        final HectocSolution solution = new HectocSolution(challange);
        assertDoesNotThrow(() -> solution.checkSolution("-5+5+99+1*1"));
        assertTrue(solution.isValid());
    }

    @Test
    public void testUnaryOperatorFollowedByMultiplicationAtFirstPosition() {
        final HectocChallenge challange = HectocChallenge.builder()
                .firstDigit(5)
                .secondDigit(1)
                .thirdDigit(9)
                .fourthDigit(9)
                .fifthDigit(1)
                .sixthDigit(5)
                .build();
        final HectocSolution solution = new HectocSolution(challange);
        assertDoesNotThrow(() -> solution.checkSolution("-5*1+99+1+5"));
        assertTrue(solution.isValid());
    }

    @Test
    public void testEqualInWrongPosition() {
        final HectocChallenge challange = HectocChallenge.builder()
                .firstDigit(5)
                .secondDigit(1)
                .thirdDigit(9)
                .fourthDigit(9)
                .fifthDigit(1)
                .sixthDigit(5)
                .build();
        final HectocSolution solution = new HectocSolution(challange);
        assertThrows(IllegalArgumentException.class,
                () -> solution.formatAndCheckSolution("-5*1=99+1+5"));
        assertFalse(solution.isValid());
    }

    @Test
    public void testEqual100AtEndOfSolution() {
        final HectocChallenge challange = HectocChallenge.builder()
                .firstDigit(5)
                .secondDigit(1)
                .thirdDigit(9)
                .fourthDigit(9)
                .fifthDigit(1)
                .sixthDigit(5)
                .build();
        final HectocSolution solution = new HectocSolution(challange);
        assertDoesNotThrow(() -> solution.formatAndCheckSolution("-5*1+99+1+5=100"));
        assertTrue(solution.isValid());
    }

    /**
     * Test real world hectocs and their valid, proposed solutions.
     * The test pairs are taken from the mastodon account of the bot
     * and are manually checked for validity.
     *
     * @param challenge The hectoc challenge
     * @param proposedSolution The proposed solution for the challenge
     */
    @ParameterizedTest
    @MethodSource("provideHectocsWithCorrectSolutions")
    public void testCorrectRealHectocs(final HectocChallenge challenge, final String proposedSolution) {
        final HectocSolution solution = new HectocSolution(challenge);
        assertDoesNotThrow(() -> solution.checkSolution(proposedSolution));
        assertTrue(solution.isValid());
    }

    private static Stream<Arguments> provideHectocsWithCorrectSolutions() {
        return Stream.of(
                Arguments.of(new HectocChallenge("498388"), "4+98-3+8/8"),
                Arguments.of(new HectocChallenge("185552"), "1^8*(5+5)*5*2"),
                Arguments.of(new HectocChallenge("459771"), "45+9*7-7-1"),
                Arguments.of(new HectocChallenge("998926"), "-9/9+89+2*6"),
                Arguments.of(new HectocChallenge("693728"), "69+37+2-8"),
                Arguments.of(new HectocChallenge("364611"), "36+4+61-1"),
                Arguments.of(new HectocChallenge("821731"), "(8+2)*1*(7+3)*1"),
                Arguments.of(new HectocChallenge("653258"), "65+32-5+8"),
                Arguments.of(new HectocChallenge("168932"), "1*6+89+3+2"),
                Arguments.of(new HectocChallenge("264346"), "(26+4)*3+4+6"),
                Arguments.of(new HectocChallenge("283961"), "2+83+9+6*1"),
                Arguments.of(new HectocChallenge("642714"), "(6+4)*(-2+7+1+4)"),
                Arguments.of(new HectocChallenge("533111"), "-5-3-3+111"),
                Arguments.of(new HectocChallenge("997518"), "99+7-5-1^8"),
                Arguments.of(new HectocChallenge("157448"), "1*5*((7-4)*4+8)"),
                Arguments.of(new HectocChallenge("648258"), "(6+4)*(8*2*5/8)"),
                Arguments.of(new HectocChallenge("416672"), "4*(-1+6+6+7*2)"),
                Arguments.of(new HectocChallenge("478866"), "4+7+88+6/6"),
                Arguments.of(new HectocChallenge("944972"), "9-4-4+97+2"),
                Arguments.of(new HectocChallenge("229958"), "2^2+99+5-8"),
                Arguments.of(new HectocChallenge("229958"), "2^2+99+5-8"),
                Arguments.of(new HectocChallenge("321617"), "32+1*61+7"),
                Arguments.of(new HectocChallenge("931687"), "93*(-1-6+8)+7"),
                Arguments.of(new HectocChallenge("823789"), "(8+2)*(3+7)*(-8+9)"),
                Arguments.of(new HectocChallenge("144273"), "1*(4+4+2)*(7+3)"),
                Arguments.of(new HectocChallenge("517291"), "(5*1+7-2)*(9+1)"),
                Arguments.of(new HectocChallenge("947555"), "(9+4+7)*5*5/5"),
                Arguments.of(new HectocChallenge("188252"), "1*88+2+5*2"),
                Arguments.of(new HectocChallenge("378558"), "(3+7)*(-8+5+5+8)"),
                Arguments.of(new HectocChallenge("281987"), "2-8+1+98+7"),
                Arguments.of(new HectocChallenge("125534"), "1*25*(5+3-4)"),
                Arguments.of(new HectocChallenge("844486"), "-8/4+4*4+86"),
                Arguments.of(new HectocChallenge("776623"), "77*6/6+23"),
                Arguments.of(new HectocChallenge("553383"), "(5+5)*(-3/3+8+3)"),
                Arguments.of(new HectocChallenge("651568"), "(6+5-1)*(5*(-6+8))"),
                Arguments.of(new HectocChallenge("328553"), "3+(2+8)*(5+5)-3"),
                Arguments.of(new HectocChallenge("621537"), "(6-2+1+5)*(3+7)"),
                Arguments.of(new HectocChallenge("215548"), "-2-1+55+48"),
                Arguments.of(new HectocChallenge("154991"), "1-5+4+99+1"),
                Arguments.of(new HectocChallenge("252885"), "25*(-2+8/8+5)"),
                Arguments.of(new HectocChallenge("267119"), "(-2+6+7-1)*(1+9)"),
                Arguments.of(new HectocChallenge("964717"), "96+4*7*1/7"),
                Arguments.of(new HectocChallenge("284354"), "(-2+8-4+3)*5*4"),
                Arguments.of(new HectocChallenge("193476"), "1+9+3*4*7+6"),
                Arguments.of(new HectocChallenge("968546"), "(-9+6+8+5)*(4+6)"),
                Arguments.of(new HectocChallenge("522712"), "5*22-(7+1+2)"),
                Arguments.of(new HectocChallenge("461222"), "-46+12^2+2")
        );
    }

    /**
     * Test real world hectocs and their wrong proposed solutions.
     * The test pairs are taken from the mastodon account of the bot
     * and are manually checked for validity.
     *
     * @param challenge The hectoc challenge
     * @param proposedSolution The proposed solution for the challenge
     */
    @ParameterizedTest
    @MethodSource("provideHectocsWithWrongSolutions")
    public void testWrongRealHectocs(final HectocChallenge challenge, final String proposedSolution) {
        final HectocSolution solution = new HectocSolution(challenge);
        assertDoesNotThrow(() -> solution.checkSolution(proposedSolution));
        assertFalse(solution.isValid());
    }

    private static Stream<Arguments> provideHectocsWithWrongSolutions() {
        return Stream.of(
                Arguments.of(new HectocChallenge("825916"), "(8+2)*(5-9+16)"),
                Arguments.of(new HectocChallenge("264346"), "(26+4)*3+4*6"),
                Arguments.of(new HectocChallenge("599342"), "5+99-3-4+2"),
                Arguments.of(new HectocChallenge("323457"), "(3+2)*(3*4+5-7)")
        );
    }
}