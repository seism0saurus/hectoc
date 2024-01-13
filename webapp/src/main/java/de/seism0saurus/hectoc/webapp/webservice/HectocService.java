package de.seism0saurus.hectoc.webapp.webservice;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.generator.HectocGenerator;
import de.seism0saurus.hectoc.shuntingyardalgorithm.HectocSolution;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:4200,https://hectoc.seism0saurus.de/", maxAge = 3600)
@RestController
public class HectocService {

    @GetMapping("/hectocs")
    HectocChallengeDto getHectoc() {
        return new HectocChallengeDto(
                HectocGenerator
                        .generate()
                        .toString());
    }

    @PostMapping("/hectocs/{hectoc}/solution")
    HectocResultDto checkSolution(@PathVariable final String hectoc, @RequestBody final HectocSolutionDto solutionDto) {
        HectocChallenge challenge = new HectocChallenge(hectoc);
        HectocSolution solution = new HectocSolution(challenge);
        boolean valid = solution.checkSolution(solutionDto.getSolution());
        BigDecimal result = solution.getResult();
        return new HectocResultDto(valid, result.intValue());
    }
}