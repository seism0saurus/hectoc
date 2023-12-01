package de.seism0saurus.hectoc.shuntingyardalgorithm;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberTest {

    @Test
    public void testConstructorAndGetter() {
        int expected = 9;
        Number number = Number.of(9);

        int actual = number.value();

        assertEquals(expected, actual);
    }
}
