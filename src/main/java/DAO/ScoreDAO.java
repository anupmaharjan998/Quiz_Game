package DAO;

import Model.ScoreModel;

import java.util.List;

public interface ScoreDAO {
    ScoreModel getScore(String user_id, String level_id);
    List<ScoreModel> getScoreByUserID(String user_id);
    boolean addScore(ScoreModel score);
    boolean updateScore(ScoreModel score);
}
