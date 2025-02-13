package np.edu.herald.quizzapp.dao;

import np.edu.herald.quizzapp.model.Levels;

import java.util.List;

/**
 * Interface for operations related to quiz levels.
 */
public interface LevelDAO {

    /**
     * Retrieves a list of all available levels.
     *
     * @return a list of {@link Levels} containing information about each level.
     */
    List<Levels> getAllLevels();

}
