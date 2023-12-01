package de.seism0saurus.hectoc.shuntingyardalgorithm;

import lombok.Data;

@Data
public class NullElement implements StackElement {
    public String toString() {
        return "NullElement";
    }
}
