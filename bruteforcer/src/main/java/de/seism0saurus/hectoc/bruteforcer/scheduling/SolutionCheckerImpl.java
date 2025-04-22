package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.bruteforcer.db.ChallengePdo;
import de.seism0saurus.hectoc.bruteforcer.db.Repository;
import de.seism0saurus.hectoc.bruteforcer.db.SolutionPdo;
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
import java.util.Stack;

@Component
public class SolutionCheckerImpl implements SolutionChecker {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(SolutionCheckerImpl.class));

    private final Repository repository;

    public SolutionCheckerImpl(@Autowired Repository repository) {
        this.repository = repository;
    }

    /**
     * Evaluates the given HectocChallenge based on the provided job context and additional stack elements.
     *
     * @param challenge the HectocChallenge to be checked, containing digit constraints and specific rules
     * @param context the JobContext containing job-specific configurations or information
     * @param elements an optional array of StackElement instances providing additional context or tools for evaluation
     * @return true if the given challenge meets all specified criteria; false otherwise
     */
    @Override
    public boolean check(HectocChallenge challenge, JobContext context, StackElement... elements) {
        Stack<StackElement> actualStack = new Stack<>();
        for (StackElement element : elements) {
            actualStack.push(element);
        }
        LOGGER.info("Challenge: {}; Stack: {}", challenge, actualStack);
        final JobDashboardProgressBar progressBar = context.progressBar(1);
        boolean result = this.checkResult(actualStack, challenge);
        if (result) {
            LOGGER.info("Challenge: {}; Stack: {} checked. It is a solution !!!!!!", challenge, actualStack);
            return true;
        } else {
            LOGGER.info("Challenge: {}; Stack: {} checked. No solution", challenge, actualStack);
            return false;
        }
    }


    /**
     * Checks if the given Reverse Polish Notation (RPN) stack results in the expected value
     * for the provided Hectoc challenge. If the result matches the target, it marks the
     * challenge as solved and logs the solution. Otherwise, it logs the evaluated result.
     *
     * @param rpn       The stack representing the Reverse Polish Notation (RPN) to evaluate.
     * @param challenge The Hectoc challenge against which the result is validated.
     * @return True if the RPN results in the desired value (e.g., 100) for the challenge,
     * false otherwise.
     */
    private boolean checkResult(Stack<StackElement> rpn, HectocChallenge challenge) {
        LOGGER.debug("Checking " + rpn + " for " + challenge);
        BigDecimal result = calculateResult(rpn);
        if (BigDecimal.valueOf(100).compareTo(result) == 0) {
            markHectocSolved(challenge, rpn.toString());
            return true;
        } else {
            increaseTryCounter(challenge);
            return false;
        }
    }

    /**
     * Calculate the result of a rpn.
     *
     * @param rpn The stack with the rpn
     * @return The result of the given stack
     */
    private BigDecimal calculateResult(Stack<StackElement> rpn) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            result = ShuntingYardAlgorithm.calculateRpn(rpn);
        } catch (ArithmeticException e) {
            LOGGER.debug(rpn + " cannot be calculated. " + e.getMessage());
        }
        return result;
    }

    /**
     * Marks a specific Hectoc challenge as solved by adding the provided solution
     * to the corresponding database entity and updating its state.
     *
     * @param challenge The Hectoc challenge to be marked as solved.
     * @param solution  The solution associated with the given challenge.
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
        challengePdo.setSolutionPdos(solutionPdos);
        challengePdo.setTries(challengePdo.getTries() + 1);
        repository.save(challengePdo);
    }

    /**
     * Increases the try counter for a given HectocChallenge by updating the corresponding entity
     * in the repository. The method retrieves the entity associated with the challenge, increments
     * its 'tries' field, and saves the updated entity back to the repository.
     *
     * @param challenge The HectocChallenge whose try counter should be incremented.
     */
    private void increaseTryCounter(HectocChallenge challenge) {
        Optional<ChallengePdo> optionalChallenge = this.repository.findByChallenge(challenge.toString());
        ChallengePdo challengePdo = optionalChallenge.get();
        challengePdo.setTries(challengePdo.getTries() + 1);
        repository.save(challengePdo);
    }

    /**
     * Transforms a given Hectoc challenge into a stack of numbers.
     *
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
