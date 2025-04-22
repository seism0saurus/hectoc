package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

@Component
public interface PermutationsScheduler {

        /**
         * Attempts to solve the given HectocChallenge using a brute force approach.
         *
         * @param challenge the HectocChallenge to be solved, which contains numeric constraints
         * @param context the JobContext providing additional information or configuration for the task
         */
        void schedulePermutations(HectocChallenge challenge, JobContext context);
}
