package de.seism0saurus.hectoc.ShuntingYardAlgorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Stack;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
    @MethodSource("provideTokens")
    public void tokenize(final String equotation, final Stack<StackElement> expectedStack) {
        final ShuntingYardAlgorithm algorithm = new ShuntingYardAlgorithm(equotation);

        final Stack<StackElement> actualStack = algorithm.tokenize(equotation);

        assertEquals(expectedStack, actualStack);
    }

    private static Stream<Arguments> provideTokens() {
        final String firstEquotation = "1+2+3";
        final Stack<StackElement> firstStack = new Stack<>();
        firstStack.push(Number.of(1));
        firstStack.push(Operator.PLUS);
        firstStack.push(Number.of(2));
        firstStack.push(Operator.PLUS);
        firstStack.push(Number.of(3));

        final String secondEquotation = "12+3";
        final Stack<StackElement> secondStack = new Stack<>();
        secondStack.push(Number.of(12));
        secondStack.push(Operator.PLUS);
        secondStack.push(Number.of(3));

        final String thirdEquotation = "(1+2)^2*51/51-1";
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

        final String negativeNumberAtTheStartEquotation = "-3+5";
        final Stack<StackElement> negativeNumberAtTheStartEquotationStack = new Stack<>();
        negativeNumberAtTheStartEquotationStack.push(Number.of(-3));
        negativeNumberAtTheStartEquotationStack.push(Operator.PLUS);
        negativeNumberAtTheStartEquotationStack.push(Number.of(5));

        final String negativeNumberInTheMiddleEquotation = "(5+8+7)*(-9+2*7)";
        final Stack<StackElement> negativeNumberInTheMiddleStack = new Stack<>();
        negativeNumberInTheMiddleStack.push(Operator.LEFTPARENTHESIS);
        negativeNumberInTheMiddleStack.push(Number.of(5));
        negativeNumberInTheMiddleStack.push(Operator.PLUS);
        negativeNumberInTheMiddleStack.push(Number.of(8));
        negativeNumberInTheMiddleStack.push(Operator.PLUS);
        negativeNumberInTheMiddleStack.push(Number.of(7));
        negativeNumberInTheMiddleStack.push(Operator.RIGHTPARENTHESIS);
        negativeNumberInTheMiddleStack.push(Operator.MULTIPLICATION);
        negativeNumberInTheMiddleStack.push(Operator.LEFTPARENTHESIS);
        negativeNumberInTheMiddleStack.push(Number.of(-9));
        negativeNumberInTheMiddleStack.push(Operator.PLUS);
        negativeNumberInTheMiddleStack.push(Number.of(2));
        negativeNumberInTheMiddleStack.push(Operator.MULTIPLICATION);
        negativeNumberInTheMiddleStack.push(Number.of(7));
        negativeNumberInTheMiddleStack.push(Operator.RIGHTPARENTHESIS);


        final String unaryMinusBeforeParenthesisEquotation = "(-(4+3)+8+4)*5*4";
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


        final String unaryMinusBetweenParenthesisEquotation = "(2+2)-(1+2)";
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

        return Stream.of(
          Arguments.of(firstEquotation, firstStack),
          Arguments.of(secondEquotation, secondStack),
          Arguments.of(thirdEquotation, thirdStack),
          Arguments.of(negativeNumberAtTheStartEquotation, negativeNumberAtTheStartEquotationStack),
          Arguments.of(negativeNumberInTheMiddleEquotation, negativeNumberInTheMiddleStack),
          Arguments.of(unaryMinusBeforeParenthesisEquotation, unaryMinusBeforeParenthesisStack),
          Arguments.of(unaryMinusBetweenParenthesisEquotation, unaryMinusBetweenParenthesisStack)
        );
    }

    @Test
    public void tokenizeHandlesIllegalCharacters() {
        final ShuntingYardAlgorithm algorithm = new ShuntingYardAlgorithm("1");

        assertThrows(IllegalArgumentException.class, () -> algorithm.tokenize("!"));
    }

    @ParameterizedTest
    @MethodSource("provideRpns")
    public void createRpn(final Stack<StackElement> tokens, final Stack<StackElement> expectedRpn){
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
    public void calculateRpn(final Stack<StackElement> rpn, final int expectedResult){
        final ShuntingYardAlgorithm algorithm = new ShuntingYardAlgorithm("1");

        final int acutalResult = algorithm.calculateRpn(rpn);

        assertEquals(expectedResult, acutalResult);
    }

    private static Stream<Arguments> provideCalculations() {
        final int firstResult = 6;
        final Stack<StackElement> firstRpn = new Stack<>();
        firstRpn.push(Number.of(1));
        firstRpn.push(Number.of(2));
        firstRpn.push(Operator.PLUS);
        firstRpn.push(Number.of(3));
        firstRpn.push(Operator.PLUS);

        final int secondResult = 15;
        final Stack<StackElement> secondRpn = new Stack<>();
        secondRpn.push(Number.of(12));
        secondRpn.push(Number.of(3));
        secondRpn.push(Operator.PLUS);

        final int thirdResult = 8;
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


        final int unaryMinusBeforeParenthesisSolution = 100;
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


        final int unaryMinusBetweenParenthesisSolution = 1;
        final Stack<StackElement> unaryMinusBetweenParenthesisRpn = new Stack<>();
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Number.of(1));
        unaryMinusBetweenParenthesisRpn.push(Number.of(2));
        unaryMinusBetweenParenthesisRpn.push(Operator.PLUS);
        unaryMinusBetweenParenthesisRpn.push(Operator.MINUS);

        
        return Stream.of(
          Arguments.of(firstRpn, firstResult),
          Arguments.of(secondRpn, secondResult),
          Arguments.of(thirdRpn, thirdResult),
          Arguments.of(unaryMinusBeforeParenthesisRpn, unaryMinusBeforeParenthesisSolution),
          Arguments.of(unaryMinusBetweenParenthesisRpn, unaryMinusBetweenParenthesisSolution)
        );
    }
}
