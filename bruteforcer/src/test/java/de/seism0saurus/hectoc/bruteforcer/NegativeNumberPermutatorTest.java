package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.webapp.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.webapp.shuntingyardalgorithm.StackElement;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Stack;

public class NegativeNumberPermutatorTest {
    @Test
    public void createPermutationsOfNegativeNumbers_EmptyStack_Returns_ListWithEmptyStack() {
        Stack<Number> stack = new Stack<>();
        Stack<Number> stack1 = (Stack<Number>) stack.clone();
        List<Stack<Number>> expected = List.of(stack1);

        List<Stack<Number>> actual = NegativeNumberPermutator.createPermutationsOfNegativeNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfNegativeNumbers_1ElementStack_Returns_ListWithPositiveAndNegativeNumberStacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(-1));
        List<Stack<StackElement>> expected = List.of(stack1, stack2);

        List<Stack<Number>> actual = NegativeNumberPermutator.createPermutationsOfNegativeNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }


    @Test
    public void createPermutationsOfNegativeNumbers_2ElementStack_Returns_ListWithFourStacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(3));
        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(3));
        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(-1));
        stack2.push(Number.of(3));
        Stack<StackElement> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(-3));
        Stack<StackElement> stack4 = new Stack<>();
        stack4.push(Number.of(-1));
        stack4.push(Number.of(-3));
        List<Stack<StackElement>> expected = List.of(stack1, stack2, stack3, stack4);

        List<Stack<Number>> actual = NegativeNumberPermutator.createPermutationsOfNegativeNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }
}
