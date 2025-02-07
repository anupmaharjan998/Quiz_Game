package DAO.Implementation;

import DAO.ScoreDAO;
import Model.Question;
import Model.ScoreModel;

import Connection.DBConnection;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreDAOImp implements ScoreDAO {
    @Override
    public ScoreModel getScore(String user_id, String level_id) {
        ScoreModel score = null;

        try (Connection conn = DBConnection.getConnection()){
            String query = "SELECT * FROM Scores WHERE user_id = ? AND level_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user_id);
            pstmt.setString(2, level_id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                score = new ScoreModel(
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

    @Override
    public List<ScoreModel> getScoreByUserID(String user_id) {
        List<ScoreModel> scores = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()){
            String query = "SELECT s.*, l.level_name FROM Scores s JOIN Levels l ON s.level_id = l.level_id WHERE user_id = ?";
//            String query = "SELECT c.user_id, c.username, c.password, r.role_name FROM Users c JOIN Roles r ON c.role_id = r.role_id WHERE c.username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user_id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ScoreModel score = new ScoreModel(
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

    @Override
    public boolean addScore(ScoreModel score) {
        float[] avgCount = calculateAvgScore(score.getScores());
        String query = "INSERT INTO Scores (score_id, user_id, level_id, score, avg_score, total_quizzes) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, score.getScoreId());
            pstmt.setString(2, score.getUserId());
            pstmt.setString(3, score.getLevelId());
            pstmt.setString(4, score.getScores());
            pstmt.setFloat(5, avgCount[0]);
            pstmt.setInt(6, (int) avgCount[1]);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the score was added successfully
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return false;
        }
    }

    @Override
    public boolean updateScore(ScoreModel score) {
        float[] avgCount = calculateAvgScore(score.getScores());
        try (Connection conn = DBConnection.getConnection()){
            String query = "UPDATE Scores SET score = ?, avg_score = ?, total_quizzes = ? WHERE user_id = ? AND level_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, score.getScores());
            pstmt.setFloat(2, avgCount[0]);
            pstmt.setInt(3, (int) avgCount[1]);
            pstmt.setString(4, score.getUserId());
            pstmt.setString(5, score.getLevelId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the score was updated successfully
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return false;
        }
    }

    private float[] calculateAvgScore(String scores) {
        // Split the scores string by commas
        String[] scoreArray = scores.split(",");

        // Initialize variables for sum and count
        float sum = 0;
        int count = 0;

        // Iterate through the score array
        for (String scoreStr : scoreArray) {
            try {
                // Convert the score string to a float
                float score = Float.parseFloat(scoreStr.trim());
                sum += score; // Add to the sum
                count++; // Increment the count
            } catch (NumberFormatException e) {
                // Handle the case where the score is not a valid float
                System.err.println("Invalid score: " + scoreStr);
            }
        }

        // Calculate the average
        float average = count > 0 ? sum / count : 0; // Return 0 if no valid scores
        float roundedAvg = Math.round(average * 100.0f) / 100.0f;

        // Return the result as an array: [average, count]
        return new float[]{roundedAvg, count};
    }
}
