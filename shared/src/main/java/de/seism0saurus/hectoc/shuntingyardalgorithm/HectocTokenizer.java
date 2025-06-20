package de.seism0saurus.hectoc.shuntingyardalgorithm;

import java.util.Stack;

/**
 * The tokenizer parses a <code>String</code> of a mathematical calculation and constructs a <code>Stack</code> of its elements.
 * This tokenizer is built for solutions of hectocs.
 * That means, that it does not accept invalid characters like 0 or a or operators like sin()
 *
 * @author seism0saurus
 */
public class HectocTokenizer {

    /**
     * Parses the given calculation and splits it into tokens.
     * Each token represents a number or operator from the calculation.
     * @param calculation The calculation in infix notation.
     * @return A <code>Stack</code> of elements.
     */
    public Stack<StackElement> tokenize(final String calculation) {
        final Stack<StackElement> tokens = new Stack<>();
        calculation.codePoints().mapToObj(c -> (char) c)
                .forEach(c -> {
                    switch (c) {
                        case '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                            if (needToMergeToNumberCharacters(tokens)) {
                                Number merged = mergeNumbers(c, tokens);
                                tokens.push(merged);
                            } else if (isPreviousOperatorAnUnaryMinus(tokens)) {
                                tokens.pop();
                                tokens.push(Number.of(-Character.getNumericValue(c)));
                            } else if (isNumberWithRightParenthesisBefore(c, tokens)) {
                                tokens.push(Operator.MULTIPLICATION);
                                tokens.push(Number.of(Character.getNumericValue(c)));
                            } else {
                                tokens.push(Number.of(Character.getNumericValue(c)));
                            }
                        }
                        case '+', '-', '−', '*', 'x', '/', '÷', ':', '^', '(', ')' -> {
                            if (isOperatorALeftParenthesisWithUnaryMinusBefore(c, tokens)) {
                                //Throw away the minus and replace it with (-1) *
                                tokens.pop();
                                tokens.push(Number.of(-1));
                                tokens.push(Operator.MULTIPLICATION);
                                tokens.push(Operator.from(c));
                            } else if (isOperatorALeftParenthesisWithOperandWithoutOperatorBefore(c, tokens)){
                                tokens.push(Operator.MULTIPLICATION);
                                tokens.push(Operator.from(c));
                            } else {
                                tokens.push(Operator.from(c));
                            }
                        }
                        default -> throw new IllegalArgumentException("Illegal character found.");
                    }
                });
        return tokens;
    }

    /**
     * Merge the last number and the current character.
     * @param c The second number as character.
     * @param tokens The stack with the other numbers.
     * @return The merged Number of the character c and the last number on the stack.
     */
    private static Number mergeNumbers(Character c, Stack<StackElement> tokens) {
        int lastInt = ((Number) tokens.pop()).value();
        Number merged;
        if (lastInt < 0){
            merged = Number.of(lastInt * 10 - Character.getNumericValue(c));
        } else {
            merged = Number.of(lastInt * 10 + Character.getNumericValue(c));
        }
        return merged;
    }

    /**
     * Determines, if a number consists of multiple digests.
     *
     * @param tokens The <code>Stack</code> of already parsed elements.
     * @return True, if the last element of the given <code>Stack</code> is a number. False otherwise.
     */
    boolean needToMergeToNumberCharacters(final Stack<StackElement> tokens) {
        return !tokens.empty() && tokens.peek() instanceof Number;
    }

    /**
     * Determines, if the previous operator was a unary minus.
     * For example:
     * x or +. In these cases, the previous operator is not a minus at all, so it can't be a unary minus.
     * (y)-x or y-x. In these cases, the minus is a binary operator.
     * -x, y*-x, y/-x, y^-x or (-(x+y)). In these cases, the minus is a unary operator.
     *
     * @param tokens The <code>Stack</code> of already parsed elements.
     * @return True, if the minus on the <code>Stack</code> is unary. False, otherwise.
     */
    boolean isPreviousOperatorAnUnaryMinus(final Stack<StackElement> tokens) {
        if (tokens.empty() || tokens.peek() != Operator.MINUS) {
            // If there are no previous tokens or the previous token was no minus, it's impossible to be an unary minus
            return false;
        } else if (tokens.size() == 1) {
            // If there is exactly one token and it is a minus it is a unary minus
            return true;
        } else {
            // If there are more tokens and the previous one was a minus, it depends on the token before the minus
            final Operator minus = (Operator) tokens.pop();
            final StackElement lastElement = tokens.peek();
            tokens.push(minus);
            if (lastElement instanceof Operator) {
                if (lastElement.equals(Operator.RIGHTPARENTHESIS)) {
                    // (y)-x. In this case, the minus is a binary operator.
                    return false;
                } else {
                    // y*-x, y/-x, y^-x, (-(x+y)). In these cases, the minus is a unary operator.
                    return true;
                }
            } else {
                // y-x. In this case, the minus is a binary operator.
                return false;
            }
        }
    }

    /**
     * Determines if the previous operator was an operand without an operator.
     *
     * @param tokens The stack of already parsed elements.
     * @return True, if the previous operator was an operand without an operator. False, otherwise.
     */
    boolean isPreviousOperatorAnOperandWithoutOperator(final Stack<StackElement> tokens) {
        // If there is a right parenthesis or a number before the current element, it is an operand without an operator
        // Otherwise it is an operator and it can't be an operand
        if (tokens.empty()) {
            // If there are no previous tokens, it's impossible to be an operator without an operand
            return false;
        } else return tokens.peek().equals(Operator.RIGHTPARENTHESIS) || tokens.peek().getClass().equals(Number.class);
    }

    /**
     * Determines if the previous operator was a right parenthesis.
     *
     * @param tokens The <code>Stack</code> of already parsed elements.
     * @return True, if the previous operator was a right parenthesis. False, otherwise.
     */
    boolean isPreviousOperatorARightParenthesis(final Stack<StackElement> tokens) {
        if (tokens.empty()) {
            // If there are no previous tokens, it's impossible to be aright parenthesis
            return false;
        } else return tokens.peek().equals(Operator.RIGHTPARENTHESIS);
    }


    /**
     * Determines if the given operator is a left parenthesis with a unary minus before it.
     *
     * @param c The operator to test.
     * @param tokens The <code>Stack</code> of already parsed elements.
     * @return True, if the minus on the <code>Stack</code> is unary. False, otherwise.
     */
    boolean isOperatorALeftParenthesisWithUnaryMinusBefore(final char c, final Stack<StackElement> tokens) {
        if ('(' == c) {
            return isPreviousOperatorAnUnaryMinus(tokens);
        }
        return false;
    }

    /**
     * Determines if the given operator is a left parenthesis with a operand without an operator before it.
     *
     * @param c The operator to test.
     * @param tokens The <code>Stack</code> of already parsed elements.
     * @return True, if the minus on the <code>Stack</code> is unary. False, otherwise.
     */
    boolean isOperatorALeftParenthesisWithOperandWithoutOperatorBefore(final char c, final Stack<StackElement> tokens) {
        if ('(' == c) {
            return isPreviousOperatorAnOperandWithoutOperator(tokens);
        }
        return false;
    }

    boolean isNumberWithRightParenthesisBefore(final char c, final Stack<StackElement> tokens) {
        return switch (c) {
            case '1', '2', '3', '4', '5', '6', '7', '8', '9' -> isPreviousOperatorARightParenthesis(tokens);
            default -> false;
        };
    }
}