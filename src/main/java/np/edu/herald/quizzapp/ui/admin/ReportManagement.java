package np.edu.herald.quizzapp.ui.admin;

import np.edu.herald.quizzapp.dao.QuestionDAO;
import np.edu.herald.quizzapp.dao.LevelDAO;
import np.edu.herald.quizzapp.dao.ReportManagerDAO;
import np.edu.herald.quizzapp.dao.implementation.LevelDAOImp;
import np.edu.herald.quizzapp.dao.implementation.QuestionDAOImp;
import np.edu.herald.quizzapp.dao.implementation.ReportManagerDAOImp;
import np.edu.herald.quizzapp.model.Levels;
import np.edu.herald.quizzapp.model.Players;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportManagement extends JFrame {
    private final LevelDAO levelDAO;
    private final QuestionDAO questionDAO;
    private JTable playerReportsTable;
    private DefaultTableModel tableModel;
    private final ReportManagerDAO reportManagerDAO;
    private List<Levels> levels;
    private String levelId;
    private JTextArea topPlayerDetailsArea; // Text area for displaying top player details
    private JTextArea selectedPlayerDetailsArea; // Text area for displaying selected player details
    private JTextField searchField; // Text field for search input
    private JButton searchButton; // Button to trigger search
    private JComboBox<Levels> levelComboBox; // Dropdown for level selection
    private String beginner = "Beginner";

    public ReportManagement(QuestionDAO questionDAO, LevelDAO levelDAO, ReportManagerDAO reportManagerDAO) {
        this.questionDAO = questionDAO;
        this.reportManagerDAO = reportManagerDAO;
        this.levelDAO = levelDAO;

        setTitle("Report Management");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        levels = levelDAO.getAllLevels();

        // Navigation Bar
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> navigateToDashboard());
        headerPanel.add(backButton, BorderLayout.WEST);



        // Navigation Dropdown Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel selectLabel = new JLabel("Select Level");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        navPanel.add(selectLabel);

        levelComboBox = new JComboBox<>(levels.toArray(new Levels[0]));

        navPanel.add(levelComboBox);
        headerPanel.add(navPanel, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20); // Text field for search input
        searchButton = new JButton("Search"); // Button to trigger search

        // Add action listener for the search button
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchPlayers();
            }
        });

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Player Reports Table
        String[] columnNames = {"Player ID", "Name", "Category", "Total Quizzes", "Scores", "Avg Score"};
        tableModel = new DefaultTableModel(columnNames, 0);
        playerReportsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(playerReportsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Player Summary Panel
        JPanel playerSummaryPanel = new JPanel(new GridLayout(1, 2)); // Use GridLayout for side by side
        // Top Player Details Section
        JPanel topPlayerPanel = new JPanel(new BorderLayout());
        JLabel topPlayerLabel = new JLabel("Top Player Details");
        topPlayerDetailsArea = new JTextArea(5, 30);
        topPlayerDetailsArea.setEditable(false); // Make it read-only
        topPlayerPanel.add(topPlayerLabel, BorderLayout.NORTH);
        topPlayerPanel.add(new JScrollPane(topPlayerDetailsArea), BorderLayout.CENTER);

        // Selected Player Details Section
        JPanel selectedPlayerPanel = new JPanel(new BorderLayout());
        JLabel selectedPlayerLabel = new JLabel("Selected Player Details");
        selectedPlayerDetailsArea = new JTextArea(5, 30);
        selectedPlayerDetailsArea.setEditable(false); // Make it read-only
        selectedPlayerPanel.add(selectedPlayerLabel, BorderLayout.NORTH);
        selectedPlayerPanel.add(new JScrollPane(selectedPlayerDetailsArea), BorderLayout.CENTER);

        playerSummaryPanel.add(topPlayerPanel);
        playerSummaryPanel.add(selectedPlayerPanel);
        panel.add(playerSummaryPanel, BorderLayout.SOUTH);

        // Load Report in Beginning Data
        levelId = getLevelIdByName(beginner);
        // Set the selected level based on the current question's level_id
        levelComboBox.setSelectedItem(levels.stream()
                .filter(level -> level.getLevelId().equals(levelId))
                .findFirst()
                .orElse(null));
        loadPlayerReports(); // Load beginner reports by default
        loadTopPlayerDetails(); // Load top player details

        // Action Listener for Dropdown Selection
        levelComboBox.addActionListener(e -> {
            String selectedLevel = ((Levels) levelComboBox.getSelectedItem()).getLevelId(); // Get the actual value
            levelId = selectedLevel;
            loadPlayerReports();
            loadTopPlayerDetails(); // Refresh top player details
        });

        playerReportsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Only respond when the selection is final
                int selectedRow = playerReportsTable.getSelectedRow();
                loadSelectedPlayerDetails(selectedRow);
            }
        });

        add(panel);
        setVisible(true);
    }

    private void navigateToDashboard() {
        this.dispose(); // Close the current ReportManagement window
        new AdminDashboard(questionDAO, levelDAO, reportManagerDAO); // Navigate to the Admin Dashboard
    }

    private String getLevelIdByName(String levelName) {
        for (Levels level : levels) {
            if (level.getLevelName().equalsIgnoreCase(levelName)) {
                return level.getLevelId();
            }
        }
        return null;
    }

    private void loadPlayerReports() {
        System.out.println(levelId);
        // Clear existing data
        tableModel.setRowCount(0);

        // Fetch data based on category
        List<Players> reports = reportManagerDAO.getReportByLevel(levelId);
        for (Players report : reports) {
            tableModel.addRow(new Object[]{
                    report.getCompetitorId(),
                    report.getName(),
                    report.getLevelName(),
                    report.getTotalQuizzes(),
                    report.getScores(),
                    report.getAvgScore()
            });
        }
    }

    private void loadTopPlayerDetails() {
        Players topPlayer = reportManagerDAO.getTopPlayerSummary(levelId);
        if (topPlayer != null) {
            String details = "Name: " + topPlayer.getName() + "\n" +
                    "Total Quizzes: " + topPlayer.getTotalQuizzes() + "\n" +
                    "Scores: " + topPlayer.getScores() + "\n" +
                    "Avg Score: " + topPlayer.getAvgScore();
            topPlayerDetailsArea.setText(details);
        } else {
            topPlayerDetailsArea.setText("No top player data available.");
        }
    }

    private void loadSelectedPlayerDetails(int selectedRow) {
        if (selectedRow != -1) { // Check if a row is selected
            // Retrieve data from the selected row
            String playerId = (String) tableModel.getValueAt(selectedRow, 0);
            Players selectedPlayer = reportManagerDAO.getSelectedPlayerSummary(levelId, playerId);

            // Update the selected player details area
            String details = "Name: " + selectedPlayer.getName() + "\n" +
                    "Total Quizzes: " + selectedPlayer.getTotalQuizzes() + "\n" +
                    "Scores: " + selectedPlayer.getScores() + "\n" +
                    "Avg Score: " + selectedPlayer.getAvgScore();
            selectedPlayerDetailsArea.setText(details);
        } else {
            selectedPlayerDetailsArea.setText("No top player data available.");
        }
    }

    private void searchPlayers() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            loadPlayerReports();
        } else {
            // Clear existing data
            tableModel.setRowCount(0);

            // Fetch data based on category
            List<Players> reports = reportManagerDAO.searchPlayer(searchText, levelId);
            for (Players report : reports) {
                tableModel.addRow(new Object[]{
                        report.getCompetitorId(),
                        report.getName(),
                        report.getLevelName(),
                        report.getTotalQuizzes(),
                        report.getScores(),
                        report.getAvgScore()
                });
            }
        }
    }

    private List<Levels> getLevels() {
        return levelDAO.getAllLevels();
    }

    // Main method to launch the ReportManagement window
    public static void main(String[] args) {
        // Mock DAO instances (replace with actual implementations)
        QuestionDAO questionDAO = new QuestionDAOImp();
        LevelDAO levelDAO = new LevelDAOImp();
        ReportManagerDAO reportManagerDAO = new ReportManagerDAOImp();

        // Run the ReportManagement UI
        SwingUtilities.invokeLater(() -> new ReportManagement(questionDAO, levelDAO, reportManagerDAO));
    }
}
