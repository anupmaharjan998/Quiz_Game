package np.edu.herald.quizzapp.model;

/**
 * Represents a player in the quiz game.
 * Contains the player's competitor ID, name, level, scores, and other statistics.
 */
public class Players {

    private String competitorId;
    private String name;
    private String levelName;
    private String levelId;
    private String scores;
    private int totalQuizzes;
    private float avgScore;

    /**
     * Constructs a Player with the specified details.
     *
     * @param competitorId the competitor's ID
     * @param name the name of the player
     * @param levelName the level name
     * @param levelId the level ID
     * @param scores the scores
     * @param totalQuizzes the total quizzes played by the player
     * @param avgScore the average score of the player
     */
    public Players(String competitorId, String name, String levelName, String levelId, String scores, int totalQuizzes, float avgScore) {
        this.competitorId = competitorId;
        this.name = name;
        this.levelName = levelName;
        this.levelId = levelId;
        this.scores = scores;
        this.totalQuizzes = totalQuizzes;
        this.avgScore = avgScore;
    }

    /**
     * Gets the competitor's ID.
     *
     * @return the competitor's ID
     */
    public String getCompetitorId() {
        return competitorId;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of the level the player is in.
     *
     * @return the level name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Gets the total quizzes played by the player.
     *
     * @return the total number of quizzes
     */
    public int getTotalQuizzes() {
        return totalQuizzes;
    }

    /**
     * Gets the scores of the player.
     *
     * @return the player's scores
     */
    public String getScores() {
        return scores;
    }

    /**
     * Gets the average score of the player.
     *
     * @return the average score
     */
    public float getAvgScore() {
        return avgScore;
    }
}
