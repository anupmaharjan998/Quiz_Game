package DAO.Implementation;

import DAO.ReportManagerDAO;
import Model.PlayerReport;
import Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportManagerDAOImp implements ReportManagerDAO {
    @Override
    public List<PlayerReport> getReportByLevel(String level_id) {
        List<PlayerReport> playersReport = new ArrayList<>();
        String query = "SELECT c.*, u.*, s.*, l.* FROM Competitors c JOIN Users u ON c.user_id = u.user_id JOIN Scores s ON c.user_id = s.user_id JOIN Levels l ON l.level_id = s.level_id WHERE s.level_id = ?";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, level_id);

            extractPlayerDetails(playersReport, pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playersReport;
    }

    @Override
    public PlayerReport getTopPlayerSummary(String level_id) {
        String query = "SELECT c.*, u.*, s.*, l.* FROM Competitors c JOIN Users u ON c.user_id = u.user_id JOIN Scores s ON c.user_id = s.user_id JOIN Levels l ON l.level_id = s.level_id WHERE s.level_id = ? AND s.avg_score = (SELECT MAX(avg_score) FROM Scores)";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, level_id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new PlayerReport(
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

    @Override
    public List<PlayerReport> searchPlayer(String keyword, String level_id) {
        List<PlayerReport> playersReport = new ArrayList<>();
        String query = "SELECT c.*, u.*, s.*, l.* FROM Competitors c JOIN Users u ON c.user_id = u.user_id JOIN Scores s ON c.user_id = s.user_id JOIN Levels l ON l.level_id = s.level_id WHERE s.level_id = ? AND (u.first_name LIKE ? OR u.last_name LIKE ? OR c.comp_id LIKE ?)";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, level_id);
            pstmt.setString(2, keyword);
            pstmt.setString(3, keyword);
            pstmt.setString(4, keyword);

            extractPlayerDetails(playersReport, pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playersReport;
    }

    @Override
    public PlayerReport getSelectedPlayerSummary(String level_id, String comp_id) {
        String query = "SELECT c.*, u.*, s.*, l.* FROM Competitors c JOIN Users u ON c.user_id = u.user_id JOIN Scores s ON c.user_id = s.user_id JOIN Levels l ON l.level_id = s.level_id WHERE s.level_id = ? AND c.comp_id = ?";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, level_id);
            pstmt.setString(2, comp_id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new PlayerReport(
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


    private void extractPlayerDetails(List<PlayerReport> playersReport, PreparedStatement pstmt) throws SQLException {
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            PlayerReport playerReport = new PlayerReport(
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
