package DAO;

import Model.Question;
import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestions();
    Question getQuestionsById(String id);
    List<Question> getQuestionsByDifficulty(String level_id);
    boolean addQuestion(Question question);
    boolean updateQuestion(Question question);
    boolean deleteQuestion(String quesId);
}