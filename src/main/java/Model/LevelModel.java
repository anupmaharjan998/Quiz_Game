package Model;

public class LevelModel {
    private String level_id;
    private String level_name;

    public LevelModel(String levelId, String levelName) {
        this.level_id = levelId;
        this.level_name = levelName;
    }
    public String getLevelId() {
        return level_id;
    }
    public void setLevelId(String levelId) {
        this.level_id = levelId;
    }

    public String getLevelName() {
        return level_name;
    }
    public  void setLevelName(String levelName) {
        this.level_name = levelName;
    }

    @Override
    public String toString() {
        return level_name;
    }

}
