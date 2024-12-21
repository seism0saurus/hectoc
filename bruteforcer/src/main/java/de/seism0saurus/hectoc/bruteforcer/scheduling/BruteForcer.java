package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

@Component
public interface BruteForcer {

        /**
         * Attempts to solve the given HectocChallenge using a brute force approach.
         *
         * @param challenge the HectocChallenge to be solved, which contains numeric constraints
         * @param context the JobContext providing additional information or configuration for the task
         * @return true if the challenge is successfully solved, false otherwise
         */
        boolean bruteForce(HectocChallenge challenge, JobContext context);
}
