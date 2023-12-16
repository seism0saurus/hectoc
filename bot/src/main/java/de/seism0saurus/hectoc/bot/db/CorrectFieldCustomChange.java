package de.seism0saurus.hectoc.bot.db;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class CorrectFieldCustomChange implements CustomTaskChange {

    private ResourceAccessor resourceAccessor;

    @Override
    public void execute(Database database) throws CustomChangeException {
        JdbcConnection databaseConnection = (JdbcConnection) database.getConnection();
        String sqlSelect = "SELECT n.id, c.challenge, n.solution FROM notifications n JOIN challenges c ON n.challenge_id = c.id";
        try (Statement statement = databaseConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlSelect)) {

            while (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String challenge = resultSet.getString("challenge");
                String proposedSolution = resultSet.getString("solution");
                boolean correct = false;
                try {
                    HectocSolution solution = new HectocSolution(new HectocChallenge(challenge));
                    boolean correctSolutionSubmitted = solution.checkSolution(proposedSolution);
                    if (correctSolutionSubmitted) {
                        correct = true;
                    }
                } catch (IllegalArgumentException e) {
                    // ignore since it is already false
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
