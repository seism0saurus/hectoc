package de.seism0saurus.hectoc.bruteforcer.scheduling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

public class NumberContainer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Stack<Number> numbers;

    public NumberContainer() {
        super();
    }

    @JsonCreator
    public NumberContainer(@JsonProperty("numbers") Stack<Number> numbers) {
        this.numbers = numbers;
    }

    @JsonGetter("numbers")
    public Stack<Number> getNumbers() {
        return numbers;
    }

    @JsonSetter("numbers")
    public void setNumbers(Stack<Number> numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberContainer that = (NumberContainer) o;
        return Objects.equals(numbers, that.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }

    @Override
    public String toString() {
        return "NumberContainer{" +
                "numbers=" + numbers +
                '}';
    }

}
