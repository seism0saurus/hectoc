package de.seism0saurus.hectoc.bot.mastodon;

import org.jetbrains.annotations.Nullable;
import social.bigbone.api.entity.Account;
import social.bigbone.api.entity.Context;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;

import java.util.List;

/**
 * The repository to access and manipulate {@link social.bigbone.api.entity.Notification Notifications}.
 *
 * @author seism0saurus
 */
public interface StatusRepository {

    /**
     * Fetches the {@link social.bigbone.api.entity.Context Context} of a status from mastodon.
     *
     * @return The <code>Context</code> of the toot with the given {@link Status#getId() Status ID}.
     * @throws BigBoneRequestException Throws an exception, if there is a communication error with the configured mastodon instance or the <code>Status ID</code> is invalid.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#context">Mastodon API Get parent and child statuses in context</a>
     * @see <a href="https://docs.joinmastodon.org/entities/Context/">Mastodon API Context</a>
     * @see social.bigbone.api.method.StatusMethods#getContext(String)
     */
    public Context getContext(final String statusId) throws BigBoneRequestException;

    /**
     * Fetches a status from mastodon and extracts a challenge as <code>String</code>.
     *
     * @param statusId The {@link Status#getId() Status ID} of the toot you want to fetch the challenge from.
     * @return The challenge of the toot with the given {@link Status#getId() Status ID}. Returns <code>null</code>, if no challenge was found in the toot.
     * @throws BigBoneRequestException Throws an exception, if there is a communication error with the configured mastodon instance or the <code>Status ID</code> is invalid.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#get">Mastodon API View a single status</a>
     * @see <a href="https://docs.joinmastodon.org/entities/Status/#content">Mastodon API Status content</a>
     * @see social.bigbone.api.method.StatusMethods#getStatus(String)
     */
    @Nullable
    public String getChallenge(final String statusId) throws BigBoneRequestException;

    /**
     * Creates a new toot on mastodon.
     * The toot will be public, in english and without sensitivity warning or spoiler text.
     *
     * @param statusText The text of the new toot.
     * @return The newly posted {@link Status Status}.
     * @throws BigBoneRequestException Throws an exception,
     * if there is a communication error with the configured mastodon instance or the contend is invalid.
     * E.g. it could be to long.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#create">Mastodon API Post a new status</a>
     * @see social.bigbone.api.method.StatusMethods#postStatus
     */
    public Status postStatus(final String statusText) throws BigBoneRequestException;

    /**
     * Creates a new private toot on mastodon for test purpose.
     * The toot will be private, in english and without sensitivity warning or spoiler text.
     *
     * @param statusText The text of the new toot.
     * @return The newly posted {@link Status Status}.
     * @throws BigBoneRequestException Throws an exception,
     * if there is a communication error with the configured mastodon instance or the contend is invalid.
     * E.g. it could be to long.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#create">Mastodon API Post a new status</a>
     * @see social.bigbone.api.method.StatusMethods#postStatus
     */
    public Status postDirectStatus(final String statusText) throws BigBoneRequestException;

    /**
     * Creates a new replay to a toot on mastodon.
     * The toot will be {@link social.bigbone.api.entity.data.Visibility#DIRECT direct}, in english and without sensitivity warning or spoiler text.
     *
     * @param statusText The text of the new toot.
     * @param inReplyToId The {@link Status#getId() Status ID} of the original toot.
     * @return The newly posted {@link Status Status}.
     * @throws BigBoneRequestException Throws an exception,
     * if there is a communication error with the configured mastodon instance or the <code>Status ID</code>
     * or the contend is invalid. E.g. it could be to long.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#create">Mastodon API Post a new status</a>
     * @see social.bigbone.api.method.StatusMethods#postStatus
     */
    public Status replyToStatus(final String statusText, final String inReplyToId) throws BigBoneRequestException;

    /**
     * Creates a new replay to a toot on mastodon with direct visibility.
     * The toot will be {@link social.bigbone.api.entity.data.Visibility#DIRECT direct}, in english and without sensitivity warning or spoiler text.
     *
     * @param statusText The text of the new toot.
     * @param inReplyToId The {@link Status#getId() Status ID} of the original toot.
     * @return The newly posted {@link Status Status}.
     * @throws BigBoneRequestException Throws an exception,
     * if there is a communication error with the configured mastodon instance or the <code>Status ID</code>
     * or the contend is invalid. E.g. it could be to long.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#create">Mastodon API Post a new status</a>
     * @see social.bigbone.api.method.StatusMethods#postStatus
     */
    public Status replyDirectToStatus(final String statusText, final String inReplyToId) throws BigBoneRequestException;


    /**
     * Favourite a status on mastodon.
     *
     * @param statusId The {@link Status#getId() Status ID} of the toot you want to favourite.
     * @return The {@link Status Status} you favoured.
     * @throws BigBoneRequestException Throws an exception, if there is a communication error with the configured mastodon instance or the <code>Status ID</code> is invalid.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#favourite">Mastodon API Favourite a status</a>
     * @see <a href="https://docs.joinmastodon.org/entities/Status">Mastodon API Status</a>
     * @see social.bigbone.api.method.StatusMethods#favouriteStatus(String)
     */
    public Status favouriteStatus(final String statusId) throws BigBoneRequestException;

    /**
     * Gets a complete list of all accounts, that favoured a status on mastodon.
     *
     * @param statusId The {@link Status#getId() Status ID} of the toot you want to get the accounts, which favoured it.
     * @return The <code>List</code> of {@link Account Accounts}, which favoured the toot.
     * @throws BigBoneRequestException Throws an exception, if there is a communication error with the configured mastodon instance
     * or the <code>Status ID</code> is invalid.
     *
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#favourited_by">Mastodon API See who favourited a status</a>
     * @see <a href="https://docs.joinmastodon.org/entities/Status">Mastodon API Status</a>
     * @see social.bigbone.api.method.StatusMethods#getFavouritedBy
     */
    public List<Account> getFavouritedBy(final String statusId) throws BigBoneRequestException;
}
