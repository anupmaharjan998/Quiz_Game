package UI.Player;

import DAO.Implementation.LevelDAOImp;
import DAO.Implementation.ScoreDAOImp;
import DAO.LevelDAO;
import DAO.ScoreDAO;
import Model.LevelModel;
import Model.ScoreModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class PlayerDashboard extends JFrame {
    private final ScoreDAO scoreDAO;
    private final LevelDAO levelDAO;
    private JPanel panel;
    private JTextArea scoreBoard;
    private List<LevelModel> levels;
    private JButton btnBeginner, btnIntermediate, btnAdvanced;
    private String user_id;

    public PlayerDashboard(String user_id) {
        this.scoreDAO = new ScoreDAOImp();
        this.levelDAO = new LevelDAOImp();
        this.user_id = user_id;

        // Set up the frame
        setTitle("Quiz Game Dashboard");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create the panel and layout
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Light gray background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Add title label
        JLabel titleLabel = new JLabel("Quiz Game Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        // Add label
        JLabel selectLabel = new JLabel("Select Level", JLabel.CENTER);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1; // Move to the next row
        panel.add(selectLabel, gbc);

        levels = levelDAO.getAllLevels();

        // Add buttons for each level
        btnBeginner = new JButton("Beginner");
        btnIntermediate = new JButton("Intermediate");
        btnAdvanced = new JButton("Advanced");

        gbc.gridy = 2; // Move to the next row
        panel.add(btnBeginner, gbc);
        gbc.gridy++;
        panel.add(btnIntermediate, gbc);
        gbc.gridy++;
        panel.add(btnAdvanced, gbc);

        // Add action listeners to buttons
        addListener(btnBeginner, "Beginner");
        addListener(btnIntermediate, "Intermediate");
        addListener(btnAdvanced, "Advanced");

        // Add scoreboard text area
        scoreBoard = new JTextArea(10, 30);
        scoreBoard.setEditable(false);
        scoreBoard.setText("Top 3 Scores for each Category:\n\n");
        showScores();
        gbc.gridy++;
        panel.add(new JScrollPane(scoreBoard), gbc);

        // Add panel to frame
        add(panel);
        setVisible(true);
    }

    // Method to add listener for the buttons
    private void addListener(JButton btn, String levelName) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String levelId = getLevelIdByName(levelName); // Get the level ID when button is clicked
                startQuiz(levelId);
            }
        });
    }

    // Method to start the quiz for the selected category
    private void startQuiz(String level_id) {
        LevelModel level = getLevelById(level_id);
        new QuizPage(level, user_id);
        dispose();
    }

    private LevelModel getLevelById(String levelId) {
        for (LevelModel level : levels) {
            if (Objects.equals(level.getLevelId(), levelId)) {
                return level; // Return the found level
            }
        }
        return null; // Return null if not found
    }

    private String getLevelIdByName(String levelName) {
        for (LevelModel level : levels) {
            if (level.getLevelName().equalsIgnoreCase(levelName)) {
                return level.getLevelId();
            }
        }
        return null; // Return null if not found
    }

    // Method to display scores for the selected category
    private void showScores() {
        StringBuilder scores = new StringBuilder();
        scores.append("Scores:\n\n");
        ScoreModel beginnerScores = null;
        ScoreModel intermediateScores = null;
        ScoreModel advancedScores = null;

        List<ScoreModel> scoreList = scoreDAO.getScoreByUserID(user_id);

        for (ScoreModel scoreModel : scoreList){
            switch (scoreModel.getLevelName()){
                case "Beginner":
                    beginnerScores = scoreModel;
                    break;
                case "Intermediate":
                    intermediateScores = scoreModel;
                    break;
                case "Advanced":
                    advancedScores = scoreModel;
                    break;
            }
        }



        // Display scores for Beginner
        scores.append("Beginner:\n");
        if (beginnerScores != null) {
            for (String score : arrayScore(beginnerScores.getScores())){
                scores.append(score).append("\n");
            }
        }

        // Display scores for Intermediate
        scores.append("\nIntermediate:\n");
        if (intermediateScores != null) {
            for (String score : arrayScore(intermediateScores.getScores())) {
                scores.append(score).append("\n");
            }
        }


        // Display scores for Advanced
        scores.append("\nAdvanced:\n");
        if (advancedScores != null) {
            for (String score : arrayScore(advancedScores.getScores())) {
                scores.append(score).append("\n");
            }
        }


        scoreBoard.setText(scores.toString());
    }

    private String[] arrayScore(String score){
        return score.split(",");
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new PlayerDashboard("anupmaha"));
    }
}