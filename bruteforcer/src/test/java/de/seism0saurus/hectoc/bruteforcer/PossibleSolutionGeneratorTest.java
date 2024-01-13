package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Operator;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class PossibleSolutionGeneratorTest {

    @Test
    public void createPermutationsOfOperands_NullAsStack_ThrowsError() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PossibleSolutionGenerator.createRpnStacks(null)
        );
    }

    @Test
    public void createPermutationsOfOperands_ZeroNumberStack_ReturnsSameStack() {
        Stack<Number> stack = new Stack<>();
        Stack<StackElement> stack1 = (Stack<StackElement>) stack.clone();
        List<Stack<StackElement>> expected = List.of(stack1);

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfOperands_OneNumberStack_ReturnsSameStack() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(1));
        Stack<StackElement> stack1 = (Stack<StackElement>) stack.clone();
        List<Stack<StackElement>> expected = List.of(stack1);

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfOperands_OneNegativeNumberStack_ReturnsSameStack() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(-1));
        Stack<StackElement> stack1 = (Stack<StackElement>) stack.clone();
        List<Stack<StackElement>> expected = List.of(stack1);

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfOperands_TwoNumberStack_ReturnsOneStackPerValidOperator() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(3));
        stack.push(Number.of(7));

        Stack<StackElement> stack1 = (Stack<StackElement>) stack.clone();
        stack1.push(Operator.PLUS);
        Stack<StackElement> stack2 = (Stack<StackElement>) stack.clone();
        stack2.push(Operator.MULTIPLICATION);
        Stack<StackElement> stack3 = (Stack<StackElement>) stack.clone();
        stack3.push(Operator.DIVISION);
        Stack<StackElement> stack4 = (Stack<StackElement>) stack.clone();
        stack4.push(Operator.POWER);
        List<Stack<StackElement>> expected = List.of(stack1, stack2, stack3, stack4);

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }


    @Test
    public void createPermutationsOfOperands_ThreNumberStack_ReturnsOneStackPerValidOperatorCombination() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(3));
        stack.push(Number.of(7));
        stack.push(Number.of(1));

        Stack<StackElement> stack1 = new Stack<>();
        stack1.push(Number.of(3));
        stack1.push(Number.of(7));
        stack1.push(Number.of(1));
        stack1.push(Operator.PLUS);
        stack1.push(Operator.PLUS);

        Stack<StackElement> stack2 = new Stack<>();
        stack2.push(Number.of(3));
        stack2.push(Number.of(7));
        stack2.push(Number.of(1));
        stack2.push(Operator.PLUS);
        stack2.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack3 = new Stack<>();
        stack3.push(Number.of(3));
        stack3.push(Number.of(7));
        stack3.push(Number.of(1));
        stack3.push(Operator.PLUS);
        stack3.push(Operator.DIVISION);

        Stack<StackElement> stack4 = new Stack<>();
        stack4.push(Number.of(3));
        stack4.push(Number.of(7));
        stack4.push(Number.of(1));
        stack4.push(Operator.PLUS);
        stack4.push(Operator.POWER);

        Stack<StackElement> stack5 = new Stack<>();
        stack5.push(Number.of(3));
        stack5.push(Number.of(7));
        stack5.push(Number.of(1));
        stack5.push(Operator.MULTIPLICATION);
        stack5.push(Operator.PLUS);

        Stack<StackElement> stack6 = new Stack<>();
        stack6.push(Number.of(3));
        stack6.push(Number.of(7));
        stack6.push(Number.of(1));
        stack6.push(Operator.MULTIPLICATION);
        stack6.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack7 = new Stack<>();
        stack7.push(Number.of(3));
        stack7.push(Number.of(7));
        stack7.push(Number.of(1));
        stack7.push(Operator.MULTIPLICATION);
        stack7.push(Operator.DIVISION);

        Stack<StackElement> stack8 = new Stack<>();
        stack8.push(Number.of(3));
        stack8.push(Number.of(7));
        stack8.push(Number.of(1));
        stack8.push(Operator.MULTIPLICATION);
        stack8.push(Operator.POWER);

        Stack<StackElement> stack9 = new Stack<>();
        stack9.push(Number.of(3));
        stack9.push(Number.of(7));
        stack9.push(Number.of(1));
        stack9.push(Operator.DIVISION);
        stack9.push(Operator.PLUS);

        Stack<StackElement> stack10 = new Stack<>();
        stack10.push(Number.of(3));
        stack10.push(Number.of(7));
        stack10.push(Number.of(1));
        stack10.push(Operator.DIVISION);
        stack10.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack11 = new Stack<>();
        stack11.push(Number.of(3));
        stack11.push(Number.of(7));
        stack11.push(Number.of(1));
        stack11.push(Operator.DIVISION);
        stack11.push(Operator.DIVISION);

        Stack<StackElement> stack12 = new Stack<>();
        stack12.push(Number.of(3));
        stack12.push(Number.of(7));
        stack12.push(Number.of(1));
        stack12.push(Operator.DIVISION);
        stack12.push(Operator.POWER);

        Stack<StackElement> stack13 = new Stack<>();
        stack13.push(Number.of(3));
        stack13.push(Number.of(7));
        stack13.push(Number.of(1));
        stack13.push(Operator.POWER);
        stack13.push(Operator.PLUS);

        Stack<StackElement> stack14 = new Stack<>();
        stack14.push(Number.of(3));
        stack14.push(Number.of(7));
        stack14.push(Number.of(1));
        stack14.push(Operator.POWER);
        stack14.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack15 = new Stack<>();
        stack15.push(Number.of(3));
        stack15.push(Number.of(7));
        stack15.push(Number.of(1));
        stack15.push(Operator.POWER);
        stack15.push(Operator.DIVISION);

        Stack<StackElement> stack16 = new Stack<>();
        stack16.push(Number.of(3));
        stack16.push(Number.of(7));
        stack16.push(Number.of(1));
        stack16.push(Operator.POWER);
        stack16.push(Operator.POWER);

        Stack<StackElement> stack17 = new Stack<>();
        stack17.push(Number.of(3));
        stack17.push(Number.of(7));
        stack17.push(Operator.PLUS);
        stack17.push(Number.of(1));
        stack17.push(Operator.PLUS);

        Stack<StackElement> stack18 = new Stack<>();
        stack18.push(Number.of(3));
        stack18.push(Number.of(7));
        stack18.push(Operator.PLUS);
        stack18.push(Number.of(1));
        stack18.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack19 = new Stack<>();
        stack19.push(Number.of(3));
        stack19.push(Number.of(7));
        stack19.push(Operator.PLUS);
        stack19.push(Number.of(1));
        stack19.push(Operator.DIVISION);

        Stack<StackElement> stack20 = new Stack<>();
        stack20.push(Number.of(3));
        stack20.push(Number.of(7));
        stack20.push(Operator.PLUS);
        stack20.push(Number.of(1));
        stack20.push(Operator.POWER);

        Stack<StackElement> stack21 = new Stack<>();
        stack21.push(Number.of(3));
        stack21.push(Number.of(7));
        stack21.push(Operator.MULTIPLICATION);
        stack21.push(Number.of(1));
        stack21.push(Operator.PLUS);

        Stack<StackElement> stack22 = new Stack<>();
        stack22.push(Number.of(3));
        stack22.push(Number.of(7));
        stack22.push(Operator.MULTIPLICATION);
        stack22.push(Number.of(1));
        stack22.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack23 = new Stack<>();
        stack23.push(Number.of(3));
        stack23.push(Number.of(7));
        stack23.push(Operator.MULTIPLICATION);
        stack23.push(Number.of(1));
        stack23.push(Operator.DIVISION);

        Stack<StackElement> stack24 = new Stack<>();
        stack24.push(Number.of(3));
        stack24.push(Number.of(7));
        stack24.push(Operator.MULTIPLICATION);
        stack24.push(Number.of(1));
        stack24.push(Operator.POWER);

        Stack<StackElement> stack25 = new Stack<>();
        stack25.push(Number.of(3));
        stack25.push(Number.of(7));
        stack25.push(Operator.DIVISION);
        stack25.push(Number.of(1));
        stack25.push(Operator.PLUS);

        Stack<StackElement> stack26 = new Stack<>();
        stack26.push(Number.of(3));
        stack26.push(Number.of(7));
        stack26.push(Operator.DIVISION);
        stack26.push(Number.of(1));
        stack26.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack27 = new Stack<>();
        stack27.push(Number.of(3));
        stack27.push(Number.of(7));
        stack27.push(Operator.DIVISION);
        stack27.push(Number.of(1));
        stack27.push(Operator.DIVISION);

        Stack<StackElement> stack28 = new Stack<>();
        stack28.push(Number.of(3));
        stack28.push(Number.of(7));
        stack28.push(Operator.DIVISION);
        stack28.push(Number.of(1));
        stack28.push(Operator.POWER);

        Stack<StackElement> stack29 = new Stack<>();
        stack29.push(Number.of(3));
        stack29.push(Number.of(7));
        stack29.push(Operator.POWER);
        stack29.push(Number.of(1));
        stack29.push(Operator.PLUS);

        Stack<StackElement> stack30 = new Stack<>();
        stack30.push(Number.of(3));
        stack30.push(Number.of(7));
        stack30.push(Operator.POWER);
        stack30.push(Number.of(1));
        stack30.push(Operator.MULTIPLICATION);

        Stack<StackElement> stack31 = new Stack<>();
        stack31.push(Number.of(3));
        stack31.push(Number.of(7));
        stack31.push(Operator.POWER);
        stack31.push(Number.of(1));
        stack31.push(Operator.DIVISION);

        Stack<StackElement> stack32 = new Stack<>();
        stack32.push(Number.of(3));
        stack32.push(Number.of(7));
        stack32.push(Operator.POWER);
        stack32.push(Number.of(1));
        stack32.push(Operator.POWER);

        List<Stack<StackElement>> expected = List.of(
                stack1, stack2, stack3, stack4,
                stack5, stack6, stack7, stack8,
                stack9, stack10, stack11, stack12,
                stack13, stack14, stack15, stack16,
                stack17, stack18, stack19, stack20,
                stack21, stack22, stack23, stack24,
                stack25, stack26, stack27, stack28,
                stack29, stack30, stack31, stack32
        );

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void createPermutationsOfOperands_FourNumberStack_ReturnsStacksWith7Elements() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(5));
        stack.push(Number.of(-6));
        stack.push(Number.of(2));
        stack.push(Number.of(1));
        // four Numbers and three operators
        final int expected = 7;

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        actual.forEach(s -> assertEquals(expected, s.size()));
    }

    @Test
    public void createPermutationsOfOperands_FourNumberStack_ReturnsWellformedStacks() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(5));
        stack.push(Number.of(-6));
        stack.push(Number.of(2));
        stack.push(Number.of(1));

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        actual.forEach(s -> {
            int numberOfNumbers = 0;
            int numberOfOperators = 0;
            for (int i = 0; i < s.size(); i++) {
                if (s.get(i) instanceof Number) {
                    numberOfNumbers++;
                } else {
                    numberOfOperators++;
                }
                assertTrue(numberOfNumbers > numberOfOperators, "Stack " + s);
            }

        });
    }

    @Test
    public void createPermutationsOfOperands_SixNumberStack_ReturnsStacksWith11Elements() {
        Stack<Number> stack = new Stack<>();
        stack.push(Number.of(-1));
        stack.push(Number.of(9));
        stack.push(Number.of(5));
        stack.push(Number.of(-6));
        stack.push(Number.of(2));
        stack.push(Number.of(1));
        //six Numbers and five operators
        final int expected = 11;

        Set<Stack<StackElement>> actual = PossibleSolutionGenerator.createRpnStacks(stack);

        actual.forEach(s -> assertEquals(expected, s.size()));
    }
}
