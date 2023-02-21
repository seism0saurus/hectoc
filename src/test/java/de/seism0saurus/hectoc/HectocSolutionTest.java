package de.seism0saurus.hectoc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class HectocSolutionTest {
    
    private final HectocChallenge challange = HectocChallenge.builder()
                                                                .firstDigit(1)
                                                                .secondDigit(2)
                                                                .thirdDigit(3)
                                                                .fourthDigit(4)
                                                                .fifthDigit(5)
                                                                .sixtDigit(6)
                                                                .build();
    private final HectocSolution solution = new HectocSolution(challange);

    @Test
    public void detectChangedOrderOfNumbers(){
        assertThrows(IllegalArgumentException.class, 
            () -> solution.checkSolution("132456"));
    }

    @Test
    public void detectMissingNumbers(){
        assertThrows(IllegalArgumentException.class,
            () -> solution.checkSolution("12345"));
    }

    @Test
    public void detectAdditionalNumbers(){
        assertThrows(IllegalArgumentException.class,
            () -> solution.checkSolution("1234567"));
    }

    @Test
    public void acceptCorrectNumberAndOrderOfNumbers(){
       assertDoesNotThrow(() -> solution.checkSolution("123456"));
    }
}