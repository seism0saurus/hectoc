package de.seism0saurus.hectoc.bot;

import de.seism0saurus.hectoc.bot.db.NotificationPdo;
import de.seism0saurus.hectoc.bot.db.NotificationRepository;
import de.seism0saurus.hectoc.bot.db.ReportPdo;
import de.seism0saurus.hectoc.bot.db.ReportRepository;
import de.seism0saurus.hectoc.bot.mastodon.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * The MonthlyReportScheduler is responsible for scheduling the creation of a regular report with the number of solutions and the best participants.
 *
 * @author seism0saurus
 */
@Service
public class MonthlyReportScheduler {

    /**
     * The {@link Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyReportScheduler.class);

    /**
     * The {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository} of this class.
     * The repository is used to create new toots at mastodon.
     */
    private final StatusRepository statusRepository;

    /**
     * The {@link de.seism0saurus.hectoc.bot.db.NotificationRepository NotificationRepository} of this class.
     * The repository is used to create new toots at mastodon.
     */
    private final NotificationRepository notificationRepository;

    /**
     * The {@link de.seism0saurus.hectoc.bot.TextGenerator TextGenerator} of this class.
     * The generator is used to create the text for new toots at mastodon.
     */
    private final TextGenerator generator;

    /**
     * The {@link de.seism0saurus.hectoc.bot.db.ReportPdo ReportPdo} of this class.
     * The repository is used to store newly posted reports for further reference.
     */
    private final ReportRepository repo;

    /**
     * The sole constructor for this class.
     * The needed classes are {@link org.springframework.beans.factory.annotation.Autowired autowired} by Spring.
     *
     * @param generator              The {@link de.seism0saurus.hectoc.bot.TextGenerator TextGenerator} of this class. Will be stored to {@link de.seism0saurus.hectoc.bot.MonthlyReportScheduler#generator generator}.
     * @param repo                   The {@link de.seism0saurus.hectoc.bot.db.ReportRepository ReportRepository} of this class. Will be stored to {@link de.seism0saurus.hectoc.bot.MonthlyReportScheduler#repo repo}.
     * @param statusRepository       The {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository} of this class. Will be stored to {@link de.seism0saurus.hectoc.bot.MonthlyReportScheduler#statusRepository statusRepository}.
     * @param notificationRepository The {@link de.seism0saurus.hectoc.bot.db.NotificationRepository NotificationRepository} of this class. Will be stored to {@link de.seism0saurus.hectoc.bot.MonthlyReportScheduler#notificationRepository notificationRepository}.
     */
    public MonthlyReportScheduler(
            @Autowired TextGenerator generator,
            @Autowired ReportRepository repo,
            @Autowired StatusRepository statusRepository,
            @Autowired NotificationRepository notificationRepository) {
        this.generator = generator;
        this.repo = repo;
        this.statusRepository = statusRepository;
        this.notificationRepository = notificationRepository;
    }

    /**
     * Schedules the posting of a report with the number of challenges and the best participants.
     * postReport will be run according to the {@link org.springframework.scheduling.annotation.Scheduled Scheduled annotation}.
     * It generates a new report and creates a new toot on mastodon via the {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository}.
     * <p>
     * Exceptions are logged as errors and suppressed. No further error handling is applied.
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void postReport() {
        final ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        if (isUnwantedDay(now)) return;
        if (isReportAlreadySent(now)) return;
        LOGGER.info("First day of the month and no previous reports. Going to post new report.");
        final String statusText = getReportText(now);
        LOGGER.info("Text will be: " + statusText);
        try {
            Status status = this.statusRepository.postDirectStatus(statusText);
            ZonedDateTime creationDateUtc = status.getCreatedAt().mostPreciseInstantOrNull().atZone(ZoneOffset.UTC);
            LOGGER.info("New report with id " + status.getId() + " created at " + creationDateUtc);

            ReportPdo reportPdo = ReportPdo.builder()
                    .statusId(status.getId())
                    .date(creationDateUtc)
                    .build();
            this.repo.save(reportPdo);
            LOGGER.info("Report " + status.getId() + " saved to repository");
        } catch (BigBoneRequestException e) {
            LOGGER.error("An error occurred. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }

    /**
     * Prepares a report text by fetching the needed data from the repository and formatting the results.
     * @param now The first day of the wanted month.
     * @return A formatted text for the report.
     */
    private String getReportText(ZonedDateTime now) {
        final ZonedDateTime lastDayOfPreviousMonth = now.minusDays(1);
        final ZonedDateTime lastSecondOfMonth = lastDayOfPreviousMonth.with(ChronoField.NANO_OF_DAY, 86400L * 1000_000_000L - 1);
        final ZonedDateTime firstSecondOfMonth = lastSecondOfMonth.with(TemporalAdjusters.firstDayOfMonth()).with(ChronoField.NANO_OF_DAY, 0);
        List<NotificationPdo> allByDateBetween = this.notificationRepository.findAllByDateBetween(firstSecondOfMonth, lastSecondOfMonth);

        final String statusText = generator.getReportText(allByDateBetween);
        return statusText;
    }

    /**
     * Checks if the report for this month is already sent.
     * @param now The first day of the wanted month.
     * @return True, if the report was already sent. False otherwise.
     */
    private boolean isReportAlreadySent(ZonedDateTime now) {
        List<ReportPdo> allOnDay = this.repo.findAllOnDay(now);
        if (!allOnDay.isEmpty()){
            LOGGER.info("Report is already present for today. Skipping report.");
            LOGGER.info("Size: " + allOnDay.size());
            allOnDay.stream().forEach(pdo -> LOGGER.info("Pdo: " + pdo));
            return true;
        }
        return false;
    }

    /**
     * Checks if the current day is not the first day of the month but one of the others.
     * @param now The current date.
     * @return True, if the date is NOT the first day of the month. False, if it is the first day of the month.
     */
    private static boolean isUnwantedDay(ZonedDateTime now) {
        final ZonedDateTime firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        if (now.getDayOfMonth() != firstDayOfMonth.getDayOfMonth()) {
            LOGGER.info("Not the first day of the month. Skipping report.");
            return false; // Only for test
//            return true;
        }
        return false;
    }

}
