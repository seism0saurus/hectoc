package de.seism0saurus.hectoc.shuntingyardalgorithm;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A HectocSolution is a solution for a hectoc represented by a {@link HectocChallenge HectocChallenge}.
 * <p>
 * An instance of this class is always relative to a challenge and can answer the question,
 * if the proposed solution is a valid solution to that challenge.
 *
 * @author seism0saurus
 */
public class HectocSolution implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final List<Character> ALLOWED_CHARS = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', 'x', '/', '÷', ':', '^', '(', ')');
    @Getter
    private BigDecimal result = BigDecimal.ZERO;
    @Getter
    private boolean valid = false;
    final HectocChallenge challenge;

    public HectocSolution(final HectocChallenge challenge) {
        this.challenge = challenge;
    }

    public boolean checkSolution(final String solution) {
        checkIllegalCharacters(solution);
        checkFormat(solution);
        checkUsedNumbers(solution);
        ShuntingYardAlgorithm algorithm = new ShuntingYardAlgorithm(solution);
        this.result = algorithm.getSolution();
        this.valid = BigDecimal.valueOf(100).equals(this.result);
        return this.valid;
    }

    private String updateSolutionInCaseOfEquation(String solution) {
        if (solution == null || solution.isEmpty()) {
            throw new IllegalArgumentException("No equotation provided. Please use +, -, *, /, (, ) and ^ and the six digits from your Hectoc to build an equotation.");
        }
        if (solution.endsWith("=100")) {
            return solution.substring(0, solution.length()-4);
        }
        return solution;
    }

    public BigDecimal getResultOfSolution(final String solution) {
        checkIllegalCharacters(solution);
        checkFormat(solution);
        checkUsedNumbers(solution);
        ShuntingYardAlgorithm algorithm = new ShuntingYardAlgorithm(solution);
        return algorithm.getSolution();
    }

    private void checkIllegalCharacters(final String equotation) {
        if (equotation == null || equotation.isEmpty()) {
            throw new IllegalArgumentException("No equotation provided. Please use +, -, *, /, (, ) and ^ and the six digits from your Hectoc to build an equotation.");
        }

        List<Character> illegalChars = equotation.codePoints()
                .mapToObj(c -> (char) c)
                .filter(c -> !ALLOWED_CHARS.contains(c))
                .distinct()
                .toList();

        if (!illegalChars.isEmpty()) {
            if (illegalChars.contains('=')) {
                throw new IllegalArgumentException("Detected an invalid use of =. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc.");
            }
            StringBuilder builder = new StringBuilder();
            illegalChars.forEach(builder::append);

            throw new IllegalArgumentException("Detected an invalid character(s): " + builder + ". Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc.");
        }
    }

    private void checkFormat(final String equotation) {
        Pattern pattern = Pattern.compile("^[-\\(]*[1-9]([+\\-*x/:÷\\^\\(\\)]*[1-9]){5}[\\)]*$");
        Matcher matcher = pattern.matcher(equotation);
        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    "Not a valid Hectoc solution. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order and form a syntactically correct equotation.");
        }
    }

    private void checkUsedNumbers(final String equotation) {
        Pattern pattern = Pattern
                .compile("^[-\\(]*([1-9])[+\\-*x/:÷\\^\\(\\)]*([1-9])[+\\-*x/:÷\\^\\(\\)]*([1-9])[+\\-*x/:÷\\^\\(\\)]*([1-9])[+\\-*x/:÷\\^\\(\\)]*([1-9])[+\\-*x/:÷\\^\\(\\)]*([1-9])[\\)]*$");
        Matcher matcher = pattern.matcher(equotation);
        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    "Not a valid Hectoc solution. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(1))).equals(challenge.getFirstDigit())) {
            throw new IllegalArgumentException(
                    "The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(2))).equals(challenge.getSecondDigit())) {
            throw new IllegalArgumentException(
                    "The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(3))).equals(challenge.getThirdDigit())) {
            throw new IllegalArgumentException(
                    "The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(4))).equals(challenge.getFourthDigit())) {
            throw new IllegalArgumentException(
                    "The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(5))).equals(challenge.getFifthDigit())) {
            throw new IllegalArgumentException(
                    "The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(6))).equals(challenge.getSixthDigit())) {
            throw new IllegalArgumentException(
                    "The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
    }

    public boolean formatAndCheckSolution(String solution) {
        solution = updateSolutionInCaseOfEquation(solution);
        return checkSolution(solution);
    }
}