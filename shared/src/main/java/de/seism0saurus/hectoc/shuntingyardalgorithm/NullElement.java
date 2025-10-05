package de.seism0saurus.hectoc.shuntingyardalgorithm;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class NullElement implements StackElement, Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    public String toString() {
        return "NullElement";
    }
}
