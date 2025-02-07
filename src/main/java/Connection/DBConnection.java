package Connection;

import java.sql.*;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DB_NAME = "quiz_app";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create database if it doesn't exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Database created or already exists.");
            stmt.executeUpdate("USE " + DB_NAME);

            boolean isRolesNew = createRolesTable(stmt);
            boolean isUsersNew = createUsersTable(stmt);
            boolean isLevelsNew = createLevelsTable(stmt);
            boolean isQuestionsNew = createQuestionsTable(stmt);
            boolean isCompetitorsNew = createCompetitorsTable(stmt);
            boolean isScoresNew = createScoresTable(stmt);

            // Seed data only if the tables were newly created
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

    // Create tables and return true if the table was newly created
    private static boolean createRolesTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Roles",
                "CREATE TABLE IF NOT EXISTS Roles (" +
                        "role_id CHAR(36) PRIMARY KEY, " +
                        "role_name VARCHAR(50) NOT NULL UNIQUE)");
    }

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

    private static boolean createLevelsTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Levels",
                "CREATE TABLE IF NOT EXISTS Levels (" +
                        "level_id CHAR(36) PRIMARY KEY, " +
                        "level_name VARCHAR(50) NOT NULL UNIQUE)");
    }

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

    private static boolean createCompetitorsTable(Statement stmt) throws SQLException {
        return executeTableCreation(stmt, "Competitors",
                "CREATE TABLE IF NOT EXISTS Competitors (" +
                        "comp_id CHAR(36) PRIMARY KEY, " +
                        "user_id CHAR(36) NOT NULL, " +
                        "FOREIGN KEY (user_id) REFERENCES Users(user_id))");
    }

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

    // Helper method to check if a table exists and create it
    private static boolean executeTableCreation(Statement stmt, String tableName, String createTableQuery) throws SQLException {
        boolean isNewTable = false;

        // Check if table exists
        ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE '" + tableName + "'");
        if (!rs.next()) { // If the table does not exist, create it
            stmt.executeUpdate(createTableQuery);
            System.out.println(tableName + " table created.");
            isNewTable = true;
        } else {
            System.out.println(tableName + " table already exists.");
        }
        rs.close();
        return isNewTable;
    }

}