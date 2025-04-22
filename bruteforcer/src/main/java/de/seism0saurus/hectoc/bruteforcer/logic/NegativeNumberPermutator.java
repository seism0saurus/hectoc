package de.seism0saurus.hectoc.bruteforcer.logic;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The NegativeNumberPermutator class provides a utility method for generating
 * all possible permutations of a stack of numeric elements, where each permutation
 * toggles the sign of numbers to create positive and negative variations.
 * The permutations are generated using a binary representation approach.
 */
public class NegativeNumberPermutator {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(NegativeNumberPermutator.class));

    /**
     * With n numbers we have 2^n different permutations of negative numbers
     *
     * @param stack the stack of numbers
     * @return a list of stacks with all different permutations of the negative and positive numbers of the original stack of numbers
     */
    public static List<Stack<Number>> createPermutationsOfNegativeNumbers(Stack<Number> stack) {
        LOGGER.debug("Creating permutations of negative numbers for stack {}", stack);
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
                    newStack.push(Number.of(number.value()));
                } else {
                    newStack.push(Number.of(-1 * number.value()));
                }
            }
            digitStacks.add(newStack);
        }
        LOGGER.debug("{} permutations of negative numbers created", digitStacks.size());
        return digitStacks;
    }
}
