package np.edu.herald.quizzapp.model;

/**
 * Represents a quiz level in the game.
 * Contains the level's ID and name.
 */
public class Levels {

    private String levelId;
    private String levelName;

    /**
     * Constructs a Level with the specified ID and name.
     *
     * @param levelId the level's ID
     * @param levelName the level's name
     */
    public Levels(String levelId, String levelName) {
        this.levelId = levelId;
        this.levelName = levelName;
    }

    /**
     * Gets the level's ID.
     *
     * @return the level's ID
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * Sets the level's ID.
     *
     * @param levelId the new level ID
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    /**
     * Gets the level's name.
     *
     * @return the level's name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Sets the level's name.
     *
     * @param levelName the new level name
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Returns the level's name as a string.
     *
     * @return the level's name
     */
    @Override
    public String toString() {
        return levelName;
    }
}

