package de.seism0saurus.hectoc.shuntingyardalgorithm;

public enum Operator implements StackElement {
    PLUS(1), MINUS(1), MULTIPLICATION(2), DIVISION(2), POWER(3), LEFTPARENTHESIS(4), RIGHTPARENTHESIS(4), NULLOPERATOR(5);

    private final int precedence;

    Operator(final int precedence) {
        this.precedence = precedence;
    }

    public static Operator from(final char character) {
        return switch (character) {
            case '+' -> PLUS;
            case '-' -> MINUS;
            case '−' -> MINUS;
            case '*' -> MULTIPLICATION;
            case 'x' -> MULTIPLICATION;
            case '/' -> DIVISION;
            case '÷' -> DIVISION;
            case '^' -> POWER;
            case '(' -> LEFTPARENTHESIS;
            case ')' -> RIGHTPARENTHESIS;
            default -> throw new IllegalArgumentException("Operator not implemented");
        };
    }

    public boolean hasGreaterPrecedenceThan(final Operator otherOperator) {
        return this.precedence > otherOperator.precedence;
    }

    public boolean hasSamePrecedenceAs(final Operator otherOperator) {
        return this.precedence == otherOperator.precedence;
    }
}
