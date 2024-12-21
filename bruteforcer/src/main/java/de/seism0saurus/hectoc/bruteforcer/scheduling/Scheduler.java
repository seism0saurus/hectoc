package de.seism0saurus.hectoc.bruteforcer.scheduling;

import de.seism0saurus.hectoc.bruteforcer.db.ChallengePdo;
import de.seism0saurus.hectoc.bruteforcer.db.Repository;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.scheduling.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static de.seism0saurus.hectoc.generator.HectocGenerator.UNSOLVABLE_HECTOCS;

@Service
@Profile("scheduler")
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BruteForcerImpl.class);

    private final JobScheduler jobScheduler;
    private final Repository repository;

    public Scheduler(@Autowired JobScheduler jobScheduler, @Autowired Repository repository) {
        this.jobScheduler = jobScheduler;
        this.repository = repository;
        for (HectocChallenge challenge : UNSOLVABLE_HECTOCS) {
            Optional<ChallengePdo> existingChallenge = repository.findByChallenge(challenge.toString());
            if (existingChallenge.isEmpty()) {
                final JobId enqueuedJobId = jobScheduler
                        .<BruteForcer>enqueue(
                                UUID.nameUUIDFromBytes(challenge.toString().getBytes()),
                                bruteForcer -> bruteForcer.bruteForce(challenge, JobContext.Null)
                        );
                repository.save(ChallengePdo.builder()
                                .challenge(challenge.toString())
                                .build()
                );
                LOGGER.info("JobId: " + enqueuedJobId);
            } else {
                LOGGER.info("Challenge already exists: " + challenge.toString());
            }
        }
    }
}
