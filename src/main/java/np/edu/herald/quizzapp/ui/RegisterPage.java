package np.edu.herald.quizzapp.ui;

import np.edu.herald.quizzapp.dao.*;
import np.edu.herald.quizzapp.model.Register;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

class RegisterPage extends JFrame {
    private final AuthServiceDAO authService;
    private final LevelDAO levelDAO;
    private final QuestionDAO questionDAO;
    private final ReportManagerDAO reportManagerDAO;
    private final ScoreDAO scoreDAO;
    private final JTextField usernameField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField countryField;
    private final JTextField ageField;
    private final JPasswordField passwordField;

    public RegisterPage(AuthServiceDAO authService, LevelDAO levelDAO, QuestionDAO questionDAO, ReportManagerDAO reportManagerDAO, ScoreDAO scoreDAO) {
        this.authService = authService;
        this.levelDAO = levelDAO;
        this.questionDAO = questionDAO;
        this.reportManagerDAO = reportManagerDAO;
        this.scoreDAO = scoreDAO;

        setTitle("Register");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255)); // Light Blue Background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Register New User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        mainPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        firstNameField = new JTextField(15);
        mainPanel.add(firstNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(15);
        mainPanel.add(lastNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(5);
        mainPanel.add(ageField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Country:"), gbc);
        gbc.gridx = 1;
        countryField = new JTextField(15);
        mainPanel.add(countryField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        mainPanel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton registerButton = getJButton();

        mainPanel.add(registerButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton getJButton() {
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(30, 144, 255)); // Dodger Blue
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        registerButton.addActionListener(e -> {
            try {
                Register model = new Register(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        usernameField.getText(),
                        new String(passwordField.getPassword()),
                        countryField.getText(),
                        Integer.parseInt(ageField.getText())
                );

                boolean success = authService.register(model);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginPage(authService, levelDAO, questionDAO, reportManagerDAO, scoreDAO);
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return registerButton;
    }
}
