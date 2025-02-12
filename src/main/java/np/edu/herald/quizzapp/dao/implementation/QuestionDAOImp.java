package np.edu.herald.quizzapp.dao.implementation;

import np.edu.herald.quizzapp.dao.QuestionDAO;
import np.edu.herald.quizzapp.model.Questions;
import np.edu.herald.quizzapp.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link QuestionDAO} interface for performing CRUD operations on questions in the database.
 */
public class QuestionDAOImp implements QuestionDAO {

    /**
     * Retrieves all questions from the database along with their corresponding level names.
     *
     * @return a list of all {@link Questions} in the database.
     */
    @Override
    public List<Questions> getAllQuestions() {

        List<Questions> questions = new ArrayList<>();
        String query = "select q.*, s.level_name from Questions q join Levels s on q.level_id = s.level_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Questions question = new Questions(
                        rs.getString("question"),
                        rs.getString("options"),
                        rs.getString("correct"),
                        rs.getString("level_id")
                );
                question.setQuesId(rs.getString("ques_id"));
                question.setLevelName(rs.getString("level_name"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * Retrieves a question by its ID from the database.
     *
     * @param id the unique ID of the question to retrieve.
     * @return the {@link Questions} object with the specified ID, or null if no question is found.
     */
    @Override
    public Questions getQuestionsById(String id) {

        String query = "select q.*, s.level_name from Questions q join Levels s on q.level_id = s.level_id where q.ques_id = ?";
        Questions question = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                question = new Questions(
                        rs.getString("question"),
                        rs.getString("options"),
                        rs.getString("correct"),
                        rs.getString("level_id")
                );
                question.setQuesId(rs.getString("ques_id"));
                question.setLevelName(rs.getString("level_name"));
                return question;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    /**
     * Retrieves a list of questions filtered by difficulty level.
     *
     * @param levelId the level ID to filter the questions by.
     * @return a list of {@link Questions} for the specified level, ordered randomly.
     */
    @Override
    public List<Questions> getQuestionsByDifficulty(String levelId) {
        String query = "select * from Questions where level_id = ? order by RAND() LIMIT 5";
        List<Questions> questions = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, levelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Questions question = new Questions(
                        rs.getString("question"),
                        rs.getString("options"),
                        rs.getString("correct"),
                        rs.getString("level_id")
                );
                question.setQuesId(rs.getString("ques_id"));
                questions.add(question);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * Adds a new question to the database.
     *
     * @param question the {@link Questions} object to add to the database.
     * @return true if the question was added successfully, false otherwise.
     */
    @Override
    public boolean addQuestion(Questions question) {
        String query = "INSERT INTO Questions (ques_id, question, options, correct, level_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, question.getQuesId());
            stmt.setString(2, question.getQuestion());
            stmt.setString(3, question.getOptions());
            stmt.setString(4, question.getCorrect());
            stmt.setString(5, question.getLevelId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing question in the database.
     *
     * @param question the {@link Questions} object containing updated information.
     */
    @Override
    public void updateQuestion(Questions question) {
        String query = "UPDATE Questions SET question = ?, options = ?, correct = ?, level_id = ? WHERE ques_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, question.getQuestion());
            stmt.setString(2, question.getOptions());
            stmt.setString(3, question.getCorrect());
            stmt.setString(4, question.getLevelId());
            stmt.setString(5, question.getQuesId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a question from the database by its ID.
     *
     * @param quesId the unique ID of the question to delete.
     */
    @Override
    public void deleteQuestion(String quesId) {
        String query = "DELETE FROM Questions WHERE ques_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);){

            stmt.setString(1, quesId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
