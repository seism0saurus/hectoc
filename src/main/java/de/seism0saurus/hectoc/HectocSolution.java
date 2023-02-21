package de.seism0saurus.hectoc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.seism0saurus.hectoc.ShuntingYardAlgorithm.ShuntingYardAlgorithm;

public class HectocSolution {
    
    private int result = 0;
    private boolean valid = false;
    final HectocChallenge challenge;

    public HectocSolution(final HectocChallenge challenge){
        this.challenge = challenge;
    }

    public boolean checkSolution(final String solution){
        checkSymbols(solution);
        checkTooManyNumbers(solution);
        checkUsedNumbers(solution);
        ShuntingYardAlgorithm algorithm = new ShuntingYardAlgorithm(solution);
        this.result = algorithm.getSolution();
        this.valid = 100 == this.result;
        return this.valid;
    }

    private void checkSymbols(final String equotation){
        Pattern pattern = Pattern.compile("([+\\-*/\\^\\(\\)]*[1-9]){6}[+\\-*/\\^\\(\\)]*");
        Matcher matcher = pattern.matcher(equotation);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Not a valid Hectoc solution. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc.");
        }
    }

    private void checkTooManyNumbers(final String equotation){
        Pattern pattern = Pattern.compile("([^1-9]*([1-9])[^1-9]*){7}");
        Matcher matcher = pattern.matcher(equotation);
        if (matcher.find()){
            throw new IllegalArgumentException("You provided too many numbers in your solution. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
    }

    private void checkUsedNumbers(final String equotation){
        Pattern pattern = Pattern.compile("[^1-9]*([1-9])[^1-9]*([1-9])[^1-9]*([1-9])[^1-9]*([1-9])[^1-9]*([1-9])[^1-9]*([1-9])[^1-9]*");
        Matcher matcher = pattern.matcher(equotation);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Not a valid Hectoc solution. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(1))).equals(challenge.getFirstDigit())){
            throw new IllegalArgumentException("The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(2))).equals(challenge.getSecondDigit())){
            throw new IllegalArgumentException("The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(3))).equals(challenge.getThirdDigit())){
            throw new IllegalArgumentException("The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(4))).equals(challenge.getFourthDigit())){
            throw new IllegalArgumentException("The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(5))).equals(challenge.getFifthDigit())){
            throw new IllegalArgumentException("The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
        if (!((Integer) Integer.parseInt(matcher.group(6))).equals(challenge.getSixtDigit())){
            throw new IllegalArgumentException("The first digit is wrong. Please use only +, -, *, /, (, ) and ^ and the six digits from your Hectoc in unchanged order.");
        }
    }

    public boolean isValid(){
        return this.valid;
    }

    public int result(){
        return this.result;
    }
}