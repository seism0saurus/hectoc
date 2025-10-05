package de.seism0saurus.hectoc.bot.db;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository} to store and access {@link de.seism0saurus.hectoc.bot.db.NotificationPdo NotificationPdos}.
 *
 * @author seism0saurus
 */
public interface NotificationRepository extends JpaRepository<NotificationPdo, UUID> {

    /**
     * Fetches the {@link de.seism0saurus.hectoc.bot.db.NotificationPdo NotificationPdo} for the toot with the given Status ID.
     *
     * @param statusId The Status ID of the toot, that triggered the notification.
     * @return The <code>NotificationPdo</code> for the toot with the given Status ID. If there is no such <code>NotificationPdo</code>, <code>null</code> is returned.
     *
     * @see <a href="https://docs.joinmastodon.org/entities/Status/#id">Mastodon API Status ID</a>
     * @see social.bigbone.api.entity.Status#getId()
     * @see NotificationPdo#getId()
     */
    List<NotificationPdo> findByStatusId(String statusId);

    /**
     * Checks, if the {@link de.seism0saurus.hectoc.bot.db.NotificationPdo NotificationPdo} for the toot with the given Status ID exists.
     *
     * @param statusId The Status ID of the toot, that triggered the notification.
     * @return <code>true</code>, if the <code>NotificationPdo</code> for the toot with the given Status ID exists. <code>false</code> otherwise.
     *
     * @see <a href="https://docs.joinmastodon.org/entities/Status/#id">Mastodon API Status ID</a>
     * @see social.bigbone.api.entity.Status#getId()
     * @see NotificationPdo#getId()
     */
    boolean existsByStatusId(String statusId);

    /**
     * Fetches all {@link de.seism0saurus.hectoc.bot.db.NotificationPdo NotificationPdo} between the given dates.
     * @param from The date from which on we are fetching pdos.
     * @param to The data until we are fetching pdos.
     * @return The list of pdos between the given dates.
     */
    List<NotificationPdo> findAllByDateBetween(ZonedDateTime from, ZonedDateTime to);
}
