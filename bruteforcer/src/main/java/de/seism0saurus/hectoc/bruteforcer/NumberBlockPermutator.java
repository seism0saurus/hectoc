package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NumberBlockPermutator {
    protected static List<Stack<Number>> createPermutationsOfBlocksOfNumbers(Stack<Number> stack) {
        System.out.println("Creating blocks of numbers for " + stack);
        final List<Stack<Number>> digitStacks = new ArrayList<>();
        int size = stack.size();
        if (size == 0){
            return digitStacks;
        }
        for (int numberOfPermutations = 0; numberOfPermutations < Math.pow(2, size - 1); numberOfPermutations++) {
            String binaryString = Integer.toBinaryString(numberOfPermutations);
            Stack<Number> newStack = new Stack<>();

            Number number = stack.elementAt(0);
            newStack.push(Number.of(number.getNumber()));

            for (int nthElement = 0; nthElement < size - 1; nthElement++) {
                String stringRepresentation = String.format("%1$" + (size-1) + "s", binaryString).replace(' ', '0');
                number = stack.elementAt(nthElement + 1);
                if (stringRepresentation.charAt(nthElement) == '0') {
                    newStack.push(Number.of(number.getNumber()));
                } else {
                    Number oldNumber = newStack.pop();
                    Number newNumber = Number.of(oldNumber.getNumber() * 10 + number.getNumber());
                    newStack.push(newNumber);
                }
            }
            digitStacks.add(newStack);
        }
        System.out.println(digitStacks.size() + " blocks created.");
        return digitStacks;
    }
}
