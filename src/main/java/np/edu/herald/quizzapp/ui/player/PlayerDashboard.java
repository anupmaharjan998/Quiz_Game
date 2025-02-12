package np.edu.herald.quizzapp.ui.player;

import np.edu.herald.quizzapp.dao.QuestionDAO;
import np.edu.herald.quizzapp.dao.LevelDAO;
import np.edu.herald.quizzapp.dao.ScoreDAO;
import np.edu.herald.quizzapp.model.Levels;
import np.edu.herald.quizzapp.model.Scores;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayerDashboard extends JFrame {
    private final ScoreDAO scoreDAO;
    private final LevelDAO levelDAO;
    private final JTextPane scoreBoard;
    private final List<Levels> levels;
    private final String userId;
    private final QuestionDAO questionDAO;

    public PlayerDashboard(String userId, ScoreDAO scoreDAO, LevelDAO levelDAO, QuestionDAO questionDAO) {
        this.scoreDAO = scoreDAO;
        this.levelDAO = levelDAO;
        this.userId = userId;
        this.questionDAO = questionDAO;

        // Set up the frame
        setTitle("Quiz Game Dashboard");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 255)); // Light blue background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Add title label
        JLabel titleLabel = new JLabel("Quiz Game Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(titleLabel, gbc);

        // Add level selection label
        JLabel selectLabel = new JLabel("Select Level");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        panel.add(selectLabel, gbc);

        levels = levelDAO.getAllLevels();

        // Create level buttons in a single row (centered)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnBeginner = createStyledButton("Beginner");
        JButton btnIntermediate = createStyledButton("Intermediate");
        JButton btnAdvanced = createStyledButton("Advanced");

        buttonPanel.add(btnBeginner);
        buttonPanel.add(btnIntermediate);
        buttonPanel.add(btnAdvanced);

        gbc.gridy = 2;
        panel.add(buttonPanel, gbc);

        // Add action listeners
        addListener(btnBeginner, "Beginner");
        addListener(btnIntermediate, "Intermediate");
        addListener(btnAdvanced, "Advanced");

        // Scoreboard Panel with 3-column layout
        scoreBoard = new JTextPane();
        scoreBoard.setContentType("text/html");
        scoreBoard.setEditable(false);
        scoreBoard.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Top Scores", TitledBorder.CENTER, TitledBorder.TOP));

        showScores();

        JScrollPane scrollPane = new JScrollPane(scoreBoard);
        scrollPane.setPreferredSize(new Dimension(500, 250));

        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(scrollPane, gbc);

        // Add panel to frame
        add(panel);
        setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }

    // Add listener for level buttons
    private void addListener(JButton btn, String levelName) {
        btn.addActionListener(e -> {
            String levelId = getLevelIdByName(levelName);
            startQuiz(levelId);
        });
    }

    // Start quiz for selected level
    private void startQuiz(String levelId) {
        Levels level = getLevelById(levelId);
        if (level != null) {
            new QuizPage(level, userId, scoreDAO, levelDAO, questionDAO);
        }
        dispose();
    }

    private Levels getLevelById(String levelId) {
        for (Levels level : levels) {
            if (Objects.equals(level.getLevelId(), levelId)) {
                return level;
            }
        }
        return null;
    }

    private String getLevelIdByName(String levelName) {
        for (Levels level : levels) {
            if (level.getLevelName().equalsIgnoreCase(levelName)) {
                return level.getLevelId();
            }
        }
        return null;
    }

    // Display formatted scores in 3 columns
    private void showScores() {
        StringBuilder scores = new StringBuilder("<html><body style='font-size:14px;'>");

        Map<String, List<String>> levelScores = new HashMap<>();
        levelScores.put("Beginner", new ArrayList<>());
        levelScores.put("Intermediate", new ArrayList<>());
        levelScores.put("Advanced", new ArrayList<>());

        for (Scores scoreModel : scoreDAO.getScoreByUserID(userId)) {
            List<String> scoresList = Arrays.asList(arrayScore(scoreModel.getScores()));

            // Convert to an ArrayList to allow sorting
            List<String> sortedScores = new ArrayList<>(scoresList);

            // Sort scores in ascending order
            sortedScores.sort(Comparator.reverseOrder());

            // Store in the map
            levelScores.put(scoreModel.getLevelName(), sortedScores);
        }

        // HTML Table for 3-column layout
        scores.append("<table border='1' cellpadding='5' cellspacing='0' style='width:100%; text-align:center;'>");
        scores.append("<tr style='background-color:#ADD8E6; font-weight:bold;'><td>Beginner</td><td>Intermediate</td><td>Advanced</td></tr>");


        for (int i = 0; i < 5; i++) {
            scores.append("<tr>");
            scores.append("<td>").append(getScoreAt(levelScores.get("Beginner"), i)).append("</td>");
            scores.append("<td>").append(getScoreAt(levelScores.get("Intermediate"), i)).append("</td>");
            scores.append("<td>").append(getScoreAt(levelScores.get("Advanced"), i)).append("</td>");
            scores.append("</tr>");
        }

        scores.append("</table></body></html>");
        scoreBoard.setText(scores.toString());
    }

    // Helper method to get score at index or return empty space
    private String getScoreAt(List<String> scores, int index) {
        return (index < scores.size()) ? scores.get(index) : "";
    }

    private String[] arrayScore(String score) {
        return score.split(",");
    }

}
