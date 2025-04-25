package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public interface NegativeNumberPermutationGenerator {

    /**
     * Generates permutations of negative numbers for the provided input numbers and schedules jobs based on these permutations.
     * The method computes all possible sign variations of the given numbers (positive and negative), updates the progress bar,
     * and schedules individual jobs for each permutation.
     *
     * @param challenge the HectocChallenge instance, representing the challenge to process
     * @param context   the JobContext containing execution-specific configurations and tools such as progress bars
     * @param numbers   a variable number of Number objects representing the input numbers for which permutations will be generated
     */
    void generateNegativeNumberPermutations(HectocChallenge challenge, JobContext context, Number... numbers);
}
