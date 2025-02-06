package Model;

import java.util.UUID;

public class ScoreModel {
    private String score_id;
    private String user_id;
    private String level_id;
    private String level_name;
    private String scores;

    public ScoreModel(String user_id, String level_id, String scores) {
        this.score_id = UUID.randomUUID().toString();;
        this.user_id = user_id;
        this.level_id = level_id;
        this.scores = scores;
    }

    public String getScoreId() {
        return score_id;
    }

    public void setScoreId(String score_id) {
        this.score_id = score_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getLevelId() {
        return level_id;
    }
    public void setLevelId(String level_id) {
        this.level_id = level_id;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getLevelName() {
        return level_name;
    }

    public void setLevelName(String level_name) {
        this.level_name = level_name;
    }
}
