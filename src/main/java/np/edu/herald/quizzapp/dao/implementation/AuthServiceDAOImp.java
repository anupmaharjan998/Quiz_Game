package np.edu.herald.quizzapp.dao.implementation;

import np.edu.herald.quizzapp.connection.DBConnection;
import np.edu.herald.quizzapp.dao.AuthServiceDAO;
import np.edu.herald.quizzapp.model.Login;
import np.edu.herald.quizzapp.model.Register;

import javax.naming.AuthenticationException;
import java.sql.*;
import java.util.UUID;

/**
 * Implementation of the AuthServiceDAO interface.
 * Provides authentication and registration functionalities.
 */

public class AuthServiceDAOImp implements AuthServiceDAO {

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A Login object if authentication is successful.
     * @throws SQLException If a database error occurs.
     * @throws AuthenticationException If authentication fails due to incorrect credentials.
     */
    @Override
    public Login login(String username, String password) throws SQLException, AuthenticationException{

        String query = "select c.user_id, c.username, c.password, r.role_name from Users c join Roles r on c.role_id = r.role_id where c.username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next() || !rs.getString("password").equals(password)) {
                throw new AuthenticationException("Invalid username or password.");
            }
            return new Login(rs.getString("user_id"), username, password, rs.getString("role_name"));
        }
    }


    /**
     * Registers a new user and assigns them a default role of 'PLAYER'.
     *
     * @param model The Register object containing user details.
     * @return true if the registration is successful, false otherwise.
     * @throws SQLException If a database error occurs during registration.
     */
    @Override
    public boolean register(Register model) throws SQLException {
        String queryUser = "insert into Users (user_id, first_name, last_name, age, country, username, password, role_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, (select role_id from Roles where role_name = 'PLAYER'))";

        String queryCompetitor = "insert into Competitors (comp_id, user_id) values (?, ?)";

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
            throw new SQLException("Registration failed. Please try again later.", e);
        }
    }
}
