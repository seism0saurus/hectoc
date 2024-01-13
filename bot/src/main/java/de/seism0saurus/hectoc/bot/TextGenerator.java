package de.seism0saurus.hectoc.bot;

import de.seism0saurus.hectoc.bot.db.NotificationPdo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A generator for texts for toots on mastodon.
 *
 * @author seism0saurus
 */
@Service
public class TextGenerator {

    /**
     * A list of greetings to greet all of your readers.
     * Add more to give the bot more options for its greeting.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("#{'${mastodon.text.greeting}'.split(';')}")
    private List<String> greetings;

    /**
     * A greeting to speak to a concrete reader.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("${mastodon.text.direct-greeting}")
    private String directGreeting;

    /**
     * A list of challenge announcements.
     * Add more to give the bot more options to announce a new challenge.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("#{'${mastodon.text.challenge}'.split(';')}")
    private List<String> challenges;

    /**
     * The hectoc explanation text.
     * Explain the concept of hectocs to the readers.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("${mastodon.text.explanation}")
    private String explanation;

    /**
     * The salutation to end the toots and wish the readers luck.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("${mastodon.text.salutation}")
    private String salutation;

    /**
     * The bug report text to give the readers the possibility to improve the bot.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("${mastodon.text.error.bugreport}")
    private String bugreport;

    /**
     * The text, if no solution could be found in a reply.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("${mastodon.text.error.not-found}")
    private String notFound;

    /**
     * The text, if the found solution is not valid.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("${mastodon.text.error.wrong-solution}")
    private String wrongSolution;

    /**
     * A list of replies for correct solutions.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("#{'${mastodon.text.correct-solutions}'.split(';')}")
    private List<String> correctSolutions;

    /**
     * A list of hashtags.
     * Add more to give the bot more range. But keep it simple and precise.
     * Can be configured in the <code>application.properties</code>.
     */
    @Value("#{'${mastodon.text.tags}'.split(';')}")
    private List<String> tags;

    /**
     * Creates a complete text for a new challenge toot.
     * The created text consists on a random greeting, the provided challenge,
     * an explanation how to solve hectocs, a salutation and a list of hashtags.
     *
     * @param challenge The challenge for which the text should be created.
     * @return A complete text for a challenge toot.
     */
    public String getChallengeText(final String challenge) {
        Random rand = new Random();
        final String randomGreeting = greetings.get(rand.nextInt(greetings.size()));
        final String randomChallenge = challenges.get(rand.nextInt(challenges.size()));
        final String tagLine = tags.stream().map(s -> "#" + s).collect(Collectors.joining(" "));
        return randomGreeting + "\n"
                + randomChallenge + "\n\n"
                + challenge + "\n\n"
                + explanation + "\n\n"
                + salutation + "\n\n"
                + tagLine;
    }

    /**
     * Creates a complete text for a response, there the solution could not be found in the toot.
     *
     * @param username The username to respond to.
     * @return A complete text for an answer.
     */
    public String notFound(final String username) {
        return directGreeting
                + username + "\n\n"
                + notFound + "\n\n"
                + bugreport;
    }

    /**
     * Creates a complete text for a response, there the solution is wrong.
     *
     * @param username The username to respond to.
     * @return A complete text for an answer.
     */
    public String wrongAnswer(final BigDecimal result, final String username) {
        return directGreeting
                + username + "\n\n"
                + wrongSolution + ": "
                + result.toString() + "\n\n"
                + bugreport;
    }

    /**
     * Creates a complete text for a report.
     * The report contains the total number of answers, correct and false ones.
     * Also, the number of participants ist counted and the participant with the most correct answers is calculated.
     *
     * @param answers The list of proposed solutions.
     * @param publicPost The participants will be directly addressed, if it is a public post. So they will get a notification about their score.
     * @return A complete text for a report.
     */
    public String getReportText(List<NotificationPdo> answers, final boolean publicPost) {
        Random rand = new Random();
        final String randomGreeting = greetings.get(rand.nextInt(greetings.size()));
        long totalAnswers = answers.size();
        long correctAnswers = answers.stream().filter(NotificationPdo::isCorrect).count();
        long wrongAnswers = totalAnswers - correctAnswers;
        long participants = answers.stream().map(NotificationPdo::getAuthor).distinct().count();
        final List<Map.Entry<String, Long>> mostActiveParticipants = getTopParticipants(answers);
        final String topParticipants = getTopParticipantsText(mostActiveParticipants, publicPost);
        final String tagLine = tags.stream().map(s -> "#" + s).collect(Collectors.joining(" "));

        return randomGreeting + "\n"
                + "In the last month we had " + totalAnswers + " total answers from " + participants + " participants. \n"
                + "From these proposed solutions " + correctAnswers + " where correct. Only " + wrongAnswers + " were wrong.\n"
                + "Maybe there were some I couldn't understand. \n"
                + topParticipants
                + salutation + "\n\n"
                + tagLine;
    }

    @NotNull
    /*
     * Creates a list with the participants sorted by the most correct answers.
     *
     * answers The list with the proposed solutions
     * Returns a sorted list with mappings from the users to their number of correct answers
     */
    private static List<Map.Entry<String, Long>> getTopParticipants(List<NotificationPdo> answers) {
        return answers.stream()
                .filter(NotificationPdo::isCorrect)
                .collect(Collectors.groupingBy(NotificationPdo::getAuthor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();
    }

    @NotNull
    /*
     * Make a text out of the list of most active participants and address them if it is a public post.
     *
     * mostActiveParticipants The list of mappings of participants and their number of correct answers
     * publicPost The participants will be directly addressed, if it is a public post, so they get a notification about their score
     *
     * Returns a ready to use text about the top participants
     */
    private static String getTopParticipantsText(final List<Map.Entry<String, Long>> mostActiveParticipants, final boolean publicPost) {
        String topParticipants = "";
        if (mostActiveParticipants.size() >= 3) {
            Map.Entry<String, Long> mostActiveParticipant = mostActiveParticipants.get(0);
            Map.Entry<String, Long> secondMostActiveParticipant = mostActiveParticipants.get(1);
            Map.Entry<String, Long> thirdMostActiveParticipant = mostActiveParticipants.get(2);
            topParticipants = "The top three participants of this month are:\n" +
                    getParticipantLine(mostActiveParticipant, 1, publicPost) +
                    getParticipantLine(secondMostActiveParticipant, 2, publicPost) +
                    getParticipantLine(thirdMostActiveParticipant, 3, publicPost) +
                    "\nCongratulations!\n\n";
        } else if (mostActiveParticipants.size() == 2) {
            Map.Entry<String, Long> mostActiveParticipant = mostActiveParticipants.get(0);
            Map.Entry<String, Long> secondMostActiveParticipant = mostActiveParticipants.get(1);
            topParticipants = "The top two participants of this month are:\n" +
                    getParticipantLine(mostActiveParticipant, 1, publicPost) +
                    getParticipantLine(secondMostActiveParticipant, 2, publicPost) +
                    "\nCongratulations!\n\n";
        } else if (mostActiveParticipants.size() == 1) {
            Map.Entry<String, Long> mostActiveParticipant = mostActiveParticipants.getFirst();
            topParticipants = "The top participant of this month is:\n" +
                    getParticipantLine(mostActiveParticipant, 1, publicPost) +
                    "\nCongratulations!\n\n";
        }
        return topParticipants;
    }

    @NotNull
    private static String getParticipantLine(final Map.Entry<String, Long> participant, final int place, final boolean publicPost) {
        String result = " " + place + ".) ";
        result += publicPost ? "@" : "";
        result += participant.getKey();
        result += " with " + participant.getValue();
        result += " correct solutions\n";
        return result;
    }

    /**
     * Creates a complete text for a response, there the solution is wrong.
     *
     * @param username The username to respond to.
     * @return A complete text for an answer.
     */
    public String correctAnswer(final String username) {
        Random rand = new Random();
        final String correctSolution = correctSolutions.get(rand.nextInt(correctSolutions.size()));
        final String tagLine = tags.stream().map(s -> "#" + s).collect(Collectors.joining(" "));

        return directGreeting
                + username + "\n\n"
                + correctSolution + "\n\n"
                + tagLine;
    }
}
