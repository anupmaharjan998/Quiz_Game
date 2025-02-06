package DAO;

import Model.Question;
import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestions();
    List<Question> getQuestionsByDifficulty(String level_id);
    boolean addQuestion(Question question);
    boolean updateQuestion(Question question);
    boolean deleteQuestion(String quesId);
}