package UI.Player;

import DAO.Implementation.QuestionDAOImp;
import DAO.Implementation.ScoreDAOImp;
import DAO.QuestionDAO;
import DAO.ScoreDAO;
import Model.LevelModel;
import Model.Question;
import Model.ScoreModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

public class QuizPage extends JFrame {
    private String user_id;
    private final QuestionDAO questionDAO;
    private final LevelModel level;
    private JPanel panel;
    private List<Question> questions;
    private int currentQuestionIndex = 0; // To track the current question
    private int correctAnswersCount = 0; // To track the number of correct answers

    public QuizPage(LevelModel level, String user_id) {
        this.questionDAO = new QuestionDAOImp();
        this.level = level;
        this.user_id = user_id;

        setTitle(level.getLevelName() + " Level");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Light gray background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        questions = getQuestions(); // Load questions
        displayQuestion(currentQuestionIndex); // Display the first question

        add(panel);
        setVisible(true);
    }

    // Method to display the current question
    // Method to display the current question or handle empty question list
    private void displayQuestion(int index) {
        panel.removeAll(); // Clear previous question

        if (questions == null || questions.isEmpty()) {
            // Display "Questions Not Available" message
            JLabel noQuestionLabel = new JLabel("Questions Not Available");
            noQuestionLabel.setFont(new Font("Arial", Font.BOLD, 20));
            noQuestionLabel.setForeground(Color.RED);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(noQuestionLabel, gbc);

            // Add "Go to Dashboard" button
            JButton dashboardButton = new JButton("Go to Dashboard");
            dashboardButton.setFont(new Font("Arial", Font.BOLD, 16));
            dashboardButton.setBackground(new Color(100, 150, 255)); // Blue color
            dashboardButton.setForeground(Color.WHITE);
            dashboardButton.setFocusPainted(false);
            dashboardButton.addActionListener(e -> {
                new PlayerDashboard(user_id); // Navigate to PlayerDashboard
                dispose(); // Close the current window
            });

            gbc.gridy = 1;
            panel.add(dashboardButton, gbc);

        } else if (index < questions.size()) {
            // Proceed with displaying questions if available
            Question question = questions.get(index);
            JLabel questionLabel = new JLabel("Q: " + question.getQuestion());
            questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
            questionLabel.setForeground(new Color(50, 50, 50)); // Dark gray text
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(questionLabel, gbc);

            String[] optionsArray = question.getOptions().split(",");

            ButtonGroup group = new ButtonGroup();
            gbc.gridy = 1;
            for (String option : optionsArray) {
                JRadioButton radioButton = new JRadioButton(option.trim());
                radioButton.setFont(new Font("Arial", Font.PLAIN, 16));
                radioButton.setBackground(new Color(240, 240, 240));
                group.add(radioButton);
                panel.add(radioButton, gbc);
                gbc.gridy++;
            }

            // Submit button
            JButton submitButton = new JButton("Submit");
            submitButton.setFont(new Font("Arial", Font.BOLD, 16));
            submitButton.setBackground(new Color(100, 150, 255));
            submitButton.setForeground(Color.WHITE);
            submitButton.setFocusPainted(false);
            submitButton.addActionListener(e -> {
                boolean isCorrect = false;
                Enumeration<AbstractButton> buttons = group.getElements();
                while (buttons.hasMoreElements()) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        if (button.getText().equals(question.getCorrect())) {
                            correctAnswersCount++;
                            isCorrect = true;
                        }
                        break;
                    }
                }

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion(currentQuestionIndex);
                } else {
                    showResults();
                }
            });

            gbc.gridy++;
            panel.add(submitButton, gbc);
        }

        panel.revalidate();
        panel.repaint();
    }

    private List<Question> getQuestions() {
        return questionDAO.getQuestionsByDifficulty(level.getLevelId());
    }

    // Method to show the results at the end of the quiz
    private void showResults() {
        panel.removeAll(); // Clear the panel
        String message = "Quiz Finished! Correct Answers: " + correctAnswersCount + "/" + questions.size();

        // Display the score in a JOptionPane
        int option = JOptionPane.showConfirmDialog(this, message, "Quiz Results", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (correctAnswersCount > 0)
                storeScoreInDatabase(); // Store the score in the database
            new PlayerDashboard(user_id);
            dispose();
        }

        panel.revalidate(); // Refresh the panel
        panel.repaint(); // Repaint the panel
    }

    // Method to store the score in the database
    private void storeScoreInDatabase() {
        // Assuming you have a ScoreDAO class to handle database operations
        ScoreDAO scoreDAO = new ScoreDAOImp();
        ScoreModel score = scoreDAO.getScore(user_id, level.getLevelId()); // Save the score
        if (score != null) {
            String sc = score.getScores();
            score.setScores(sc + "," + correctAnswersCount);
            scoreDAO.updateScore(score);
        }else {
            ScoreModel model = new ScoreModel(user_id, level.getLevelId(),String.valueOf(correctAnswersCount));
            scoreDAO.addScore(model);
        }
    }
}