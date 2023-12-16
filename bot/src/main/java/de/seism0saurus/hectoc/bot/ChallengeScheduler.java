package de.seism0saurus.hectoc.bot;

import de.seism0saurus.hectoc.bot.db.ChallengePdo;
import de.seism0saurus.hectoc.bot.db.ChallengeRepository;
import de.seism0saurus.hectoc.bot.mastodon.StatusRepository;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.generator.HectocGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * The ChallengeScheduler is responsible for scheduling toots with a new hectoc challenge.
 *
 * @author seism0saurus
 */
@Service
public class ChallengeScheduler {

    /**
     * The {@link org.slf4j.Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ChallengeScheduler.class);

    /**
     * The {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository} of this class.
     * The repository is used to create new toots at mastodon.
     */
    private final StatusRepository statusRepository;

    /**
     * The {@link de.seism0saurus.hectoc.bot.TextGenerator TextGenerator} of this class.
     * The generator is used to create the text for new toots at mastodon.
     */
    private final TextGenerator generator;

    /**
     * The {@link de.seism0saurus.hectoc.bot.db.ChallengeRepository ChallengeRepository} of this class.
     * The repository is used to store newly posted challenges for further reference.
     */
    private final ChallengeRepository repo;

    /**
     * The sole constructor for this class.
     * The needed classes are {@link org.springframework.beans.factory.annotation.Autowired autowired} by Spring.
     *
     * @param generator The {@link de.seism0saurus.hectoc.bot.TextGenerator TextGenerator} of this class. Will be stored to {@link de.seism0saurus.hectoc.bot.ChallengeScheduler#generator generator}.
     * @param repo The {@link de.seism0saurus.hectoc.bot.db.ChallengeRepository ChallengeRepository} of this class. Will be stored to {@link de.seism0saurus.hectoc.bot.ChallengeScheduler#repo repo}.
     * @param statusRepository The {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository} of this class. Will be stored to {@link de.seism0saurus.hectoc.bot.ChallengeScheduler#statusRepository statusRepository}.
     */
    public ChallengeScheduler(
            @Autowired TextGenerator generator,
            @Autowired ChallengeRepository repo,
            @Autowired StatusRepository statusRepository) {
        this.generator = generator;
        this.repo = repo;
        this.statusRepository = statusRepository;
    }

    /**
     * Schedules the posting of new challenges via mastodon toots.
     * postHectoc will be run according to the {@link org.springframework.scheduling.annotation.Scheduled Scheduled annotation}.
     * It generates a new hectoc challenge and creates a new toot on mastodon via the {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository}.
     * The result of the call to mastodon is transformed and stored to the {@link de.seism0saurus.hectoc.bot.db.ChallengeRepository ChallengeRepository}.
     *
     * Exceptions are logged as errors and suppresed. No further error handling is applied.
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void postHectoc() {
        LOGGER.info("Going to post new hectoc challenge");
        HectocChallenge challenge = HectocGenerator.generate();
        final String statusText = generator.getChallengeText(challenge.toString());
        LOGGER.info("Text will be: " + statusText);
        try {
            Status status = this.statusRepository.postStatus(statusText);
            ZonedDateTime creationDateUtc = status.getCreatedAt().mostPreciseInstantOrNull().atZone(ZoneOffset.UTC);
            LOGGER.info("New challenge with id " + status.getId() + " created at " + creationDateUtc);

            ChallengePdo challengePdo = ChallengePdo.builder()
                    .statusId(status.getId())
                    .challenge(challenge.toString())
                    .date(creationDateUtc)
                    .build();
            this.repo.save(challengePdo);
            LOGGER.info("Challenge " + status.getId() + " saved to repository");
        } catch (BigBoneRequestException e) {
            LOGGER.error("An error occured. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }
}
