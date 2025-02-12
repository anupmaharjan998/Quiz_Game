package np.edu.herald.quizzapp.dao.implementation;

import np.edu.herald.quizzapp.dao.ScoreDAO;
import np.edu.herald.quizzapp.model.Scores;
import np.edu.herald.quizzapp.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ScoreDAO interface.
 * Provides functionality to interact with the Scores table in the database.
 */
public class ScoreDAOImp implements ScoreDAO {

    /**
     * Retrieves a score entry for a specific user and level.
     *
     * @param userId  The ID of the user.
     * @param levelId The ID of the level.
     * @return The Scores object if found, otherwise null.
     */
    @Override
    public Scores getScore(String userId, String levelId) {
        Scores score = null;
        String query = "select * from Scores where user_id = ? and level_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, userId);
            pstmt.setString(2, levelId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                score = new Scores(
                        rs.getString("user_id"),
                        rs.getString("level_id"),
                        rs.getString("score")
                );
                score.setScoreId(rs.getString("score_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return score;
    }

    /**
     * Retrieves all scores for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of Scores objects for the given user.
     */
    @Override
    public List<Scores> getScoreByUserID(String userId) {
        List<Scores> scores = new ArrayList<>();
        String query = "select s.*, l.level_name from Scores s join Levels l on s.level_id = l.level_id where user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Scores score = new Scores(
                        rs.getString("user_id"),
                        rs.getString("level_id"),
                        rs.getString("score")
                );
                score.setScoreId(rs.getString("score_id"));
                score.setLevelName(rs.getString("level_name"));
                scores.add(score);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scores;
    }

    /**
     * Adds a new score entry to the database.
     *
     * @param score The Scores object containing the data to be inserted.
     */
    @Override
    public void addScore(Scores score) {
        float[] avgCount = new AvgCalculationImp().calculateAvgScore(score.getScores());
        String query = "insert into Scores (score_id, user_id, level_id, score, avg_score, total_quizzes) values (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, score.getScoreId());
            pstmt.setString(2, score.getUserId());
            pstmt.setString(3, score.getLevelId());
            pstmt.setString(4, score.getScores());
            pstmt.setFloat(5, avgCount[0]);
            pstmt.setInt(6, (int) avgCount[1]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }

    /**
     * Updates an existing score entry in the database.
     *
     * @param score The Scores object containing updated data.
     */
    @Override
    public void updateScore(Scores score) {
        float[] avgCount = new AvgCalculationImp().calculateAvgScore(score.getScores());
        String query = "update Scores set score = ?, avg_score = ?, total_quizzes = ? where user_id = ? and level_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, score.getScores());
            pstmt.setFloat(2, avgCount[0]);
            pstmt.setInt(3, (int) avgCount[1]);
            pstmt.setString(4, score.getUserId());
            pstmt.setString(5, score.getLevelId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }
}
