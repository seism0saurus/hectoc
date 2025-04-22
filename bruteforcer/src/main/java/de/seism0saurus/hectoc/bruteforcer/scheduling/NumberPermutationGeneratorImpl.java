package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.bruteforcer.logic.NumberBlockPermutator;
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
public class NumberPermutationGeneratorImpl implements NumberPermutationGenerator {

    /**
     * Maximum number of possible permutations of a hectoc challenge in PNR
     */
    public static final int MAX_PERMUTATIONS = 32;

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(NumberPermutationGeneratorImpl.class));

    private final JobScheduler jobScheduler;

    public NumberPermutationGeneratorImpl(@Autowired JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    /**
     * Attempts to solve the given HectocChallenge using a brute force approach.
     * This method utilizes the findSolutions process to evaluate all possible solutions
     * and tracks progress during computation using the provided JobContext.
     *
     * @param challenge the HectocChallenge to be solved, containing the set of numbers and constraints
     * @param context   the JobContext providing the progress bar and other task-related utilities
     */
    @Override
    public void generateNumberPermutations(HectocChallenge challenge, JobContext context) {
        final JobDashboardProgressBar progressBar = context.progressBar(MAX_PERMUTATIONS);
        schedulePermutations(challenge, progressBar);
    }

    /**
     * Attempts to find and evaluate all possible solutions for a given Hectoc challenge
     * by generating permutations of numbers, transforming them into Reverse Polish Notation (RPN) stacks,
     * and validating these stacks against the expected challenge result.
     * Tracks progress using the provided progress bar and logs the outcomes.
     * Updates the challenge status in the repository based on the results.
     *
     * @param challenge   The Hectoc challenge to be solved. This includes the set of numbers
     *                    that must be used to find solutions.
     * @param progressBar The progress bar to track progress during the solution search process.
     */
    private void schedulePermutations(HectocChallenge challenge, JobDashboardProgressBar progressBar) {
        NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(getChallengeAsStack(challenge)).stream()
                .peek(s -> {
                    final JobId enqueuedJobId = jobScheduler
                            .<NegativeNumberPermutationGenerator>enqueue(
                                    UUID.nameUUIDFromBytes(("n_" + challenge + s.toString()).getBytes()),
                                    ngs -> ngs.generateNegativeNumberPermutations(challenge, s, JobContext.Null)
                            );
                    LOGGER.info("JobId: " + enqueuedJobId);
                })
                .forEach(s -> progressBar.increaseByOne());

        LOGGER.info("Challenge: {} - {} jobs scheduled", challenge, progressBar.getProgress());
    }

    /**
     * Transforms a given Hectoc challenge into a stack of numbers.
     *
     * @param challenge The given hectoc challenge
     * @return The stack with the numbers from the challenge
     */
    private static Stack<Number> getChallengeAsStack(HectocChallenge challenge) {
        Stack<Number> challengeStack = new Stack<>();
        challengeStack.push(Number.of(challenge.getFirstDigit()));
        challengeStack.push(Number.of(challenge.getSecondDigit()));
        challengeStack.push(Number.of(challenge.getThirdDigit()));
        challengeStack.push(Number.of(challenge.getFourthDigit()));
        challengeStack.push(Number.of(challenge.getFifthDigit()));
        challengeStack.push(Number.of(challenge.getSixthDigit()));

        return challengeStack;
    }
}
