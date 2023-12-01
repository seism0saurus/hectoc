package de.seism0saurus.hectoc.bot.db;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * The {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository} to store and access {@link de.seism0saurus.hectoc.bot.db.ChallengePdo ChallengePdos}.
 *
 * @author seism0saurus
 */
public interface ChallengeRepository extends JpaRepository<ChallengePdo, UUID> {

    /**
     * Fetches the {@link de.seism0saurus.hectoc.bot.db.ChallengePdo ChallengePdo} for the given Status ID.
     *
     * @param statusId The Status ID of the wanted Challenge.
     * @return The <code>ChallengePdo</code> with the given Status ID. If there is no such <code>ChallengePdo</code>, <code>null</code> is returned.
     *
     * @see <a href="https://docs.joinmastodon.org/entities/Status/#id">Mastodon API Status ID</a>
     * @see social.bigbone.api.entity.Status#getId()
     * @see ChallengePdo#getId()
     */
    ChallengePdo findByStatusId(String statusId);

    /**
     * Checks, if the {@link de.seism0saurus.hectoc.bot.db.ChallengePdo ChallengePdo} for the given Status ID exists.
     *
     * @param statusId The Status ID of the wanted Challenge.
     * @return <code>true</code>, if the <code>ChallengePdo</code> with the given Status ID exists. <code>false</code> otherwise.
     *
     * @see <a href="https://docs.joinmastodon.org/entities/Status/#id">Mastodon API Status ID</a>
     * @see social.bigbone.api.entity.Status#getId()
     * @see ChallengePdo#getId()
     */
    boolean existsByStatusId(String statusId);
}
