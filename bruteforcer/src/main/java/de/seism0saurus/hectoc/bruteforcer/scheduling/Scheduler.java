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

/**
 * The Scheduler class is responsible for initializing and managing the scheduling of jobs
 * that generate number permutations for specific challenges. This class is a Spring
 * Service and is only activated when the "scheduler" profile is active.
 * <p>
 * The class utilizes the {@link JobScheduler} to enqueue jobs for generating permutations
 * of numbers provided in {@link HectocChallenge} instances that are identified as unsolvable.
 * Additionally, it ensures that challenges are persisted in the database using the
 * {@link Repository} and logs the results of each scheduling attempt.
 * <p>
 * Responsibilities include:
 * - Checking if a given unsolvable challenge is already stored in the database.
 * - Enqueuing a job for unsolvable challenges not found in the database.
 * - Persisting new challenges into the database.
 * - Logging information about job scheduling and existing challenges.
 * <p>
 * This service ensures that unsolvable challenges are properly scheduled for job processing,
 * while avoiding duplicate database entries or job submissions.
 * <p>
 * An instance of {@link JobScheduler} is used for handling job queuing, while
 * repository operations are performed through {@link Repository}.
 */
@Service
@Profile("scheduler")
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    private final JobScheduler jobScheduler;
    private final Repository repository;

    public Scheduler(@Autowired JobScheduler jobScheduler, @Autowired Repository repository) {
        this.jobScheduler = jobScheduler;
        this.repository = repository;
        for (HectocChallenge challenge : UNSOLVABLE_HECTOCS) {
            Optional<ChallengePdo> existingChallenge = repository.findByChallenge(challenge.toString());
            if (existingChallenge.isEmpty()) {
                final JobId enqueuedJobId = jobScheduler
                        .<NumberPermutationGenerator>enqueue(
                                UUID.nameUUIDFromBytes(challenge.toString().getBytes()),
                                ps -> ps.generateNumberPermutations(challenge, JobContext.Null)
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
