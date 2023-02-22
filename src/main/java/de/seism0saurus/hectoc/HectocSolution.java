package de.seism0saurus.hectoc;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.seism0saurus.hectoc.ShuntingYardAlgorithm.ShuntingYardAlgorithm;

public class HectocSolution {

    public static final List<Character> ALLOWED_CHARS = List.of('1','2','3','4','5','6','7','8','9','+','-','*','/','^','(',')');
    private int result = 0;
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
        this.valid = 100 == this.result;
        return this.valid;
    }

    private void checkIllegalCharacters(final String equotation){
        if (equotation == null || equotation.isEmpty()){
            throw new IllegalArgumentException("No equotation provided. Please use +, -, *, /, (, ) and ^ and the six digits from your Hectoc to build an equotation.");
        }
        List<Character> illegalChars = equotation.codePoints()
            .mapToObj(c -> (char) c)
            .filter(c -> !ALLOWED_CHARS.contains(c))
            .distinct()
            .collect(Collectors.toList());

        if (!illegalChars.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            illegalChars.stream()
                .forEach(c -> builder.append(c));
            throw new IllegalArgumentException("Detected an invalid character(s): "+builder.toString()+". Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc.");
        }
    }

    private void checkFormat(final String equotation) {
        Pattern pattern = Pattern.compile("^[-\\(]*[1-9]([+\\-*/\\^\\(\\)]*[1-9]){5}[\\)]*$");
        Matcher matcher = pattern.matcher(equotation);
        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    "Not a valid Hectoc solution. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order and form a syntactically correct equotation.");
        }
    }

    private void checkUsedNumbers(final String equotation) {
        Pattern pattern = Pattern
                .compile("^[-\\(]*([1-9])[+\\-*/\\^\\(\\)]*([1-9])[+\\-*/\\^\\(\\)]*([1-9])[+\\-*/\\^\\(\\)]*([1-9])[+\\-*/\\^\\(\\)]*([1-9])[+\\-*/\\^\\(\\)]*([1-9])[\\)]*$");
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
        if (!((Integer) Integer.parseInt(matcher.group(6))).equals(challenge.getSixtDigit())) {
            throw new IllegalArgumentException(
                    "The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
    }

    public boolean isValid() {
        return this.valid;
    }

    public int result() {
        return this.result;
    }
}