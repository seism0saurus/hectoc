package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.bruteforcer.logic.NegativeNumberPermutator;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
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
    public void createPermutationsOfNegativeNumbers_1ElementStack_Returns_ListWith2NumberStacks() {
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
    public void createPermutationsOfNegativeNumbers_2ElementStack_Returns_ListWith4Stacks() {
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

    @Test
    public void createPermutationsOfNegativeNumbers_4ElementStack_Returns_ListWith8Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(3));
        stack.push(Number.of(44));

        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(3));
        stack1.push(Number.of(44));
        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(-1));
        stack2.push(Number.of(3));
        stack2.push(Number.of(44));
        Stack<StackElement> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(-3));
        stack3.push(Number.of(44));
        Stack<StackElement> stack4 = new Stack<>();
        stack4.push(Number.of(1));
        stack4.push(Number.of(3));
        stack4.push(Number.of(-44));
        Stack<StackElement> stack5 = new Stack<>();
        stack5.push(Number.of(-1));
        stack5.push(Number.of(-3));
        stack5.push(Number.of(44));
        Stack<StackElement> stack6 = new Stack<>();
        stack6.push(Number.of(-1));
        stack6.push(Number.of(3));
        stack6.push(Number.of(-44));
        Stack<StackElement> stack7 = new Stack<>();
        stack7.push(Number.of(1));
        stack7.push(Number.of(-3));
        stack7.push(Number.of(-44));
        Stack<StackElement> stack8 = new Stack<>();
        stack8.push(Number.of(-1));
        stack8.push(Number.of(-3));
        stack8.push(Number.of(-44));
        List<Stack<StackElement>> expected = List.of(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8);

        List<Stack<Number>> actual = NegativeNumberPermutator.createPermutationsOfNegativeNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfNegativeNumbers_5ElementStack_Returns_ListWith16Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(3));
        stack.push(Number.of(44));
        stack.push(Number.of(5));

        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(3));
        stack1.push(Number.of(44));
        stack1.push(Number.of(5));
        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(-1));
        stack2.push(Number.of(3));
        stack2.push(Number.of(44));
        stack2.push(Number.of(5));
        Stack<StackElement> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(-3));
        stack3.push(Number.of(44));
        stack3.push(Number.of(5));
        Stack<StackElement> stack4 = new Stack<>();
        stack4.push(Number.of(1));
        stack4.push(Number.of(3));
        stack4.push(Number.of(-44));
        stack4.push(Number.of(5));
        Stack<StackElement> stack5 = new Stack<>();
        stack5.push(Number.of(1));
        stack5.push(Number.of(3));
        stack5.push(Number.of(44));
        stack5.push(Number.of(-5));
        Stack<StackElement> stack6 = new Stack<>();
        stack6.push(Number.of(-1));
        stack6.push(Number.of(-3));
        stack6.push(Number.of(44));
        stack6.push(Number.of(5));
        Stack<StackElement> stack7 = new Stack<>();
        stack7.push(Number.of(-1));
        stack7.push(Number.of(3));
        stack7.push(Number.of(-44));
        stack7.push(Number.of(5));
        Stack<StackElement> stack8 = new Stack<>();
        stack8.push(Number.of(-1));
        stack8.push(Number.of(3));
        stack8.push(Number.of(44));
        stack8.push(Number.of(-5));
        Stack<StackElement> stack9 = new Stack<>();
        stack9.push(Number.of(1));
        stack9.push(Number.of(-3));
        stack9.push(Number.of(-44));
        stack9.push(Number.of(5));
        Stack<StackElement> stack10 = new Stack<>();
        stack10.push(Number.of(1));
        stack10.push(Number.of(-3));
        stack10.push(Number.of(44));
        stack10.push(Number.of(-5));
        Stack<StackElement> stack11 = new Stack<>();
        stack11.push(Number.of(1));
        stack11.push(Number.of(3));
        stack11.push(Number.of(-44));
        stack11.push(Number.of(-5));
        Stack<StackElement> stack12 = new Stack<>();
        stack12.push(Number.of(-1));
        stack12.push(Number.of(-3));
        stack12.push(Number.of(-44));
        stack12.push(Number.of(5));
        Stack<StackElement> stack13 = new Stack<>();
        stack13.push(Number.of(-1));
        stack13.push(Number.of(-3));
        stack13.push(Number.of(44));
        stack13.push(Number.of(-5));
        Stack<StackElement> stack14 = new Stack<>();
        stack14.push(Number.of(-1));
        stack14.push(Number.of(3));
        stack14.push(Number.of(-44));
        stack14.push(Number.of(-5));
        Stack<StackElement> stack15 = new Stack<>();
        stack15.push(Number.of(1));
        stack15.push(Number.of(-3));
        stack15.push(Number.of(-44));
        stack15.push(Number.of(-5));
        Stack<StackElement> stack16 = new Stack<>();
        stack16.push(Number.of(-1));
        stack16.push(Number.of(-3));
        stack16.push(Number.of(-44));
        stack16.push(Number.of(-5));
        List<Stack<StackElement>> expected = List.of(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8,
                stack9, stack10, stack11, stack12, stack13, stack14, stack15, stack16);

        List<Stack<Number>> actual = NegativeNumberPermutator.createPermutationsOfNegativeNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfNegativeNumbers_6ElementStack_Returns_ListWith32Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(3));
        stack.push(Number.of(44));
        stack.push(Number.of(5));
        stack.push(Number.of(6));

        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(3));
        stack1.push(Number.of(44));
        stack1.push(Number.of(5));
        stack1.push(Number.of(6));
        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(-1));
        stack2.push(Number.of(3));
        stack2.push(Number.of(44));
        stack2.push(Number.of(5));
        stack2.push(Number.of(6));
        Stack<StackElement> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(-3));
        stack3.push(Number.of(44));
        stack3.push(Number.of(5));
        stack3.push(Number.of(6));
        Stack<StackElement> stack4 = new Stack<>();
        stack4.push(Number.of(1));
        stack4.push(Number.of(3));
        stack4.push(Number.of(-44));
        stack4.push(Number.of(5));
        stack4.push(Number.of(6));
        Stack<StackElement> stack5 = new Stack<>();
        stack5.push(Number.of(1));
        stack5.push(Number.of(3));
        stack5.push(Number.of(44));
        stack5.push(Number.of(-5));
        stack5.push(Number.of(6));
        Stack<StackElement> stack6 = new Stack<>();
        stack6.push(Number.of(1));
        stack6.push(Number.of(3));
        stack6.push(Number.of(44));
        stack6.push(Number.of(5));
        stack6.push(Number.of(-6));
        Stack<StackElement> stack7 = new Stack<>();
        stack7.push(Number.of(-1));
        stack7.push(Number.of(-3));
        stack7.push(Number.of(44));
        stack7.push(Number.of(5));
        stack7.push(Number.of(6));
        Stack<StackElement> stack8 = new Stack<>();
        stack8.push(Number.of(-1));
        stack8.push(Number.of(3));
        stack8.push(Number.of(-44));
        stack8.push(Number.of(5));
        stack8.push(Number.of(6));
        Stack<StackElement> stack9 = new Stack<>();
        stack9.push(Number.of(-1));
        stack9.push(Number.of(3));
        stack9.push(Number.of(44));
        stack9.push(Number.of(-5));
        stack9.push(Number.of(6));
        Stack<StackElement> stack10 = new Stack<>();
        stack10.push(Number.of(-1));
        stack10.push(Number.of(3));
        stack10.push(Number.of(44));
        stack10.push(Number.of(5));
        stack10.push(Number.of(-6));
        Stack<StackElement> stack11 = new Stack<>();
        stack11.push(Number.of(1));
        stack11.push(Number.of(-3));
        stack11.push(Number.of(-44));
        stack11.push(Number.of(5));
        stack11.push(Number.of(6));
        Stack<StackElement> stack12 = new Stack<>();
        stack12.push(Number.of(1));
        stack12.push(Number.of(-3));
        stack12.push(Number.of(44));
        stack12.push(Number.of(-5));
        stack12.push(Number.of(6));
        Stack<StackElement> stack13 = new Stack<>();
        stack13.push(Number.of(1));
        stack13.push(Number.of(-3));
        stack13.push(Number.of(44));
        stack13.push(Number.of(5));
        stack13.push(Number.of(-6));
        Stack<StackElement> stack14 = new Stack<>();
        stack14.push(Number.of(1));
        stack14.push(Number.of(3));
        stack14.push(Number.of(-44));
        stack14.push(Number.of(-5));
        stack14.push(Number.of(6));
        Stack<StackElement> stack15 = new Stack<>();
        stack15.push(Number.of(1));
        stack15.push(Number.of(3));
        stack15.push(Number.of(-44));
        stack15.push(Number.of(5));
        stack15.push(Number.of(-6));
        Stack<StackElement> stack16 = new Stack<>();
        stack16.push(Number.of(1));
        stack16.push(Number.of(3));
        stack16.push(Number.of(44));
        stack16.push(Number.of(-5));
        stack16.push(Number.of(-6));
        Stack<StackElement> stack17 = new Stack<>();
        stack17.push(Number.of(-1));
        stack17.push(Number.of(-3));
        stack17.push(Number.of(-44));
        stack17.push(Number.of(5));
        stack17.push(Number.of(6));
        Stack<StackElement> stack18 = new Stack<>();
        stack18.push(Number.of(-1));
        stack18.push(Number.of(-3));
        stack18.push(Number.of(44));
        stack18.push(Number.of(-5));
        stack18.push(Number.of(6));
        Stack<StackElement> stack19 = new Stack<>();
        stack19.push(Number.of(-1));
        stack19.push(Number.of(-3));
        stack19.push(Number.of(44));
        stack19.push(Number.of(5));
        stack19.push(Number.of(-6));
        Stack<StackElement> stack20 = new Stack<>();
        stack20.push(Number.of(-1));
        stack20.push(Number.of(3));
        stack20.push(Number.of(-44));
        stack20.push(Number.of(-5));
        stack20.push(Number.of(6));
        Stack<StackElement> stack21 = new Stack<>();
        stack21.push(Number.of(-1));
        stack21.push(Number.of(3));
        stack21.push(Number.of(-44));
        stack21.push(Number.of(5));
        stack21.push(Number.of(-6));
        Stack<StackElement> stack22 = new Stack<>();
        stack22.push(Number.of(-1));
        stack22.push(Number.of(3));
        stack22.push(Number.of(44));
        stack22.push(Number.of(-5));
        stack22.push(Number.of(-6));
        Stack<StackElement> stack23 = new Stack<>();
        stack23.push(Number.of(1));
        stack23.push(Number.of(-3));
        stack23.push(Number.of(-44));
        stack23.push(Number.of(-5));
        stack23.push(Number.of(6));
        Stack<StackElement> stack24 = new Stack<>();
        stack24.push(Number.of(1));
        stack24.push(Number.of(-3));
        stack24.push(Number.of(-44));
        stack24.push(Number.of(5));
        stack24.push(Number.of(-6));
        Stack<StackElement> stack25 = new Stack<>();
        stack25.push(Number.of(1));
        stack25.push(Number.of(-3));
        stack25.push(Number.of(44));
        stack25.push(Number.of(-5));
        stack25.push(Number.of(-6));
        Stack<StackElement> stack26 = new Stack<>();
        stack26.push(Number.of(1));
        stack26.push(Number.of(3));
        stack26.push(Number.of(-44));
        stack26.push(Number.of(-5));
        stack26.push(Number.of(-6));
        Stack<StackElement> stack27 = new Stack<>();
        stack27.push(Number.of(-1));
        stack27.push(Number.of(-3));
        stack27.push(Number.of(-44));
        stack27.push(Number.of(-5));
        stack27.push(Number.of(6));
        Stack<StackElement> stack28 = new Stack<>();
        stack28.push(Number.of(-1));
        stack28.push(Number.of(-3));
        stack28.push(Number.of(-44));
        stack28.push(Number.of(5));
        stack28.push(Number.of(-6));
        Stack<StackElement> stack29 = new Stack<>();
        stack29.push(Number.of(-1));
        stack29.push(Number.of(-3));
        stack29.push(Number.of(44));
        stack29.push(Number.of(-5));
        stack29.push(Number.of(-6));
        Stack<StackElement> stack30 = new Stack<>();
        stack30.push(Number.of(-1));
        stack30.push(Number.of(3));
        stack30.push(Number.of(-44));
        stack30.push(Number.of(-5));
        stack30.push(Number.of(-6));
        Stack<StackElement> stack31 = new Stack<>();
        stack31.push(Number.of(1));
        stack31.push(Number.of(-3));
        stack31.push(Number.of(-44));
        stack31.push(Number.of(-5));
        stack31.push(Number.of(-6));
        Stack<StackElement> stack32 = new Stack<>();
        stack32.push(Number.of(-1));
        stack32.push(Number.of(-3));
        stack32.push(Number.of(-44));
        stack32.push(Number.of(-5));
        stack32.push(Number.of(-6));
        List<Stack<StackElement>> expected = List.of(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8,
                stack9, stack10, stack11, stack12, stack13, stack14, stack15, stack16,
                stack17, stack18, stack19, stack20, stack21, stack22, stack23, stack24,
                stack25, stack26, stack27, stack28, stack29, stack30, stack31, stack32);

        List<Stack<Number>> actual = NegativeNumberPermutator.createPermutationsOfNegativeNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }
}
