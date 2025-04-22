package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.bruteforcer.logic.NegativeNumberPermutator;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.scheduling.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Stack;
import java.util.UUID;

@Component
public class NegativeNumberPermutationGeneratorImpl implements NegativeNumberPermutationGenerator {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(NegativeNumberPermutationGeneratorImpl.class));

    private final JobScheduler jobScheduler;

    public NegativeNumberPermutationGeneratorImpl(@Autowired JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    /**
     * Generates permutations of negative numbers for a given HectocChallenge and stack of numbers.
     * The method processes each permutation, enqueues it as a job for operator scheduling,
     * and updates the progress bar while logging the progress.
     *
     * @param challenge the HectocChallenge instance representing the current challenge configuration
     * @param stack the stack of Number objects for which permutations of negative numbers are generated
     * @param context the JobContext from JobRunr
     */
    @Override
    public void generateNegativeNumberPermutations(HectocChallenge challenge, Stack<Number> stack, JobContext context) {
        final JobDashboardProgressBar progressBar = context.progressBar(2^stack.size());
        generateNegativeNumberPermutations(challenge, stack, progressBar);
    }

    /**
     * Generates permutations of negative numbers for a given HectocChallenge and stack of numbers.
     * The method processes each permutation, enqueues it as a job for operator scheduling,
     * and updates the progress bar while logging the progress.
     *
     * @param challenge the HectocChallenge instance representing the current challenge configuration
     * @param stack the stack of Number objects for which permutations of negative numbers are generated
     * @param progressBar the JobDashboardProgressBar that tracks and updates the progress of the job scheduling
     */
    private void generateNegativeNumberPermutations(HectocChallenge challenge, Stack<Number> stack, JobDashboardProgressBar progressBar) {
        NegativeNumberPermutator.createPermutationsOfNegativeNumbers(stack).stream()
                .peek(s -> {
                    final JobId enqueuedJobId = jobScheduler
                            .<OperatorGenerator>enqueue(
                                    UUID.nameUUIDFromBytes(("o_" + challenge + s.toString()).getBytes()),
                                    os -> os.generateOperatorPermutations(challenge, s, JobContext.Null)
                            );
                    LOGGER.info("JobId: " + enqueuedJobId);
                })
                .forEach(s -> progressBar.increaseByOne());

        LOGGER.info("Challenge: {} - {} jobs scheduled", challenge, progressBar.getProgress());
    }
}
