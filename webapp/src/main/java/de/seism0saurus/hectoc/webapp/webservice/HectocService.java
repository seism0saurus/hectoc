package de.seism0saurus.hectoc.webapp.webservice;

import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.generator.HectocGenerator;
import de.seism0saurus.hectoc.shuntingyardalgorithm.HectocSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:4200,https://hectoc.seism0saurus.de/", maxAge = 3600)
@RestController
public class HectocService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HectocService.class);

    @GetMapping("/hectocs")
    HectocChallengeDto getHectoc() {
        String challenge = HectocGenerator
                .generate()
                .toString();
        LOGGER.info("Hectoc requested. Sending {}", challenge);
        return new HectocChallengeDto(
                challenge);
    }

    @PostMapping("/hectocs/{hectoc}/solution")
    HectocResultDto checkSolution(@PathVariable final String hectoc, @RequestBody final HectocSolutionDto solutionDto) {
        LOGGER.info("Solution for {} received, trying to check {}", hectoc, solutionDto.getSolution());
        HectocChallenge challenge = new HectocChallenge(hectoc);
        HectocSolution solution = new HectocSolution(challenge);
        boolean valid = solution.checkSolution(solutionDto.getSolution());
        BigDecimal result = solution.getResult();
        LOGGER.info("Solution {} for {} has validity {} and result {}", solutionDto.getSolution(), hectoc, valid, result);
        return new HectocResultDto(valid, result.intValue());
    }
}