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
        final Stack<StackElement> tokens = new Stack<StackElement>();
        calculation.codePoints().mapToObj(c -> (char) c)
                .forEach(c -> {
                    switch (c) {
                        case '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                            if (needToMergeToNumberCharacters(tokens)) {
                                int lastInt = ((Number) tokens.pop()).value();
                                tokens.push(Number.of(lastInt * 10 + Character.getNumericValue(c)));
                            } else if (isPreviousOperatorAnUnaryMinus(tokens)) {
                                tokens.pop();
                                tokens.push(Number.of(-Character.getNumericValue(c)));
                            } else {
                                tokens.push(Number.of(Character.getNumericValue(c)));
                            }
                        }
                        case '+', '-', '*', '/', '^', '(', ')' -> {
                            if (isOperatorALeftParenthesisWithUnaryMinusBefore(c, tokens)) {
                                //Throw away the minus and replace it with (-1) *
                                tokens.pop();
                                tokens.push(Number.of(-1));
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
}