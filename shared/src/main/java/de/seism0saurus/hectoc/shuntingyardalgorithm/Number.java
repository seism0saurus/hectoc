package de.seism0saurus.hectoc.shuntingyardalgorithm;

import java.io.Serial;
import java.io.Serializable;

public record Number(int number) implements StackElement, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static Number of(final int number) {
        return new Number(number);
    }

    public int value() {
        return number;
    }

    public String toString() {
        return String.valueOf(number);
    }
}
