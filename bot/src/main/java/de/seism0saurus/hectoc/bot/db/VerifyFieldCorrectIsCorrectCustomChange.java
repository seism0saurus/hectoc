package de.seism0saurus.hectoc.bot.db;

import de.seism0saurus.hectoc.bot.ReportScheduler;
import de.seism0saurus.hectoc.generator.HectocChallenge;
import de.seism0saurus.hectoc.shuntingyardalgorithm.HectocSolution;
import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EmptyStackException;
import java.util.UUID;

public class VerifyFieldCorrectIsCorrectCustomChange implements CustomTaskChange {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyFieldCorrectIsCorrectCustomChange.class);

    private ResourceAccessor resourceAccessor;

    @Override
    public void execute(Database database) throws CustomChangeException {
        int wrongEntries = 0;
        JdbcConnection databaseConnection = (JdbcConnection) database.getConnection();
        String sqlSelect = "SELECT n.id, n.correct, c.challenge, n.solution FROM notifications n JOIN challenges c ON n.challenge_id = c.id";
        try (Statement statement = databaseConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlSelect)) {

            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String challenge = resultSet.getString("challenge");
                String proposedSolution = resultSet.getString("solution");
                boolean correct = resultSet.getBoolean("correct");
                try {
                    HectocSolution solution = new HectocSolution(new HectocChallenge(challenge));
                    boolean correctSolutionSubmitted = solution.checkSolution(proposedSolution);
                    if (correctSolutionSubmitted != correct) {
                        LOGGER.warn("Values for id differ: " + id + ". DB: " + correct + ", actual: " + correctSolutionSubmitted);
                        wrongEntries++;
                        correct = correctSolutionSubmitted;
                    }
                } catch (IllegalArgumentException | EmptyStackException e) {
                    LOGGER.warn("Could not calculate value for id " + id, e);
                } finally {
                    String sqlUpdate = "UPDATE notifications set correct = ? where id = ?";
                    try (PreparedStatement updateStatement = databaseConnection.prepareStatement(sqlUpdate)) {
                        updateStatement.setBoolean(1, correct);
                        updateStatement.setObject(2, id);
                        updateStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new CustomChangeException("Error executing SQL update: " + sqlUpdate, e);
                    }
                }
            }
        } catch (SQLException | DatabaseException e) {
            throw new CustomChangeException("Error executing SQL query: " + sqlSelect, e);
        }
        LOGGER.info("Total of " + wrongEntries + " had to be corrected.");
    }

    @Override
    public String getConfirmationMessage() {
        return "Values for empty fields 'correct' in table 'notifications' calculated.";
    }

    @Override
    public void setUp() throws SetupException {
        // Nothing to do
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
        this.resourceAccessor = resourceAccessor;
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }
}
