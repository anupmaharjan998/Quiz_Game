package np.edu.herald.quizzapp.dao;

import np.edu.herald.quizzapp.model.Questions;

import java.util.List;

/**
 * Interface for operations related to quiz questions.
 */
public interface QuestionDAO {

    /**
     * Retrieves a list of all questions in the database.
     *
     * @return a list of {@link Questions}.
     */
    List<Questions> getAllQuestions();

    /**
     * Retrieves a question by its unique ID.
     *
     * @param id the unique ID of the question.
     * @return the {@link Questions} object with the specified ID.
     */
    Questions getQuestionsById(String id);

    /**
     * Retrieves a list of questions filtered by difficulty level.
     *
     * @param levelId the level ID used to filter the questions.
     * @return a list of {@link Questions} for the specified level.
     */
    List<Questions> getQuestionsByDifficulty(String levelId);

    /**
     * Adds a new question to the database.
     *
     * @param question the {@link Questions} object to add.
     * @return true if the question is added successfully, false otherwise.
     */
    boolean addQuestion(Questions question);

    /**
     * Updates an existing question in the database.
     *
     * @param question the {@link Questions} object containing updated information.
     */
    void updateQuestion(Questions question);

    /**
     * Deletes a question from the database by its unique ID.
     *
     * @param quesId the unique ID of the question to delete.
     */
    void deleteQuestion(String quesId);
}
