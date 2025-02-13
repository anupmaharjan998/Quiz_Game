package np.edu.herald.quizzapp.model;

import java.util.UUID;

/**
 * Represents the scores associated with a specific user and level.
 * Contains the score's ID, user ID, level ID, and the score value.
 */
public class Scores {

    private String scoreId;
    private String userId;
    private String levelId;
    private String levelName;
    private String scores;

    /**
     * Constructs a Scores object with the specified details.
     *
     * @param userId the user ID
     * @param levelId the level ID
     * @param scores the scores achieved by the user
     */
    public Scores(String userId, String levelId, String scores) {
        this.scoreId = UUID.randomUUID().toString();
        this.userId = userId;
        this.levelId = levelId;
        this.scores = scores;
    }

    /**
     * Gets the score ID.
     *
     * @return the score ID
     */
    public String getScoreId() {
        return scoreId;
    }

    /**
     * Sets the score ID.
     *
     * @param scoreId the new score ID
     */
    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    /**
     * Gets the user ID associated with the score.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the score.
     *
     * @param userId the new user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the level ID associated with the score.
     *
     * @return the level ID
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * Sets the level ID associated with the score.
     *
     * @param levelId the new level ID
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    /**
     * Gets the score value.
     *
     * @return the score value
     */
    public String getScores() {
        return scores;
    }

    /**
     * Sets the score value.
     *
     * @param scores the new score value
     */
    public void setScores(String scores) {
        this.scores = scores;
    }

    /**
     * Gets the level name associated with the score.
     *
     * @return the level name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Sets the level name associated with the score.
     *
     * @param levelName the new level name
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
