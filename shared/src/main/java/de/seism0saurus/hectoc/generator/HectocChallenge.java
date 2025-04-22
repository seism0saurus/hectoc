package de.seism0saurus.hectoc.generator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class HectocChallenge implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    public static final int MIN = 1;
    public static final int MAX = 9;
    public static final List<Character> ALLOWED_CHARS = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9');

    private final int firstDigit;
    private final int secondDigit;
    private final int thirdDigit;
    private final int fourthDigit;
    private final int fifthDigit;
    private final int sixthDigit;

    @JsonCreator
    protected HectocChallenge(@JsonProperty("firstDigit") int firstDigit,
                              @JsonProperty("secondDigit") int secondDigit,
                              @JsonProperty("thirdDigit") int thirdDigit,
                              @JsonProperty("fourthDigit") int fourthDigit,
                              @JsonProperty("fifthDigit") int fifthDigit,
                              @JsonProperty("sixthDigit") int sixthDigit) {
        if (firstDigit < MIN || firstDigit > MAX) {
            throw new IllegalArgumentException("The first number is invalid. Please provide a number between 1 and 9");
        }
        if (secondDigit < MIN || secondDigit > MAX) {
            throw new IllegalArgumentException("The second number is invalid. Please provide a number between 1 and 9");
        }
        if (thirdDigit < MIN || thirdDigit > MAX) {
            throw new IllegalArgumentException("The third number is invalid. Please provide a number between 1 and 9");
        }
        if (fourthDigit < MIN || fourthDigit > MAX) {
            throw new IllegalArgumentException("The fourth number is invalid. Please provide a number between 1 and 9");
        }
        if (fifthDigit < MIN || fifthDigit > MAX) {
            throw new IllegalArgumentException("The fifth number is invalid. Please provide a number between 1 and 9");
        }
        if (sixthDigit < MIN || sixthDigit > MAX) {
            throw new IllegalArgumentException("The sixt number is invalid. Please provide a number between 1 and 9");
        }

        this.firstDigit = firstDigit;
        this.secondDigit = secondDigit;
        this.thirdDigit = thirdDigit;
        this.fourthDigit = fourthDigit;
        this.fifthDigit = fifthDigit;
        this.sixthDigit = sixthDigit;
    }

    public HectocChallenge(final String hectoc) {
        if (hectoc == null || hectoc.length() != 6) {
            throw new IllegalArgumentException("String is not a hectoc. Please provide a String with 6 numbers between 1 and 9");
        }
        List<Integer> hectocSymbols = new ArrayList<>();
        hectoc.codePoints().mapToObj(c -> (char) c).forEach(c -> {
            if (ALLOWED_CHARS.contains(c)) {
                hectocSymbols.add(Character.getNumericValue(c));
            } else {
                throw new IllegalArgumentException("Illegal character found.");
            }
        });
        this.firstDigit = hectocSymbols.get(0);
        this.secondDigit = hectocSymbols.get(1);
        this.thirdDigit = hectocSymbols.get(2);
        this.fourthDigit = hectocSymbols.get(3);
        this.fifthDigit = hectocSymbols.get(4);
        this.sixthDigit = hectocSymbols.get(5);
    }

    public String toString() {
        return String.valueOf(firstDigit) + secondDigit + thirdDigit + fourthDigit + fifthDigit + sixthDigit;
    }
}