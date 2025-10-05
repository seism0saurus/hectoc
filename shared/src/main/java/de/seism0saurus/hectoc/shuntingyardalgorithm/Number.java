package de.seism0saurus.hectoc.shuntingyardalgorithm;

import com.fasterxml.jackson.annotation.*;

import java.io.Serial;
import java.util.Objects;

/**
 * Represents a number that implements the StackElement interface and is serializable.
 */
public class Number implements StackElement {

    @Serial
    private static final long serialVersionUID = 4L;

    private int value;

    public Number() {
        super();
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Number(@JsonProperty("value") int value) {
        this.value = value;
    }

    @JsonIgnore
    public static Number of(int value) {
        return new Number(value);
    }

    @JsonGetter("value")
    public int getValue() {
        return value;
    }

    @JsonIgnore
    public int value() {
        return getValue();
    }

    @JsonSetter("value")
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Number number = (Number) obj;
        return this.value == number.value;
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
