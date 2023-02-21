package de.seism0saurus.hectoc.ShuntingYardAlgorithm;

import lombok.Data;

@Data
public class Number implements StackElement{
    private final int number;

    private Number(final int number){
        this.number = number;
    }

    public static final Number of(final int number){
        return new Number(number);
    }

    public int value(){
        return number;
    }
}
