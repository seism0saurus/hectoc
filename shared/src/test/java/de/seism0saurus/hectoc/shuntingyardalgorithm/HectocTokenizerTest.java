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
    public void tokenize(final String Equitation, final Stack<StackElement> expectedStack) {
        final HectocTokenizer hectocTokenizer = new HectocTokenizer();

        final Stack<StackElement> actualStack = hectocTokenizer.tokenize(Equitation);

        assertEquals(expectedStack, actualStack);
    }

    private static Stream<Arguments> provideTokens() {
        final String firstEquitation = "1+2+3";
        final Stack<StackElement> firstStack = new Stack<>();
        firstStack.push(Number.of(1));
        firstStack.push(Operator.PLUS);
        firstStack.push(Number.of(2));
        firstStack.push(Operator.PLUS);
        firstStack.push(Number.of(3));

        final String secondEquitation = "12+3";
        final Stack<StackElement> secondStack = new Stack<>();
        secondStack.push(Number.of(12));
        secondStack.push(Operator.PLUS);
        secondStack.push(Number.of(3));

        final String thirdEquitation = "(1+2)^2*51/51-1";
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

        final String negativeNumberAtTheStartEquitation = "-3+5";
        final Stack<StackElement> negativeNumberAtTheStartEquitationStack = new Stack<>();
        negativeNumberAtTheStartEquitationStack.push(Number.of(-3));
        negativeNumberAtTheStartEquitationStack.push(Operator.PLUS);
        negativeNumberAtTheStartEquitationStack.push(Number.of(5));

        final String negativeNumberInTheMiddleEquitation = "(5+8+7)*(-9+2*7)";
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

        final String unaryMinusBeforeParenthesisEquitation = "(-(4+3)+8+4)*5*4";
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

        final String unaryMinusBeforeParenthesisAsFirstElementEquitation = "-(1+2)";
        final Stack<StackElement> unaryMinusBeforeParenthesisAsFirstElementStack = new Stack<>();
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Number.of(-1));
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.MULTIPLICATION);
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.LEFTPARENTHESIS);
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Number.of(1));
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.PLUS);
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Number.of(2));
        unaryMinusBeforeParenthesisAsFirstElementStack.push(Operator.RIGHTPARENTHESIS);

        final String unaryMinusBetweenParenthesisEquitation = "(2+2)-(1+2)";
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

        final String parenthesisMinusNumberEquitation = "(1+2)-3";
        final Stack<StackElement> parenthesisMinusNumberStack = new Stack<>();
        parenthesisMinusNumberStack.push(Operator.LEFTPARENTHESIS);
        parenthesisMinusNumberStack.push(Number.of(1));
        parenthesisMinusNumberStack.push(Operator.PLUS);
        parenthesisMinusNumberStack.push(Number.of(2));
        parenthesisMinusNumberStack.push(Operator.RIGHTPARENTHESIS);
        parenthesisMinusNumberStack.push(Operator.MINUS);
        parenthesisMinusNumberStack.push(Number.of(3));

        final String otherOperatorsMinusNumberEquitation = "1*-1+2/-2+3^-3";
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

        final String alternativeMathSymbolsMinusEquitation = "3−5";
        final Stack<StackElement> alternativeMathSymbolsMinusStack = new Stack<>();
        alternativeMathSymbolsMinusStack.push(Number.of(3));
        alternativeMathSymbolsMinusStack.push(Operator.MINUS);
        alternativeMathSymbolsMinusStack.push(Number.of(5));

        final String alternativeMathSymbolsMultiplicationEquitation = "3x5";
        final Stack<StackElement> alternativeMathSymbolsMultiplicationStack = new Stack<>();
        alternativeMathSymbolsMultiplicationStack.push(Number.of(3));
        alternativeMathSymbolsMultiplicationStack.push(Operator.MULTIPLICATION);
        alternativeMathSymbolsMultiplicationStack.push(Number.of(5));

        final String alternativeMathSymbolsDivisionEquitation = "3÷5";
        final Stack<StackElement> alternativeMathSymbolsDivisionStack = new Stack<>();
        alternativeMathSymbolsDivisionStack.push(Number.of(3));
        alternativeMathSymbolsDivisionStack.push(Operator.DIVISION);
        alternativeMathSymbolsDivisionStack.push(Number.of(5));

        final String noSymbolBetweenPrenthesisAndOperandEquitation = "(4+1)2";
        final Stack<StackElement> noSymbolBetweenPrenthesisAndOperandStack = new Stack<>();
        noSymbolBetweenPrenthesisAndOperandStack.push(Operator.LEFTPARENTHESIS);
        noSymbolBetweenPrenthesisAndOperandStack.push(Number.of(4));
        noSymbolBetweenPrenthesisAndOperandStack.push(Operator.PLUS);
        noSymbolBetweenPrenthesisAndOperandStack.push(Number.of(1));
        noSymbolBetweenPrenthesisAndOperandStack.push(Operator.RIGHTPARENTHESIS);
        noSymbolBetweenPrenthesisAndOperandStack.push(Operator.MULTIPLICATION);
        noSymbolBetweenPrenthesisAndOperandStack.push(Number.of(2));

        final String noSymbolBetweenOperandAndPrenthesisEquitation = "2(4+1)";
        final Stack<StackElement> noSymbolBetweenOperandAndPrenthesisStack = new Stack<>();
        noSymbolBetweenOperandAndPrenthesisStack.push(Number.of(2));
        noSymbolBetweenOperandAndPrenthesisStack.push(Operator.MULTIPLICATION);
        noSymbolBetweenOperandAndPrenthesisStack.push(Operator.LEFTPARENTHESIS);
        noSymbolBetweenOperandAndPrenthesisStack.push(Number.of(4));
        noSymbolBetweenOperandAndPrenthesisStack.push(Operator.PLUS);
        noSymbolBetweenOperandAndPrenthesisStack.push(Number.of(1));
        noSymbolBetweenOperandAndPrenthesisStack.push(Operator.RIGHTPARENTHESIS);

        final String noSymbolBetweenTwoPrenthesisEquitation = "(1+2)(4+1)";
        final Stack<StackElement> noSymbolBetweenTwoPrenthesisStack = new Stack<>();
        noSymbolBetweenTwoPrenthesisStack.push(Operator.LEFTPARENTHESIS);
        noSymbolBetweenTwoPrenthesisStack.push(Number.of(1));
        noSymbolBetweenTwoPrenthesisStack.push(Operator.PLUS);
        noSymbolBetweenTwoPrenthesisStack.push(Number.of(2));
        noSymbolBetweenTwoPrenthesisStack.push(Operator.RIGHTPARENTHESIS);
        noSymbolBetweenTwoPrenthesisStack.push(Operator.MULTIPLICATION);
        noSymbolBetweenTwoPrenthesisStack.push(Operator.LEFTPARENTHESIS);
        noSymbolBetweenTwoPrenthesisStack.push(Number.of(4));
        noSymbolBetweenTwoPrenthesisStack.push(Operator.PLUS);
        noSymbolBetweenTwoPrenthesisStack.push(Number.of(1));
        noSymbolBetweenTwoPrenthesisStack.push(Operator.RIGHTPARENTHESIS);

        final String alternativeCharsEquitation = "1x2+9:3÷3";
        final Stack<StackElement> alternativeCharsStack = new Stack<>();
        alternativeCharsStack.push(Number.of(1));
        alternativeCharsStack.push(Operator.MULTIPLICATION);
        alternativeCharsStack.push(Number.of(2));
        alternativeCharsStack.push(Operator.PLUS);
        alternativeCharsStack.push(Number.of(9));
        alternativeCharsStack.push(Operator.DIVISION);
        alternativeCharsStack.push(Number.of(3));
        alternativeCharsStack.push(Operator.DIVISION);
        alternativeCharsStack.push(Number.of(3));

        return Stream.of(
                Arguments.of(firstEquitation, firstStack),
                Arguments.of(secondEquitation, secondStack),
                Arguments.of(thirdEquitation, thirdStack),
                Arguments.of(negativeNumberAtTheStartEquitation, negativeNumberAtTheStartEquitationStack),
                Arguments.of(negativeNumberInTheMiddleEquitation, negativeNumberInTheMiddleStack),
                Arguments.of(unaryMinusBeforeParenthesisEquitation, unaryMinusBeforeParenthesisStack),
                Arguments.of(unaryMinusBeforeParenthesisAsFirstElementEquitation, unaryMinusBeforeParenthesisAsFirstElementStack),
                Arguments.of(unaryMinusBetweenParenthesisEquitation, unaryMinusBetweenParenthesisStack),
                Arguments.of(parenthesisMinusNumberEquitation, parenthesisMinusNumberStack),
                Arguments.of(otherOperatorsMinusNumberEquitation, otherOperatorsMinusNumberStack),
                Arguments.of(alternativeMathSymbolsMinusEquitation, alternativeMathSymbolsMinusStack),
                Arguments.of(alternativeMathSymbolsMultiplicationEquitation, alternativeMathSymbolsMultiplicationStack),
                Arguments.of(alternativeMathSymbolsDivisionEquitation, alternativeMathSymbolsDivisionStack),
                Arguments.of(noSymbolBetweenPrenthesisAndOperandEquitation, noSymbolBetweenPrenthesisAndOperandStack),
                Arguments.of(noSymbolBetweenOperandAndPrenthesisEquitation, noSymbolBetweenOperandAndPrenthesisStack),
                Arguments.of(noSymbolBetweenTwoPrenthesisEquitation, noSymbolBetweenTwoPrenthesisStack),
                Arguments.of(alternativeCharsEquitation, alternativeCharsStack)
        );
    }
}
