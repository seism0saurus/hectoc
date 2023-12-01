package de.seism0saurus.hectoc.bruteforcer;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.Number;
import de.seism0saurus.hectoc.shuntingyardalgorithm.ShuntingYardAlgorithm;
import de.seism0saurus.hectoc.shuntingyardalgorithm.StackElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import static de.seism0saurus.hectoc.generator.HectocGenerator.UNSOLVABLE_HECTOCS;

public class BruteForceApplication {

    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * Iterates over all 'unsolvable' Hectocs and tries to calculate all possible
     * combinations of numbers and operators, that aren't redundant.
     * @param args Default Java arguments. Will be ignored.
     */
    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of("./results/"));

        UNSOLVABLE_HECTOCS.parallelStream()
                .filter(challenge -> !Files.exists(Path.of("./results/" + challenge.toString() + ".finished")))
                .forEach(challenge -> findSolutions(challenge));
    }

    /**
     * Loads the results from the previous runs from the results.txt file
     * @return The previous results from the results.txt file as String or an empty String, if the file could not be read.
     */
    private static String getPreviousResults(String file) {
        final String previousResults;
        String fileContent;
        try {
            fileContent = Files.readString(Path.of(file));
        } catch (IOException e){
            System.out.println("Coudn't read " + file + ". This is ok, it the hectoc was not tried before.");
            writeToResultFile("challenge;solution;result;correct\n", file);
            fileContent = "";
        }
        previousResults = fileContent;
        return previousResults;
    }

    /**
     * Checks if the challenge wasn't already processed and there are still possible solutions untried
     * @param challenge The challenge to check
     * @param stack The stack with the proposed solution
     * @return True, if the challenge wasn't processed before. False if it was processed and written to the result.txt
     */
    private static boolean notCheckedYet(HectocChallenge challenge, Stack<StackElement> stack) {
        final String previousResults = getPreviousResults("./results/" + challenge + ".csv");
        return !previousResults.contains("solution: " + stack.toString());
    }

    private static void findSolutions(HectocChallenge challenge) {
        counter = new AtomicInteger(0);
        AtomicInteger solutions = new AtomicInteger(0);
        NumberBlockPermutator.createPermutationsOfBlocksOfNumbers(getChallengeAsStack(challenge))
                .parallelStream()
                .map(
                        s -> NegativeNumberPermutator.createPermutationsOfNegativeNumbers(s)
                ).flatMap(List::parallelStream)
                .map(
                        s -> PossibleSolutionGenerator.createRpnStacks(s)
                ).flatMap(Set::parallelStream)
                .peek(s -> solutions.incrementAndGet())
                .filter(s -> notCheckedYet(challenge, s))
                .forEach(s -> checkResult(s, challenge));

        String str = "Challenge: " + challenge + " - " + solutions + " solutions checked. Found " + counter.get() + " possible correct solutions\n";
        writeToResultFile(str, "./results/" + challenge + ".finished");
    }

    /**
     * Writes the given String to the result.txt file
     * @param str The String that should be written to the results file
     */
    private static void writeToResultFile(String str, String file) {
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            byte[] strToBytes = str.getBytes();
            outputStream.write(strToBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transforms a given Hectoc challenge into a stack of numbers.
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

    /**
     * Checks the result of a Stack with RPN calculation in it
     *
     * @param rpn the stack
     */
    protected static void checkResult(Stack<StackElement> rpn, HectocChallenge challenge) {
        counter.incrementAndGet();
        System.out.println("Checking " + rpn);

        // First try a 64 bit resolution for the numbers
        // This saves a lot of computational ressources
        // on wrong results
        BigDecimal result = calculateResultLowRes(rpn);
        if (BigDecimal.valueOf(100).equals(result)) {
            writeToResultFile(challenge + ";" + rpn + ";" + result + ";" + true + "\n", "./results/" + challenge + ".csv");
            System.out.println("Found solution at low resolution for " + challenge + ": " + rpn);

            // If a correct result is found
            // doublecheck with a 128 bit resolution
            // for the numbers to reduce false positives
            // with very large powers
            result = calculateResultHighRes(rpn);

            if (!BigDecimal.valueOf(100).equals(result)) {
                writeToResultFile(challenge + ";" + rpn + ";" + result + ";" + false + "\n", "./results/" + challenge + ".csv");
                System.out.println("Previous found solution (" + result + ") for " + challenge + " could not be confirmed at HIGH resolution: " + rpn);
            }
        } else {
            writeToResultFile(challenge + ";" + rpn + ";" + result + ";" + false + "\n", "./results/" + challenge + ".csv");
            System.out.println("No solution (" + result + ") for " + challenge + ": " + rpn);
        }
    }

    /**
     * Calculate the result of an rpn with 128 bit resolution.
     * This is slower but more accurate.
     * Used for double checking results of possible findings.
     * @param rpn The stack with the rpn
     * @return The result of the given stack
     */
    private static BigDecimal calculateResultHighRes(Stack<StackElement> rpn) {
        BigDecimal result;
        try {
            result = ShuntingYardAlgorithm.calculateRpn(rpn, MathContext.DECIMAL64);
        } catch (ArithmeticException e) {
            System.out.println(rpn + " cannot be calculated. " + e.getMessage());
            result = BigDecimal.ZERO;
        }
        return result;
    }

    /**
     * Calculate the result of a rpn with 64 bit resolution.
     * This is faster but fails at certain calculations with
     * nested POW operators for example.
     * @param rpn The stack with the rpn
     * @return The result of the given stack
     */
    private static BigDecimal calculateResultLowRes(Stack<StackElement> rpn) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            result = ShuntingYardAlgorithm.calculateRpn(rpn);
        } catch (ArithmeticException e) {
            System.out.println(rpn + " cannot be calculated. " + e.getMessage());
        }
        return result;
    }
}
