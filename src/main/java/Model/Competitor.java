package Model;

import java.util.UUID;

public class Competitor {
    private int comp_id;
    private String user_id; // Use the Name model
    private String score_id;

    public Competitor(int comp_id, String user_id, String score_id) {
        this.comp_id = comp_id;
        this.user_id = user_id;
        this.score_id = score_id;
    }

    // Getters and Setters
    public int getCompetitorId() {
        return comp_id;
    }

    public void setCompetitorId(int comp_id) {
        this.comp_id = comp_id;
    }

    public String getUserId() {
        return user_id;
    }
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getScoreId() {
        return score_id;
    }

    public void setScoreId(String score_id) {
        this.score_id = score_id;
    }

//    @Override
//    public String toString() {
//        return "Player{" +
//                "id=" + id +
//                ", name=" + name +
//                ", level='" + level + '\'' +
//                ", score=" + score +
//                '}';
//    }
}