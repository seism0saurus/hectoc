package de.seism0saurus.hectoc.bruteforcer.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "challenges")
public class ChallengePdo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String challenge;
    private boolean solveable;
    private long tries;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "challenge")
    private List<SolutionPdo> solutionPdos = new ArrayList<>();
}