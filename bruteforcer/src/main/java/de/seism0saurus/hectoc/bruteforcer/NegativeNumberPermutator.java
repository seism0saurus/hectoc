package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NegativeNumberPermutator {

    /**
     * With n numbers we have 2^n different permutations of negative numbers
     *
     * @param stack the stack of numbers
     * @return a list of stacks with all different permutations of the negative and positive numbers of the original stack of numbers
     */
    protected static List<Stack<Number>> createPermutationsOfNegativeNumbers(Stack<Number> stack) {
        System.out.println("Creating permutations of negative numbers for " + stack);
        final List<Stack<Number>> digitStacks = new ArrayList<>();
        int size = stack.size();
        for (int numberOfPermutations = 0; numberOfPermutations < Math.pow(2, size); numberOfPermutations++) {
            String binaryString = Integer.toBinaryString(numberOfPermutations);
            String stringRepresentation;
            if (size == 0) {
                stringRepresentation = "";
            } else {
                stringRepresentation = String.format("%1$" + size + "s", binaryString).replace(' ', '0');
            }
            Stack<Number> newStack = new Stack<>();

            for (int nthElement = 0; nthElement < size; nthElement++) {
                Number number = stack.elementAt(nthElement);
                if (stringRepresentation.charAt(nthElement) == '0') {
                    newStack.push(Number.of(number.getNumber()));
                } else {
                    newStack.push(Number.of(-1 * number.getNumber()));
                }
            }
            digitStacks.add(newStack);
        }
        System.out.println(digitStacks.size() + " permutations of negative numbers created");
        return digitStacks;
    }
}
