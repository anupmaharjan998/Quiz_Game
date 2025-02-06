//package java11.ui;
//
//import java.awt.HeadlessException;
//import java.sql.SQLException;
//import java.util.List;
//import javax.swing.BorderFactory;
//import javax.swing.JOptionPane;
//import javax.swing.JTextField;
//import javax.swing.table.DefaultTableModel;
//
//import dao.CompetitorDAO;
//import model.Competitor;
//import model.Name;
//
//public class CompetitorUI extends javax.swing.JFrame {
//
//    private final CompetitorDAO competitorDao;
//
//    private final String[] columns = new String[]{"Id", "Name", "Level", "Scores", "Overall Score"};
//
//    private final DefaultTableModel model = new DefaultTableModel();
//
////    public CompetitorUI(CompetitorDAO competitorDao) {
////        this.competitorDao = competitorDao;
////        initComponents();
////        setUpTableModel();
////        setUpPaddingInTextField();
////        findAll();
////        setVisible(true);
////        System.out.println("CompetitorUI CompetitorUI is now visible!");
////    }
//
//    public CompetitorUI(CompetitorDAO competitorDao) {
//        this.competitorDao = competitorDao;
//        try {
//            initComponents();
//            setUpTableModel();
//            setUpPaddingInTextField();
//            findAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error initializing UI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void save() throws NumberFormatException, HeadlessException {
//        try {
//            Competitor competitor = getValueFromTextField();
//            int rowCount = competitorDao.addCompetitor(competitor);
//            if (rowCount >= 1) {
//                showMessageDialog("Competitor successfully saved");
//                resetForm();
//                findAll();
//            } else {
//                showMessageDialog("Something went wrong");
//            }
//        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
//            showMessageDialog(ex.getMessage());
//        }
//    }
//
//    private void update() throws NumberFormatException {
//        try {
//            int selectedRow = competitorTable.getSelectedRow();
//            if (selectedRow == -1) {
//                showMessageDialog("Please select a competitor to edit.");
//                return;
//            }
//            int id = (int) competitorTable.getValueAt(selectedRow, 0);
//            Competitor competitor = competitorDao.getCompetitorById(id);
//            if (editOrUpdateButton.getText().equals("Edit")) {
//                editOrUpdateButton.setText("Update");
//                competitorNameTextField.setText(competitor.getName().getFirstName());
//                competitorLastNameTextField.setText(competitor.getName().getLastName());
//                levelTextField.setText(competitor.getLevel());
//                scoreTextField.setText(String.valueOf(competitor.getScores()));
//                countryTextField.setText(String.valueOf(competitor.getCountry()));
//                ageTextField.setText(String.valueOf(competitor.getAge()));
//            } else if (editOrUpdateButton.getText().equals("Update")) {
//                competitor.setName(new Name(competitorNameTextField.getText(), competitorLastNameTextField.getText()));
//                competitor.setAge(Integer.parseInt(ageTextField.getText()));
//                competitor.setCountry(countryTextField.getText());
//                competitor.setScores(scoreTextField.getText());
//                int rowCount = competitorDao.updateCompetitor(competitor, id);
//
//                if (rowCount >= 1) {
//                    showMessageDialog("Competitor sucessfully updated");
//                    resetForm();
//                    findAll();
//                    editOrUpdateButton.setText("Edit");
//                } else {
//                    showMessageDialog("Something went wrong");
//                }
//            }
//        } catch (SQLException | ClassNotFoundException ex) {
//            showMessageDialog(ex.getMessage());
//        }
//    }
//
//    private void remove() {
//        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
//        if (confirm != JOptionPane.YES_OPTION) return;
//        int selectedRow = competitorTable.getSelectedRow();
//        int id = (int) competitorTable.getValueAt(selectedRow, 0);
//        try {
//            Competitor competitor = competitorDao.getCompetitorById(id);
//            if (competitor != null) {
//                int rowCount = competitorDao.deleteCompetitor(id);
//                if (rowCount >= 1) {
//                    showMessageDialog("Competitor sucessfully deleted");
//                    findAll();
//                }
//            }
//        } catch (SQLException | ClassNotFoundException ex) {
//            showMessageDialog(ex.getMessage());
//        }
//    }
//
//    private void findAll() {
//        model.setRowCount(0);
//        try {
//            List<Competitor> competitors = competitorDao.getAllCompetitor();
//            for (Competitor competitor : competitors) {
//                Object[] row = {competitor.getCompetitorId(), competitor.getName().getFullName(), competitor.getLevel(), competitor.getScores(), competitor.getAvgScore()};
//                model.addRow(row);
//            }
//
//        } catch (SQLException | ClassNotFoundException ex) {
//            showMessageDialog(ex.getMessage());
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private void search() {
//        String query = searchTextField.getText();
//        if (query.length() == 0) {
//            findAll();
//        } else {
//            model.setRowCount(0);
//            try {
//                List<Competitor> competitors = competitorDao.searchCompetitor(searchTextField.getText());
//                for (Competitor competitor : competitors) {
//                    Object[] row = {competitor.getCompetitorId(), competitor.getName().getFullName(), competitor.getLevel(), competitor.getScores(), competitor.getAvgScore()};
//                    model.addRow(row);
//                }
//            } catch (SQLException | ClassNotFoundException ex) {
//                showMessageDialog(ex.getMessage());
//            }
//        }
//    }
//
//    private void setUpTableModel() {
//        competitorTable.setModel(model);
//        model.setColumnIdentifiers(columns);
//    }
//
//    private void setUpPaddingInTextField() {
//        setUpBorderFactory(competitorNameTextField);
//        setUpBorderFactory(competitorLastNameTextField);
//        setUpBorderFactory(levelTextField);
//        setUpBorderFactory(scoreTextField);
//        setUpBorderFactory(countryTextField);
//        setUpBorderFactory(ageTextField);
//        setUpBorderFactory(searchTextField);
//    }
//
//    private void setUpBorderFactory(JTextField textField) {
//        textField.setBorder(BorderFactory.createCompoundBorder(
//                textField.getBorder(),
//                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
//    }
//
//    private void showMessageDialog(String message) {
//        JOptionPane.showMessageDialog(null, message);
//    }
//
//    private Competitor getValueFromTextField() throws NumberFormatException {
//        String firstName = competitorNameTextField.getText();
//        String lastName = competitorLastNameTextField.getText();
//        String level = levelTextField.getText();
//        String country = countryTextField.getText();
//        String scores = scoreTextField.getText();
//        int age = Integer.parseInt(ageTextField.getText());
//        return new Competitor(new Name(firstName, lastName), level, country, age, scores);
//    }
//
//    private void resetForm() {
//        competitorNameTextField.setText("");
//        competitorLastNameTextField.setText("");
//        levelTextField.setText("");
//        scoreTextField.setText("");
//        countryTextField.setText("");
//        ageTextField.setText("");
//    }
//
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        inputPanel = new javax.swing.JPanel();
//        userInputLabel = new javax.swing.JPanel();
//        competitorNameLabel = new javax.swing.JLabel();
//        competitorNameTextField = new JTextField();
//        competitorLastNameLabel = new javax.swing.JLabel();
//        competitorLastNameTextField = new JTextField();
//
//        levelLabel = new javax.swing.JLabel();
//        levelTextField = new JTextField();
//
//        scoreLabel = new javax.swing.JLabel();
//        scoreTextField = new JTextField();
//
//        countryLabel = new javax.swing.JLabel();
//        countryTextField = new JTextField();
//        ageLabel = new javax.swing.JLabel();
//        ageTextField = new JTextField();
//
//        buttonPanel = new javax.swing.JPanel();
//        saveButton = new javax.swing.JButton();
//        editOrUpdateButton = new javax.swing.JButton();
//        deleteButton = new javax.swing.JButton();
//        tablePanel = new javax.swing.JPanel();
//        competitorTableScrollPane = new javax.swing.JScrollPane();
//        competitorTable = new javax.swing.JTable();
//        searchPanel = new javax.swing.JPanel();
//        searchTextField = new JTextField();
//        searchLabel = new javax.swing.JLabel();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//
//        inputPanel.setPreferredSize(new java.awt.Dimension(400, 787));
//
//        competitorNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        competitorNameLabel.setText("First Name");
//
//        competitorNameTextField.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//        competitorNameTextField.setHorizontalAlignment(JTextField.LEFT);
//        competitorNameTextField.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                bookNameTextFieldActionPerformed(evt);
//            }
//        });
//
//        competitorLastNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        competitorLastNameLabel.setText("Last Name");
//
//        competitorLastNameTextField.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//        competitorLastNameTextField.setHorizontalAlignment(JTextField.LEFT);
//
//
//        levelLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        levelLabel.setText("Level");
//
//        levelTextField.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//
//
//        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        scoreLabel.setText("Scores");
//
//        scoreTextField.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//
//        countryLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        countryLabel.setText("Country");
//
//        countryTextField.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//
//        ageLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//        ageLabel.setText("Age");
//
//        ageTextField.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//
//        javax.swing.GroupLayout userInputLabelLayout = new javax.swing.GroupLayout(userInputLabel);
//        userInputLabel.setLayout(userInputLabelLayout);
//        userInputLabelLayout.setHorizontalGroup(
//                userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(userInputLabelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                        .addComponent(competitorNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(competitorLastNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(levelLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(countryLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(ageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(scoreLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
//
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                        .addComponent(countryTextField)
//                                        .addComponent(ageTextField)
//                                        .addComponent(competitorNameTextField)
//                                        .addComponent(competitorLastNameTextField)
//                                        .addComponent(levelTextField)
//                                        .addComponent(scoreTextField))
//                                .addContainerGap())
//        );
//        userInputLabelLayout.setVerticalGroup(
//                userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(userInputLabelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(competitorNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(competitorNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(competitorLastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(competitorLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(levelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addGap(18, 18, 18)
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(countryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(countryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addGap(18, 18, 18)
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(ageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(ageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addGap(16, 16, 16)
//                                .addGroup(userInputLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(scoreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addGap(18, 18, 18)
//                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//
//        saveButton.setText("Save");
//        saveButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                saveButtonActionPerformed(evt);
//            }
//        });
//
//        editOrUpdateButton.setText("Edit");
//        editOrUpdateButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editOrUpdateButtonActionPerformed(evt);
//            }
//        });
//
//        deleteButton.setText("Remove");
//        deleteButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                deleteButtonActionPerformed(evt);
//            }
//        });
//
//        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
//        buttonPanel.setLayout(buttonPanelLayout);
//        buttonPanelLayout.setHorizontalGroup(
//                buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(buttonPanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(18, 18, 18)
//                                .addComponent(editOrUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
//                                .addContainerGap())
//        );
//        buttonPanelLayout.setVerticalGroup(
//                buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(buttonPanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(editOrUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//
//        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
//        inputPanel.setLayout(inputPanelLayout);
//        inputPanelLayout.setHorizontalGroup(
//                inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(inputPanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                        .addComponent(userInputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                        .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                                .addContainerGap())
//        );
//        inputPanelLayout.setVerticalGroup(
//                inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(inputPanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(userInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addContainerGap(490, Short.MAX_VALUE))
//        );
//
//        getContentPane().add(inputPanel, java.awt.BorderLayout.LINE_START);
//
//        competitorTable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//        competitorTable.setModel(new DefaultTableModel(
//                new Object [][] {
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null}
//                },
//                new String [] {
//                        "Title 1", "Title 2", "Title 3", "Title 4",  "Title 5"
//                }
//        ));
//        competitorTable.setRowHeight(35);
//        competitorTable.setRowMargin(5);
//        competitorTable.setShowGrid(true);
//        competitorTableScrollPane.setViewportView(competitorTable);
//
//        searchTextField.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
//        searchTextField.setHorizontalAlignment(JTextField.LEFT);
//        searchTextField.setToolTipText("Search here");
//        searchTextField.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                searchTextFieldActionPerformed(evt);
//            }
//        });
//        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyReleased(java.awt.event.KeyEvent evt) {
//                searchTextFieldKeyReleased(evt);
//            }
//        });
//
//        searchLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        searchLabel.setText("Search");
//
//        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
//        searchPanel.setLayout(searchPanelLayout);
//        searchPanelLayout.setHorizontalGroup(
//                searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addComponent(searchTextField)
//                                .addContainerGap())
//        );
//        searchPanelLayout.setVerticalGroup(
//                searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(searchPanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                        .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
//                                        .addComponent(searchTextField))
//                                .addContainerGap())
//        );
//
//        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
//        tablePanel.setLayout(tablePanelLayout);
//        tablePanelLayout.setHorizontalGroup(
//                tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(tablePanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                        .addComponent(competitorTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
//                                        .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                                .addContainerGap())
//        );
//        tablePanelLayout.setVerticalGroup(
//                tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addComponent(competitorTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
//                                .addContainerGap())
//        );
//
//        getContentPane().add(tablePanel, java.awt.BorderLayout.CENTER);
//
//        pack();
//    }// </editor-fold>//GEN-END:initComponents
//
//
//    private void bookNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookNameTextFieldActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_bookNameTextFieldActionPerformed
//
//    private void authorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorTextFieldActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_authorTextFieldActionPerformed
//
//    private void publishedByTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publishedByTextFieldActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_publishedByTextFieldActionPerformed
//
//    private void priceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceTextFieldActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_priceTextFieldActionPerformed
//    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
//        save();
//    }//GEN-LAST:event_saveButtonActionPerformed
//
//    private void editOrUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editOrUpdateButtonActionPerformed
//        update();
//    }//GEN-LAST:event_editOrUpdateButtonActionPerformed
//
//    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
//        remove();
//    }//GEN-LAST:event_deleteButtonActionPerformed
//
//    private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyReleased
//        search();
//    }//GEN-LAST:event_searchTextFieldKeyReleased
//
//    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_searchTextFieldActionPerformed
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JLabel levelLabel;
//    private JTextField levelTextField;
//    private javax.swing.JLabel competitorNameLabel;
//    private JTextField competitorNameTextField;
//    private javax.swing.JLabel competitorLastNameLabel;
//    private JTextField competitorLastNameTextField;
//    private javax.swing.JTable competitorTable;
//    private javax.swing.JScrollPane competitorTableScrollPane;
//    private javax.swing.JPanel buttonPanel;
//    private javax.swing.JButton deleteButton;
//    private javax.swing.JButton editOrUpdateButton;
//    private javax.swing.JPanel inputPanel;
//    private javax.swing.JLabel countryLabel;
//    private JTextField countryTextField;
//    private javax.swing.JLabel ageLabel;
//    private JTextField ageTextField;
//    private javax.swing.JLabel scoreLabel;
//    private JTextField scoreTextField;
//    private javax.swing.JButton saveButton;
//    private javax.swing.JLabel searchLabel;
//    private javax.swing.JPanel searchPanel;
//    private JTextField searchTextField;
//    private javax.swing.JPanel tablePanel;
//    private javax.swing.JPanel userInputLabel;
//    // End of variables declaration//GEN-END:variables
//}
