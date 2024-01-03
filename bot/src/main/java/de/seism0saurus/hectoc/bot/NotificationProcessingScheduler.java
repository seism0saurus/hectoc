package de.seism0saurus.hectoc.bot;

import de.seism0saurus.hectoc.bot.db.ChallengePdo;
import de.seism0saurus.hectoc.bot.db.ChallengeRepository;
import de.seism0saurus.hectoc.bot.db.NotificationPdo;
import de.seism0saurus.hectoc.bot.db.NotificationRepository;
import de.seism0saurus.hectoc.bot.mastodon.StatusRepository;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.HectocSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import social.bigbone.api.entity.Account;
import social.bigbone.api.entity.Context;
import social.bigbone.api.entity.Notification;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The NotificationProcessingScheduler is responsible for scheduling the processing of responses to challenges.
 *
 * @author seism0saurus
 */
@Service
public class NotificationProcessingScheduler {

    /**
     * The {@link org.slf4j.Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationProcessingScheduler.class);

    /**
     * The {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository} of this class.
     * The repository is used to create new toots at mastodon.
     */
    private final StatusRepository statusRepository;

    /**
     * The {@link de.seism0saurus.hectoc.bot.mastodon.NotificationRepository NotificationRepository} of this class.
     * The repository is used to get and process notifications.
     */
    private final de.seism0saurus.hectoc.bot.mastodon.NotificationRepository mastodonNotificationRepo;

    /**
     * The {@link de.seism0saurus.hectoc.bot.TextGenerator TextGenerator} of this class.
     * The generator is used to create the text for new toots at mastodon.
     */
    private final TextGenerator generator;

    /**
     * The {@link de.seism0saurus.hectoc.bot.db.NotificationRepository NotificationRepository} of this class.
     * The repository is used to store or load notifications and solutions.
     */
    private final NotificationRepository notificationRepo;

    /**
     * The {@link de.seism0saurus.hectoc.bot.db.ChallengeRepository ChallengeRepository} of this class.
     * The repository is used to store or load existing challenges.
     */
    private final ChallengeRepository challengeRepo;

    /**
     * The sole constructor for this class.
     * The needed classes are {@link org.springframework.beans.factory.annotation.Autowired autowired} by Spring.
     *
     * @param generator The {@link de.seism0saurus.hectoc.bot.TextGenerator TextGenerator} of this class. Will be stored to {@link #generator generator}.
     * @param notificationRepo The {@link de.seism0saurus.hectoc.bot.db.NotificationRepository NotificationRepository} of this class. Will be stored to {@link #notificationRepo notificationRepo}.
     * @param mastodonNotificationRepo The {@link de.seism0saurus.hectoc.bot.mastodon.NotificationRepository NotificationRepository} of this class. Will be stored to {@link #mastodonNotificationRepo mastodonNotificationRepo}.
     * @param statusRepository The {@link de.seism0saurus.hectoc.bot.mastodon.StatusRepository StatusRepository} of this class. Will be stored to {@link #statusRepository statusRepository}.
     * @param challengeRepo The {@link de.seism0saurus.hectoc.bot.db.ChallengeRepository ChallengeRepository} of this class. Will be stored to {@link #challengeRepo challengeRepo}.
     */
    public NotificationProcessingScheduler(
            @Autowired TextGenerator generator,
            @Autowired NotificationRepository notificationRepo,
            @Autowired de.seism0saurus.hectoc.bot.mastodon.NotificationRepository mastodonNotificationRepo,
            @Autowired StatusRepository statusRepository,
            @Autowired ChallengeRepository challengeRepo
            ) {
        this.mastodonNotificationRepo = mastodonNotificationRepo;
        LOGGER.info("Handler for mentions on created");
        this.generator = generator;
        this.notificationRepo = notificationRepo;
        this.statusRepository = statusRepository;
        this.challengeRepo = challengeRepo;
    }

    /**
     * Schedules the processing of new mastodon notifications.
     * processNotifications will be run according to the {@link org.springframework.scheduling.annotation.Scheduled Scheduled annotation}.
     * This fetches all notifications from mastodon via the {@link de.seism0saurus.hectoc.bot.mastodon.NotificationRepository NotificationRepository}.
     * Then the notifications are processed and answers to previous challenges are extracted and stored.
     * The answers are parsed and depending on the probed solution they will be favourite or answered.
     *
     * Exceptions are logged as errors and suppressed. No further error handling applies.
     */
    @Scheduled(cron = "0 */5 0/1 * * ?")
    public void processNotifications(){
        LOGGER.info("Going to fetch all notifications");
        try {
            List<Notification> mentions = this.mastodonNotificationRepo.getNotifications();
            LOGGER.info("Got " + mentions.size() + " mentions");

            List<NotificationPdo> pdos = convertToNotificationPdos(mentions);
            this.notificationRepo.saveAll(pdos);
            LOGGER.info("Saved " + pdos.size() + " pdos to the repository");

            processSolutions(pdos);
            LOGGER.info("All notifications processed");
        } catch (BigBoneRequestException e) {
            LOGGER.error("An error occurred. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }

    /**
     * Parses the prepared notifications and answers favourites or answers the toots.
     * Correct answers are favourited.
     * Unreadable answered with an error message, that they could not be parsed.
     * Wrong answers are answered with an error message, that they are wrong.
     *
     * @param pdos The <code>list</code> of {@link NotificationPdo NotificationPdos}
     */
    private void processSolutions(List<NotificationPdo> pdos) {
        pdos.forEach(
                pdo -> {
                    String challengeString = pdo.getChallenge().getChallenge();
                    String solutionString = pdo.getSolution();
                    try {
                        HectocSolution solution = new HectocSolution(new HectocChallenge(challengeString));
                        boolean correctSolutionSubmitted = solution.checkSolution(solutionString);
                        if (correctSolutionSubmitted) {
                            LOGGER.info("Solution in status " + pdo.getStatusId() + " ist correct");
                            favouriteStatusWithCorrectSolution(pdo);
                            pdo.setCorrect(true);
                        } else {
                            LOGGER.info("Solution in status " + pdo.getStatusId() + " ist wrong");
                            sendWrongSolutionAnswerStatus(pdo, solution.getResultOfSolution(solutionString));
                            pdo.setCorrect(false);
                        }
                    } catch (IllegalArgumentException e){
                        LOGGER.info("Solution in status " + pdo.getStatusId() + " ist syntactically wrong or couldn't be found: " + e.getMessage());
                        sendCouldNotParseStatus(pdo);
                        pdo.setCorrect(false);
                    }
                }
        );
        // Store if the proposed solutions were correct or false
        this.notificationRepo.saveAll(pdos);
    }

    /**
     * Favourite a toot with a correct solution and dismiss the notification about it.
     * An exception in the communication with mastodon is caught and logged.
     * No further error handling is applied.
     *
     * @param pdo The {@link NotificationPdo NotificationPdo} for the toot with the solution.
     */
    private void favouriteStatusWithCorrectSolution(NotificationPdo pdo) {
        try {
            LOGGER.info("Check if " + pdo.getStatusId() + " is already favoured");
            List<Account> accounts = this.statusRepository.getFavouritedBy(pdo.getStatusId());
            if (accounts.isEmpty() || !accounts.contains("")){
                LOGGER.info("Going to favour status " + pdo.getStatusId());
                statusRepository.favouriteStatus(pdo.getStatusId());
                LOGGER.info("Status " + pdo.getStatusId() + " successfully favoured");
                dismissNotification(pdo);
            }
        } catch (BigBoneRequestException e) {
            LOGGER.error("Could not favourite status. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }

    /**
     * Dismiss a {@link NotificationPdo notification} and save that in the <code>pdo</code>.
     * An exception in the communication with mastodon is caught and logged.
     * No further error handling is applied.
     *
     * @param pdo The notification that should be dismissed.
     */
    private void dismissNotification(NotificationPdo pdo){
        try {
            LOGGER.info("Going to dismiss notification " + pdo.getNotificationId());
            this.mastodonNotificationRepo.dismissNotification(pdo.getNotificationId());
            pdo.setDismissed(true);
            this.notificationRepo.save(pdo);
            LOGGER.info("Dismiss notification " + pdo.getNotificationId() + " successfully");
        } catch (BigBoneRequestException e) {
            LOGGER.error("Could not dismiss notification. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }

    /**
     * Dismiss a {@link NotificationPdo notification} but don't save that in the <code>pdo</code>.
     * An exception in the communication with mastodon is caught and logged.
     * No further error handling is applied.
     *
     * @param pdo The notification that should be dismissed.
     */
    private void dismissNotificationWithoutSave(NotificationPdo pdo){
        try {
            LOGGER.info("Going to dismiss notification " + pdo.getNotificationId());
            this.mastodonNotificationRepo.dismissNotification(pdo.getNotificationId());
            pdo.setDismissed(true);
            LOGGER.info("Dismiss notification " + pdo.getNotificationId() + " successfully");
        } catch (BigBoneRequestException e) {
            LOGGER.error("Could not dismiss status. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }

    /**
     * Send an answer with the calculated result of the proposed solution to the author.
     * The {@link NotificationPdo notification} is dismissed afterward.
     * An exception in the communication with mastodon is caught and logged.
     * No further error handling is applied.
     *
     * @param pdo The notification of the toot with the solution.
     */
    private void sendWrongSolutionAnswerStatus(NotificationPdo pdo, BigDecimal result) {
        try {
            LOGGER.info("Check if " + pdo.getStatusId() + " is already answered");
            Context context = this.statusRepository.getContext(pdo.getStatusId());
            if (wasNotAnsweredByBot(context)){
                try {
                    Status status = this.statusRepository.replyToStatus(generator.wrongAnswer(result, pdo.getAuthor()), pdo.getStatusId());
                    LOGGER.info("Status " + pdo.getStatusId() + " successfully answered with " + status.getId());
                    dismissNotification(pdo);
                } catch (BigBoneRequestException e) {
                    LOGGER.error("An error occurred. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
                }
            }
        } catch (BigBoneRequestException e) {
            LOGGER.error("Could not favourite status. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }

    /**
     * Send an answer with an excuse to the author.
     * The {@link NotificationPdo notification} is dismissed afterward.
     * An exception in the communication with mastodon is caught and logged.
     * No further error handling is applied.
     *
     * @param pdo The notification of the toot with the solution.
     */
    private void sendCouldNotParseStatus(NotificationPdo pdo) {
        try {
            LOGGER.info("Check if " + pdo.getStatusId() + " is already answered");
            Context context = this.statusRepository.getContext(pdo.getStatusId());
            if (wasNotAnsweredByBot(context)){
                try {
                    Status status = statusRepository.replyToStatus(generator.notFound(pdo.getAuthor()), pdo.getStatusId());
                    LOGGER.info("Status " + pdo.getStatusId() + " successfully answered with " + status.getId());
                    dismissNotification(pdo);
                } catch (BigBoneRequestException e) {
                    LOGGER.error("An error occurred. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
                }
            }
        } catch (BigBoneRequestException e) {
            LOGGER.error("Could not favourite status. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
    }

    /**
     * Checks if there is an answer from the bot in the given context
     *
     * @param context The context to check
     * @return Returns true, if the bot has answered in the given context.
     * False otherwise.
     */
    private static boolean wasNotAnsweredByBot(Context context) {
        return context.getDescendants().stream()
                .filter(s ->
                        "@hourlyhectoc@botsin.space".equals(s.getAccount()
                                .getUsername())) //getUsername is ok, because we are on the same server instance as our bot since we are the bot
                .findAny()
                .isEmpty();
    }

    /**
     * Converts the given <code>list</code> of {@link Notification mentions} to {@link NotificationPdo NotificationPdos}
     * Additional information about the corresponding challenge is fetched from the database or mastodon if needed.
     * <code>Notifications</code> that are no answer to a challenge are dismissed.
     *
     * @param mentions A <code>list</code> of <code>mentions</code>. The notifications should be mentions.
     *                 Otherwise, exceptions may occur.
     * @return Returns a <code>list</code> of <code>notification pdos</code>.
     */
    private List<NotificationPdo> convertToNotificationPdos(List<Notification> mentions) {
        List<NotificationPdo> pdos = mentions.stream()
                .map(m -> {
                    ChallengePdo challenge = null;
                    String replyId = m.getStatus().getInReplyToId();
                    if (challengeRepo.existsByStatusId(replyId)){
                        challenge = challengeRepo.findByStatusId(replyId);
                    } else {
                        challenge = fetchAndStoreChallenge(replyId);
                    }
                    NotificationPdo pdo;
                    if (notificationRepo.existsByStatusId(m.getStatus().getId())){
                        pdo = notificationRepo.findByStatusId(m.getStatus().getId());
                    } else {
                        pdo = NotificationPdo.builder()
                                .notificationId(m.getId())
                                .statusId(m.getStatus().getId())
                                .challenge(challenge)
                                .solution(m.getStatus().getContent())
                                .author(m.getStatus().getAccount().getAcct())
                                .correct(false)
                                .date(m.getStatus().getCreatedAt().mostPreciseInstantOrNull().atZone(ZoneOffset.UTC))
                                .build();
                    }
                    // Dismiss if the post was no answer to a challenge
                    if (null == challenge){
                        dismissNotificationWithoutSave(pdo);
                        return null;
                    }
                    return pdo;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return pdos;
    }

    /**
     * Fetches and saves the toot with a challenge for a given Staus ID of a response.
     *
     * @param replyId The Staus ID of a response.
     * @return Returns a {@link ChallengePdo ChallengePdo}, if the Status ID was an answer to a challenge.
     * <code>null</code> otherwise.
     */
    private ChallengePdo fetchAndStoreChallenge(String replyId) {
        ChallengePdo challenge = null;
        try {
            LOGGER.info("Going to fetch challenge " + replyId);
            String challengeText = this.statusRepository.getChallenge(replyId);
            if (null == challengeText){
                return challenge;
            }
            challenge = ChallengePdo.builder()
                    .statusId(replyId)
                    .challenge(challengeText)
                    .build();
            this.challengeRepo.save(challenge);
            LOGGER.info("Challenge " + replyId + " saved to repository");
        } catch (BigBoneRequestException e) {
            LOGGER.error("Could not fetch challenge status. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
        }
        return challenge;
    }

}
