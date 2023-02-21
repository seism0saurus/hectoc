package de.seism0saurus.hectoc.Webservice;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.seism0saurus.hectoc.HectocChallenge;
import de.seism0saurus.hectoc.HectocGenerator;
import de.seism0saurus.hectoc.HectocSolution;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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
    HectocResultDto checkSolution(@PathVariable final String hectoc, @RequestBody final HectocSolutionDto solutionDto){
        HectocChallenge challenge = new HectocChallenge(hectoc);
        HectocSolution solution = new HectocSolution(challenge);
        boolean valid = solution.checkSolution(solutionDto.getSolution());
        int result = solution.result();
        return new HectocResultDto(valid,result);
    }
}