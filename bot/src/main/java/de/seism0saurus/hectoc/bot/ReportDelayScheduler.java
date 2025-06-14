package de.seism0saurus.hectoc.bot;

import de.seism0saurus.hectoc.bot.mastodon.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * The MonthlyReportScheduler is responsible for scheduling the creation of a regular report with the number of solutions and the best participants.
 *
 * @author seism0saurus
 */
@Service
@Profile("reports & delay-scheduling")
public class ReportDelayScheduler {

    /**
     * The {@link Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDelayScheduler.class);

    /**
     * A service utilized by the {@link ReportDelayScheduler} to handle
     * the generation and management of reports.
     * This field is initialized via dependency injection by Spring.
     */
    private final ReportService service;

    /**
     * The sole constructor for this class.
     * The needed classes are {@link org.springframework.beans.factory.annotation.Autowired autowired} by Spring.
     */
    public ReportDelayScheduler(@Autowired ReportService service) {
        this.service = service;
    }

    /**
     * Schedules the posting of a report with the number of challenges and the best participants.
     * postReport will be run according to the {@link Scheduled Scheduled annotation}.
     * It generates a new report and creates a new toot on mastodon via the {@link StatusRepository StatusRepository}.
     * <p>
     * Exceptions are logged as errors and suppressed. No further error handling is applied.
     */
    @Scheduled(fixedDelayString = "${schedule.report.delay}", initialDelay = 100000)
    public void postReport() {
        this.service.postReport();
    }
}
