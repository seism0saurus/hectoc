package de.seism0saurus.hectoc.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * The NotificationProcessingScheduler is responsible for scheduling the processing of responses to challenges.
 *
 * @author seism0saurus
 */
@Service
@Profile("notifications & cron-scheduling")
public class NotificationProcessingCronScheduler {

    /**
     * The {@link Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationProcessingCronScheduler.class);

    /**
     * A service responsible for handling the processing of notifications.
     * This service might include functionalities such as sending,
     * managing, or queuing notifications for further operations.
     */
    private final NotificationProcessingService service;

    /**
     * The sole constructor for this class.
     * The needed classes are {@link Autowired autowired} by Spring.
     */
    public NotificationProcessingCronScheduler(@Autowired NotificationProcessingService service) {
        this.service = service;
    }

    /**
     * Schedules the processing of new mastodon notifications.
     * processNotifications will be run according to the {@link Scheduled Scheduled annotation}.
     * This fetches all notifications from mastodon via the {@link de.seism0saurus.hectoc.bot.mastodon.NotificationRepository NotificationRepository}.
     * Then the notifications are processed and answers to previous challenges are extracted and stored.
     * The answers are parsed and depending on the probed solution they will be favourite or answered.
     * <p>
     * Exceptions are logged as errors and suppressed. No further error handling applies.
     */
    @Scheduled(cron = "${schedule.notification}")
    public void processNotifications() {
        this.service.processNotifications();
    }
}
