package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

@Component
public interface NumberPermutationGenerator {

        /**
         * Generates and schedules permutations of blocks of numbers from the given HectocChallenge.
         * Each permutation is used to enqueue a job for processing negative number permutations.
         * A progress bar tracks the number of scheduled jobs, and detailed logs provide visibility
         * into the permutation generation and job scheduling process.
         *
         * @param challenge The HectocChallenge that provides the numbers to generate permutations for.
         *                  It contains six digits, each between 1 and 9.
         * @param context   The JobContext which provides utilities for job processing,
         *                  such as a progress bar for tracking the number of scheduled jobs.
         */
        void generateNumberPermutations(HectocChallenge challenge, JobContext context);
}
