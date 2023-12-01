package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberBlockPermutatorTest {

    @Test
    public void createPermutationsOfBlocksOfNumbers_EmptyStack_Returns_ListWithEmptyStack() {
        Stack<Number> stack = new Stack<>();

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        assertEquals(0, actual.size());
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_1ElementStack_Returns_ListWith1ElementStack() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));

        Stack<Number> stack1 = (Stack<Number>) stack.clone();
        List<Stack<Number>> expected = List.of(stack1);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_2ElementStack_Returns_ListWith2Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(4));

        Stack<Number> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(4));
        Stack<Number> stack2 = new Stack<>();
        stack2.push(Number.of(14));
        List<Stack<Number>> expected = List.of(stack1, stack2);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_3ElementStack_Returns_ListWith4Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(4));
        stack.push(Number.of(2));

        Stack<Number> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(4));
        stack1.push(Number.of(2));
        Stack<Number> stack2 = new Stack<>();
        stack2.push(Number.of(14));
        stack2.push(Number.of(2));
        Stack<Number> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(42));
        Stack<Number> stack4 = new Stack<>();
        stack4.push(Number.of(142));
        List<Stack<Number>> expected = List.of(stack1, stack2, stack3, stack4);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_4ElementStack_Returns_ListWith8Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(2));
        stack.push(Number.of(3));
        stack.push(Number.of(4));
        stack.push(Number.of(5));

        Stack<Number> stack1 = new Stack<>();
        stack1.push(Number.of(2));
        stack1.push(Number.of(3));
        stack1.push(Number.of(4));
        stack1.push(Number.of(5));
        Stack<Number> stack2 = new Stack<>();
        stack2.push(Number.of(23));
        stack2.push(Number.of(4));
        stack2.push(Number.of(5));
        Stack<Number> stack3 = new Stack<>();
        stack3.push(Number.of(2));
        stack3.push(Number.of(34));
        stack3.push(Number.of(5));
        Stack<Number> stack4 = new Stack<>();
        stack4.push(Number.of(2));
        stack4.push(Number.of(3));
        stack4.push(Number.of(45));
        Stack<Number> stack5 = new Stack<>();
        stack5.push(Number.of(23));
        stack5.push(Number.of(45));
        Stack<Number> stack6 = new Stack<>();
        stack6.push(Number.of(234));
        stack6.push(Number.of(5));
        Stack<Number> stack7 = new Stack<>();
        stack7.push(Number.of(2));
        stack7.push(Number.of(345));
        Stack<Number> stack8 = new Stack<>();
        stack8.push(Number.of(2345));
        List<Stack<Number>> expected = List.of(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfBlocksOfNumbers_5ElementStack_Returns_ListWith16Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(2));
        stack.push(Number.of(3));
        stack.push(Number.of(4));
        stack.push(Number.of(5));

        Stack<Number> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(2));
        stack1.push(Number.of(3));
        stack1.push(Number.of(4));
        stack1.push(Number.of(5));
        Stack<Number> stack2 = new Stack<>();
        stack2.push(Number.of(12));
        stack2.push(Number.of(3));
        stack2.push(Number.of(4));
        stack2.push(Number.of(5));
        Stack<Number> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(23));
        stack3.push(Number.of(4));
        stack3.push(Number.of(5));
        Stack<Number> stack4 = new Stack<>();
        stack4.push(Number.of(1));
        stack4.push(Number.of(2));
        stack4.push(Number.of(34));
        stack4.push(Number.of(5));
        Stack<Number> stack5 = new Stack<>();
        stack5.push(Number.of(1));
        stack5.push(Number.of(2));
        stack5.push(Number.of(3));
        stack5.push(Number.of(45));
        Stack<Number> stack6 = new Stack<>();
        stack6.push(Number.of(12));
        stack6.push(Number.of(34));
        stack6.push(Number.of(5));
        Stack<Number> stack7 = new Stack<>();
        stack7.push(Number.of(1));
        stack7.push(Number.of(23));
        stack7.push(Number.of(45));
        Stack<Number> stack8 = new Stack<>();
        stack8.push(Number.of(12));
        stack8.push(Number.of(3));
        stack8.push(Number.of(45));
        Stack<Number> stack9 = new Stack<>();
        stack9.push(Number.of(123));
        stack9.push(Number.of(4));
        stack9.push(Number.of(5));
        Stack<Number> stack10 = new Stack<>();
        stack10.push(Number.of(1));
        stack10.push(Number.of(234));
        stack10.push(Number.of(5));
        Stack<Number> stack11 = new Stack<>();
        stack11.push(Number.of(1));
        stack11.push(Number.of(2));
        stack11.push(Number.of(345));
        Stack<Number> stack12 = new Stack<>();
        stack12.push(Number.of(123));
        stack12.push(Number.of(45));
        Stack<Number> stack13 = new Stack<>();
        stack13.push(Number.of(12));
        stack13.push(Number.of(345));
        Stack<Number> stack14 = new Stack<>();
        stack14.push(Number.of(1));
        stack14.push(Number.of(2345));
        Stack<Number> stack15 = new Stack<>();
        stack15.push(Number.of(1234));
        stack15.push(Number.of(5));
        Stack<Number> stack16 = new Stack<>();
        stack16.push(Number.of(12345));
        List<Stack<Number>> expected = List.of(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8,
                stack9, stack10, stack11, stack12, stack13, stack14, stack15, stack16);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }


    @Test
    public void createPermutationsOfBlocksOfNumbers_6ElementStack_Returns_ListWith32Stacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        stack.push(Number.of(2));
        stack.push(Number.of(3));
        stack.push(Number.of(4));
        stack.push(Number.of(5));
        stack.push(Number.of(6));

        Stack<Number> stack1 = new Stack<>();
        stack1.push(Number.of(1));
        stack1.push(Number.of(2));
        stack1.push(Number.of(3));
        stack1.push(Number.of(4));
        stack1.push(Number.of(5));
        stack1.push(Number.of(6));
        Stack<Number> stack2 = new Stack<>();
        stack2.push(Number.of(12));
        stack2.push(Number.of(3));
        stack2.push(Number.of(4));
        stack2.push(Number.of(5));
        stack2.push(Number.of(6));
        Stack<Number> stack3 = new Stack<>();
        stack3.push(Number.of(1));
        stack3.push(Number.of(23));
        stack3.push(Number.of(4));
        stack3.push(Number.of(5));
        stack3.push(Number.of(6));
        Stack<Number> stack4 = new Stack<>();
        stack4.push(Number.of(1));
        stack4.push(Number.of(2));
        stack4.push(Number.of(34));
        stack4.push(Number.of(5));
        stack4.push(Number.of(6));
        Stack<Number> stack5 = new Stack<>();
        stack5.push(Number.of(1));
        stack5.push(Number.of(2));
        stack5.push(Number.of(3));
        stack5.push(Number.of(45));
        stack5.push(Number.of(6));
        Stack<Number> stack6 = new Stack<>();
        stack6.push(Number.of(1));
        stack6.push(Number.of(2));
        stack6.push(Number.of(3));
        stack6.push(Number.of(4));
        stack6.push(Number.of(56));
        Stack<Number> stack7 = new Stack<>();
        stack7.push(Number.of(12));
        stack7.push(Number.of(34));
        stack7.push(Number.of(5));
        stack7.push(Number.of(6));
        Stack<Number> stack8 = new Stack<>();
        stack8.push(Number.of(12));
        stack8.push(Number.of(3));
        stack8.push(Number.of(45));
        stack8.push(Number.of(6));
        Stack<Number> stack9 = new Stack<>();
        stack9.push(Number.of(12));
        stack9.push(Number.of(3));
        stack9.push(Number.of(4));
        stack9.push(Number.of(56));
        Stack<Number> stack10 = new Stack<>();
        stack10.push(Number.of(1));
        stack10.push(Number.of(23));
        stack10.push(Number.of(45));
        stack10.push(Number.of(6));
        Stack<Number> stack11 = new Stack<>();
        stack11.push(Number.of(1));
        stack11.push(Number.of(23));
        stack11.push(Number.of(4));
        stack11.push(Number.of(56));
        Stack<Number> stack12 = new Stack<>();
        stack12.push(Number.of(1));
        stack12.push(Number.of(2));
        stack12.push(Number.of(34));
        stack12.push(Number.of(56));
        Stack<Number> stack13 = new Stack<>();
        stack13.push(Number.of(12));
        stack13.push(Number.of(34));
        stack13.push(Number.of(56));
        Stack<Number> stack14 = new Stack<>();
        stack14.push(Number.of(123));
        stack14.push(Number.of(4));
        stack14.push(Number.of(5));
        stack14.push(Number.of(6));
        Stack<Number> stack15 = new Stack<>();
        stack15.push(Number.of(1));
        stack15.push(Number.of(234));
        stack15.push(Number.of(5));
        stack15.push(Number.of(6));
        Stack<Number> stack16 = new Stack<>();
        stack16.push(Number.of(1));
        stack16.push(Number.of(2));
        stack16.push(Number.of(345));
        stack16.push(Number.of(6));
        Stack<Number> stack17 = new Stack<>();
        stack17.push(Number.of(1));
        stack17.push(Number.of(2));
        stack17.push(Number.of(3));
        stack17.push(Number.of(456));
        Stack<Number> stack18 = new Stack<>();
        stack18.push(Number.of(123));
        stack18.push(Number.of(45));
        stack18.push(Number.of(6));
        Stack<Number> stack19 = new Stack<>();
        stack19.push(Number.of(123));
        stack19.push(Number.of(4));
        stack19.push(Number.of(56));
        Stack<Number> stack30 = new Stack<>();
        stack30.push(Number.of(1));
        stack30.push(Number.of(234));
        stack30.push(Number.of(56));
        Stack<Number> stack32 = new Stack<>();
        stack32.push(Number.of(12));
        stack32.push(Number.of(345));
        stack32.push(Number.of(6));
        Stack<Number> stack29 = new Stack<>();
        stack29.push(Number.of(1));
        stack29.push(Number.of(23));
        stack29.push(Number.of(456));
        Stack<Number> stack31 = new Stack<>();
        stack31.push(Number.of(12));
        stack31.push(Number.of(3));
        stack31.push(Number.of(456));
        Stack<Number> stack20 = new Stack<>();
        stack20.push(Number.of(123));
        stack20.push(Number.of(456));
        Stack<Number> stack21 = new Stack<>();
        stack21.push(Number.of(1234));
        stack21.push(Number.of(5));
        stack21.push(Number.of(6));
        Stack<Number> stack22 = new Stack<>();
        stack22.push(Number.of(1));
        stack22.push(Number.of(2345));
        stack22.push(Number.of(6));
        Stack<Number> stack23 = new Stack<>();
        stack23.push(Number.of(1));
        stack23.push(Number.of(2));
        stack23.push(Number.of(3456));
        Stack<Number> stack24 = new Stack<>();
        stack24.push(Number.of(1234));
        stack24.push(Number.of(56));
        Stack<Number> stack25 = new Stack<>();
        stack25.push(Number.of(12));
        stack25.push(Number.of(3456));
        Stack<Number> stack26 = new Stack<>();
        stack26.push(Number.of(12345));
        stack26.push(Number.of(6));
        Stack<Number> stack27 = new Stack<>();
        stack27.push(Number.of(1));
        stack27.push(Number.of(23456));
        Stack<Number> stack28 = new Stack<>();
        stack28.push(Number.of(123456));
        List<Stack<Number>> expected = List.of(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8,
                stack9, stack10, stack11, stack12, stack13, stack14, stack15, stack16, stack17, stack18,
                stack19, stack20, stack21, stack22, stack23, stack24, stack25, stack26, stack27, stack28,
                stack29, stack30, stack31, stack32);

        List<Stack<Number>> actual = NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }
}
