package de.seism0saurus.hectoc.shuntingyardalgorithm;

import lombok.Data;

@Data
public class Number implements StackElement {
    private final int number;

    private Number(final int number) {
        this.number = number;
    }

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
