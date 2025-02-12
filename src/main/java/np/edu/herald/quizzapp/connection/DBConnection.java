package np.edu.herald.quizzapp.connection;

import java.sql.*;

/**
 * The DBConnection class manages the connection to the database,
 * initializes the database by creating tables and seeding them with default data if necessary.
 */
public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DB_NAME = "quiz_app";

    /**
     * Establishes a connection to the database.
     *
     * @return a {@link Connection} object to the database.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
    }

    /**
     * Initializes the database by creating necessary tables and inserting default data if the tables are newly created.
     * This includes creating the Roles, Users, Levels, Questions, Competitors, and Scores tables.
     */
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create the database if it doesn't exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Database created or already exists.");
            stmt.executeUpdate("USE " + DB_NAME);

            boolean isRolesNew = createRolesTable(stmt);
            boolean isUsersNew = createUsersTable(stmt);
            boolean isLevelsNew = createLevelsTable(stmt);
            boolean isQuestionsNew = createQuestionsTable(stmt);
            boolean isCompetitorsNew = createCompetitorsTable(stmt);
            boolean isScoresNew = createScoresTable(stmt);

            // Seed data if the tables were newly created
            if (isRolesNew) {
                stmt.executeUpdate("INSERT INTO Roles (role_id, role_name) VALUES " +
                        "(UUID(), 'ADMIN'), (UUID(), 'PLAYER')");
                System.out.println("Roles seeded.");
            }

            if (isLevelsNew) {
                stmt.executeUpdate("INSERT INTO Levels (level_id, level_name) VALUES " +
                        "(UUID(), 'Beginner'), (UUID(), 'Intermediate'), (UUID(), 'Advanced')");
                System.out.println("Levels seeded.");
            }

            if (isUsersNew) {
                stmt.executeUpdate("INSERT INTO Users (user_id, username, password, first_name, last_name, country, age, role_id) " +
                        "VALUES (UUID(), 'admin', 'admin123', 'System', 'Admin', 'N/A', 30, " +
                        "(SELECT role_id FROM Roles WHERE role_name = 'ADMIN'))");
                System.out.println("Admin user seeded.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the Roles table if it does not exist.
     *
     * @param stmt the {@link Statement} object used to execute SQL queries.
     * @return true if the Roles table was newly created, false if it already exists.
     * @throws SQLException if a database access error occurs.
     */
    private static boolean createRolesTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Roles",
                "CREATE TABLE IF NOT EXISTS Roles (" +
                        "role_id CHAR(36) PRIMARY KEY, " +
                        "role_name VARCHAR(50) NOT NULL UNIQUE)");
    }

    /**
     * Creates the Users table if it does not exist.
     *
     * @param stmt the {@link Statement} object used to execute SQL queries.
     * @return true if the Users table was newly created, false if it already exists.
     * @throws SQLException if a database access error occurs.
     */
    private static boolean createUsersTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Users",
                "CREATE TABLE IF NOT EXISTS Users (" +
                        "user_id CHAR(36) PRIMARY KEY, " +
                        "username VARCHAR(50) NOT NULL UNIQUE, " +
                        "password VARCHAR(255) NOT NULL, " +
                        "first_name VARCHAR(50) NOT NULL, " +
                        "last_name VARCHAR(50) NOT NULL, " +
                        "country VARCHAR(50) NOT NULL, " +
                        "age INT NOT NULL, " +
                        "role_id CHAR(36), " +
                        "FOREIGN KEY (role_id) REFERENCES Roles(role_id))");
    }

    /**
     * Creates the Levels table if it does not exist.
     *
     * @param stmt the {@link Statement} object used to execute SQL queries.
     * @return true if the Levels table was newly created, false if it already exists.
     * @throws SQLException if a database access error occurs.
     */
    private static boolean createLevelsTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Levels",
                "CREATE TABLE IF NOT EXISTS Levels (" +
                        "level_id CHAR(36) PRIMARY KEY, " +
                        "level_name VARCHAR(50) NOT NULL UNIQUE)");
    }

    /**
     * Creates the Questions table if it does not exist.
     *
     * @param stmt the {@link Statement} object used to execute SQL queries.
     * @return true if the Questions table was newly created, false if it already exists.
     * @throws SQLException if a database access error occurs.
     */
    private static boolean createQuestionsTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Questions",
                "CREATE TABLE IF NOT EXISTS Questions (" +
                        "ques_id CHAR(36) PRIMARY KEY, " +
                        "question TEXT NOT NULL, " +
                        "options VARCHAR(255) NOT NULL, " +
                        "correct VARCHAR(255) NOT NULL, " +
                        "level_id CHAR(36), " +
                        "FOREIGN KEY (level_id) REFERENCES Levels(level_id))");
    }

    /**
     * Creates the Competitors table if it does not exist.
     *
     * @param stmt the {@link Statement} object used to execute SQL queries.
     * @return true if the Competitors table was newly created, false if it already exists.
     * @throws SQLException if a database access error occurs.
     */
    private static boolean createCompetitorsTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Competitors",
                "CREATE TABLE IF NOT EXISTS Competitors (" +
                        "comp_id CHAR(36) PRIMARY KEY, " +
                        "user_id CHAR(36) NOT NULL, " +
                        "FOREIGN KEY (user_id) REFERENCES Users(user_id))");
    }

    /**
     * Creates the Scores table if it does not exist.
     *
     * @param stmt the {@link Statement} object used to execute SQL queries.
     * @return true if the Scores table was newly created, false if it already exists.
     * @throws SQLException if a database access error occurs.
     */
    private static boolean createScoresTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Scores",
                "CREATE TABLE IF NOT EXISTS Scores (" +
                        "score_id CHAR(36) PRIMARY KEY, " +
                        "user_id CHAR(36) NOT NULL, " +
                        "level_id CHAR(36) NOT NULL, " +
                        "score VARCHAR(255) NOT NULL, " +
                        "avg_score FLOAT NOT NULL, " +
                        "total_quizzes INT NOT NULL, " +
                        "FOREIGN KEY (user_id) REFERENCES Users(user_id), " +
                        "FOREIGN KEY (level_id) REFERENCES Levels(level_id))");
    }

    /**
     * Checks if a table exists and creates it if necessary.
     *
     * @param stmt the {@link Statement} object used to execute SQL queries.
     * @param tableName the name of the table to check.
     * @param createTableQuery the SQL query to create the table if it does not exist.
     * @return true if the table was newly created, false if it already exists.
     * @throws SQLException if a database access error occurs.
     */
    private static boolean executeTableCreation(Statement stmt, String tableName, String createTableQuery) throws SQLException {
        boolean isNewTable = false;

        // Check if table exists
        try (ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE '" + tableName + "'")) {
            if (!rs.next()) { // If the table does not exist, create it
                stmt.executeUpdate(createTableQuery);
                System.out.println(tableName + " table created.");
                isNewTable = true;
            } else {
                System.out.println(tableName + " table already exists.");
            }
        }
        return isNewTable;
    }
}
