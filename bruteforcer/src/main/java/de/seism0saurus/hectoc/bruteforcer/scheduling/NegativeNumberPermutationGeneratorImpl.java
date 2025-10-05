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
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class NegativeNumberPermutationGeneratorImpl implements NegativeNumberPermutationGenerator {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(NegativeNumberPermutationGeneratorImpl.class));

    private final JobScheduler jobScheduler;

    public NegativeNumberPermutationGeneratorImpl(@Autowired JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    /**
     * Generates permutations of negative numbers for the provided input numbers and schedules jobs based on these permutations.
     * The method computes all possible sign variations of the given numbers (positive and negative), updates the progress bar,
     * and schedules individual jobs for each permutation.
     *
     * @param challenge the HectocChallenge instance, representing the challenge to process
     * @param context   the JobContext containing execution-specific configurations and tools such as progress bars
     * @param numbers   a variable number of Number objects representing the input numbers for which permutations will be generated
     */
    @Override
    public void generateNegativeNumberPermutations(HectocChallenge challenge, JobContext context, Number... numbers) {
        Stack<Number> actualStack = new Stack<>();
        for (Number number : numbers) {
            actualStack.push(number);
        }
        LOGGER.info("Challenge: {}; Stack: {}", challenge, actualStack);
        final JobDashboardProgressBar progressBar = context.progressBar(2^actualStack.size());
        AtomicInteger counter = new AtomicInteger();
        NegativeNumberPermutator.createPermutationsOfNegativeNumbers(actualStack).stream()
                .peek(s -> {
                    final JobId enqueuedJobId = jobScheduler
                            .<OperatorGenerator>enqueue(
                                    UUID.nameUUIDFromBytes(("o_" + challenge + s.toString()).getBytes()),
                                    os -> os.generateOperatorPermutations(challenge, JobContext.Null, s.toArray(new Number[0]))
                            );
                    LOGGER.info("Challenge: {}; Stack: {}; JobId: {}",challenge, s, enqueuedJobId);
                })
                .forEach(s -> {
                    progressBar.increaseByOne();
                    counter.getAndIncrement();
                });
        LOGGER.info("Challenge: {}; Stack: {} - {} jobs scheduled", challenge, actualStack, counter.get());
    }
}
