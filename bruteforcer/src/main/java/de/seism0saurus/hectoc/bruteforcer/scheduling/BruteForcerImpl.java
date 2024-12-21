package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.bruteforcer.db.ChallengePdo;
import de.seism0saurus.hectoc.bruteforcer.db.Repository;
import de.seism0saurus.hectoc.bruteforcer.db.SolutionPdo;
import de.seism0saurus.hectoc.bruteforcer.logic.NegativeNumberPermutator;
import de.seism0saurus.hectoc.bruteforcer.logic.NumberBlockPermutator;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.ShuntingYardAlgorithm;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

import static de.seism0saurus.hectoc.bruteforcer.logic.PossibleSolutionGenerator.createRpnStacks;

@Component
public class BruteForcerImpl implements BruteForcer {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(BruteForcerImpl.class));

    private static AtomicLong counter = new AtomicLong(0);

    private final Repository repository;

    public BruteForcerImpl(@Autowired Repository repository) {
        this.repository = repository;
    }

    /**
     * Attempts to solve the given HectocChallenge using a brute force approach.
     * This method utilizes the findSolutions process to evaluate all possible solutions
     * and tracks progress during computation using the provided JobContext.
     *
     * @param challenge the HectocChallenge to be solved, containing the set of numbers and constraints
     * @param context the JobContext providing the progress bar and other task-related utilities
     * @return true if the challenge is determined to be solvable based on the repository state, false otherwise
     */
    @Override
    public boolean bruteForce(HectocChallenge challenge, JobContext context) {
        final JobDashboardProgressBar progressBar = context.progressBar(1);
        return findSolutions(challenge, progressBar);
    }

    /**
     * Attempts to find and evaluate all possible solutions for a given Hectoc challenge
     * by generating permutations of numbers, transforming them into Reverse Polish Notation (RPN) stacks,
     * and validating these stacks against the expected challenge result.
     * Tracks progress using the provided progress bar and logs the outcomes.
     * Updates the challenge status in the repository based on the results.
     *
     * @param challenge The Hectoc challenge to be solved. This includes the set of numbers
     *                  that must be used to find solutions.
     * @param progressBar The progress bar to track progress during the solution search process.
     * @return True if the challenge is solvable based on the repository state; false otherwise.
     */
    private boolean findSolutions(HectocChallenge challenge, JobDashboardProgressBar progressBar) {
        counter = new AtomicLong(0);
        AtomicLong solutions = new AtomicLong(0);
        NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(getChallengeAsStack(challenge))
                .parallelStream()
                .map(
                        NegativeNumberPermutator::createPermutationsOfNegativeNumbers
                ).flatMap(List::parallelStream)
                .map(
                        s -> createRpnStacks(s)
                ).flatMap(Set::parallelStream)
                .peek(s -> solutions.incrementAndGet())
                .peek(s -> this.checkResult(s, challenge))
                .forEach(s -> progressBar.increaseByOne());

        increaseTryCounter(challenge, counter.get());

        LOGGER.info("Challenge: {} - {} solutions checked. Found {} possible correct solutions", challenge, solutions, counter.get());

        Optional<ChallengePdo> optionalChallenge = repository.findByChallenge(challenge.toString());
        ChallengePdo challengePdo = optionalChallenge.get();
        return challengePdo.isSolveable();
    }

    /**
     * Checks if the given Reverse Polish Notation (RPN) stack results in the expected value
     * for the provided Hectoc challenge. If the result matches the target, it marks the
     * challenge as solved and logs the solution. Otherwise, it logs the evaluated result.
     *
     * @param rpn The stack representing the Reverse Polish Notation (RPN) to evaluate.
     * @param challenge The Hectoc challenge against which the result is validated.
     * @return True if the RPN results in the desired value (e.g., 100) for the challenge,
     *         false otherwise.
     */
    private boolean checkResult(Stack<StackElement> rpn, HectocChallenge challenge) {
        counter.incrementAndGet();
        LOGGER.debug("Checking " + rpn + " for " + challenge);
        BigDecimal result = calculateResult(rpn);
        if (BigDecimal.valueOf(100).equals(result)) {
            markHectocSolved(challenge, rpn.toString());
            LOGGER.info("!!!!!!!!!!!!!!!! Found solution for {}: {}", challenge, rpn);
            return true;
        } else {
            LOGGER.debug("No solution ({}) for {}: {}", result, challenge, rpn);
            return false;
        }
    }

    /**
     * Calculate the result of a rpn.
     * @param rpn The stack with the rpn
     * @return The result of the given stack
     */
    private BigDecimal calculateResult(Stack<StackElement> rpn) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            result = ShuntingYardAlgorithm.calculateRpn(rpn);
        } catch (ArithmeticException e) {
            LOGGER.error(rpn + " cannot be calculated. " + e.getMessage());
        }
        return result;
    }

    /**
     * Marks a specific Hectoc challenge as solved by adding the provided solution
     * to the corresponding database entity and updating its state.
     *
     * @param challenge The Hectoc challenge to be marked as solved.
     * @param solution The solution associated with the given challenge.
     */
    private void markHectocSolved(HectocChallenge challenge, String solution) {
        Optional<ChallengePdo> optionalChallenge = this.repository.findByChallenge(challenge.toString());
        ChallengePdo challengePdo = optionalChallenge.get();
        List<SolutionPdo> solutionPdos = challengePdo.getSolutionPdos();
        solutionPdos.add(
                SolutionPdo.builder()
                        .challenge(challengePdo)
                        .solution(solution)
                        .build());
        challengePdo.setSolveable(true);
        challengePdo.setTries(challengePdo.getTries() + 1);
        challengePdo.setSolutionPdos(solutionPdos);
        repository.save(challengePdo);
    }

    /**
     * Increases the try counter for a given HectocChallenge by updating the corresponding entity
     * in the repository. The method retrieves the entity associated with the challenge, increments
     * its 'tries' field, and saves the updated entity back to the repository.
     *
     * @param challenge The HectocChallenge whose try counter should be incremented.
     */
    private void increaseTryCounter(HectocChallenge challenge, long tries) {
        Optional<ChallengePdo> optionalChallenge = this.repository.findByChallenge(challenge.toString());
        ChallengePdo challengePdo = optionalChallenge.get();
        challengePdo.setTries(tries);
        repository.save(challengePdo);
    }

    /**
     * Transforms a given Hectoc challenge into a stack of numbers.
     * @param challenge The given hectoc challenge
     * @return The stack with the numbers from the challenge
     */
    private static Stack<de.seism0saurus.hectoc.shuntingyardalgorithm.Number> getChallengeAsStack(HectocChallenge challenge) {
        Stack<de.seism0saurus.hectoc.shuntingyardalgorithm.Number> challengeStack = new Stack<>();
        challengeStack.push(de.seism0saurus.hectoc.shuntingyardalgorithm.Number.of(challenge.getFirstDigit()));
        challengeStack.push(de.seism0saurus.hectoc.shuntingyardalgorithm.Number.of(challenge.getSecondDigit()));
        challengeStack.push(de.seism0saurus.hectoc.shuntingyardalgorithm.Number.of(challenge.getThirdDigit()));
        challengeStack.push(de.seism0saurus.hectoc.shuntingyardalgorithm.Number.of(challenge.getFourthDigit()));
        challengeStack.push(de.seism0saurus.hectoc.shuntingyardalgorithm.Number.of(challenge.getFifthDigit()));
        challengeStack.push(Number.of(challenge.getSixthDigit()));

        return challengeStack;
    }
}
