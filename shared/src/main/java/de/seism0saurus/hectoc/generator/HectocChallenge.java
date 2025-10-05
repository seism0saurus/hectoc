package de.seism0saurus.hectoc.generator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class HectocChallenge implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    public static final int MIN = 1;
    public static final int MAX = 9;
    public static final List<Character> ALLOWED_CHARS = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9');

    private int firstDigit;
    private int secondDigit;
    private int thirdDigit;
    private int fourthDigit;
    private int fifthDigit;
    private int sixthDigit;

    @JsonGetter("firstDigit")
    public int getFirstDigit() {
        return firstDigit;
    }

    @JsonSetter("firstDigit")
    public void setFirstDigit(int firstDigit) {
        this.firstDigit = firstDigit;
    }

    @JsonGetter("secondDigit")
    public int getSecondDigit() {
        return secondDigit;
    }

    @JsonSetter("secondDigit")
    public void setSecondDigit(int secondDigit) {
        this.secondDigit = secondDigit;
    }

    @JsonGetter("thirdDigit")
    public int getThirdDigit() {
        return thirdDigit;
    }

    @JsonSetter("thirdDigit")
    public void setThirdDigit(int thirdDigit) {
        this.thirdDigit = thirdDigit;
    }

    @JsonGetter("fourthDigit")
    public int getFourthDigit() {
        return fourthDigit;
    }

    @JsonSetter("fourthDigit")
    public void setFourthDigit(int fourthDigit) {
        this.fourthDigit = fourthDigit;
    }

    @JsonGetter("fifthDigit")
    public int getFifthDigit() {
        return fifthDigit;
    }

    @JsonSetter("fifthDigit")
    public void setFifthDigit(int fifthDigit) {
        this.fifthDigit = fifthDigit;
    }

    @JsonGetter("sixthDigit")
    public int getSixthDigit() {
        return sixthDigit;
    }

    @JsonSetter("sixthDigit")
    public void setSixthDigit(int sixthDigit) {
        this.sixthDigit = sixthDigit;
    }

    public HectocChallenge() {
        super();
    }

    @JsonCreator
    public HectocChallenge(@JsonProperty("firstDigit") int firstDigit,
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

    @Override
    public String toString() {
        return String.valueOf(firstDigit) + secondDigit + thirdDigit + fourthDigit + fifthDigit + sixthDigit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        de.seism0saurus.hectoc.generator.HectocChallenge challenge = (HectocChallenge) obj;
        return firstDigit == challenge.firstDigit &&
                secondDigit == challenge.secondDigit &&
                thirdDigit == challenge.thirdDigit &&
                fourthDigit == challenge.fourthDigit &&
                fifthDigit == challenge.fifthDigit &&
                sixthDigit == challenge.sixthDigit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}