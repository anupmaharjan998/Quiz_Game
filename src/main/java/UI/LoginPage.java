package UI;

import DAO.AuthServiceDAO;
import Model.LoginModel;
import UI.Admin.AdminDashboard;
import UI.Player.PlayerDashboard;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    private final AuthServiceDAO authService;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage(AuthServiceDAO authService) {
        this.authService = authService;

        setTitle("Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255)); // Light Blue Background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
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
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(30, 144, 255)); // Dodger Blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            LoginModel result = authService.login(usernameField.getText(), new String(passwordField.getPassword()));

            if (result != null) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

                if (result.getRole().equals("ADMIN")) {
                    new AdminDashboard();  // Redirect to Admin Page
                } else {
                    new PlayerDashboard(result.getUserId()); // Redirect to Player Page
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(loginButton, gbc);

        gbc.gridy++;
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 12));
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        registerButton.addActionListener(e -> {
            dispose();
            new RegisterPage(authService);
        });

        mainPanel.add(registerButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
