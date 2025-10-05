package de.seism0saurus.hectoc.bruteforcer.scheduling;

import com.fasterxml.jackson.annotation.*;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class StackElementContainer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Stack<StackElement> stackElements;

    public StackElementContainer() {
        super();
    }

    @JsonCreator
    public StackElementContainer(@JsonProperty("stackElements") Stack<StackElement> stackElements) {
        this.stackElements = stackElements;
    }

    @JsonGetter("stackElements")
    public Stack<StackElement> getStackElements() {
        return stackElements;
    }

    @JsonSetter("stackElements")
    public void setStackElements(Stack<StackElement> stackElements) {
        this.stackElements = stackElements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StackElementContainer that = (StackElementContainer) o;
        return Objects.equals(stackElements, that.stackElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stackElements);
    }

    @Override
    public String toString() {
        return "StackElementContainer{" +
                "stackElements=" + stackElements +
                '}';
    }

}
