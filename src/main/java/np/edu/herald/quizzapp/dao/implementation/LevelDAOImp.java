package np.edu.herald.quizzapp.dao.implementation;

import np.edu.herald.quizzapp.dao.LevelDAO;
import np.edu.herald.quizzapp.model.Levels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import np.edu.herald.quizzapp.connection.DBConnection;
import java.util.List;

/**
 * Implementation of the LevelDAO interface.
 * Provides functionality to retrieve all quiz levels from the database.
 */
public class LevelDAOImp implements LevelDAO {

    /**
     * Retrieves all levels from the Levels table in the database.
     *
     * @return A list of Levels objects representing all available levels.
     */
    @Override
    public List<Levels> getAllLevels() {
        List<Levels> levels = new ArrayList<>();
        String query = "select * from Levels";


        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();){

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Levels level = new Levels(
                        rs.getString("level_id"),
                        rs.getString("level_name")
                );
                levels.add(level);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return levels;
    }
}
