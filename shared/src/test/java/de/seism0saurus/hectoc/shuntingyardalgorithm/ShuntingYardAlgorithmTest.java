package de.seism0saurus.hectoc.shuntingyardalgorithm;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Operator;
import de.seism0saurus.hectoc.shuntingyardalgorithm.ShuntingYardAlgorithm;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShuntingYardAlgorithmTest {

    @Test
    public void ConstructorHandlesNull() {
        assertThrows(IllegalArgumentException.class, () -> new ShuntingYardAlgorithm(null));
    }

    @Test
    public void ConstructorHandlesEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> new ShuntingYardAlgorithm(""));
    }

    @Test
    public void ConstructorHandlesUnknownSymbols() {
        assertThrows(IllegalArgumentException.class, () -> new ShuntingYardAlgorithm("1!"));
    }

    @Test
    public void ConstructorHandlesAdditionalClosingParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> new ShuntingYardAlgorithm("(1+2))"));
    }

    @Test
    public void ConstructorHandlesUnclosedLeftParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> new ShuntingYardAlgorithm("(1+2"));
    }

    @ParameterizedTest
    @MethodSource("provideRpns")
    public void createRpn(final Stack<StackElement> tokens, final Stack<StackElement> expectedRpn) {
        final ShuntingYardAlgorithm algorithm = new ShuntingYardAlgorithm("1");

        final Stack<StackElement> actualRpn = algorithm.createRpn(tokens);

        assertEquals(expectedRpn, actualRpn);
    }

    private static Stream<Arguments> provideRpns() {
        final Stack<StackElement> firstStack = new Stack<>();
        firstStack.push(Number.of(1));
        firstStack.push(Operator.PLUS);
        firstStack.push(Number.of(2));
        firstStack.push(Operator.PLUS);
        firstStack.push(Number.of(3));

        final Stack<StackElement> firstRpn = new Stack<>();
        firstRpn.push(Number.of(1));
        firstRpn.push(Number.of(2));
        firstRpn.push(Operator.PLUS);
        firstRpn.push(Number.of(3));
        firstRpn.push(Operator.PLUS);


        final Stack<StackElement> secondStack = new Stack<>();
        secondStack.push(Number.of(12));
        secondStack.push(Operator.PLUS);
        secondStack.push(Number.of(3));

        final Stack<StackElement> secondRpn = new Stack<>();
        secondRpn.push(Number.of(12));
        secondRpn.push(Number.of(3));
        secondRpn.push(Operator.PLUS);


        final Stack<StackElement> thirdStack = new Stack<>();
        thirdStack.push(Operator.LEFTPARENTHESIS);
        thirdStack.push(Number.of(1));
        thirdStack.push(Operator.PLUS);
        thirdStack.push(Number.of(2));
        thirdStack.push(Operator.RIGHTPARENTHESIS);
        thirdStack.push(Operator.POWER);
        thirdStack.push(Number.of(2));
        thirdStack.push(Operator.MULTIPLICATION);
        thirdStack.push(Number.of(51));
        thirdStack.push(Operator.DIVISION);
        thirdStack.push(Number.of(51));
        thirdStack.push(Operator.MINUS);
        thirdStack.push(Number.of(1));

        final Stack<StackElement> thirdRpn = new Stack<>();
        thirdRpn.push(Number.of(1));
        thirdRpn.push(Number.of(2));
        thirdRpn.push(Operator.PLUS);
        thirdRpn.push(Number.of(2));
        thirdRpn.push(Operator.POWER);
        thirdRpn.push(Number.of(51));
        thirdRpn.push(Operator.MULTIPLICATION);
        thirdRpn.push(Number.of(51));
        thirdRpn.push(Operator.DIVISION);
        thirdRpn.push(Number.of(1));
        thirdRpn.push(Operator.MINUS);


        final Stack<StackElement> unaryMinusBeforeParenthesisStack = new Stack<>();
        unaryMinusBeforeParenthesisStack.push(Operator.LEFTPARENTHESIS);
        unaryMinusBeforeParenthesisStack.push(Number.of(-1));
        unaryMinusBeforeParenthesisStack.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisStack.push(Operator.LEFTPARENTHESIS);
        unaryMinusBeforeParenthesisStack.push(Number.of(4));
        unaryMinusBeforeParenthesisStack.push(Operator.PLUS);
        unaryMinusBeforeParenthesisStack.push(Number.of(3));
        unaryMinusBeforeParenthesisStack.push(Operator.RIGHTPARENTHESIS);
        unaryMinusBeforeParenthesisStack.push(Operator.PLUS);
        unaryMinusBeforeParenthesisStack.push(Number.of(8));
        unaryMinusBeforeParenthesisStack.push(Operator.PLUS);
        unaryMinusBeforeParenthesisStack.push(Number.of(4));
        unaryMinusBeforeParenthesisStack.push(Operator.RIGHTPARENTHESIS);
        unaryMinusBeforeParenthesisStack.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisStack.push(Number.of(5));
        unaryMinusBeforeParenthesisStack.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisStack.push(Number.of(4));

        final Stack<StackElement> unaryMinusBeforeParenthesisRpn = new Stack<>();
        unaryMinusBeforeParenthesisRpn.push(Number.of(-1));
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Number.of(3));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisRpn.push(Number.of(8));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Number.of(5));
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);


        final Stack<StackElement> unaryMinusBetweenParenthesisStack = new Stack<>();
        unaryMinusBetweenParenthesisStack.push(Operator.LEFTPARENTHESIS);
        unaryMinusBetweenParenthesisStack.push(Number.of(2));
        unaryMinusBetweenParenthesisStack.push(Operator.PLUS);
        unaryMinusBetweenParenthesisStack.push(Number.of(2));
        unaryMinusBetweenParenthesisStack.push(Operator.RIGHTPARENTHESIS);
        unaryMinusBetweenParenthesisStack.push(Operator.MINUS);
        unaryMinusBetweenParenthesisStack.push(Operator.LEFTPARENTHESIS);
        unaryMinusBetweenParenthesisStack.push(Number.of(1));
        unaryMinusBetweenParenthesisStack.push(Operator.PLUS);
        unaryMinusBetweenParenthesisStack.push(Number.of(2));
        unaryMinusBetweenParenthesisStack.push(Operator.RIGHTPARENTHESIS);

        final Stack<StackElement> unaryMinusBetweenParenthesisRpn = new Stack<>();
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Number.of(1));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Operator.MINUS);


        return Stream.of(
                Arguments.of(firstStack, firstRpn),
                Arguments.of(secondStack, secondRpn),
                Arguments.of(thirdStack, thirdRpn),
                Arguments.of(unaryMinusBeforeParenthesisStack, unaryMinusBeforeParenthesisRpn),
                Arguments.of(unaryMinusBetweenParenthesisStack, unaryMinusBetweenParenthesisRpn)
        );
    }


    @ParameterizedTest
    @MethodSource("provideCalculations")
    public void calculateRpn(final Stack<StackElement> rpn, final BigDecimal expectedResult) {
        final BigDecimal acutalResult = ShuntingYardAlgorithm.calculateRpn(rpn);

        assertEquals(expectedResult, acutalResult);
    }


    @ParameterizedTest
    @MethodSource("provideHighResCalculations")
    public void calculateHighResRpn(final Stack<StackElement> rpn, final BigDecimal expectedResult) {
        final BigDecimal acutalResult = ShuntingYardAlgorithm.calculateRpn(rpn, MathContext.DECIMAL128);

        assertEquals(expectedResult, acutalResult);
    }

    private static Stream<Arguments> provideCalculations() {
        final BigDecimal firstResult = BigDecimal.valueOf(6);
        final Stack<StackElement> firstRpn = new Stack<>();
        firstRpn.push(Number.of(1));
        firstRpn.push(Number.of(2));
        firstRpn.push(Operator.PLUS);
        firstRpn.push(Number.of(3));
        firstRpn.push(Operator.PLUS);

        final BigDecimal secondResult = BigDecimal.valueOf(15);
        final Stack<StackElement> secondRpn = new Stack<>();
        secondRpn.push(Number.of(12));
        secondRpn.push(Number.of(3));
        secondRpn.push(Operator.PLUS);

        final BigDecimal thirdResult = BigDecimal.valueOf(8);
        final Stack<StackElement> thirdRpn = new Stack<>();
        thirdRpn.push(Number.of(1));
        thirdRpn.push(Number.of(2));
        thirdRpn.push(Operator.PLUS);
        thirdRpn.push(Number.of(2));
        thirdRpn.push(Operator.POWER);
        thirdRpn.push(Number.of(51));
        thirdRpn.push(Operator.MULTIPLICATION);
        thirdRpn.push(Number.of(51));
        thirdRpn.push(Operator.DIVISION);
        thirdRpn.push(Number.of(1));
        thirdRpn.push(Operator.MINUS);


        final BigDecimal unaryMinusBeforeParenthesisSolution = BigDecimal.valueOf(100);
        final Stack<StackElement> unaryMinusBeforeParenthesisRpn = new Stack<>();
        unaryMinusBeforeParenthesisRpn.push(Number.of(-1));
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Number.of(3));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisRpn.push(Number.of(8));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Number.of(5));
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);


        final BigDecimal unaryMinusBetweenParenthesisSolution = BigDecimal.valueOf(1);
        final Stack<StackElement> unaryMinusBetweenParenthesisRpn = new Stack<>();
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Number.of(1));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Operator.MINUS);

        final BigDecimal tenToThePowerOfTwoPointThreeSolution = BigDecimal.valueOf(193.0696);
        final Stack<StackElement> tenToThePowerOfTwoPointThreeRpn = new Stack<>();
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-1));
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-1));
        tenToThePowerOfTwoPointThreeRpn.push(Operator.PLUS);
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-5));
        tenToThePowerOfTwoPointThreeRpn.push(Operator.MULTIPLICATION);
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-8));
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-2));
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(7));
        tenToThePowerOfTwoPointThreeRpn.push(Operator.DIVISION);
        tenToThePowerOfTwoPointThreeRpn.push(Operator.MULTIPLICATION);
        tenToThePowerOfTwoPointThreeRpn.push(Operator.POWER);

        final BigDecimal bugFromHectocBot = BigDecimal.valueOf(100);
        final Stack<StackElement> bugFromHectocBotRpn = new Stack<>();
        bugFromHectocBotRpn.push(Number.of(3));
        bugFromHectocBotRpn.push(Number.of(8));
        bugFromHectocBotRpn.push(Number.of(2));
        bugFromHectocBotRpn.push(Operator.PLUS);
        bugFromHectocBotRpn.push(Number.of(5));
        bugFromHectocBotRpn.push(Number.of(5));
        bugFromHectocBotRpn.push(Operator.PLUS);
        bugFromHectocBotRpn.push(Operator.MULTIPLICATION);
        bugFromHectocBotRpn.push(Number.of(3));
        bugFromHectocBotRpn.push(Operator.MINUS);
        bugFromHectocBotRpn.push(Operator.PLUS);

        return Stream.of(
                Arguments.of(firstRpn, firstResult),
                Arguments.of(secondRpn, secondResult),
                Arguments.of(thirdRpn, thirdResult),
                Arguments.of(unaryMinusBeforeParenthesisRpn, unaryMinusBeforeParenthesisSolution),
                Arguments.of(unaryMinusBetweenParenthesisRpn, unaryMinusBetweenParenthesisSolution),
                Arguments.of(tenToThePowerOfTwoPointThreeRpn, tenToThePowerOfTwoPointThreeSolution),
                Arguments.of(bugFromHectocBotRpn, bugFromHectocBot)
        );
    }

    private static Stream<Arguments> provideHighResCalculations() {
        final BigDecimal firstResult = BigDecimal.valueOf(6);
        final Stack<StackElement> firstRpn = new Stack<>();
        firstRpn.push(Number.of(1));
        firstRpn.push(Number.of(2));
        firstRpn.push(Operator.PLUS);
        firstRpn.push(Number.of(3));
        firstRpn.push(Operator.PLUS);

        final BigDecimal secondResult = BigDecimal.valueOf(15);
        final Stack<StackElement> secondRpn = new Stack<>();
        secondRpn.push(Number.of(12));
        secondRpn.push(Number.of(3));
        secondRpn.push(Operator.PLUS);

        final BigDecimal thirdResult = BigDecimal.valueOf(8);
        final Stack<StackElement> thirdRpn = new Stack<>();
        thirdRpn.push(Number.of(1));
        thirdRpn.push(Number.of(2));
        thirdRpn.push(Operator.PLUS);
        thirdRpn.push(Number.of(2));
        thirdRpn.push(Operator.POWER);
        thirdRpn.push(Number.of(51));
        thirdRpn.push(Operator.MULTIPLICATION);
        thirdRpn.push(Number.of(51));
        thirdRpn.push(Operator.DIVISION);
        thirdRpn.push(Number.of(1));
        thirdRpn.push(Operator.MINUS);


        final BigDecimal unaryMinusBeforeParenthesisSolution = BigDecimal.valueOf(100);
        final Stack<StackElement> unaryMinusBeforeParenthesisRpn = new Stack<>();
        unaryMinusBeforeParenthesisRpn.push(Number.of(-1));
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Number.of(3));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisRpn.push(Number.of(8));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Operator.PLUS);
        unaryMinusBeforeParenthesisRpn.push(Number.of(5));
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisRpn.push(Number.of(4));
        unaryMinusBeforeParenthesisRpn.push(Operator.MULTIPLICATION);


        final BigDecimal unaryMinusBetweenParenthesisSolution = BigDecimal.valueOf(1);
        final Stack<StackElement> unaryMinusBetweenParenthesisRpn = new Stack<>();
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Number.of(1));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Operator.MINUS);

        final BigDecimal tenToThePowerOfTwoPointThreeSolution = new BigDecimal("193.0697728883250167007074799840190");
        final Stack<StackElement> tenToThePowerOfTwoPointThreeRpn = new Stack<>();
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-1));
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-1));
        tenToThePowerOfTwoPointThreeRpn.push(Operator.PLUS);
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-5));
        tenToThePowerOfTwoPointThreeRpn.push(Operator.MULTIPLICATION);
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-8));
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(-2));
        tenToThePowerOfTwoPointThreeRpn.push(Number.of(7));
        tenToThePowerOfTwoPointThreeRpn.push(Operator.DIVISION);
        tenToThePowerOfTwoPointThreeRpn.push(Operator.MULTIPLICATION);
        tenToThePowerOfTwoPointThreeRpn.push(Operator.POWER);

        return Stream.of(
                Arguments.of(firstRpn, firstResult),
                Arguments.of(secondRpn, secondResult),
                Arguments.of(thirdRpn, thirdResult),
                Arguments.of(unaryMinusBeforeParenthesisRpn, unaryMinusBeforeParenthesisSolution),
                Arguments.of(unaryMinusBetweenParenthesisRpn, unaryMinusBetweenParenthesisSolution),
                Arguments.of(tenToThePowerOfTwoPointThreeRpn, tenToThePowerOfTwoPointThreeSolution)
        );
    }
}
