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
import java.util.concurrent.atomic.AtomicInteger;

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
    @Override
    public void generateNumberPermutations(HectocChallenge challenge, JobContext context) {
        LOGGER.info("Challenge: {}", challenge);
        final JobDashboardProgressBar progressBar = context.progressBar(MAX_PERMUTATIONS);
        AtomicInteger counter = new AtomicInteger();
        NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(getChallengeAsStack(challenge)).stream()
                .peek(s -> {
                    final JobId enqueuedJobId = jobScheduler
                            .<NegativeNumberPermutationGenerator>enqueue(
                                    UUID.nameUUIDFromBytes(("n_" + challenge + s.toString()).getBytes()),
                                    ngs -> ngs.generateNegativeNumberPermutations(challenge, JobContext.Null, s.toArray(new Number[0]))
                            );
                    LOGGER.info("Challenge: {}; Stack: {}; JobId: {}",challenge, s, enqueuedJobId);
                })
                .forEach(s -> {
                    progressBar.increaseByOne();
                    counter.getAndIncrement();
                });

        LOGGER.info("Challenge: {} - {} jobs scheduled", challenge, counter.get());
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
