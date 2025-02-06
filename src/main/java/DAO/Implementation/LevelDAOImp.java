package DAO.Implementation;

import DAO.LevelDAO;
import Model.LevelModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Connection.DBConnection;
import java.util.List;

public class LevelDAOImp implements LevelDAO {
    @Override
    public List<LevelModel> getAllLevels() {
        List<LevelModel> levels = new ArrayList<>();


        try (Connection conn = DBConnection.getConnection()){
            String query = "SELECT * FROM Levels";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                LevelModel level = new LevelModel(
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
