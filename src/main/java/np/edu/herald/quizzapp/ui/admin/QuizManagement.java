package np.edu.herald.quizzapp.ui.admin;

import np.edu.herald.quizzapp.dao.LevelDAO;
import np.edu.herald.quizzapp.dao.QuestionDAO;
import np.edu.herald.quizzapp.dao.ReportManagerDAO;
import np.edu.herald.quizzapp.model.Levels;
import np.edu.herald.quizzapp.model.Questions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class QuizManagement extends JFrame {
    private final JTable questionTable;
    private final DefaultTableModel tableModel;
    private final QuestionDAO questionDAO;
    private final LevelDAO levelDAO;
    private final ReportManagerDAO reportManagerDAO;

    public QuizManagement(QuestionDAO questionDAO, LevelDAO levelDAO, ReportManagerDAO reportManagerDAO) {
        this.questionDAO = questionDAO;
        this.levelDAO = levelDAO;
        this.reportManagerDAO = reportManagerDAO;

        setTitle("Quiz Management");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Header Panel with Add Question Button and Back Button
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        JButton addQuestionButton = new JButton("+ Add Question");
//        addQuestionButton.setFont(new Font("Arial", Font.BOLD, 14));
//        addQuestionButton.addActionListener(e -> openAddQuestionDialog());
//        headerPanel.add(addQuestionButton);

        // Back Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e ->
            // Navigate back to the dashboard
            navigateToDashboard()
        );
        headerPanel.add(backButton);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Table for displaying questions
        String[] columnNames = {"ID", "Question", "Options", "Correct", "Difficulty"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        // Hide the ID column by setting its width to 0
        questionTable.getColumnModel().getColumn(0).setMinWidth(0);
        questionTable.getColumnModel().getColumn(0).setMaxWidth(0);
        questionTable.getColumnModel().getColumn(0).setPreferredWidth(0);

        questionTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(questionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel for Edit and Delete buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addQuestionButton = new JButton("Add Question");
        addQuestionButton.addActionListener(e -> openAddQuestionDialog());
        JButton editQuestionButton = new JButton("Edit Selected Question");
        JButton deleteQuestionButton = new JButton("Delete Selected Question");

        editQuestionButton.addActionListener(e -> {
            int selectedRow = questionTable.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) questionTable.getValueAt(selectedRow, 0);
                editQuestion(id);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a question to edit.");
            }
        });

        deleteQuestionButton.addActionListener(e -> {
            int selectedRow = questionTable.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) questionTable.getValueAt(selectedRow, 0);
                deleteQuestion(id);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a question to delete.");
            }
        });

        actionPanel.add(addQuestionButton);
        actionPanel.add(editQuestionButton);
        actionPanel.add(deleteQuestionButton);
        panel.add(actionPanel, BorderLayout.SOUTH);

        listQuestions();
        add(panel);
        setVisible(true);
    }

    private void listQuestions() {
        List<Questions> questions = questionDAO.getAllQuestions();
        for (Questions question : questions) {
            addQuestionToTable(
                    question.getQuesId(),
                    question.getQuestion(),
                    question.getOptions(),
                    question.getCorrect(),
                    question.getLevelName()
            );
        }
    }

    private void openAddQuestionDialog() {
        JDialog addDialog = new JDialog(this, "Add Question", true);
        addDialog.setSize(650, 350);
        addDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel questionLabel = new JLabel("Question:");
        JTextArea questionField = new JTextArea(6, 30); // Increased rows for height
        questionField.setLineWrap(true); // Enable line wrapping
        questionField.setWrapStyleWord(true); // Wrap at word boundaries
        JLabel optionsLabel = new JLabel("Options (comma separated):");
        JTextArea optionsField = new JTextArea(6, 30); // Increased rows for height
        optionsField.setLineWrap(true); // Enable line wrapping
        optionsField.setWrapStyleWord(true); // Wrap at word boundaries
        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        JTextField correctAnswerField = new JTextField();
        correctAnswerField.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        JLabel levelLabel = new JLabel("Level:");
        List<Levels> levels = getLevels();
        JComboBox<Levels> levelDropdown = new JComboBox<>(levels.toArray(new Levels[0])); // Populate JComboBox

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String question = questionField.getText();
            String options = optionsField.getText();
            String correctAnswer = correctAnswerField.getText();
            String level_id = ((Levels) levelDropdown.getSelectedItem()).getLevelId(); // Get the actual value

            Questions ques = new Questions(
                    question,
                    options,
                    correctAnswer,
                    level_id
            );

            boolean success = questionDAO.addQuestion(ques);
            if (success) {
                JOptionPane.showMessageDialog(this, "Question Added", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshTable();
            addDialog.dispose();
        });

        cancelButton.addActionListener(e -> addDialog.dispose());

        gbc.gridx = 0; gbc.gridy = 0; addDialog.add(questionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; addDialog.add(new JScrollPane(questionField), gbc);
        gbc.gridx = 0; gbc.gridy = 1; addDialog.add(optionsLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; addDialog.add(new JScrollPane(optionsField), gbc);
        gbc.gridx = 0; gbc.gridy = 2; addDialog.add(correctAnswerLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; addDialog.add(correctAnswerField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; addDialog.add(levelLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; addDialog.add(levelDropdown, gbc);
        gbc.gridx = 0; gbc.gridy = 4; addDialog.add(saveButton, gbc);
        gbc.gridx = 1; gbc.gridy = 4; addDialog.add(cancelButton, gbc);

        addDialog.setLocationRelativeTo(this);
        addDialog.setVisible(true);
    }

    private void addQuestionToTable(String id, String question, String options, String correct, String difficulty) {
        tableModel.addRow(new Object[]{id, question, options, correct, difficulty});
    }

    private void editQuestion(String id) {
        Questions question = questionDAO.getQuestionsById(id);

        JDialog editDialog = new JDialog(this, "Edit Question", true);
        editDialog.setSize(650, 350);
        editDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel questionLabel = new JLabel("Question:");
        JTextArea questionField = new JTextArea(6, 30); // Increased rows for height
        questionField.setText(question.getQuestion());
        questionField.setLineWrap(true); // Enable line wrapping
        questionField.setWrapStyleWord(true); // Wrap at word boundaries
        JLabel optionsLabel = new JLabel("Options (comma separated):");
        JTextArea optionsField = new JTextArea(6, 30); // Increased rows for height
        optionsField.setText(question.getOptions());
        optionsField.setLineWrap(true); // Enable line wrapping
        optionsField.setWrapStyleWord(true); // Wrap at word boundaries
        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        JTextField correctAnswerField = new JTextField(question.getCorrect());
        correctAnswerField.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        JLabel levelLabel = new JLabel("Level:");

        // Fetch levels and populate JComboBox
        List<Levels> levels = getLevels(); // Assuming this method returns a List<LevelModel>
        JComboBox<Levels> levelDropdown = new JComboBox<>(levels.toArray(new Levels[0]));

        // Set the selected level based on the current question's level_id
        levelDropdown.setSelectedItem(levels.stream()
                .filter(level -> level.getLevelId().equals(question.getLevelId()))
                .findFirst()
                .orElse(null));

        levelDropdown.setSelectedItem(question.getLevelName());

        JButton saveButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            question.setQuestion(questionField.getText());
            question.setOptions(optionsField.getText());
            question.setCorrect(correctAnswerField.getText());

            // Update the level_id based on the selected item in the JComboBox
            Levels selectedLevel = (Levels) levelDropdown.getSelectedItem();
            if (selectedLevel != null) {
                question.setLevelId(selectedLevel.getLevelId()); // Ensure you have a method to set level_id
            }

            questionDAO.updateQuestion(question);
            refreshTable();
            editDialog.dispose();
        });

        cancelButton.addActionListener(e -> editDialog.dispose());

        gbc.gridx = 0; gbc.gridy = 0; editDialog.add(questionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; editDialog.add(new JScrollPane(questionField), gbc);
        gbc.gridx = 0; gbc.gridy = 1; editDialog.add(optionsLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; editDialog.add(new JScrollPane(optionsField), gbc);
        gbc.gridx = 0; gbc.gridy = 2; editDialog.add(correctAnswerLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; editDialog.add(correctAnswerField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; editDialog.add(levelLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; editDialog.add(levelDropdown, gbc);
        gbc.gridx = 0; gbc.gridy = 4; editDialog.add(saveButton, gbc);
        gbc.gridx = 1; gbc.gridy = 4; editDialog.add(cancelButton, gbc);

        editDialog.setLocationRelativeTo(this);
        editDialog.setVisible(true);
    }

    private void deleteQuestion(String id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this question?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            questionDAO.deleteQuestion(id);
            refreshTable();
        }
    }

    private List<Levels> getLevels() {
        return levelDAO.getAllLevels();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        listQuestions();
    }

    private void navigateToDashboard() {
        // Assuming you have a Dashboard class to navigate to
        new AdminDashboard(questionDAO, levelDAO, reportManagerDAO); // Create an instance of the Dashboard
        this.dispose(); // Close the current QuizManagement window
    }

}