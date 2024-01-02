package de.seism0saurus.hectoc.bot.mastodon;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import social.bigbone.MastodonClient;
import social.bigbone.api.entity.Account;
import social.bigbone.api.entity.Context;
import social.bigbone.api.entity.Status;
import social.bigbone.api.entity.data.Visibility;
import social.bigbone.api.exception.BigBoneRequestException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of the {@link StatusRepository StatusRepository} to access, post and favourite toots as {@link social.bigbone.api.entity.Status Status}.
 *
 * @author seism0saurus
 */
@Service
public class StatusRepositoryImpl implements StatusRepository {

    /**
     * The {@link org.slf4j.Logger Logger} for this class.
     * The logger is used for logging as configured for the application.
     *
     * @see "src/main/ressources/logback.xml"
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(StatusRepositoryImpl.class);

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
    public StatusRepositoryImpl(
            @Value(value = "${mastodon.instance}") String instance,
            @Value(value = "${mastodon.accessToken}") String accessToken) {
        this.client = new MastodonClient.Builder(instance)
                .accessToken(accessToken)
                .setReadTimeoutSeconds(240)
                .setReadTimeoutSeconds(240)
                .build();
        LOGGER.info("StatusInterfaceImpl for mastodon instance " + instance + " created");
    }

    public Context getContext(final String statusId) throws BigBoneRequestException {
        LOGGER.debug("Fetch Context for statusID " + statusId + " created");
        Context context = this.client.statuses().getContext(statusId).execute();
        return context;
    }

    ;

    public String getChallenge(final String statusId) throws BigBoneRequestException {
        LOGGER.debug("Fetch content of Status with id " + statusId);
        String content = this.client.statuses().getStatus(statusId).execute().getContent();
        String challenge = extractChallenge(content);
        return challenge;
    }

    ;

    public Status postStatus(final String statusText) throws BigBoneRequestException {
        return getStatus(statusText, Visibility.PUBLIC);
    }

    ;

    public Status postDirectStatus(final String statusText) throws BigBoneRequestException {
        return getStatus(statusText, Visibility.DIRECT);
    }

    /**
     * Creates a new toot on mastodon for test purpose.
     * The toot will be of the of given visibility, in english and without sensitivity warning or spoiler text.
     *
     * @param statusText The text of the new toot.
     * @param visibility The visibility of the new toot.
     * @return The newly posted {@link Status Status}.
     * @throws BigBoneRequestException Throws an exception,
     *                                 if there is a communication error with the configured mastodon instance or the contend is invalid.
     *                                 E.g. it could be to long.
     * @see <a href="https://docs.joinmastodon.org/methods/statuses/#create">Mastodon API Post a new status</a>
     * @see social.bigbone.api.method.StatusMethods#postStatus
     */
    private Status getStatus(final String statusText, final Visibility visibility) throws BigBoneRequestException {
        LOGGER.debug("Post new Status");
        final String inReplyToId = null;
        final boolean sensitive = false;
        final String spoilerText = null;
        final String language = "en";
        final List<String> mediaIds = List.of();
        Status status = client.statuses().postStatus(statusText, mediaIds, visibility, inReplyToId, sensitive, spoilerText, language).execute();
        return status;
    }

    ;

    public Status replyToStatus(final String statusText, final String inReplyToId) throws BigBoneRequestException {
        LOGGER.debug("Answer status " + inReplyToId);
        final Visibility visibility = Visibility.DIRECT;
        final boolean sensitive = false;
        final String spoilerText = null;
        final String language = "en";
        final List<String> mediaIds = List.of();
        Status status = client.statuses().postStatus(statusText, mediaIds, visibility, inReplyToId, sensitive, spoilerText, language).execute();
        return status;
    }

    ;

    public Status favouriteStatus(final String statusId) throws BigBoneRequestException {
        LOGGER.debug("Favour status " + statusId);
        Status status = this.client.statuses().favouriteStatus(statusId).execute();
        return status;
    }

    ;

    public List<Account> getFavouritedBy(final String statusId) throws BigBoneRequestException {
        LOGGER.debug("Get list of favourites for status " + statusId);
        List<Account> accounts = this.client.statuses().getFavouritedBy(statusId).execute().getPart();
        return accounts;
    }

    ;

    @Nullable
    private String extractChallenge(final String status) {
        String solution = null;
        Pattern p = Pattern.compile("<(br|p)>([123456789]{6})");
        Matcher matcher = p.matcher(status);
        if (matcher.find()) {
            solution = matcher.group(2);
        }
        return solution;
    }
}
