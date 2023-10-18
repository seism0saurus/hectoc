package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.webapp.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.webapp.shuntingyardalgorithm.StackElement;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberBlockPermutatorTest {

    @Test
    public void createPermutationsOfBlocksOfNumbers_EmptyStack_Returns_ListWithEmptyStack() {
        Stack<Number> stack = new Stack<>();

        Stack<StackElement> stack1 = (Stack<StackElement>) stack.clone();
        List<Stack<StackElement>> expected = List.of(stack1);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        assertEquals(expected, actual);
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_1ElementStack_Returns_ListWith1ElementStack() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));

        Stack<StackElement> stack1 = (Stack<StackElement>) stack.clone();
        List<Stack<StackElement>> expected = List.of(stack1);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        assertEquals(expected, actual);
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_2ElementStack_Returns_ListWithTwoStacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(4));

        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(4));
        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(14));
        List<Stack<StackElement>> expected = List.of(stack1, stack2);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        assertEquals(expected, actual);
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_3ElementStack_Returns_ListWithFourStacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(4));
        stack.push(Number.of(2));

        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(4));
        stack1.push(Number.of(2));
        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(14));
        stack2.push(Number.of(2));
        Stack<StackElement> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(42));
        Stack<StackElement> stack4 = new Stack<>();
        stack4.push(Number.of(142));
        List<Stack<StackElement>> expected = List.of(stack1, stack2, stack3, stack4);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        assertEquals(expected, actual);
    }
}
