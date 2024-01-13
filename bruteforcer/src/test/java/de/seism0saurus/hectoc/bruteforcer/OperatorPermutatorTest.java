package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperatorPermutatorTest {

    @Test
    public void createPermutationOfOperators_ONE_OneOperatorOfEachKindIsCreated() {
        List<Stack<StackElement>> operators = OperatorPermutator.createPermutationOfOperators(1);

        OperatorPermutator.validOperators.forEach(
                v -> assertTrue(operators.stream().anyMatch(o -> o.getFirst().equals(v)))
        );
    }

    @Test
    public void createPermutationOfOperators_ONE_CorrectNumberIsCreates() {
        int expected = OperatorPermutator.validOperators.size();

        List<Stack<StackElement>> operators = OperatorPermutator.createPermutationOfOperators(1);
        int actual = operators.size();

        assertEquals(expected, actual);
    }


    @Test
    public void createPermutationOfOperators_TWO_EachToupleIsCreated() {
        List<Stack<StackElement>> expected = new ArrayList<>();
        OperatorPermutator.validOperators.forEach(
                o1 -> OperatorPermutator.validOperators.forEach(
                        o2 -> {
                            Stack<StackElement> s = new Stack<>();
                            s.push(o1);
                            s.push(o2);
                            expected.add(s);
                        }
                )
        );

        List<Stack<StackElement>> actual = OperatorPermutator.createPermutationOfOperators(2);

        expected.forEach(
                s1 -> assertTrue(actual.stream().anyMatch(s1::equals))
        );
    }

    @Test
    public void createPermutationOfOperators_TWO_CorrectNumberIsCreates() {
        double expected = Math.pow(OperatorPermutator.validOperators.size(), 2);

        List<Stack<StackElement>> operators = OperatorPermutator.createPermutationOfOperators(2);
        double actual = operators.size();

        assertEquals(expected, actual);
    }

    @Test
    public void createPermutationOfOperators_THREE_EachTripleIsCreated() {
        List<Stack<StackElement>> expected = new ArrayList<>();
        OperatorPermutator.validOperators.forEach(
                o1 -> OperatorPermutator.validOperators.forEach(
                        o2 -> {
                            OperatorPermutator.validOperators.forEach(
                                    o3 -> {
                                        Stack<StackElement> s = new Stack<>();
                                        s.push(o1);
                                        s.push(o2);
                                        s.push(o3);
                                        expected.add(s);
                                    }
                            );
                        }
                )
        );

        List<Stack<StackElement>> actual = OperatorPermutator.createPermutationOfOperators(3);

        expected.forEach(
                s1 -> assertTrue(actual.stream().anyMatch(s1::equals))
        );
    }

    @Test
    public void createPermutationOfOperators_THREE_CorrectNumberIsCreates() {
        double expected = Math.pow(OperatorPermutator.validOperators.size(), 3);

        List<Stack<StackElement>> operators = OperatorPermutator.createPermutationOfOperators(3);
        double actual = operators.size();

        assertEquals(expected, actual);
    }

    @Test
    public void createPermutationOfOperators_FOURTH_EachQuadroupleIsCreated() {
        List<Stack<StackElement>> expected = new ArrayList<>();
        OperatorPermutator.validOperators.forEach(
                o1 -> OperatorPermutator.validOperators.forEach(
                        o2 -> {
                            OperatorPermutator.validOperators.forEach(
                                    o3 -> {
                                        OperatorPermutator.validOperators.forEach(
                                                o4 -> {
                                                    Stack<StackElement> s = new Stack<>();
                                                    s.push(o1);
                                                    s.push(o2);
                                                    s.push(o3);
                                                    s.push(o4);
                                                    expected.add(s);
                                                }
                                        );
                                    }
                            );
                        }
                )
        );

        List<Stack<StackElement>> actual = OperatorPermutator.createPermutationOfOperators(4);

        expected.forEach(
                s1 -> assertTrue(actual.stream().anyMatch(s1::equals))
        );
    }

    @Test
    public void createPermutationOfOperators_FOUR_CorrectNumberIsCreates() {
        double expected = Math.pow(OperatorPermutator.validOperators.size(), 4);

        List<Stack<StackElement>> operators = OperatorPermutator.createPermutationOfOperators(4);
        double actual = operators.size();

        assertEquals(expected, actual);
    }

    @Test
    public void createPermutationOfOperators_FIVE_EachQuintupleIsCreated() {
        List<Stack<StackElement>> expected = new ArrayList<>();
        OperatorPermutator.validOperators.forEach(
                o1 -> OperatorPermutator.validOperators.forEach(
                        o2 -> {
                            OperatorPermutator.validOperators.forEach(
                                    o3 -> {
                                        OperatorPermutator.validOperators.forEach(
                                                o4 -> {
                                                    OperatorPermutator.validOperators.forEach(
                                                            o5 -> {
                                                                Stack<StackElement> s = new Stack<>();
                                                                s.push(o1);
                                                                s.push(o2);
                                                                s.push(o3);
                                                                s.push(o4);
                                                                s.push(o5);
                                                                expected.add(s);
                                                            }
                                                    );
                                                }
                                        );
                                    }
                            );
                        }
                )
        );

        List<Stack<StackElement>> actual = OperatorPermutator.createPermutationOfOperators(5);

        expected.forEach(
                s1 -> assertTrue(actual.stream().anyMatch(s1::equals))
        );
    }

    @Test
    public void createPermutationOfOperators_FIVE_CorrectNumberIsCreates() {
        double expected = Math.pow(OperatorPermutator.validOperators.size(), 5);

        List<Stack<StackElement>> operators = OperatorPermutator.createPermutationOfOperators(5);
        double actual = operators.size();

        assertEquals(expected, actual);
    }

    @Test
    public void createPermutationOfOperators_SIX_EachSixtupleIsCreated() {
        List<Stack<StackElement>> expected = new ArrayList<>();
        OperatorPermutator.validOperators.forEach(
                o1 -> OperatorPermutator.validOperators.forEach(
                        o2 -> {
                            OperatorPermutator.validOperators.forEach(
                                    o3 -> {
                                        OperatorPermutator.validOperators.forEach(
                                                o4 -> {
                                                    OperatorPermutator.validOperators.forEach(
                                                            o5 -> {
                                                                OperatorPermutator.validOperators.forEach(
                                                                        o6 -> {
                                                                            Stack<StackElement> s = new Stack<>();
                                                                            s.push(o1);
                                                                            s.push(o2);
                                                                            s.push(o3);
                                                                            s.push(o4);
                                                                            s.push(o5);
                                                                            s.push(o6);
                                                                            expected.add(s);
                                                                        }
                                                                );
                                                            }
                                                    );
                                                }
                                        );
                                    }
                            );
                        }
                )
        );

        List<Stack<StackElement>> actual = OperatorPermutator.createPermutationOfOperators(6);

        expected.forEach(
                s1 -> assertTrue(actual.stream().anyMatch(s1::equals))
        );
    }

    @Test
    public void createPermutationOfOperators_SIX_CorrectNumberIsCreates() {
        double expected = Math.pow(OperatorPermutator.validOperators.size(), 6);

        List<Stack<StackElement>> operators = OperatorPermutator.createPermutationOfOperators(6);
        double actual = operators.size();

        assertEquals(expected, actual);
    }
}
