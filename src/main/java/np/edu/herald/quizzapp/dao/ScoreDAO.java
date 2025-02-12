package np.edu.herald.quizzapp.dao;

import np.edu.herald.quizzapp.model.Scores;

import java.util.List;

/**
 * Interface for operations related to player scores.
 */
public interface ScoreDAO {

    /**
     * Retrieves the score of a user for a specific level.
     *
     * @param userId the user ID for which the score is fetched.
     * @param levelId the level ID for which the score is fetched.
     * @return the {@link Scores} object containing the user's score for the specified level.
     */
    Scores getScore(String userId, String levelId);

    /**
     * Retrieves all scores of a user.
     *
     * @param userId the user ID whose scores are to be fetched.
     * @return a list of {@link Scores} for the user.
     */
    List<Scores> getScoreByUserID(String userId);

    /**
     * Adds a new score for a user.
     *
     * @param score the {@link Scores} object containing the score to be added.
     */
    void addScore(Scores score);

    /**
     * Updates an existing score for a user.
     *
     * @param score the {@link Scores} object containing the updated score information.
     */
    void updateScore(Scores score);
}
