package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;

import java.util.*;

public class PossibleSolutionGenerator {

    protected static Set<Stack<StackElement>> createRpnStacks(Stack<Number> stack) {
        System.out.println("Creating RPN stacks for " + stack);

        // Null guarding clause
        if (stack == null) {
            throw new IllegalArgumentException("Stack has to be nun null!");
        }

        Set<Stack<StackElement>> rpnStacks = new HashSet<>();
        final int size = stack.size();

        // Directly return the
        if (size == 0) {
            Stack<StackElement> newStack = new Stack<>();
            rpnStacks.add(newStack);
            System.out.println("Stack " + stack + " was empty, so there is now real rpn. Returning list with empty stack.");
            return rpnStacks;
        } else if (size == 1) {
            Stack<StackElement> newStack = new Stack<>();
            final Number number = stack.elementAt(0);
            newStack.push(number);
            rpnStacks.add(newStack);
            System.out.println("Stack " + stack + " had only one element. So the only valid rpn ist the stack itself without any operators.");
            return rpnStacks;
        }

        int numberOfOperators = size - 1;
        int maxPlacesForOperators = maxPositionsForOperator(size);

        for (int numberOfPermutations = 0; numberOfPermutations < Math.pow(2, maxPlacesForOperators); numberOfPermutations++) {
            String binaryString = Integer.toBinaryString(numberOfPermutations);

            // We don't have to process permutations with an invalid number of operators,
            // since they aren't valid RPN stacks.
            long countNumberOfOnes = countNumberOfOnes(binaryString);
            if (countNumberOfOnes != numberOfOperators) {
                continue;
            }

            String stringRepresentation = String.format("%1$" + maxPlacesForOperators + "s", binaryString).replace(' ', '0');

            List<Stack<StackElement>> tmpStacks = new ArrayList<>();
            for (int nthElement = 0; nthElement < size; nthElement++) {
                final List<Stack<StackElement>> newTmpStacks = new ArrayList<>();
                final int startOperandsIndex = maxPositionsForOperator(nthElement);
                final int endOperandsIndex = startOperandsIndex + nthElement;
                final Number number = stack.elementAt(nthElement);

                if (nthElement == 0) {
                    // If it's the first element at index 0 we don't add any operators,
                    // Since we would only have one operand.
                    // Add a stack with the single number to the list of stacks
                    Stack<StackElement> newStack = new Stack<>();
                    newStack.push(number);
                    newTmpStacks.add(newStack);
                } else {
                    // Iterate through all existing stacks that were created so far
                    tmpStacks.forEach(
                            s -> {
                                // Add the number of the current position to it
                                s.push(number);

                                // Calculate all permutations of the correct number of operands for the current stack
                                CharSequence relevantOperandPositions = stringRepresentation.subSequence(startOperandsIndex, endOperandsIndex);
                                List<Stack<StackElement>> permutationOfOperators = OperatorPermutator.createPermutationOfOperators(countNumberOfOnes(relevantOperandPositions));

                                // Iterate through all permutations of operators
                                // and create a stack with the existing stack and the
                                // stack of operators
                                permutationOfOperators.forEach(
                                        os -> {
                                            Stack<StackElement> newStack = (Stack<StackElement>) s.clone();
                                            newStack.addAll(os);

                                            int numNumbers = 0;
                                            int numOperators = 0;
                                            for (StackElement stackElement : newStack) {
                                                if (stackElement instanceof Number) {
                                                    numNumbers++;
                                                } else {
                                                    numOperators++;
                                                }
                                            }
                                            if (numNumbers > numOperators) {
                                                newTmpStacks.add(newStack);
                                            }
                                        }
                                );
                            }
                    );
                }
                tmpStacks = newTmpStacks;
            }
            rpnStacks.addAll(tmpStacks);
        }

        System.out.println(rpnStacks.size() + " unique RPN stacks created.");
        return rpnStacks;
    }

    private static long countNumberOfOnes(String binaryString) {
        return binaryString.codePoints().mapToObj(c -> (char) c).filter(c -> c.equals('1')).count();
    }

    private static long countNumberOfOnes(CharSequence binaryString) {
        return binaryString.codePoints().mapToObj(c -> (char) c).filter(c -> c.equals('1')).count();
    }

    /**
     * The number of operators is the "Dreieckszahl" of the stackSize - 1.
     * It's calculated with the "Gaußsche Summenformel" for n-1 instead of n: (n-1)(n)/2.
     *
     * @param stackSize The size of the stack
     * @return The number of positions of operators for a stack of size @stackSize
     */
    protected static int maxPositionsForOperator(final int stackSize) {
        return stackSize * (stackSize - 1) / 2;
    }
}
