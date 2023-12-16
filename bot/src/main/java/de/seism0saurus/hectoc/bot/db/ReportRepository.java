package de.seism0saurus.hectoc.bot.db;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * The {@link JpaRepository JpaRepository} to store and access {@link ReportPdo ReportPdos}.
 *
 * @author seism0saurus
 */
public interface ReportRepository extends JpaRepository<ReportPdo, UUID> {

}
