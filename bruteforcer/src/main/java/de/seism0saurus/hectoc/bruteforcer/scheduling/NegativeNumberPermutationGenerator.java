package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public interface NegativeNumberPermutationGenerator {

    /**
     *
     * @param challenge
     * @param stack
     * @param context
     */
    void generateNegativeNumberPermutations(HectocChallenge challenge, Stack<Number> stack, JobContext context);
}
