package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.bruteforcer.logic.PossibleSolutionGenerator;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
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
public class OperatorGeneratorImpl implements OperatorGenerator {

    /**
     * Maximum number of possible permnutations of a hectoc challenge in PNR
     */
    public static final int MAX_PERMUTATIONS = 3_379_794;

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(OperatorGeneratorImpl.class));

    private final JobScheduler jobScheduler;

    public OperatorGeneratorImpl(@Autowired JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    /**
     * Generates all possible operator permutations for the given HectocChallenge using the provided numbers
     * and schedules jobs for evaluating possible solutions. The method processes the numbers as a stack and
     * calculates the permutations in Reverse Polish Notation (RPN) format. It uses the job scheduler to enqueue
     * tasks to check the generated solutions against the challenge.
     *
     * @param challenge the HectocChallenge object representing the problem to solve, which includes constraints and rules
     * @param context the JobContext object that provides resources such as a progress bar for tracking the scheduling progress
     * @param numbers the array of Number objects that represent the input numbers used for computing operator permutations
     */
    @Override
    public void generateOperatorPermutations(HectocChallenge challenge, JobContext context, Number... numbers) {
        Stack<Number> actualStack = new Stack<>();
        for (Number number : numbers) {
            actualStack.push(number);
        }
        LOGGER.info("Challenge: {}; Stack: {}", challenge, actualStack);
        final JobDashboardProgressBar progressBar = context.progressBar(MAX_PERMUTATIONS);
        AtomicInteger counter = new AtomicInteger();
        PossibleSolutionGenerator.createRpnStacks(actualStack).stream()
                .peek(s -> {
                    final JobId enqueuedJobId = jobScheduler
                            .<SolutionChecker>enqueue(
                                    UUID.nameUUIDFromBytes(("b_" + challenge + s.toString()).getBytes()),
                                    bruteForcer -> bruteForcer.check(challenge, JobContext.Null, s.toArray(new StackElement[0]))
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
