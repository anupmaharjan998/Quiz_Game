//package java11.dao.imp;
//
//import connection.DBConnection;
//import dao.CompetitorDAO;
//import model.Competitor;
//import model.Name;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CompetitorDaoImp implements CompetitorDAO {
//
//    @Override
//    public int addCompetitor(Competitor competitor) throws SQLException, ClassNotFoundException {
//        String query = "INSERT INTO Competitors (competitor_id, first_name, last_name, level, country, age, score, avg_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = Connection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, competitor.getCompetitorId());
//            stmt.setString(2, competitor.getName().getFirstName());
//            stmt.setString(3, competitor.getName().getLastName());
//            stmt.setString(4, competitor.getCompetitorLevel());
//            stmt.setString(5, competitor.getCountry());
//            stmt.setInt(6, competitor.getAge());
//            stmt.setString(7, competitor.getScores());
//            stmt.setDouble(8, competitor.getOverallScore());
//
//            return stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return 0;
//    }
//
//    @Override
//    public int updateCompetitor(Competitor competitor, int id) throws SQLException, ClassNotFoundException {
//        String query = "UPDATE Competitors SET first_name = ?, last_name = ?, level = ?, country = ?, age = ?, score = ?, avg_score = ? WHERE competitor_id = ?";
//
//        try (Connection conn = Connection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, competitor.getName().getFirstName());
//            stmt.setString(2, competitor.getName().getLastName());
//            stmt.setString(3, competitor.getCompetitorLevel());
//            stmt.setString(4, competitor.getCountry());
//            stmt.setInt(5, competitor.getAge());
//            stmt.setString(6, competitor.getScores());
//            stmt.setDouble(7, competitor.getOverallScore());
//            stmt.setInt(8, id);
//
//            return stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//
//    @Override
//    public int deleteCompetitor(int competitorId) throws SQLException, ClassNotFoundException {
//        String query = "DELETE FROM Competitors WHERE competitor_id = ?";
//
//        try (Connection conn = Connection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, competitorId);
//            return stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return 0;
//    }
//
//    @Override
//    public Competitor getCompetitorById(int competitorId) throws SQLException, ClassNotFoundException {
//        String query = "SELECT * FROM Competitors WHERE competitor_id = ?";
//        Competitor competitor = null;
//
//        try (Connection conn = Connection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, competitorId);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                competitor = extractCompetitorFromResultSet(rs);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        return competitor;
//    }
//
//    @Override
//    public List<Competitor> getAllCompetitor() throws SQLException, ClassNotFoundException {
//        List<Competitor> competitors = new ArrayList<>();
//        String query = "SELECT * FROM Competitors";
//
//        try (Connection conn = Connection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                Competitor competitor = extractCompetitorFromResultSet(rs);
//                competitors.add(competitor);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        return competitors;
//    }
//
//    @Override
//    public List<Competitor> searchCompetitor(String keyword) throws SQLException, ClassNotFoundException {
//        List<Competitor> competitors = new ArrayList<>();
//        String query = "SELECT * FROM Competitors WHERE first_name LIKE ? OR last_name LIKE ? OR competitor_id LIKE ?";
//
//        try (Connection conn = Connection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            String searchPattern = "%" + keyword + "%";
//            stmt.setString(1, searchPattern);
//            stmt.setString(2, searchPattern);
//            stmt.setString(3, searchPattern);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    Competitor competitor = extractCompetitorFromResultSet(rs);
//                    competitors.add(competitor);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return competitors;
//    }
//
//
//    private Competitor extractCompetitorFromResultSet(ResultSet rs) throws SQLException {
//        int id = rs.getInt("competitor_id");
//        String firstName = rs.getString("first_name");
//        String lastName = rs.getString("last_name");
//        String level = rs.getString("level");
//        String country = rs.getString("country");
//        int age = rs.getInt("age");
//        String scores = rs.getString("score");
//        double avg_score = rs.getDouble("avg_score");
//
//        Name name = new Name(firstName, lastName);
//        return new Competitor(id, name, level, country, age, scores, avg_score);
//    }
//}