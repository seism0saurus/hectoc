package de.seism0saurus.hectoc.shuntingyardalgorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HectocTokenizerTest {

    @Test
    public void tokenizeHandlesIllegalCharacters() {
        final HectocTokenizer hectocTokenizer = new HectocTokenizer();

        assertThrows(IllegalArgumentException.class, () -> hectocTokenizer.tokenize("!"));
        assertThrows(IllegalArgumentException.class, () -> hectocTokenizer.tokenize("0"));
        assertThrows(IllegalArgumentException.class, () -> hectocTokenizer.tokenize("a"));
        assertThrows(IllegalArgumentException.class, () -> hectocTokenizer.tokenize("sin()"));
    }

    @ParameterizedTest
    @MethodSource("provideTokens")
    public void tokenize(final String equotation, final Stack<StackElement> expectedStack) {
        final HectocTokenizer hectocTokenizer = new HectocTokenizer();

        final Stack<StackElement> actualStack = hectocTokenizer.tokenize(equotation);

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

        final String unaryMinusBeforeParenthesisAsFirstElementEquotation = "-(1+2)";
        final Stack<StackElement> unaryMinusBeforeParenthesisAsFirstElementStack = new Stack<>();
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Number.of(-1));
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.LEFTPARENTHESIS);
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Number.of(1));
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.PLUS);
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Number.of(2));
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.RIGHTPARENTHESIS);

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

        final String parenthesisMinusNumberEquotation = "(1+2)-3";
        final Stack<StackElement> parenthesisMinusNumberStack = new Stack<>();
        parenthesisMinusNumberStack.push(Operator.LEFTPARENTHESIS);
        parenthesisMinusNumberStack.push(Number.of(1));
        parenthesisMinusNumberStack.push(Operator.PLUS);
        parenthesisMinusNumberStack.push(Number.of(2));
        parenthesisMinusNumberStack.push(Operator.RIGHTPARENTHESIS);
        parenthesisMinusNumberStack.push(Operator.MINUS);
        parenthesisMinusNumberStack.push(Number.of(3));

        final String otherOperatorsMinusNumberEquotation = "1*-1+2/-2+3^-3";
        final Stack<StackElement> otherOperatorsMinusNumberStack = new Stack<>();
        otherOperatorsMinusNumberStack.push(Number.of(1));
        otherOperatorsMinusNumberStack.push(Operator.MULTIPLICATION);
        otherOperatorsMinusNumberStack.push(Number.of(-1));
        otherOperatorsMinusNumberStack.push(Operator.PLUS);
        otherOperatorsMinusNumberStack.push(Number.of(2));
        otherOperatorsMinusNumberStack.push(Operator.DIVISION);
        otherOperatorsMinusNumberStack.push(Number.of(-2));
        otherOperatorsMinusNumberStack.push(Operator.PLUS);
        otherOperatorsMinusNumberStack.push(Number.of(3));
        otherOperatorsMinusNumberStack.push(Operator.POWER);
        otherOperatorsMinusNumberStack.push(Number.of(-3));

        return Stream.of(
                Arguments.of(firstEquotation, firstStack),
                Arguments.of(secondEquotation, secondStack),
                Arguments.of(thirdEquotation, thirdStack),
                Arguments.of(negativeNumberAtTheStartEquotation, negativeNumberAtTheStartEquotationStack),
                Arguments.of(negativeNumberInTheMiddleEquotation, negativeNumberInTheMiddleStack),
                Arguments.of(unaryMinusBeforeParenthesisEquotation, unaryMinusBeforeParenthesisStack),
                Arguments.of(unaryMinusBeforeParenthesisAsFirstElementEquotation, unaryMinusBeforeParenthesisAsFirstElementStack),
                Arguments.of(unaryMinusBetweenParenthesisEquotation, unaryMinusBetweenParenthesisStack),
                Arguments.of(parenthesisMinusNumberEquotation, parenthesisMinusNumberStack),
                Arguments.of(otherOperatorsMinusNumberEquotation, otherOperatorsMinusNumberStack)
        );
    }
}
