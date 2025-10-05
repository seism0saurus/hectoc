package de.seism0saurus.hectoc.bruteforcer.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface Repository  extends JpaRepository<ChallengePdo, Long> {
    Optional<ChallengePdo> findByChallenge(String string);
}
