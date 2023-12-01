package de.seism0saurus.hectoc.shuntingyardalgorithm;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Operator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperatorTest {

    @ParameterizedTest
    @MethodSource("provideOperatorChars")
    public void createCorrectOperatorFromChar(final Operator expectedOperator, final char character) {
        Operator actual = Operator.from(character);

        assertEquals(expectedOperator, actual);
    }

    private static Stream<Arguments> provideOperatorChars() {
        return Stream.of(
                Arguments.of(Operator.PLUS, '+'),
                Arguments.of(Operator.MINUS, '-'),
                Arguments.of(Operator.MULTIPLICATION, '*'),
                Arguments.of(Operator.DIVISION, '/'),
                Arguments.of(Operator.POWER, '^'),
                Arguments.of(Operator.LEFTPARENTHESIS, '('),
                Arguments.of(Operator.RIGHTPARENTHESIS, ')')
        );
    }

    @ParameterizedTest
    @MethodSource("providePrecedenceArgumentsPlusMinus")
    public void checkPrecedencePlus(final Operator othOperator,
                                    final boolean expectedHasGreaterPrecedence,
                                    final boolean expectedHasSamePrecedence) {
        Operator operator = Operator.PLUS;

        boolean actualHasGreaterPrecedence = operator.hasGreaterPrecedenceThan(othOperator);
        boolean actualHasSamePrecedence = operator.hasSamePrecedenceAs(othOperator);

        assertEquals(expectedHasGreaterPrecedence, actualHasGreaterPrecedence);
        assertEquals(expectedHasSamePrecedence, actualHasSamePrecedence);
    }

    @ParameterizedTest
    @MethodSource("providePrecedenceArgumentsPlusMinus")
    public void checkPrecedenceMinus(final Operator othOperator,
                                     final boolean expectedHasGreaterPrecedence,
                                     final boolean expectedHasSamePrecedence) {
        Operator operator = Operator.MINUS;

        boolean actualHasGreaterPrecedence = operator.hasGreaterPrecedenceThan(othOperator);
        boolean actualHasSamePrecedence = operator.hasSamePrecedenceAs(othOperator);

        assertEquals(expectedHasGreaterPrecedence, actualHasGreaterPrecedence);
        assertEquals(expectedHasSamePrecedence, actualHasSamePrecedence);
    }

    private static Stream<Arguments> providePrecedenceArgumentsPlusMinus() {
        return Stream.of(
                Arguments.of(Operator.PLUS, false, true),
                Arguments.of(Operator.MINUS, false, true),
                Arguments.of(Operator.MULTIPLICATION, false, false),
                Arguments.of(Operator.DIVISION, false, false),
                Arguments.of(Operator.POWER, false, false),
                Arguments.of(Operator.LEFTPARENTHESIS, false, false),
                Arguments.of(Operator.RIGHTPARENTHESIS, false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("providePrecedenceArgumentsMultiplicationDivision")
    public void checkPrecedenceMultiplication(final Operator othOperator,
                                              final boolean expectedHasGreaterPrecedence,
                                              final boolean expectedHasSamePrecedence) {
        Operator operator = Operator.MULTIPLICATION;

        boolean actualHasGreaterPrecedence = operator.hasGreaterPrecedenceThan(othOperator);
        boolean actualHasSamePrecedence = operator.hasSamePrecedenceAs(othOperator);

        assertEquals(expectedHasGreaterPrecedence, actualHasGreaterPrecedence);
        assertEquals(expectedHasSamePrecedence, actualHasSamePrecedence);
    }

    @ParameterizedTest
    @MethodSource("providePrecedenceArgumentsMultiplicationDivision")
    public void checkPrecedenceDivision(final Operator othOperator,
                                        final boolean expectedHasGreaterPrecedence,
                                        final boolean expectedHasSamePrecedence) {
        Operator operator = Operator.DIVISION;

        boolean actualHasGreaterPrecedence = operator.hasGreaterPrecedenceThan(othOperator);
        boolean actualHasSamePrecedence = operator.hasSamePrecedenceAs(othOperator);

        assertEquals(expectedHasGreaterPrecedence, actualHasGreaterPrecedence);
        assertEquals(expectedHasSamePrecedence, actualHasSamePrecedence);
    }

    private static Stream<Arguments> providePrecedenceArgumentsMultiplicationDivision() {
        return Stream.of(
                Arguments.of(Operator.PLUS, true, false),
                Arguments.of(Operator.MINUS, true, false),
                Arguments.of(Operator.MULTIPLICATION, false, true),
                Arguments.of(Operator.DIVISION, false, true),
                Arguments.of(Operator.POWER, false, false),
                Arguments.of(Operator.LEFTPARENTHESIS, false, false),
                Arguments.of(Operator.RIGHTPARENTHESIS, false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("providePrecedenceArgumentsPower")
    public void checkPrecedencePower(final Operator othOperator,
                                     final boolean expectedHasGreaterPrecedence,
                                     final boolean expectedHasSamePrecedence) {
        Operator operator = Operator.POWER;

        boolean actualHasGreaterPrecedence = operator.hasGreaterPrecedenceThan(othOperator);
        boolean actualHasSamePrecedence = operator.hasSamePrecedenceAs(othOperator);

        assertEquals(expectedHasGreaterPrecedence, actualHasGreaterPrecedence);
        assertEquals(expectedHasSamePrecedence, actualHasSamePrecedence);
    }

    private static Stream<Arguments> providePrecedenceArgumentsPower() {
        return Stream.of(
                Arguments.of(Operator.PLUS, true, false),
                Arguments.of(Operator.MINUS, true, false),
                Arguments.of(Operator.MULTIPLICATION, true, false),
                Arguments.of(Operator.DIVISION, true, false),
                Arguments.of(Operator.POWER, false, true),
                Arguments.of(Operator.LEFTPARENTHESIS, false, false),
                Arguments.of(Operator.RIGHTPARENTHESIS, false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("providePrecedenceParenthesis")
    public void checkPrecedenceLeftParenthesis(final Operator othOperator,
                                               final boolean expectedHasGreaterPrecedence,
                                               final boolean expectedHasSamePrecedence) {
        Operator operator = Operator.LEFTPARENTHESIS;

        boolean actualHasGreaterPrecedence = operator.hasGreaterPrecedenceThan(othOperator);
        boolean actualHasSamePrecedence = operator.hasSamePrecedenceAs(othOperator);

        assertEquals(expectedHasGreaterPrecedence, actualHasGreaterPrecedence);
        assertEquals(expectedHasSamePrecedence, actualHasSamePrecedence);
    }

    @ParameterizedTest
    @MethodSource("providePrecedenceParenthesis")
    public void checkPrecedenceRightParenthesis(final Operator othOperator,
                                                final boolean expectedHasGreaterPrecedence,
                                                final boolean expectedHasSamePrecedence) {
        Operator operator = Operator.RIGHTPARENTHESIS;

        boolean actualHasGreaterPrecedence = operator.hasGreaterPrecedenceThan(othOperator);
        boolean actualHasSamePrecedence = operator.hasSamePrecedenceAs(othOperator);

        assertEquals(expectedHasGreaterPrecedence, actualHasGreaterPrecedence);
        assertEquals(expectedHasSamePrecedence, actualHasSamePrecedence);
    }

    private static Stream<Arguments> providePrecedenceParenthesis() {
        return Stream.of(
                Arguments.of(Operator.PLUS, true, false),
                Arguments.of(Operator.MINUS, true, false),
                Arguments.of(Operator.MULTIPLICATION, true, false),
                Arguments.of(Operator.DIVISION, true, false),
                Arguments.of(Operator.POWER, true, false),
                Arguments.of(Operator.LEFTPARENTHESIS, false, true),
                Arguments.of(Operator.RIGHTPARENTHESIS, false, true)
        );
    }

    @Test
    public void illegalCharacter() {
        assertThrows(IllegalArgumentException.class, () -> Operator.from('!'));
    }
}
