package de.seism0saurus.hectoc.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * The ChallengeScheduler is responsible for scheduling toots with a new hectoc challenge.
 *
 * @author seism0saurus
 */
@Service
@Profile("challenges & delay-scheduling")
public class ChallengeDelayScheduler {

    /**
     * The {@link org.slf4j.Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ChallengeDelayScheduler.class);

    /**
     * A service used for managing the core functionality of challenges.
     * This field is a dependency injected into the class to enable the creation,
     * processing, and handling of challenge-related operations.
     */
    private final ChallengeService service;

    /**
     * The sole constructor for this class.
     * The needed classes are {@link org.springframework.beans.factory.annotation.Autowired autowired} by Spring.
     */
    public ChallengeDelayScheduler(@Autowired ChallengeService service) {
        this.service = service;
    }

    /**
     * Schedules the posting of new challenges via mastodon toots.
     * postHectoc will be run according to the {@link org.springframework.scheduling.annotation.Scheduled Scheduled annotation}.
     * It generates a new hectoc challenge and creates a new toot on mastodon via the {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository}.
     * The result of the call to mastodon is transformed and stored to the {@link de.seism0saurus.hectoc.bot.db.ChallengeRepository ChallengeRepository}.
     * <p>
     * Exceptions are logged as errors and suppressed. No further error handling is applied.
     */
    @Scheduled(fixedDelayString = "${schedule.challenge.delay}", initialDelay = 1000)
    public void postHectoc() {
        this.service.postHectoc();
    }
}
