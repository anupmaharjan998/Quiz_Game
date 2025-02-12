package np.edu.herald.quizzapp.model;

import java.util.UUID;

/**
 * Represents a question in the quiz game.
 * Contains the question's ID, question text, options, correct answer, and level ID.
 */
public class Questions {

    private String quesId;
    private String question;
    private String options;
    private String correct;
    private String levelId;
    private String levelName;

    /**
     * Constructs a Question with the specified details.
     *
     * @param question the question text
     * @param options the options for the question
     * @param correct the correct answer
     * @param levelId the level ID for the question
     */
    public Questions(String question, String options, String correct, String levelId) {
        this.quesId = UUID.randomUUID().toString(); // Generate a unique ID
        this.question = question;
        this.options = options;
        this.correct = correct;
        this.levelId = levelId;
    }

    /**
     * Gets the question ID.
     *
     * @return the question ID
     */
    public String getQuesId() {
        return quesId;
    }

    /**
     * Sets the question ID.
     *
     * @param quesId the new question ID
     */
    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    /**
     * Gets the question text.
     *
     * @return the question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question text.
     *
     * @param question the new question text
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the options for the question.
     *
     * @return the options
     */
    public String getOptions() {
        return options;
    }

    /**
     * Sets the options for the question.
     *
     * @param options the new options
     */
    public void setOptions(String options) {
        this.options = options;
    }

    /**
     * Gets the correct answer for the question.
     *
     * @return the correct answer
     */
    public String getCorrect() {
        return correct;
    }

    /**
     * Sets the correct answer for the question.
     *
     * @param correct the new correct answer
     */
    public void setCorrect(String correct) {
        this.correct = correct;
    }

    /**
     * Gets the level ID for the question.
     *
     * @return the level ID
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * Sets the level ID for the question.
     *
     * @param levelId the new level ID
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    /**
     * Gets the level name for the question.
     *
     * @return the level name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Sets the level name for the question.
     *
     * @param levelName the new level name
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
