package de.seism0saurus.hectoc.shuntingyardalgorithm;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;

import static java.math.MathContext.DECIMAL32;

public class ShuntingYardAlgorithm {

    public static final MathContext CONTEXT = DECIMAL32;
    private final BigDecimal solution;
    private final HectocTokenizer hectocTokenizer = new HectocTokenizer();

    public ShuntingYardAlgorithm(final String equotation) {
        if (equotation == null || equotation.isEmpty()) {
            throw new IllegalArgumentException("Please provide an equotation");
        }
        Stack<StackElement> tokens = hectocTokenizer.tokenize(equotation);
        Stack<StackElement> rpn = createRpn(tokens);
        this.solution = calculateRpn(rpn);
    }

    protected Stack<StackElement> createRpn(final Stack<StackElement> tokens) {
        final Stack<Operator> operators = new Stack<>();
        final Stack<StackElement> output = new Stack<>();
        tokens.iterator().forEachRemaining(
                element -> {
                    if (element instanceof Number) {
                        output.push(element);
                    } else if (element instanceof Operator operator) {
                        switch (operator) {
                            case PLUS, MINUS, MULTIPLICATION, DIVISION, POWER -> {
                                while (!operators.empty() && shouldIPopOtherOperatorFirst(operator, operators.peek())) {
                                    Operator oldOperator = operators.pop();
                                    output.push(oldOperator);
                                }
                                operators.push(operator);
                            }
                            case LEFTPARENTHESIS -> operators.push(operator);
                            case RIGHTPARENTHESIS -> {
                                while (operators.empty() || Operator.LEFTPARENTHESIS != operators.peek()) {
                                    if (operators.empty()) {
                                        throw new IllegalArgumentException("Missing left parenthesis");
                                    }
                                    Operator oldOperator = operators.pop();
                                    output.push(oldOperator);
                                }
                                //Discard matching left paranthesis
                                operators.pop();
                            }
                        }
                    }
                });

        while (!operators.empty()) {
            Operator operator = operators.pop();
            if (Operator.LEFTPARENTHESIS == operator || Operator.RIGHTPARENTHESIS == operator) {
                throw new IllegalArgumentException("Found missmatched parenthesis");
            }
            output.push(operator);
        }

        return output;
    }

    private boolean shouldIPopOtherOperatorFirst(Operator newOperator, Operator oldOperator) {
        return (
                oldOperator != null
                        && oldOperator != Operator.LEFTPARENTHESIS
                        && (
                        oldOperator.hasGreaterPrecedenceThan(newOperator)
                                || oldOperator.hasSamePrecedenceAs(newOperator)
                )
        );
    }

    public static BigDecimal calculateRpn(final Stack<StackElement> rpn) throws ArithmeticException {
        return calculateRpn(rpn, CONTEXT);
    }
    public static BigDecimal calculateRpn(final Stack<StackElement> rpn, MathContext context) throws ArithmeticException {
        Stack<StackElement> rpnClone = (Stack<StackElement>) rpn.clone();
        final Stack<BigDecimal> stack = new Stack<>();
        rpnClone.iterator().forEachRemaining(
                element -> {
                    if (element instanceof Number) {
                        stack.push(BigDecimal.valueOf(((Number) element).value()));
                    } else if (element instanceof Operator operator) {
                        switch (operator) {
                            case PLUS -> stack.push(stack.pop().add(stack.pop(), context));
                            case MINUS ->
                                    stack.push(BigDecimal.valueOf(-1).multiply(stack.pop(), context).add(stack.pop(), context));
                            case MULTIPLICATION -> stack.push(stack.pop().multiply(stack.pop(), context));
                            case DIVISION -> {
                                BigDecimal divisor = stack.pop();
                                stack.push(stack.pop().divide(divisor, context));
                            }
                            case POWER -> {
                                BigDecimal exponent = stack.pop();
                                BigDecimal base = stack.pop();
                                stack.push(BigDecimalMath.pow(base, exponent, context));
                            }
                            default ->
                                    throw new IllegalStateException("This symbol should not be on the stack: " + operator);
                        }
                    }
                }
        );
        return stack.pop();
    }

    public BigDecimal getSolution() {
        return solution;
    }
}
