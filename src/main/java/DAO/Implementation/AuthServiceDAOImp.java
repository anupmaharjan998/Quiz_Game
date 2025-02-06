package DAO.Implementation;

import Connection.DBConnection;
import DAO.AuthServiceDAO;
import Model.LoginModel;
import Model.RegisterModel;

import java.sql.*;
import java.util.UUID;

public class AuthServiceDAOImp implements AuthServiceDAO {
    @Override
    public LoginModel   login(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT c.user_id, c.username, c.password, r.role_name FROM Users c JOIN Roles r ON c.role_id = r.role_id WHERE c.username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    return new LoginModel(rs.getString("user_id"), username, password, rs.getString("role_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(RegisterModel model) {
        String queryUser = "INSERT INTO Users (user_id, first_name, last_name, age, country, username, password, role_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, (SELECT role_id FROM Roles WHERE role_name = 'PLAYER'))";

        String queryCompetitor = "INSERT INTO Competitors (comp_id, user_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            UUID userId = UUID.randomUUID();
            UUID competitorId = UUID.randomUUID();

            // Insert User
            try (PreparedStatement stmtUser = conn.prepareStatement(queryUser)) {
                stmtUser.setString(1, userId.toString());
                stmtUser.setString(2, model.getFirstName());
                stmtUser.setString(3, model.getLastName());
                stmtUser.setInt(4, model.getAge());
                stmtUser.setString(5, model.getCountry());
                stmtUser.setString(6, model.getUsername());
                stmtUser.setString(7, model.getPassword());

                int rowsInserted = stmtUser.executeUpdate();

                if (rowsInserted == 0) {
                    conn.rollback(); // Rollback if user insert fails
                    return false;
                }
            }

            // Insert Competitor
            try (PreparedStatement stmtCompetitor = conn.prepareStatement(queryCompetitor)) {
                stmtCompetitor.setString(1, competitorId.toString());
                stmtCompetitor.setString(2, userId.toString());
                stmtCompetitor.executeUpdate();
            }

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = DBConnection.getConnection()) {
                conn.rollback(); // Rollback if any error occurs
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
