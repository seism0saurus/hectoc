package de.seism0saurus.hectoc.bruteforcer.logic;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Operator;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The OperatorPermutator class is responsible for creating permutations of valid operators
 * for a given number of operator positions. It is used in the context of generating
 * Reverse Polish Notation (RPN) stacks that adhere to mathematical rules.
 *
 * This class provides methods to calculate all possible permutations of operators
 * based on the constraints of operator count and stack structure. It works with
 * predefined valid operators and leverages the {@code Stack} and {@code List} data structures
 * for computation and result management.
 */
public class OperatorPermutator {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(OperatorPermutator.class));

    public static final List<Operator> validOperators = List.of(Operator.PLUS, Operator.MULTIPLICATION, Operator.DIVISION, Operator.POWER);

    /**
     * Generates all possible permutations of operators for a given number of operator positions
     * while adhering to specified constraints. This method is used internally to create operator
     * permutations required for Reverse Polish Notation (RPN) stack generation.
     *
     * @param operatorsLeft The number of operator positions left to create permutations for.
     * @return A list of stacks where each stack contains a unique permutation of operators.
     */
    public static List<Stack<StackElement>> createPermutationOfOperators(long operatorsLeft) {
        Stack<Number> emptyStack = new Stack<>();
        return createPermutationOfOperators(emptyStack, operatorsLeft);
    }

    /**
     * Creates all possible permutations of operators for a given number of operator positions,
     * using a stack to maintain the state during recursive computation.
     *
     * @param stack The stack that maintains the intermediate state of operators and numbers.
     *              Each element of the stack can be a Number or an Operator.
     * @param operatorsLeft The number of operator positions left to create permutations for.
     *                       This determines the depth of the recursion.
     * @return A list of stacks, where each stack contains one unique permutation of operators.
     *         The resulting stacks represent all possible configurations of operators with
     *         the specified constraints.
     */
    private static List<Stack<StackElement>> createPermutationOfOperators(Stack<Number> stack, long operatorsLeft) {
        final List<Stack<StackElement>> stacks = new ArrayList<>();

        if (operatorsLeft < 1) {
            Stack<StackElement> stackCopy = (Stack<StackElement>) stack.clone();
            stacks.add(stackCopy);
        } else if (operatorsLeft == 1) {
            validOperators.forEach(
                    operator -> {
                        Stack<StackElement> s = new Stack<>();
                        if (Operator.NULLOPERATOR != operator) {
                            s.push(operator);
                        }
                        stacks.add(s);
                    }
            );
        } else {
            validOperators.forEach(
                    operator -> {
                        Stack<Number> stackCopy = (Stack<Number>) stack.clone();
                        createPermutationOfOperators(stackCopy, operatorsLeft - 1).forEach(
                                s -> {
                                    if (Operator.NULLOPERATOR != operator) {
                                        s.push(operator);
                                    }
                                    stacks.add(s);
                                }
                        );
                    }
            );
        }
        return stacks;
    }
}
