package np.edu.herald.quizzapp.dao;

import np.edu.herald.quizzapp.model.Players;

import java.util.List;

/**
 * Interface for generating reports based on players' performance and level.
 */
public interface ReportManagerDAO {

    /**
     * Retrieves a list of players' report filtered by difficulty level.
     *
     * @param levelId the level ID used to filter players' reports.
     * @return a list of {@link Players} for the specified level.
     */
    List<Players> getReportByLevel(String levelId);

    /**
     * Retrieves the top player summary for a specified difficulty level.
     *
     * @param levelId the level ID for the top player summary.
     * @return the {@link Players} object for the top player of the specified level.
     */
    Players getTopPlayerSummary(String levelId);

    /**
     * Searches for players based on a keyword and level.
     *
     * @param keyword the keyword to search for.
     * @param levelId the level ID to filter the search by.
     * @return a list of {@link Players} matching the search criteria.
     */
    List<Players> searchPlayer(String keyword, String levelId);

    /**
     * Retrieves the summary of a selected player based on level and competition ID.
     *
     * @param levelId the level ID to filter the summary.
     * @param compId the competition ID for the selected player.
     * @return the {@link Players} object containing the selected player's summary.
     */
    Players getSelectedPlayerSummary(String levelId, String compId);
}
