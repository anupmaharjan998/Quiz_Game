package DAO.Implementation;

import DAO.QuestionDAO;
import Model.Question;
import Connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAOImp implements QuestionDAO {
    @Override
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();


        try (Connection conn = DBConnection.getConnection()){
            String query = "SELECT q.*, s.level_name FROM Questions q JOIN Levels s ON q.level_id = s.level_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Question question = new Question(
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

    @Override
    public Question getQuestionsById(String id) {
        String query = "SELECT q.*, s.level_name FROM Questions q JOIN Levels s ON q.level_id = s.level_id WHERE q.ques_id = ?";
        Question question = null;

        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                question = new Question(
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

    @Override
    public List<Question> getQuestionsByDifficulty(String levelId) {
        String query = "SELECT * FROM Questions WHERE level_id = ? ORDER BY RAND() LIMIT 5";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()){
             PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, levelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question question = new Question(
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

    @Override
    public boolean addQuestion(Question question) {

        try (Connection conn = DBConnection.getConnection()){
            String query = "INSERT INTO Questions (ques_id, question, options, correct, level_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
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

    @Override
    public boolean updateQuestion(Question question) {

        try (Connection conn = DBConnection.getConnection()){
            String query = "UPDATE Questions SET question = ?, options = ?, correct = ?, level_id = ? WHERE ques_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, question.getQuestion());
            stmt.setString(2, question.getOptions());
            stmt.setString(3, question.getCorrect());
            stmt.setString(4, question.getLevelId());
            stmt.setString(5, question.getQuesId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteQuestion(String quesId) {

        try (Connection conn = DBConnection.getConnection()){
            String query = "DELETE FROM Questions WHERE ques_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, quesId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
