package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public interface OperatorGenerator {

    /**
     * Attempts to solve the given HectocChallenge using a brute force approach.
     *
     * @param challenge the HectocChallenge to be solved, which contains numeric constraints
     * @param context   the JobContext providing additional information or configuration for the task
     */
    void generateOperatorPermutations(HectocChallenge challenge, Stack<Number> stack, JobContext context);
}
