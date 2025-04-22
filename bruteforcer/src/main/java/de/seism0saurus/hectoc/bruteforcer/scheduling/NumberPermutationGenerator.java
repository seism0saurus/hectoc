package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

@Component
public interface NumberPermutationGenerator {

        /**
         * Generates all possible permutations of numbers based on the specified HectocChallenge.
         * This method is intended to work within a job execution context, utilizing the provided
         * JobContext for job-specific configurations or metadata.
         *
         * @param challenge the HectocChallenge instance containing numeric constraints and digits
         *                  for which permutations are to be generated
         * @param context   the JobContext providing additional information or configuration for the
         *                  task, such as job metadata or logging context
         */
        void generateNumberPermutations(HectocChallenge challenge, JobContext context);
}
