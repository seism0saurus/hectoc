package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
import org.jobrunr.jobs.context.JobContext;
import java.util.Stack;
import org.springframework.stereotype.Component;

@Component
public interface SolutionChecker {

        /**
         * Validates the given HectocChallenge based on specific criteria and the current state of the stack,
         * possibly utilizing contextual information provided by the JobContext.
         *
         * @param challenge the HectocChallenge to be validated, containing a set of six numeric digits from 1 to 9
         * @param stack a stack of StackElement objects used in the validation process
         * @param context the JobContext providing additional contextual information for the operation
         * @return true if the challenge meets the criteria, false otherwise
         */
        boolean check(HectocChallenge challenge, Stack<StackElement> stack, JobContext context);
}
