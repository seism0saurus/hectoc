package de.seism0saurus.hectoc.bruteforcer.logic;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The NumberBlockPermutator class provides functionality to generate
 * permutations of numbers grouped into blocks based on binary patterns.
 * It processes a stack of numbers and creates combinations where adjacent
 * numbers may be grouped together or kept as separate blocks.
 */
public class NumberBlockPermutator {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(NumberBlockPermutator.class));

    /**
     * Generates all possible permutations of blocks of numbers from the given stack of numbers.
     * Each permutation represents a grouping where adjacent numbers may be combined into a single block
     * or kept as separate blocks based on binary patterns.
     *
     * @param stack The stack of numbers to generate block permutations for.
     *              Each number in the stack is represented as an instance of the Number class.
     * @return A list of stacks, where each stack represents one permutation of blocks of numbers.
     *         Each stack contains combinations of numbers grouped into blocks.
     */
    public static List<Stack<Number>> createPermutationsOfBlocksOfNumbers(Stack<Number> stack) {
        LOGGER.debug("Creating blocks of numbers for " + stack);
        final List<Stack<Number>> digitStacks = new ArrayList<>();
        int size = stack.size();
        if (size == 0){
            return digitStacks;
        }
        for (int numberOfPermutations = 0; numberOfPermutations < Math.pow(2, size - 1); numberOfPermutations++) {
            String binaryString = Integer.toBinaryString(numberOfPermutations);
            Stack<Number> newStack = new Stack<>();

            Number number = stack.elementAt(0);
            newStack.push(Number.of(number.value()));

            for (int nthElement = 0; nthElement < size - 1; nthElement++) {
                String stringRepresentation = String.format("%1$" + (size-1) + "s", binaryString).replace(' ', '0');
                number = stack.elementAt(nthElement + 1);
                if (stringRepresentation.charAt(nthElement) == '0') {
                    newStack.push(Number.of(number.value()));
                } else {
                    Number oldNumber = newStack.pop();
                    Number newNumber = Number.of(oldNumber.value() * 10 + number.value());
                    newStack.push(newNumber);
                }
            }
            digitStacks.add(newStack);
        }
        LOGGER.debug(digitStacks.size() + " blocks created.");
        return digitStacks;
    }
}
