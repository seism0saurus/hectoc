package de.seism0saurus.hectoc.bot.mastodon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import social.bigbone.MastodonClient;
import social.bigbone.api.entity.Notification;
import social.bigbone.api.entity.NotificationType;
import social.bigbone.api.exception.BigBoneRequestException;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

/**
 * The implementation of the {@link NotificationRepository NotificationRepository} to access and manipulate {@link social.bigbone.api.entity.Notification Notifications}.
 *
 * @author seism0saurus
 */
@Service
public class NotificationRepositoryImpl implements NotificationRepository {

    /**
     * The {@link org.slf4j.Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(NotificationRepositoryImpl.class);

    /**
     * The mastodon client is required to communicate with the configured mastodon instance.
     */
    private final MastodonClient client;

    /**
     * The sole constructor for this class.
     * The needed classes are provided by Spring {@link org.springframework.beans.factory.annotation.Value Values}.
     *
     * @param instance    The mastodon instance for this repository. Can be configured in the <code>application.properties</code>.
     * @param accessToken The access token for this repository.
     *                    You get an access token on the instance of your bot at the {@link <a href="https://docs.joinmastodon.org/spec/oauth/#token">Token Endpoint</a>} of your bot's instance or in the GUI.
     *                    Can be configured in the <code>application.properties</code>.
     */
    public NotificationRepositoryImpl(
            @Value(value = "${mastodon.instance}") String instance,
            @Value(value = "${mastodon.accessToken}") String accessToken) {
        this.client = new MastodonClient.Builder(instance)
                .accessToken(accessToken)
                .setReadTimeoutSeconds(180)
                .setWriteTimeoutSeconds(180)
                .build();
        LOGGER.info("NotificationRepositoryImpl for mastodon instance " + instance + " created");
    }

    public List<Notification> getNotifications() throws BigBoneRequestException {
        LOGGER.debug("Fetch all notifications");
        List<Notification> notifications = this.client.notifications().getAllNotifications().execute().getPart();
        notifications = dismissExceptMentions(notifications);
        return notifications;
    }

    public void dismissNotification(final String notificationId) throws BigBoneRequestException {
        LOGGER.debug("Dismiss notification " + notificationId);
        this.client.notifications().dismissNotification(notificationId);
    }

    /**
     * Dismisses all {@link Notification Notifications}, that are not {@link social.bigbone.api.entity.Notification.NotificationType#MENTION mentions}
     * and returns only the mentions from the list.
     * Mentions are answers to postet toots. Other
     *
     * @param notifications The <code>list</code> of <code>Notifications</code> to process.
     * @return A filtered <code>list</code> of <code>Notifications</code>, that only contains mentions.
     * @see <a href="https://docs.joinmastodon.org/entities/Notification/#type">Mastodon API Notification type</a>
     * @see social.bigbone.api.method.NotificationMethods#dismissNotification(String)
     */
    private List<Notification> dismissExceptMentions(List<Notification> notifications) {
        Map<Boolean, List<Notification>> groupedNotifications = notifications.stream()
                .collect(Collectors.partitioningBy(n -> {
                    if ((NotificationType.MENTION != n.getType() && NotificationType.UPDATE != n.getType()))
                        return false;
                    assert n.getStatus() != null;
                    return n.getStatus().getInReplyToId() != null;
                }));
        groupedNotifications.get(false).stream()
                .map(Notification::getId)
                .forEach(id -> {
                    try {
                        LOGGER.info("Going to dismiss notification because it's not an answer: " + id);
                        this.dismissNotification(id);
                    } catch (BigBoneRequestException e) {
                        LOGGER.error("Could not dismiss notification. Status code: " + e.getHttpStatusCode() + "; message: " + e.getMessage() + "; cause:" + e.getCause());
                    }
                });
        List<Notification> deduplicatedNotificartions = groupedNotifications.get(true).stream()
                .collect(groupingBy(n -> {
                    assert n.getStatus() != null;
                    return n.getStatus().getId();
                }, toSet()))
                .values()
                .stream()
                .map(s -> {
                    if (s.size() > 1) {
                        return s.stream()
                                .max(Comparator.comparing(n -> n.getCreatedAt().mostPreciseOrFallback(Instant.ofEpochMilli(0))))
                                .get();
                    } else {
                        return s.iterator().next();
                    }
                }).toList();
        return deduplicatedNotificartions;
    }
}
