package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
import org.jobrunr.jobs.context.JobContext;
import java.util.Stack;
import org.springframework.stereotype.Component;

@Component
public interface SolutionChecker {

        /**
         * Evaluates the given HectocChallenge based on the provided job context and additional stack elements.
         *
         * @param challenge the HectocChallenge to be checked, containing digit constraints and specific rules
         * @param context the JobContext containing job-specific configurations or information
         * @param elements an optional array of StackElement instances providing additional context or tools for evaluation
         * @return true if the given challenge meets all specified criteria; false otherwise
         */
        boolean check(HectocChallenge challenge, JobContext context, StackElement... elements);
}
