package de.seism0saurus.hectoc.ShuntingYardAlgorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NumberTest{
    
    @Test
    public void testConstructorAndGetter(){
        int expected = 9;
        Number number = Number.of(9);

        int actual = number.value();

        assertEquals(expected, actual);
    }
}
