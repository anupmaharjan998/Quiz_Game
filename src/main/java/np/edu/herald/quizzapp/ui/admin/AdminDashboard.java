package np.edu.herald.quizzapp.ui.admin;

import np.edu.herald.quizzapp.dao.LevelDAO;
import np.edu.herald.quizzapp.dao.QuestionDAO;
import np.edu.herald.quizzapp.dao.ReportManagerDAO;
import np.edu.herald.quizzapp.main.Main;
import np.edu.herald.quizzapp.ui.LoginPage; // Import LoginPage

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private final QuestionDAO questionDAO;
    private final LevelDAO levelDAO;
    private final ReportManagerDAO reportManagerDAO;

    public AdminDashboard(QuestionDAO questionDAO, LevelDAO levelDAO, ReportManagerDAO reportManagerDAO) {
        this.questionDAO = questionDAO;
        this.levelDAO = levelDAO;
        this.reportManagerDAO = reportManagerDAO;
        setTitle("Admin Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50)); // Dark background

        JLabel titleLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Increased number of rows for the logout button
        buttonPanel.setBackground(new Color(50, 50, 50));

        JButton quizManagementButton = createButton("Quiz Management");
        JButton reportManagementButton = createButton("Report Management");
        JButton logoutButton = createLogoutButton();  // Create the logout button

        buttonPanel.add(quizManagementButton);
        buttonPanel.add(reportManagementButton);
        buttonPanel.add(logoutButton); // Add the logout button

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(100, 150, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor on hover

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 130, 235)); // Darker blue on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 150, 255)); // Original color
            }
        });

        button.addActionListener(e -> {
            if (text.equals("Quiz Management")) {
                dispose();
                new QuizManagement(questionDAO, levelDAO, reportManagerDAO); // Open Quiz Management Panel
            } else if (text.equals("Report Management")) {
                dispose();
                new ReportManagement(questionDAO, levelDAO, reportManagerDAO); // Open Report Management Panel
            }
        });

        return button;
    }

    // Create a logout button
    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(255, 100, 100)); // Red color for logout
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(235, 80, 80)); // Darker red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 100, 100)); // Original color
            }
        });

        // Add action listener for logout
        button.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?",
                    "Logout", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                Main.main(new String[]{}); // Call main method of your MainApp class
                dispose(); // Close the current PlayerDashboard window
            }
        });

        return button;
    }
}
