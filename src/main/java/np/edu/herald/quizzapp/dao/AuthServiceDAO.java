package np.edu.herald.quizzapp.dao;

import np.edu.herald.quizzapp.model.Login;
import np.edu.herald.quizzapp.model.Register;

import javax.naming.AuthenticationException;
import java.sql.SQLException;

/**
 * Interface for authentication-related operations such as user registration and login.
 */
public interface AuthServiceDAO {

    /**
     * Registers a new user in the system.
     *
     * @param model the {@link Register} object containing user registration details.
     * @return true if the registration is successful, false otherwise.
     * @throws SQLException if an SQL error occurs during the registration process.
     */
    boolean register(Register model) throws SQLException;

    /**
     * Authenticates a user and returns login details.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return the {@link Login} object containing user login details.
     * @throws SQLException if an SQL error occurs during the login process.
     * @throws AuthenticationException if authentication fails (invalid username or password).
     */
    Login login(String username, String password) throws SQLException, AuthenticationException;

}
