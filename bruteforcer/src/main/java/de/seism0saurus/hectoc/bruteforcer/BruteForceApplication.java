package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.bruteforcer.logic.NegativeNumberPermutator;
import de.seism0saurus.hectoc.bruteforcer.logic.NumberBlockPermutator;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import static de.seism0saurus.hectoc.bruteforcer.logic.PossibleSolutionGenerator.createRpnStacks;

@SpringBootApplication
public class BruteForceApplication {

    /**
     * Iterates over all 'unsolvable' Hectocs and tries to calculate all possible
     * combinations of numbers and operators, that aren't redundant.
     * @param args Default Java arguments. Will be ignored.
     */
    public static void main(String[] args) throws IOException {
            SpringApplication.run(BruteForceApplication.class, args);
            countPermutations(new HectocChallenge("111111"));
    }

    private static void countPermutations(HectocChallenge challenge) {
        AtomicInteger solutions = new AtomicInteger(0);
        NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(getChallengeAsStack(challenge))
                .parallelStream()
                .map(
                        NegativeNumberPermutator::createPermutationsOfNegativeNumbers
                ).flatMap(List::parallelStream)
                .map(
                        s -> createRpnStacks(s)
                ).flatMap(Set::parallelStream)
                .peek(s -> solutions.incrementAndGet())
                .toList()
        ;

        String str = "Challenge: " + challenge + " - " + solutions + " possible solutions";
    }
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
