package de.seism0saurus.hectoc.shuntingyardalgorithm;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a number that implements the StackElement interface and is serializable.
 */
public class Number implements StackElement, Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    private int value;

    public Number() {
        this(0);
    }

    public Number(int value) {
        this.value = value;
    }

    public static Number of(int value) {
        return new Number(value);
    }

    public int getValue() {
        return value;
    }

    public int value() {
        return getValue();
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Number number = (Number) obj;
        return value == number.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
