package np.edu.herald.quizzapp.model;

/**
 * Represents a user's login details.
 * Contains user ID, username, password, and role.
 */
public class Login {

    private String userId;
    private String username;
    private String password;
    private String role;

    /**
     * Constructs a Login object with the specified user ID, username, password, and role.
     *
     * @param userId the user's ID
     * @param username the username
     * @param password the password
     * @param role the role of the user (e.g., "admin", "user")
     */
    public Login(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the user's ID.
     *
     * @return the user's ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user's ID.
     *
     * @param userId the new user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's role.
     *
     * @return the role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role the new role of the user
     */
    public void setRole(String role) {
        this.role = role;
    }
}
