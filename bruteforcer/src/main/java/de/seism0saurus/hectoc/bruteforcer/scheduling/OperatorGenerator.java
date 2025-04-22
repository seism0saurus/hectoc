package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public interface OperatorGenerator {

    /**
     * Generates all possible operator permutations for the given HectocChallenge using the provided numbers
     * and schedules jobs for evaluating possible solutions. The method processes the numbers as a stack and
     * calculates the permutations in Reverse Polish Notation (RPN) format. It uses the job scheduler to enqueue
     * tasks to check the generated solutions against the challenge.
     *
     * @param challenge the HectocChallenge object representing the problem to solve, which includes constraints and rules
     * @param context the JobContext object that provides resources such as a progress bar for tracking the scheduling progress
     * @param numbers the array of Number objects that represent the input numbers used for computing operator permutations
     */
    void generateOperatorPermutations(HectocChallenge challenge, JobContext context, Number... numbers);
}
