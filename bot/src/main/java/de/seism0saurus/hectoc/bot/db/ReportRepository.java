package de.seism0saurus.hectoc.bot.db;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.UUID;

/**
 * The {@link JpaRepository JpaRepository} to store and access {@link ReportPdo ReportPdos}.
 *
 * @author seism0saurus
 */
public interface ReportRepository extends JpaRepository<ReportPdo, UUID> {

    /**
     * Fetches all {@link de.seism0saurus.hectoc.bot.db.ReportPdo ReportPdos} on the given day.
     *
     * @param day The date on which day the reports should be fetched.
     * @return The list of pdos on the day of the given date.
     */
    default List<ReportPdo> findAllOnDay(final ZonedDateTime day) {
        final ZonedDateTime morning = day.with(ChronoField.NANO_OF_DAY, 0);
        final ZonedDateTime evening = day.with(ChronoField.NANO_OF_DAY, 86400L * 1000_000_000L - 1);
        return findAllByDateBetween(morning, evening);
    }

    /**
     * Fetches all {@link de.seism0saurus.hectoc.bot.db.ReportPdo ReportPdos} between the given dates.
     *
     * @param from The date from which on we are fetching pdos.
     * @param to   The data until we are fetching pdos.
     * @return The list of pdos between the given dates.
     */
    List<ReportPdo> findAllByDateBetween(ZonedDateTime from, ZonedDateTime to);
}
