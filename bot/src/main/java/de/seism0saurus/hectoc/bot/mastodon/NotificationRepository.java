package de.seism0saurus.hectoc.bot.mastodon;

import de.seism0saurus.hectoc.bot.db.NotificationPdo;
import org.jetbrains.annotations.Nullable;
import social.bigbone.api.entity.Notification;
import social.bigbone.api.exception.BigBoneRequestException;
import social.bigbone.api.method.NotificationMethods;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The repository to access and manipulate {@link social.bigbone.api.entity.Notification Notifications}.
 *
 * @author seism0saurus
 */
public interface NotificationRepository {

    /**
     * Fetches all {@link social.bigbone.api.entity.Notification Notifications} from mastodon, that are not {@link NotificationPdo#isDismissed() dismissed} .
     *
     * @return The <code>list</code> of all <code>Notifications</code>, that are not <code>dismissed</code>.
     * @throws BigBoneRequestException Throws an exception, if there is a communication error with the configured mastodon instance.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/notifications/#get">Mastodon API Get all notifications</a>
     * @see NotificationMethods#getAllNotifications()
     */
    public List<Notification> getNotifications() throws BigBoneRequestException ;

    /**
     * Dismisses the {@link social.bigbone.api.entity.Notification Notification} with the given {@link Notification#getId() Notification ID}.
     *
     * @param notificationId The id of the notification, that should be dismissed.
     * @throws BigBoneRequestException Throws an exception, if there is a communication error with the configured mastodon instance or the id is invalid.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/notifications/#dismiss">Mastodon API Dismiss a single notification</a>
     * @see <a href="https://docs.joinmastodon.org/entities/Notification/#id">Mastodon API Notification ID</a>
     * @see social.bigbone.api.method.NotificationMethods#dismissNotification(String)
     */
    public void dismissNotification(final String notificationId) throws BigBoneRequestException;
}
