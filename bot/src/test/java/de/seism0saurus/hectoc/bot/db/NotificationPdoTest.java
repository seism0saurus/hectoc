package de.seism0saurus.hectoc.bot.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The Tests for the NotificationPdo.
 *
 * @author seism0saurus
 */
public class NotificationPdoTest {

    @ParameterizedTest
    @MethodSource("syntacticalCorrectSolutions")
    public void parseSyntacticalCorrectSolutions(final String text, final String expected) {
        NotificationPdo pdo = NotificationPdo.builder().solution(text)
                .build();

        String actual = pdo.getSolution();

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> syntacticalCorrectSolutions() {
        return Stream.of(
                Arguments.of("<br>45+9*7-7-1<br>", "45+9*7-7-1"),
                Arguments.of("My solution:<br>4+98-3+8/8<br>Thanks for this bot!", "4+98-3+8/8"),
                Arguments.of("I'm not sure. Is this correct:<br>1^8*(5+5)*5*2<br>", "1^8*(5+5)*5*2"),
                Arguments.of("<br>(8+2)*1*(7+3)*1<br>Right?", "(8+2)*1*(7+3)*1"),
                Arguments.of("<p>69+37+2-8</p>", "69+37+2-8"),
                Arguments.of("Look at this: <p>-9/9+89+2*6</p>", "-9/9+89+2*6"),
                Arguments.of("<p>36+4+61-1</p>Is that correct?", "36+4+61-1"),
                Arguments.of("<br>65+32-5+8<br>", "65+32-5+8"),
                Arguments.of("<br>1*6+89+3+2<br>", "1*6+89+3+2"),
                Arguments.of("<br>(26+4)*3+4+6<br>", "(26+4)*3+4+6"),
                Arguments.of("<br>2+83+9+6*1<br>", "2+83+9+6*1"),
                Arguments.of("<br>(6+4)*(-2+7+1+4)<br>", "(6+4)*(-2+7+1+4)"),
                Arguments.of("<br>-5-3-3+111<br>", "-5-3-3+111"),
                Arguments.of("<br>99+7-5-1^8<br>", "99+7-5-1^8"),
                Arguments.of("<br>1*5*((7-4)*4+8)<br>", "1*5*((7-4)*4+8)"),
                Arguments.of("<br>(6+4)*(8*2*5/8)<br>", "(6+4)*(8*2*5/8)"),
                Arguments.of("<br>4*(-1+6+6+7*2)<br>", "4*(-1+6+6+7*2)"),
                Arguments.of("<br>4+7+88+6/6<br>", "4+7+88+6/6"),
                Arguments.of("<br>9-4-4+97+2<br>", "9-4-4+97+2"),
                Arguments.of("<br>2^2+99+5-8<br>", "2^2+99+5-8"),
                Arguments.of("<br>2^2+99+5-8<br>", "2^2+99+5-8"),
                Arguments.of("<br>32+1*61+7<br>", "32+1*61+7"),
                Arguments.of("<br>93*(-1-6+8)+7<br>", "93*(-1-6+8)+7"),
                Arguments.of("<br>(8+2)*(3+7)*(-8+9)<br>", "(8+2)*(3+7)*(-8+9)"),
                Arguments.of("<br>1*(4+4+2)*(7+3)<br>", "1*(4+4+2)*(7+3)"),
                Arguments.of("<br>(5*1+7-2)*(9+1)<br>", "(5*1+7-2)*(9+1)"),
                Arguments.of("<br>(9+4+7)*5*5/5<br>", "(9+4+7)*5*5/5"),
                Arguments.of("<br>1*88+2+5*2<br>", "1*88+2+5*2"),
                Arguments.of("<br>(3+7)*(-8+5+5+8)<br>", "(3+7)*(-8+5+5+8)"),
                Arguments.of("<br>2-8+1+98+7<br>", "2-8+1+98+7"),
                Arguments.of("<br>1*25*(5+3-4)<br>", "1*25*(5+3-4)"),
                Arguments.of("<br>-8/4+4*4+86<br>", "-8/4+4*4+86"),
                Arguments.of("<br>77*6/6+23<br>", "77*6/6+23"),
                Arguments.of("<br>(5+5)*(-3/3+8+3)<br>", "(5+5)*(-3/3+8+3)"),
                Arguments.of("<br>(6+5-1)*(5*(-6+8))<br>", "(6+5-1)*(5*(-6+8))"),
                Arguments.of("<br>3+(2+8)*(5+5)-3<br>", "3+(2+8)*(5+5)-3"),
                Arguments.of("<br>(6-2+1+5)*(3+7)<br>", "(6-2+1+5)*(3+7)"),
                Arguments.of("<br>-2-1+55+48<br>", "-2-1+55+48"),
                Arguments.of("<br>1-5+4+99+1<br>", "1-5+4+99+1"),
                Arguments.of("<br>25*(-2+8/8+5)<br>", "25*(-2+8/8+5)"),
                Arguments.of("<br>(-2+6+7-1)*(1+9)<br>", "(-2+6+7-1)*(1+9)"),
                Arguments.of("<br>96+4*7*1/7<br>", "96+4*7*1/7"),
                Arguments.of("<br>(-2+8-4+3)*5*4<br>", "(-2+8-4+3)*5*4"),
                Arguments.of("<br>1+9+3*4*7+6<br>", "1+9+3*4*7+6"),
                Arguments.of("<br>(-9+6+8+5)*(4+6)<br>", "(-9+6+8+5)*(4+6)"),
                Arguments.of("<br>5*22-(7+1+2)<br><br>", "5*22-(7+1+2)"),
                Arguments.of("5*22", null)
        );
    }

    @Test
    public void testEqualsTrue(){
        ZonedDateTime now = ZonedDateTime.now();
        ChallengePdo challengePdo = ChallengePdo.builder()
                .challenge("533712")
                .date(now)
                .statusId("5678")
                .build();
        NotificationPdo notificationPdo1 = NotificationPdo.builder()
                .date(now)
                .challenge(challengePdo)
                .solution("<br>5*22-(7+1+2)<br><br>")
                .author("Cthulhu")
                .correct(true)
                .statusId("123456")
                .dismissed(true)
                .notificationId("98765")
                .build();
        NotificationPdo notificationPdo2 =  new NotificationPdo();
                notificationPdo2.setDate(now);
                notificationPdo2.setChallenge(challengePdo);
                notificationPdo2.setSolution("doesntworkcorrectnext");
                notificationPdo2.setSolution("<br>5*22-(7+1+2)<br><br>");
                notificationPdo2.setAuthor("Cthulhu");
                notificationPdo2.setCorrect(true);
                notificationPdo2.setStatusId("123456");
                notificationPdo2.setDismissed(true);
                notificationPdo2.setNotificationId("98765");

        assertTrue(notificationPdo1.equals(notificationPdo2));
        assertTrue(notificationPdo2.equals(notificationPdo1));
    }

    @Test
    public void testEqualsFalse(){
        ZonedDateTime now = ZonedDateTime.now();
        ChallengePdo challengePdo = ChallengePdo.builder()
                .challenge("533712")
                .date(now)
                .statusId("5678")
                .build();
        NotificationPdo notificationPdo1 = NotificationPdo.builder()
                .date(now)
                .challenge(challengePdo)
                .solution("<br>5*22-(7+1+2)<br><br>")
                .author("Cthulhu")
                .correct(true)
                .statusId("123456")
                .dismissed(true)
                .notificationId("98765")
                .build();
        NotificationPdo notificationPdo2 =  new NotificationPdo();
                notificationPdo2.setDate(now);
                notificationPdo2.setChallenge(challengePdo);
                notificationPdo2.setSolution("doesntworkcorrectnext");
                notificationPdo2.setAuthor("Cthulhu");
                notificationPdo2.setCorrect(true);
                notificationPdo2.setStatusId("123456");
                notificationPdo2.setDismissed(true);
                notificationPdo2.setNotificationId("98765");

        assertFalse(notificationPdo1.equals(notificationPdo2));
        assertFalse(notificationPdo2.equals(notificationPdo1));
    }

    @Test
    public void testHashCode() {
        ZonedDateTime now = ZonedDateTime.now();
        ChallengePdo challengePdo = ChallengePdo.builder()
                .challenge("533712")
                .date(now)
                .statusId("5678")
                .build();
        NotificationPdo notificationPdo1 = NotificationPdo.builder()
                .date(now)
                .challenge(challengePdo)
                .solution("<br>5*22-(7+1+2)<br><br>")
                .author("Cthulhu")
                .correct(true)
                .statusId("123456")
                .dismissed(true)
                .notificationId("98765")
                .build();
        NotificationPdo notificationPdo2 = new NotificationPdo();
        notificationPdo2.setDate(now);
        notificationPdo2.setChallenge(challengePdo);
        notificationPdo2.setSolution("doesntworkcorrectnext");
        notificationPdo2.setAuthor("Cthulhu");
        notificationPdo2.setCorrect(true);
        notificationPdo2.setStatusId("123456");
        notificationPdo2.setDismissed(true);
        notificationPdo2.setNotificationId("98765");

        assertNotEquals(notificationPdo1.hashCode(), notificationPdo2.hashCode());
    }

    @Test
    public void testToString() {
        Clock clockMock = mock(Clock.class);
        when(clockMock.instant()).thenReturn(Instant.ofEpochSecond(10000l));
        when(clockMock.getZone()).thenReturn(ZoneId.of("UTC"));
        ZonedDateTime now = ZonedDateTime.now(clockMock);
        ChallengePdo challengePdo = ChallengePdo.builder()
                .challenge("533712")
                .date(now)
                .statusId("5678")
                .build();
        NotificationPdo notificationPdo = NotificationPdo.builder()
                .date(now)
                .challenge(challengePdo)
                .solution("<br>5*22-(7+1+2)<br><br>")
                .author("Cthulhu")
                .correct(true)
                .statusId("123456")
                .dismissed(true)
                .notificationId("98765")
                .build();
        String expected = "NotificationPdo(id=null, notificationId=98765, statusId=123456, author=Cthulhu, solution=5*22-(7+1+2), dismissed=true, challenge=ChallengePdo(id=null, challenge=533712, statusId=5678, date=1970-01-01T02:46:40Z[UTC]), date=1970-01-01T02:46:40Z[UTC], correct=true)";

        String actual = notificationPdo.toString();

        assertEquals(expected, actual);
    }
}


;