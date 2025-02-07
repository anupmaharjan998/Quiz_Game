package UI.Admin;

import DAO.Implementation.LevelDAOImp;
import DAO.Implementation.ReportManagerDAOImp;
import DAO.LevelDAO;
import DAO.ReportManagerDAO;
import Model.LevelModel;
import Model.PlayerReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportManagement extends JFrame {
    private JTable playerReportsTable;
    private DefaultTableModel tableModel;
    private final LevelDAO levelDAO;
    private final ReportManagerDAO reportManagerDAO;
    private List<LevelModel> levels;
    private String level_id;
    private JTextArea topPlayerDetailsArea; // Text area for displaying top player details
    private JTextArea selectedPlayerDetailsArea; // Text area for displaying selected player details
    private JTextField searchField; // Text field for search input
    private JButton searchButton; // Button to trigger search

    public ReportManagement() {
        this.levelDAO = new LevelDAOImp();
        this.reportManagerDAO = new ReportManagerDAOImp();

        setTitle("Report Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        levels = levelDAO.getAllLevels();

        // Navigation Bar
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> navigateToDashboard());
        headerPanel.add(backButton, BorderLayout.WEST);

        // Navigation Buttons Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton beginnerButton = new JButton("Beginner");
        JButton intermediateButton = new JButton("Intermediate");
        JButton advancedButton = new JButton("Advanced");

        navPanel.add(beginnerButton);
        navPanel.add(intermediateButton);
        navPanel.add(advancedButton);
        headerPanel.add(navPanel, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20); // Text field for search input
        searchButton = new JButton("Search"); // Button to trigger search

        // Add action listener for the search button
//        searchButton.addActionListener(e -> searchPlayers());
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
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
        level_id = getLevelIdByName("Beginner");
        loadPlayerReports(); // Load beginner reports by default
        loadTopPlayerDetails(); // Load top player details

        // Action Listeners for Navigation Buttons
        beginnerButton.addActionListener(e -> {
            level_id = getLevelIdByName("Beginner");
            loadPlayerReports();
            loadTopPlayerDetails(); // Refresh top player details
        });
        intermediateButton.addActionListener(e -> {
            level_id = getLevelIdByName("Intermediate");
            loadPlayerReports();
            loadTopPlayerDetails(); // Refresh top player details
        });
        advancedButton.addActionListener(e -> {
            level_id = getLevelIdByName("Advanced");
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
        new AdminDashboard(); // Navigate to the Admin Dashboard
    }

    private String getLevelIdByName(String levelName) {
        for (LevelModel level : levels) {
            if (level.getLevelName().equalsIgnoreCase(levelName)) {
                return level.getLevelId();
            }
        }
        return null;
    }

    private void loadPlayerReports() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Fetch data based on category
        List<PlayerReport> reports = reportManagerDAO.getReportByLevel(level_id);
        for (PlayerReport report : reports) {
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
        PlayerReport topPlayer = reportManagerDAO.getTopPlayerSummary(level_id);
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
            PlayerReport selectedPlayer = reportManagerDAO.getSelectedPlayerSummary(level_id, playerId);

            // Update the selected player details area
            String details = "Name: " + selectedPlayer.getName() + "\n" +
                    "Total Quizzes: " + selectedPlayer.getTotalQuizzes() + "\n" +
                    "Scores: " + selectedPlayer.getScores() + "\n" +
                    "Avg Score: " + selectedPlayer.getAvgScore();
            selectedPlayerDetailsArea.setText(details);
        }else {
            selectedPlayerDetailsArea.setText("No top player data available.");
        }
    }

    private void searchPlayers() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.length() == 0) {
            loadPlayerReports();
        }else {
            // Clear existing data
            tableModel.setRowCount(0);

            // Fetch data based on category
            List<PlayerReport> reports = reportManagerDAO.searchPlayer(searchText, level_id);
            for (PlayerReport report : reports) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReportManagement::new);
    }
}