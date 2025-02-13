package np.edu.herald.quizzapp.dao.implementation;

import np.edu.herald.quizzapp.dao.ReportManagerDAO;
import np.edu.herald.quizzapp.model.Players;
import np.edu.herald.quizzapp.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ReportManagerDAO interface, responsible for managing
 * reports related to players, scores, and levels.
 */
public class ReportManagerDAOImp implements ReportManagerDAO {

    /**
     * Retrieves a report of players for a specific level.
     *
     * @param levelId The ID of the level.
     * @return A list of Players containing report details.
     */
    @Override
    public List<Players> getReportByLevel(String levelId) {
        List<Players> playersReport = new ArrayList<>();
        String query = "select c.*, u.*, s.*, l.* from Competitors c join Users u on c.user_id = u.user_id join Scores s on c.user_id = s.user_id join Levels l on l.level_id = s.level_id where s.level_id = ?";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, levelId);

            extractPlayerDetails(playersReport, stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playersReport;
    }

    /**
     * Retrieves the top player summary for a given level based on the highest average score.
     *
     * @param levelId The ID of the level.
     * @return A Players object containing the top player's details, or null if no player found.
     */
    @Override
    public Players getTopPlayerSummary(String levelId) {
        String query = "select c.*, u.*, s.*, l.* from Competitors c join Users u on c.user_id = u.user_id join Scores s on c.user_id = s.user_id join Levels l on l.level_id = s.level_id where s.level_id = ? and s.avg_score = (select max(avg_score) from Scores)";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, levelId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Players(
                        rs.getString("comp_id"),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getString("level_name"),
                        rs.getString("level_id"),
                        rs.getString("score"),
                        rs.getInt("total_quizzes"),
                        rs.getFloat("avg_score")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Searches for players based on a keyword and level ID.
     *
     * @param keyword The search keyword (matches first name, last name, or competitor ID).
     * @param levelId The level ID to filter by.
     * @return A list of Players matching the search criteria.
     */
    @Override
    public List<Players> searchPlayer(String keyword, String levelId) {
        List<Players> playersReport = new ArrayList<>();
        String query = "select c.*, u.*, s.*, l.* from Competitors c join Users u on c.user_id = u.user_id join Scores s on c.user_id = s.user_id join Levels l on l.level_id = s.level_id where s.level_id = ? and (u.first_name like ? or u.last_name like ? or c.comp_id like ?)";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, levelId);
            pstmt.setString(2, keyword);
            pstmt.setString(3, keyword);
            pstmt.setString(4, keyword);

            extractPlayerDetails(playersReport, pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playersReport;
    }

    /**
     * Retrieves summary details for a specific player in a given level.
     *
     * @param levelId The level ID.
     * @param compId The competitor ID.
     * @return A Players object containing the selected player's details, or null if not found.
     */
    @Override
    public Players getSelectedPlayerSummary(String levelId, String compId) {
        String query = "select c.*, u.*, s.*, l.* from Competitors c join Users u on c.user_id = u.user_id join Scores s on c.user_id = s.user_id join Levels l on l.level_id = s.level_id where s.level_id = ? and c.comp_id = ?";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, levelId);
            pstmt.setString(2, compId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Players(
                        rs.getString("comp_id"),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getString("level_name"),
                        rs.getString("level_id"),
                        rs.getString("score"),
                        rs.getInt("total_quizzes"),
                        rs.getFloat("avg_score")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * Helper method to extract player details from a ResultSet and populate the playersReport list.
     *
     * @param playersReport The list to store extracted player details.
     * @param stmt The PreparedStatement used to execute the query.
     * @throws SQLException If a database access error occurs.
     */
    private void extractPlayerDetails(List<Players> playersReport, PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Players playerReport = new Players(
                    rs.getString("comp_id"),
                    rs.getString("first_name") + " " + rs.getString("last_name"),
                    rs.getString("level_name"),
                    rs.getString("level_id"),
                    rs.getString("score"),
                    rs.getInt("total_quizzes"),
                    rs.getFloat("avg_score")
            );
            playersReport.add(playerReport);
        }
    }
}
