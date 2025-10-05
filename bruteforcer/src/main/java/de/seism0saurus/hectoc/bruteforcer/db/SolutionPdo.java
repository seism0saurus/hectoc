package de.seism0saurus.hectoc.bruteforcer.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Diese Klasse repräsentiert eine Entität in der Tabelle "solution".
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solutions")
public class SolutionPdo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID der Lösung

    private String solution;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private ChallengePdo challenge; // Zugehörige Challenge
}