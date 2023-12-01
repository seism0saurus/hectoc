package de.seism0saurus.hectoc.bot.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;
import social.bigbone.api.entity.Status;

import java.util.UUID;

/**
 * The Challenge to process and store a challenge and it's corresponding status id in mastodon.
 *
 * @author seism0saurus
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "challenges")
public class ChallengePdo {

    /**
     * The id of this class in form of a {@link java.util.UUID UUID}.
     * The id will be automatically generated by the database layer if left empty.
     *
     * Example: AA97B177-9383-4934-8543-0F91A7A02836
     *
     * @see <a href="https://www.ietf.org/rfc/rfc4122.txt">rfc4122</a>
     */
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    /**
     * The actual hectoc challenge as String.
     * A challenge consists of 6 digits from 1 to 9.
     *
     * Example: 991771
     */
    @Column
    @Length(max = 6)
    private String challenge;

    /**
     * The status id of the toot with the challenge.
     *
     * Example: 111425781005997018
     *
     * @see <a href="https://docs.joinmastodon.org/entities/Status/#id">Mastodon API Status ID</a>
     * @see Status#getId()
     */
    @Column
    @Length(max = 255)
    private String statusId;
}
