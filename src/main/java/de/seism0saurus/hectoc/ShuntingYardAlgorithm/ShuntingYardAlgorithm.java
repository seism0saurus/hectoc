package de.seism0saurus.hectoc.ShuntingYardAlgorithm;

import java.util.Stack;

public class ShuntingYardAlgorithm {
    
    private final Stack<StackElement> tokens;
    private final Stack<StackElement> rpn;
    private final int solution;

    public ShuntingYardAlgorithm(final String equotation){
        if (equotation == null || equotation.isEmpty()){
            throw new IllegalArgumentException("Please provide an equotation");
        }
        this.tokens = tokenize(equotation);
        this.rpn = createRpn(this.tokens);
        this.solution = calculateRpn(this.rpn);
    }

    protected Stack<StackElement> tokenize(final String equotation) {
        final Stack<StackElement> tokens = new Stack<>();
        equotation.codePoints().mapToObj(c -> (char) c)
            .forEach( c -> {
                switch(c) {
                    case '0','1','2','3','4','5','6','7','8','9' -> {
                        if (needToMergeToNumberCharacters(tokens)){
                            int lastInt = ((Number) tokens.pop()).value();
                            tokens.push(Number.of(lastInt * 10 + Character.getNumericValue(c)));
                        } else if (isPreviousOperatorAnUnaryMinus(tokens)){
                            tokens.pop();
                            tokens.push(Number.of(-Character.getNumericValue(c)));
                        } else {
                            tokens.push(Number.of(Character.getNumericValue(c)));   
                        }
                    }
                    case '+','-','*','/','^','(',')' -> {
                        if (isOperatorALeftParenthesisWithUnaryMinusBefore(c,tokens)){
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

    private boolean needToMergeToNumberCharacters(final Stack<StackElement> tokens) {
        return !tokens.empty() && tokens.peek() instanceof Number;
    }

    private boolean isPreviousOperatorAnUnaryMinus(final Stack<StackElement> tokens) {
        if (tokens.empty() || tokens.peek() != Operator.MINUS){
            // If there are no previous tokens or the previous token was no minus, it's impossible to be an unary minus
            return false;
        } else if (tokens.size() == 1) {
            // If there is exactly one token and it is a minus it is an unary minus
            return true;
        } else {
            // If there are more tokens and the previous one was a minus, it depends on the token before the minus
            final Operator minus = (Operator) tokens.pop();
            final boolean isOperator = tokens.peek() instanceof Operator;
            tokens.push(minus);
            return isOperator;
        }
    }

    private boolean isOperatorALeftParenthesisWithUnaryMinusBefore(final char c, final Stack<StackElement> tokens) {
        if ('(' == c){
            if(tokens.size() < 2 || tokens.peek() != Operator.MINUS){
                return false;
            } else {
                final Operator minus = (Operator) tokens.pop();
                final StackElement element = tokens.peek();
                tokens.push(minus);
                return !(element instanceof Number || element == Operator.RIGHTPARENTHESIS);
            }
        }
        return false;
    }

    protected Stack<StackElement> createRpn(final Stack<StackElement> tokens){
        final Stack<Operator> operators  = new Stack<>();
        final Stack<StackElement> output = new Stack<>();
        tokens.iterator().forEachRemaining(
            element -> {
                if (element instanceof Number){
                    output.push(element);
                } else if (element instanceof Operator) {
                    Operator operator = (Operator) element;
                    switch(operator){
                        case PLUS,MINUS,MULTIPLICATION,DIVISION,POWER -> {
                            while(!operators.empty() && shouldIPopOtherOperatorFirst(operator, operators.peek())){
                                Operator oldOperator = operators.pop();
                                output.push(oldOperator);
                            }
                            operators.push(operator);
                        }
                        case LEFTPARENTHESIS -> operators.push(operator);
                        case RIGHTPARENTHESIS -> {
                            while (operators.empty() || Operator.LEFTPARENTHESIS != operators.peek()){
                                if(operators.empty()){
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
        
        while(!operators.empty()){
            Operator operator = operators.pop();
            if(Operator.LEFTPARENTHESIS == operator || Operator.RIGHTPARENTHESIS == operator){
                throw new IllegalArgumentException("Found missmatched parenthesis");
            }
            output.push(operator);
        }
        
        return output;
    }

    private boolean shouldIPopOtherOperatorFirst(Operator newOperator, Operator oldOperator){
        return (
            oldOperator != null
            &&  oldOperator != Operator.LEFTPARENTHESIS
            &&  (
                    oldOperator.hasGreaterPrecedenceThan(newOperator)
                    ||  oldOperator.hasSamePrecedenceAs(newOperator)
                )
            );
    }
    
    protected int calculateRpn(final Stack<StackElement> rpn){
        final Stack<Double> stack = new Stack<>();
        rpn.iterator().forEachRemaining(
            element -> {
                if (element instanceof Number){
                    stack.push((double) ((Number) element).value());
                } else if (element instanceof Operator) {
                    Operator operator = (Operator) element;
                    switch (operator) {
                        case PLUS -> stack.push(stack.pop() + stack.pop());
                        case MINUS -> stack.push(-stack.pop() + stack.pop());
                        case MULTIPLICATION -> stack.push(stack.pop() * stack.pop());
                        case DIVISION -> {
                            Double divisor = stack.pop();
                            stack.push(stack.pop() / divisor);
                        }
                        case POWER -> {
                            Double exponent = stack.pop();
                            stack.push(Math.pow(stack.pop(), exponent));
                        }
                        default -> throw new IllegalStateException("This symbol should not be on the stack");
                    }
                }
            }
        );
        return stack.pop().intValue();
    }

    public int getSolution(){
        return solution;
    }
}
