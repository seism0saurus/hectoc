package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Operator;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OperatorPermutator {
    public static final List<Operator> validOperators = List.of(Operator.PLUS, Operator.MULTIPLICATION, Operator.DIVISION, Operator.POWER);

    protected static List<Stack<StackElement>> createPermutationOfOperators(long operatorsLeft) {
        Stack<Number> emptyStack = new Stack<>();
        return createPermutationOfOperators(emptyStack, operatorsLeft);
    }

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
