package de.seism0saurus.hectoc.bot;

import de.seism0saurus.hectoc.bot.db.NotificationPdo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
     * @return A complete text for a report.
     */
    public String getReportText(List<NotificationPdo> answers) {
        Random rand = new Random();
        final String randomGreeting = greetings.get(rand.nextInt(greetings.size()));
        long totalAnswers = answers.size();
        long correctAnswers = answers.stream().filter(NotificationPdo::isCorrect).count();
        long wrongAnswers = totalAnswers - correctAnswers;
        long participants = answers.stream().map(NotificationPdo::getAuthor).distinct().count();
        List<Map.Entry<String, Long>> mostActiveParticipants = answers.stream()
                .filter(NotificationPdo::isCorrect)
                .collect(Collectors.groupingBy(NotificationPdo::getAuthor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((m1, m2) -> Long.compare(m1.getValue(), m2.getValue()))
                .toList();
        String topParticipants = "";
        if (mostActiveParticipants.size() >= 3) {
            Map.Entry<String, Long> mostActiveParticipant = mostActiveParticipants.get(0);
            Map.Entry<String, Long> secondMostActiveParticipant = mostActiveParticipants.get(1);
            Map.Entry<String, Long> thirdMostActiveParticipant = mostActiveParticipants.get(2);
            topParticipants = "The top three participants of this month are:\n" +
                    " 1.) " + mostActiveParticipant.getKey() + " with " + mostActiveParticipant.getValue() + " correct solutions" +
                    " 2.) " + secondMostActiveParticipant.getKey() + " with " + secondMostActiveParticipant.getValue() + " correct solutions" +
                    " 3.) " + thirdMostActiveParticipant.getKey() + " with " + thirdMostActiveParticipant.getValue() + " correct solutions" +
                    "\n\nCongratulations!\n\n";
        } else if (mostActiveParticipants.size() == 2) {
            Map.Entry<String, Long> mostActiveParticipant = mostActiveParticipants.get(0);
            Map.Entry<String, Long> secondMostActiveParticipant = mostActiveParticipants.get(1);
            topParticipants = "The top two participants of this month are:\n" +
                    " 1.) " + mostActiveParticipant.getKey() + " with " + mostActiveParticipant.getValue() + " correct solutions" +
                    " 2.) " + secondMostActiveParticipant.getKey() + " with " + secondMostActiveParticipant.getValue() + " correct solutions" +
                    "\n\nCongratulations!\n\n";
        } else if (mostActiveParticipants.size() == 1) {
            Map.Entry<String, Long> mostActiveParticipant = mostActiveParticipants.getFirst();
            topParticipants = "The top participant of this month is:\n" +
                    " " + mostActiveParticipant.getKey() + " with " + mostActiveParticipant.getValue() + " correct solutions" +
                    "\n\nCongratulations!\n\n";
        }

        final String tagLine = tags.stream().map(s -> "#" + s).collect(Collectors.joining(" "));
        return randomGreeting + "\n"
                + "In the last month we had " + totalAnswers + " total answers from " + participants + " participants. \n"
                + "From these proposed solutions " + correctAnswers + " where correct. Only " + wrongAnswers + " were wrong.\n"
                + "Maybe there were some I couldn't understand. \n"
                + topParticipants
                + salutation + "\n\n"
                + tagLine;
    }
}
