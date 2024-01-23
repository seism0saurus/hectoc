package de.seism0saurus.hectoc.bot.db;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChallengePdoTest {

    @Test
    public void testToString(){
        Clock clockMock = mock(Clock.class);
        when(clockMock.instant()).thenReturn(Instant.ofEpochSecond(10000l));
        when(clockMock.getZone()).thenReturn(ZoneId.of("UTC"));
        ZonedDateTime now = ZonedDateTime.now(clockMock);
        ChallengePdo challengePdo = ChallengePdo.builder()
                .id(UUID.fromString("AA97B177-9383-4934-8543-0F91A7A02836"))
                .challenge("533712")
                .date(now)
                .statusId("5678")
                .build();
        String expected = "ChallengePdo(id=aa97b177-9383-4934-8543-0f91a7a02836, challenge=533712, statusId=5678, date=1970-01-01T02:46:40Z[UTC])";

        String actual = challengePdo.toString();

        assertEquals(expected,actual);
    }
}
