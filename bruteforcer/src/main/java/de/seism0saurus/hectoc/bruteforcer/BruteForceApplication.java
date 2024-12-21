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
    }
}
