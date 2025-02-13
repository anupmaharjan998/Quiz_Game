package np.edu.herald.quizzapp.model;

/**
 * Represents a competitor in the quiz game.
 * Contains the competitor's ID, user ID, and score ID.
 */
public class Competitors {

    private int compId;
    private String userId;
    private String scoreId;

    /**
     * Constructs a Competitor with the specified ID, user ID, and score ID.
     *
     * @param compId the competitor's ID
     * @param userId the ID of the user
     * @param scoreId the ID of the score associated with the competitor
     */
    public Competitors(int compId, String userId, String scoreId) {
        this.compId = compId;
        this.userId = userId;
        this.scoreId = scoreId;
    }

    /**
     * Gets the competitor's ID.
     *
     * @return the competitor's ID
     */
    public int getCompetitorId() {
        return compId;
    }

    /**
     * Sets the competitor's ID.
     *
     * @param compId the new competitor's ID
     */
    public void setCompetitorId(int compId) {
        this.compId = compId;
    }

    /**
     * Gets the user ID of the competitor.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID of the competitor.
     *
     * @param userId the new user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the score ID of the competitor.
     *
     * @return the score ID
     */
    public String getScoreId() {
        return scoreId;
    }

    /**
     * Sets the score ID of the competitor.
     *
     * @param scoreId the new score ID
     */
    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }
}
